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
import com.highcapable.gropify.debug.error
import com.highcapable.gropify.gradle.api.entity.ProjectDescriptor
import com.highcapable.gropify.gradle.api.extension.getFullName
import com.highcapable.gropify.gradle.api.extension.getOrNull
import com.highcapable.gropify.plugin.DefaultDeployer.generateMap
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.config.extension.from
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.deployer.extension.ExtensionName
import com.highcapable.gropify.plugin.deployer.extension.ProjectType
import com.highcapable.gropify.plugin.deployer.extension.resolveType
import com.highcapable.gropify.plugin.deployer.proxy.Deployer
import com.highcapable.gropify.plugin.generator.SourceCodeGenerator
import com.highcapable.gropify.plugin.generator.config.GenerateConfig
import com.highcapable.gropify.plugin.generator.config.SourceCodeSpec
import com.highcapable.gropify.plugin.generator.extension.PropertyMap
import com.highcapable.gropify.plugin.helper.AndroidProjectHelper
import com.highcapable.gropify.utils.extension.flatted
import com.highcapable.gropify.utils.extension.isEmpty
import com.highcapable.gropify.utils.extension.upperCamelcase
import com.highcapable.kavaref.KavaRef.Companion.asResolver
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import kotlin.collections.set

/**
 * Source code deployer.
 */
internal class SourceCodeDeployer(private val _config: () -> GropifyConfig) : Deployer {

    private val config get() = _config()

    private val debugMode get() = config.debugMode

    private val sourceCodeGenerator = SourceCodeGenerator()

    private var cachedProjectProperties = mutableMapOf<String, PropertyMap>()

    override fun init(settings: Settings, configModified: Boolean) {
        // No initialization required.
    }

    override fun resolve(rootProject: Project, configModified: Boolean) {
        // No resolution required.
    }

    override fun deploy(rootProject: Project, configModified: Boolean) {
        fun Project.generate() {
            val projectType = resolveType()

            val config = config.from(this).let {
                when (projectType) {
                    ProjectType.Android -> it.android
                    ProjectType.Kotlin, ProjectType.Java -> it.jvm
                    ProjectType.KMP -> it.kmp
                    else -> null
                } ?: return
            }

            val sourceCodeType = decideSourceCodeType(config, projectType)
            val generateDirPath = resolveGenerateDirPath(config)

            if (!isEnabled(config)) return

            val outputDir = file(generateDirPath)
            val properties = generateMap(config, ProjectDescriptor.create(project = this))

            if (!configModified && properties == cachedProjectProperties[getFullName()] && !outputDir.isEmpty()) {
                if (config.isEnabled) configureSourceSets(project = this)
                return
            }

            outputDir.apply { if (exists()) deleteRecursively() }

            // The directory will be re-created every time.
            outputDir.mkdirs()

            cachedProjectProperties[getFullName()] = properties

            val packageName = generatedPackageName(config)
            val className = generatedClassName(config)

            val generateConfig = GenerateConfig(packageName, className)

            sourceCodeGenerator.build(projectType, config, generateConfig, properties).let { generator ->
                generator.first { it.type == sourceCodeType }
            }.writeTo(outputDir)
            configureSourceSets(project = this)
        }

        rootProject.generate()
        rootProject.subprojects.forEach {
            it.afterEvaluate {
                generate()
            }
        }
    }

