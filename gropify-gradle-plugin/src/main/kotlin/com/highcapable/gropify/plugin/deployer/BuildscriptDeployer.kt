/*
 * Gropify - A type-safe and modern properties plugin for Gradle.
 * Copyright (C) 2019 HighCapable
 * https://github.com/HighCapable/Gropify
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2025/10/17.
 */
package com.highcapable.gropify.plugin.deployer

import com.highcapable.gropify.debug.Logger
import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.gradle.api.entity.ProjectDescriptor
import com.highcapable.gropify.gradle.api.extension.addDependencyToBuildscript
import com.highcapable.gropify.gradle.api.extension.getOrCreate
import com.highcapable.gropify.gradle.api.extension.hasExtension
import com.highcapable.gropify.gradle.api.extension.toClassOrNull
import com.highcapable.gropify.plugin.DefaultDeployer
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.compiler.extension.toCompileSpec
import com.highcapable.gropify.plugin.config.extension.from
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.deployer.proxy.Deployer
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import com.highcapable.gropify.plugin.generator.BuildscriptGenerator
import com.highcapable.gropify.plugin.generator.extension.PropertyMap
import com.highcapable.gropify.plugin.repository.BuildscriptAccessorsRepository
import com.highcapable.gropify.utils.extension.calculateSha256
import com.highcapable.gropify.utils.extension.camelcase
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import kotlin.properties.Delegates

/**
 * Buildscript deployer.
 */
internal class BuildscriptDeployer(private val _config: () -> GropifyConfig) : Deployer {

    private val config get() = _config()

    private val generator = BuildscriptGenerator()

    private var accessorsDir by Delegates.notNull<File>()
    private val accessorsDependency = Dependency(Gropify.GROUP_NAME, GropifyConfig.ACCESSORS_NAME, Gropify.VERSION)
    private val accessorsRepository get() = BuildscriptAccessorsRepository(accessorsDir, accessorsDependency)

    private var cachedSettingsProperties = mutableListOf<PropertyMap>()

    override fun init(settings: Settings, configModified: Boolean) {
        accessorsDir = generatedBuildscriptAccessorsDir(settings)

        val allConfig = mutableListOf<GropifyConfig.BuildscriptGenerateConfig>()
        val allProperties = mutableListOf<PropertyMap>()

        if (config.global.buildscript.isEnabled) {
            val map = DefaultDeployer.generateMap(config.global.buildscript, ProjectDescriptor.create(settings))
            allProperties.add(map)
            allConfig.add(config.global.buildscript)
        }

        config.projects.forEach { (name, subConfig) ->
            if (!subConfig.buildscript.isEnabled) return@forEach

            val map = DefaultDeployer.generateMap(subConfig.buildscript, ProjectDescriptor.create(settings, name))
            allProperties.add(map)
            allConfig.add(subConfig.buildscript)
        }

        val compileSpec = generator.build(allConfig, allProperties)
            .toCompileSpec(generator.compileStubFiles)
        val expectedClassNames = allConfig.map { generator.propertiesClass(it.name) }
        val fingerprint = createFingerprint(allConfig, allProperties)
        val repository = accessorsRepository

        repository.withLock {
            if (!configModified &&
                allProperties == cachedSettingsProperties &&
                repository.isReady(fingerprint, expectedClassNames)
            ) return@withLock

            if (repository.isReady(fingerprint, expectedClassNames)) {
                cachedSettingsProperties = allProperties
                return@withLock
            }

            cachedSettingsProperties = allProperties
            repository.publish(compileSpec, fingerprint, expectedClassNames)
        }
    }

    override fun resolve(rootProject: Project, configModified: Boolean) {
        var hasReadyBuildscriptAccessors = false
        val repository = accessorsRepository
        repository.withLock {
            hasReadyBuildscriptAccessors = repository.isReady()
        }
        if (!hasReadyBuildscriptAccessors) return

        Logger.debug("Resolving classpath for $accessorsDependency")
        rootProject.addDependencyToBuildscript(accessorsDir.absolutePath, accessorsDependency)
    }

    override fun deploy(rootProject: Project, configModified: Boolean) {
        fun Project.deploy() {
            val config = config.from(this).buildscript
            if (!isEnabled(config)) return

            val className = generator.propertiesClass(config.name)
            val accessorsClass = className.toClassOrNull(this) ?: throw RuntimeException(
                """
                  Generated class "$className" not found, stop loading $this.
                  Please check whether the initialization process is interrupted and re-run Gradle sync.
                  If this doesn't work, please manually delete the entire "${accessorsDir.absolutePath}" directory.
                """.trimIndent()
            )

            val extensionName = config.extensionName.camelcase()

            getOrCreate(extensionName, accessorsClass)

            if (hasExtension(extensionName))
                Logger.debug("Created buildscript extension \"$extensionName\" for $this")
            else Logger.warn("Failed to create buildscript extension \"$extensionName\" for $this")
        }

        rootProject.deploy()
        rootProject.subprojects.forEach { it.deploy() }
    }

    private fun generatedBuildscriptAccessorsDir(settings: Settings) =
        settings.rootDir.resolve(".gradle")
            .resolve(GropifyConfigureExtension.NAME)
            .resolve(GropifyConfig.ARTIFACTS_NAME)
            .apply { mkdirs() }

    private fun createFingerprint(
        allConfig: List<GropifyConfig.BuildscriptGenerateConfig>,
        allProperties: List<PropertyMap>
    ) = buildString {
        appendLine(Gropify.VERSION)
        allConfig.zip(allProperties).forEach { (config, properties) ->
            appendLine(config.name)
            appendLine(config.extensionName)
            appendLine(config.isEnabled)
            properties.toSortedMap().forEach { (key, value) ->
                appendLine(key)
                appendLine(value.raw)
                appendLine(value.codeValue)
                appendLine(value.type.qualifiedName)
            }
        }
    }.calculateSha256()

    private fun isEnabled(config: GropifyConfig.BuildscriptGenerateConfig): Boolean {
        if (!config.isEnabled) Logger.debug("Config buildscript is disabled in ${config.name}, skipping deployment process.")
        return config.isEnabled
    }
}