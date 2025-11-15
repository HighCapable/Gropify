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

import com.highcapable.gropify.debug.Logger
import com.highcapable.gropify.debug.require
import com.highcapable.gropify.gradle.api.entity.ProjectDescriptor
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.config.type.GropifyLocation
import com.highcapable.gropify.plugin.deployer.BuildscriptDeployer
import com.highcapable.gropify.plugin.deployer.SourceCodeDeployer
import com.highcapable.gropify.plugin.generator.extension.PropertyMap
import com.highcapable.gropify.plugin.generator.extension.PropertyTypeValue
import com.highcapable.gropify.plugin.generator.extension.createTypeValue
import com.highcapable.gropify.plugin.generator.extension.createTypeValueByType
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
        if (!isEnabled()) return

        checkingConfigModified(settings)

        Logger.debug("Initializing deployers, config modified: $configModified")
        deployers.forEach { it.init(settings, configModified) }
    }

    /**
     * Resolve deployers before project evaluation.
     * @param rootProject the current root project.
     */
    fun resolve(rootProject: Project) {
        if (!isEnabled()) return

        Logger.debug("Resolving deployers, config modified: $configModified")
        deployers.forEach { it.resolve(rootProject, configModified) }
    }

    /**
     * Deploy deployers after project evaluation.
     * @param rootProject the current root project.
     */
    fun deploy(rootProject: Project) {
        if (!isEnabled()) return

        Logger.debug("Deploying deployers, config modified: $configModified")
        deployers.forEach { it.deploy(rootProject, configModified) }
    }

    /**
     * Generate properties' key-values map.
     * @param config the generate configuration.
     * @param descriptor the project descriptor.
     * @return [PropertyMap]
     */
    fun generateMap(config: GropifyConfig.CommonGenerateConfig, descriptor: ProjectDescriptor): PropertyMap {
        val properties = mutableMapOf<String, PropertyTypeValue>()
        val resolveProperties = mutableMapOf<Any?, Any?>()

        val locations = mutableMapOf<String, String>()

        config.permanentKeyValues.forEach { (key, value) ->
            properties[key] = value.createTypeValueByType(config.useTypeAutoConversion, key)
            locations[key] = "Permanent Key-Value"
        }
        config.locations.forEach { location ->
            when (location) {
                GropifyLocation.CurrentProject -> createProperties(config, descriptor.currentDir).forEach {
                    resolveProperties.putAll(it)

                    it.forEach { (key, _) -> locations[key.toString()] = location.name }
                }
                GropifyLocation.RootProject -> createProperties(config, descriptor.rootDir).forEach {
                    resolveProperties.putAll(it)

                    it.forEach { (key, _) -> locations[key.toString()] = location.name }
                }
                GropifyLocation.Global -> createProperties(config, descriptor.homeDir).forEach {
                    resolveProperties.putAll(it)

                    it.forEach { (key, _) -> locations[key.toString()] = location.name }
                }
                GropifyLocation.System -> {
                    val system = System.getProperties()
                    resolveProperties.putAll(system)

                    system.forEach { (key, _) -> locations[key.toString()] = location.name }
                }
                GropifyLocation.SystemEnv -> {
                    val systemEnv = System.getenv()
                    resolveProperties.putAll(systemEnv)

                    systemEnv.forEach { (key, _) -> locations[key] = location.name }
                }
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
            resolveKeyValues.forEach { (key, value) ->
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
            }

            properties.putAll(resolveKeyValues.map { (key, value) ->
                key to value.createTypeValue(config.useTypeAutoConversion, key)
            })
        }

        // Replace all key-values if exists.
        config.replacementKeyValues.forEach { (key, value) ->
            properties[key] = value.createTypeValueByType(config.useTypeAutoConversion, key)
            locations[key] = "Replacement Key-Value${locations[key]?.let { ", $it" }}"
        }

        // Apply key-values rules.
        properties.forEach { (key, value) ->
            val (mapper, type) = config.keyValuesRules[key] ?: return@forEach

            val resolveValue = mapper(value.raw).createTypeValue(config.useTypeAutoConversion, key, type)
            properties[key] = resolveValue

            locations[key] = "Rule-based Key-Value${locations[key]?.let { ", $it" }}"
        }

        properties.forEach { (key, value) ->
            Logger.debug(
                """
                  Generated property for ${config.name}
                  ----------
                  [Key]: $key
                  [Value]: ${value.raw}
                  [Code Value]: ${value.codeValue}
                  [Type]: ${value.type.simpleName}
                  [Location]: ${locations[key] ?: "Unknown"}
                  ----------
                """.trimIndent()
            )
        }

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

    private fun isEnabled(): Boolean {
        if (!config.isEnabled) Logger.debug("Gropify is disabled, skipping deployment process.")
        return config.isEnabled
    }
}