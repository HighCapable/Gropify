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

import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.gradle.api.entity.ProjectDescriptor
import com.highcapable.gropify.gradle.api.extension.addDependencyToBuildscript
import com.highcapable.gropify.gradle.api.extension.getOrCreate
import com.highcapable.gropify.gradle.api.extension.toClassOrNull
import com.highcapable.gropify.plugin.DefaultDeployer
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.compiler.extension.compile
import com.highcapable.gropify.plugin.config.extension.from
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.deployer.proxy.Deployer
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import com.highcapable.gropify.plugin.generator.BuildscriptGenerator
import com.highcapable.gropify.plugin.generator.extension.PropertyMap
import com.highcapable.gropify.utils.extension.camelcase
import com.highcapable.gropify.utils.extension.isEmpty
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import kotlin.properties.Delegates

/**
 * Buildscript deployer.
 */
internal class BuildscriptDeployer(private val _config: () -> GropifyConfig) : Deployer {

    private val config get() = _config()

    private val buildscriptGenerator = BuildscriptGenerator()

    private var buildscriptAccessorsDir by Delegates.notNull<File>()
    private val buildscriptAccessorsDependency = Dependency(Gropify.GROUP_NAME, GropifyConfig.ACCESSORS_NAME, Gropify.VERSION)

    private var cachedSettingsProperties = mutableListOf<PropertyMap>()

    override fun init(settings: Settings, configModified: Boolean) {
        buildscriptAccessorsDir = generatedBuildscriptAccessorsDir(settings)

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

        if (!configModified &&
            allProperties == cachedSettingsProperties &&
            !buildscriptAccessorsDir.resolve(buildscriptAccessorsDependency.relativePath).isEmpty()
        ) return

        cachedSettingsProperties = allProperties
        buildscriptGenerator.build(allConfig, allProperties).compile(
            buildscriptAccessorsDependency,
            buildscriptAccessorsDir.absolutePath,
            buildscriptGenerator.compileStubFiles
        )
    }

    override fun resolve(rootProject: Project, configModified: Boolean) {
        if (!buildscriptAccessorsDir.resolve(buildscriptAccessorsDependency.relativePath).isEmpty())
            rootProject.addDependencyToBuildscript(buildscriptAccessorsDir.absolutePath, buildscriptAccessorsDependency)
    }

    override fun deploy(rootProject: Project, configModified: Boolean) {
        fun Project.deploy() {
            val config = config.from(this).buildscript
            if (!config.isEnabled) return

            val className = buildscriptGenerator.propertiesClass(config.name)
            val accessorsClass = className.toClassOrNull(this) ?: throw RuntimeException(
                """
                  Generated class "$className" not found, stop loading $this.
                  Please check whether the initialization process is interrupted and re-run Gradle sync.
                  If this doesn't work, please manually delete the entire "${buildscriptAccessorsDir.absolutePath}" directory.
                """.trimIndent()
            )

            getOrCreate(config.extensionName.camelcase(), accessorsClass)
        }

        rootProject.deploy()
        rootProject.subprojects.forEach { it.deploy() }
    }

    private fun generatedBuildscriptAccessorsDir(settings: Settings) =
        settings.rootDir.resolve(".gradle")
            .resolve(GropifyConfigureExtension.NAME)
            .resolve(GropifyConfig.ARTIFACTS_NAME)
            .apply { mkdirs() }
}