    private fun configureSourceSets(project: Project) {
        val projectType = project.resolveType()

        val config = config.from(project).let {
            when (projectType) {
                ProjectType.Android -> it.android
                ProjectType.Kotlin, ProjectType.Java -> it.jvm
                ProjectType.KMP -> it.kmp
                else -> null
            } ?: return
        }

        Logger.debug("Configuring source sets in project '${project.getFullName()}' (${projectType.name}).")

        val sourceCodeType = decideSourceCodeType(config, projectType)
        val resolveSourceCodeType = if (projectType == ProjectType.Java) SourceCodeSpec.Type.Java else sourceCodeType
        val generateDirPath = resolveGenerateDirPath(config)

        val androidExtension = project.getOrNull(ExtensionName.ANDROID)
        val kotlinExtension = project.getOrNull(ExtensionName.KOTLIN)
        val javaExtension = project.getOrNull(ExtensionName.JAVA)

        val extension = when (projectType) {
            ProjectType.Android -> androidExtension
            ProjectType.Kotlin -> if (sourceCodeType == SourceCodeSpec.Type.Kotlin) kotlinExtension else javaExtension
            ProjectType.Java -> javaExtension
            ProjectType.KMP -> kotlinExtension
            else -> return
        } ?: return Logger.debug("No supportable extension found for configuring source sets in $project")

        Logger.debug("Found $extension: ${extension.javaClass}")

        val collection = extension.asResolver().optional(!debugMode).firstMethodOrNull {
            name = "getSourceSets"
        }?.invokeQuietly<Iterable<*>>()

        val sourceSet = collection?.firstOrNull {
            it?.asResolver()?.optional(!debugMode)?.firstMethodOrNull {
                name = "getName"
            }?.invokeQuietly<String>() == config.sourceSetName
        } ?: return Logger.warn(
            "Could not found source set \"${config.sourceSetName}\" in project '${project.getFullName()}' ($projectType)."
        )

        val directorySet = sourceSet.asResolver().optional(!debugMode).firstMethodOrNull {
            name = when (resolveSourceCodeType) {
                SourceCodeSpec.Type.Java -> "getJava"
                SourceCodeSpec.Type.Kotlin -> "getKotlin"
            }
            superclass()
        }?.invokeQuietly()
        val srcDirs = directorySet?.asResolver()?.optional(!debugMode)?.firstMethodOrNull {
            name = "getSrcDirs"
        }?.invokeQuietly<Set<*>>()

        Logger.debug("Deploying generated source to \"$generateDirPath\".")

        val alreadyAdded = srcDirs?.any { it is File && it.canonicalPath.endsWith(generateDirPath) } == true
        if (!alreadyAdded) {
            val resolver = directorySet?.asResolver()?.optional(!debugMode)?.firstMethodOrNull {
                name = "srcDir"
                parameters(Any::class)
                superclass()
            }
            resolver?.invokeQuietly(generateDirPath) ?: Logger.error(
                "Project '${project.getFullName()}' source sets deployed failed, method \"srcDir\" maybe failed during the processing."
            )
        } else Logger.debug("Source directory \"$generateDirPath\" already added to source set \"${config.sourceSetName}\", skipping.")
    }

    private fun decideSourceCodeType(config: GropifyConfig.CommonCodeGenerateConfig, type: ProjectType) = when (type) {
        ProjectType.Android, ProjectType.Kotlin ->
            if (config is GropifyConfig.JvmGenerateConfig && !config.useKotlin)
                SourceCodeSpec.Type.Java
            else SourceCodeSpec.Type.Kotlin
        ProjectType.Java -> SourceCodeSpec.Type.Java
        ProjectType.KMP -> SourceCodeSpec.Type.Kotlin
        else -> Gropify.error("Unsupported project type for source code generation.")
    }

    private fun resolveGenerateDirPath(config: GropifyConfig.CommonCodeGenerateConfig) = "${config.generateDirPath}/src/main"

    private fun Project.generatedPackageName(config: GropifyConfig.CommonCodeGenerateConfig): String {
        val packageName = config.packageName.ifBlank { null }
            ?: AndroidProjectHelper.getNamespace(this)
            ?: group.toString().ifBlank { null }
            ?: "${GropifyConfig.DEFAULT_PACKAGE_NAME}.${getFullName(useColon = false).replace(":", "").flatted()}"

        return if (config.isIsolationEnabled) "$packageName.generated" else packageName
    }

    private fun Project.generatedClassName(config: GropifyConfig.CommonCodeGenerateConfig): String {
        val className = config.className.ifBlank { null }
            ?: getFullName(useColon = false).replace(":", "_").upperCamelcase().ifBlank { null }
            ?: "Undefined"

        return "${className.upperCamelcase()}Properties"
    }

    private fun isEnabled(config: GropifyConfig.SourceCodeGenerateConfig): Boolean {
        if (!config.isEnabled) Logger.debug("Config ${
            when (config){
                is GropifyConfig.AndroidGenerateConfig -> "android"
                is GropifyConfig.JvmGenerateConfig -> "jvm"
                is GropifyConfig.KmpGenerateConfig -> "kmp"
                else -> "unknown"
            }
        } is disabled in ${config.name}, skipping deployment process.")
        return config.isEnabled
    }
}