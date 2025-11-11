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
 * This file is created by fankes on 2025/10/12.
 */
package com.highcapable.gropify.plugin

import com.highcapable.gropify.gradle.api.entity.ProjectDescriptor
import com.highcapable.gropify.internal.require
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.config.type.GropifyLocation
import com.highcapable.gropify.plugin.deployer.BuildscriptDeployer
import com.highcapable.gropify.plugin.deployer.SourceCodeDeployer
import com.highcapable.gropify.plugin.generator.extension.PropertyMap
import com.highcapable.gropify.utils.extension.hasInterpolation
import com.highcapable.gropify.utils.extension.removeSurroundingQuotes
import com.highcapable.gropify.utils.extension.replaceInterpolation
import com.highcapable.gropify.utils.extension.toStringMap
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.properties.Delegates

/**
 * Default properties' key-values deployer.
 */
internal object DefaultDeployer {

    private var config by Delegates.notNull<GropifyConfig>()

    private var lastModifiedHashCode = 0
    private var configModified = true

    private val deployers by lazy {
        listOf(
            BuildscriptDeployer { config },
            SourceCodeDeployer { config }
        )
    }

    /**
     * Initialize deployers.
     * @param settings the current Gradle settings.
     * @param config the gropify configuration.
     */
    fun init(settings: Settings, config: GropifyConfig) {
        DefaultDeployer.config = config
        if (!config.isEnabled) return

        checkingConfigModified(settings)

        deployers.forEach { it.init(settings, configModified) }
    }

    /**
     * Resolve deployers before project evaluation.
     * @param rootProject the current root project.
     */
    fun resolve(rootProject: Project) {
        if (!config.isEnabled) return

        deployers.forEach { it.resolve(rootProject, configModified) }
    }

    /**
     * Deploy deployers after project evaluation.
     * @param rootProject the current root project.
     */
    fun deploy(rootProject: Project) {
        if (!config.isEnabled) return

        deployers.forEach { it.deploy(rootProject, configModified) }
    }

    /**
     * Generate properties' key-values map.
     * @param config the generate configuration.
     * @param descriptor the project descriptor.
     * @return [PropertyMap]
     */
    fun generateMap(config: GropifyConfig.CommonGenerateConfig, descriptor: ProjectDescriptor): PropertyMap {
        val properties = mutableMapOf<String, Any>()
        val resolveProperties = mutableMapOf<Any?, Any?>()

        config.permanentKeyValues.forEach { (key, value) -> properties[key] = value }
        config.locations.forEach { location ->
            when (location) {
                GropifyLocation.CurrentProject -> createProperties(config, descriptor.currentDir).forEach { resolveProperties.putAll(it) }
                GropifyLocation.RootProject -> createProperties(config, descriptor.rootDir).forEach { resolveProperties.putAll(it) }
                GropifyLocation.Global -> createProperties(config, descriptor.homeDir).forEach { resolveProperties.putAll(it) }
                GropifyLocation.System -> resolveProperties.putAll(System.getProperties())
                GropifyLocation.SystemEnv -> resolveProperties.putAll(System.getenv())
            }
        }

        resolveProperties.filter { (key, value) ->
            if (config.excludeNonStringValue)
                key is CharSequence && key.isNotBlank() && value is CharSequence
            else key.toString().isNotBlank() && value != null
        }.toStringMap().filter { (key, _) ->
            config.includeKeys.ifEmpty { null }?.any { content ->
                when (content) {
                    is Regex -> content.matches(key)
                    else -> content.toString() == key
                }
            } ?: true
        }.filter { (key, _) ->
            config.excludeKeys.ifEmpty { null }?.none { content ->
                when (content) {
                    is Regex -> content.matches(key)
                    else -> content.toString() == key
                }
            } ?: true
        }.toMutableMap().also { resolveKeyValues ->
            resolveKeyValues.onEach { (key, value) ->
                val resolveKeys = mutableListOf<String>()

                fun String.resolveValue(): String = replaceInterpolation { matchKey ->
                    Gropify.require(resolveKeys.size <= 5) {
                        "Key \"$key\" has been called recursively multiple times of those $resolveKeys."
                    }

                    resolveKeys.add(matchKey)
                    var resolveValue = if (config.useValueInterpolation)
                        resolveKeyValues[matchKey] ?: ""
                    else matchKey
                    resolveValue = resolveValue.removeSurroundingQuotes()

                    if (resolveValue.hasInterpolation()) resolveValue.resolveValue()
                    else resolveValue
                }

                if (value.hasInterpolation()) resolveKeyValues[key] = value.resolveValue()
            }.takeIf { config.keyValuesRules.isNotEmpty() }?.forEach { (key, value) ->
                config.keyValuesRules[key]?.also { resolveKeyValues[key] = it(value) }
            }

            properties.putAll(resolveKeyValues)
        }

        // Replace all key-values if exists.
        config.replacementKeyValues.forEach { (key, value) -> properties[key] = value }

        return properties
    }

    private fun createProperties(config: GropifyConfig.CommonGenerateConfig, dir: File?) = runCatching {
        mutableListOf<Properties>().apply {
            config.existsPropertyFiles.forEach {
                val propertiesFile = dir?.resolve(it)
                if (propertiesFile?.exists() == true)
                    add(Properties().apply { load(FileReader(propertiesFile.absolutePath)) })
            }
        }
    }.getOrNull() ?: mutableListOf()

    private fun checkingConfigModified(settings: Settings) {
        settings.settingsDir.also { dir ->
            val groovyHashCode = dir.resolve("settings.gradle").takeIf { it.exists() }?.readText()?.hashCode()
            val kotlinHashCode = dir.resolve("settings.gradle.kts").takeIf { it.exists() }?.readText()?.hashCode()
            val gradleHashCode = groovyHashCode ?: kotlinHashCode ?: -1

            configModified = gradleHashCode == -1 || lastModifiedHashCode != gradleHashCode
            lastModifiedHashCode = gradleHashCode
        }
    }
}