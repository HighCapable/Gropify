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
 * This file is created by fankes on 2025/10/9.
 */
package com.highcapable.gropify.plugin.config

import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import com.highcapable.gropify.plugin.generator.extension.PropertyValueRule

/**
 * Default configuration for Gropify.
 */
internal object DefaultConfig {

    fun createGenerateConfig(name: String, common: GropifyConfigureExtension.CommonGenerateConfigureScope? = null) =
        object : GropifyConfig.GenerateConfig {
            override val buildscript get() = createBuildscriptGenerateConfig(name, common)
            override val android get() = createAndroidGenerateConfig(name, common)
            override val jvm get() = createJvmGenerateConfig(name, common)
            override val kmp get() = createKmpGenerateConfig(name, common)
        }

    fun createBuildscriptGenerateConfig(
        name: String,
        selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
        globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
    ) = object : GropifyConfig.BuildscriptGenerateConfig {

        override val name get() = name

        override val extensionName get() = GropifyConfig.DEFAULT_EXTENSION_NAME

        override val isEnabled
            get() = selfCommon?.isEnabled
                ?: globalCommon?.isEnabled
                ?: createCommonGenerateConfig(name).isEnabled

        override val existsPropertyFiles
            get() = selfCommon?.existsPropertyFiles
                ?: globalCommon?.existsPropertyFiles
                ?: createCommonGenerateConfig(name).existsPropertyFiles

        override val permanentKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val replacementKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val excludeKeys
            get() = selfCommon?.excludeKeys
                ?: globalCommon?.excludeKeys
                ?: createCommonGenerateConfig(name).excludeKeys

        override val includeKeys
            get() = selfCommon?.includeKeys
                ?: globalCommon?.includeKeys
                ?: createCommonGenerateConfig(name).includeKeys

        override val keyValuesRules
            get() = selfCommon?.keyValuesRules
                ?: globalCommon?.keyValuesRules
                ?: createCommonGenerateConfig(name).keyValuesRules

        override val excludeNonStringValue
            get() = selfCommon?.excludeNonStringValue
                ?: globalCommon?.excludeNonStringValue
                ?: createCommonGenerateConfig(name).excludeNonStringValue

        override val useTypeAutoConversion
            get() = selfCommon?.useTypeAutoConversion
                ?: globalCommon?.useTypeAutoConversion
                ?: createCommonGenerateConfig(name).useTypeAutoConversion

        override val useValueInterpolation
            get() = selfCommon?.useValueInterpolation
                ?: globalCommon?.useValueInterpolation
                ?: createCommonGenerateConfig(name).useValueInterpolation

        override val locations
            get() = selfCommon?.locations
                ?: globalCommon?.locations
                ?: createCommonGenerateConfig(name).locations
    }

    fun createAndroidGenerateConfig(
        name: String,
        selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
        globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
    ) = object : GropifyConfig.AndroidGenerateConfig {

        override val name get() = name

        override val generateDirPath get() = GropifyConfig.DEFAULT_COMMON_CODE_GENERATE_DIR_PATH

        override val sourceSetName get() = GropifyConfig.DEFAULT_ANDROID_JVM_SOURCE_SET_NAME

        override val useKotlin get() = true

        override val packageName get() = ""

        override val className get() = ""

        override val isRestrictedAccessEnabled get() = false

        override val isIsolationEnabled get() = true

        override val isEnabled
            get() = selfCommon?.isEnabled
                ?: globalCommon?.isEnabled
                ?: createCommonGenerateConfig(name).isEnabled

        override val existsPropertyFiles
            get() = selfCommon?.existsPropertyFiles
                ?: globalCommon?.existsPropertyFiles
                ?: createCommonGenerateConfig(name).existsPropertyFiles

        override val permanentKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val replacementKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val excludeKeys
            get() = selfCommon?.excludeKeys
                ?: globalCommon?.excludeKeys
                ?: createCommonGenerateConfig(name).excludeKeys

        override val includeKeys
            get() = selfCommon?.includeKeys
                ?: globalCommon?.includeKeys
                ?: createCommonGenerateConfig(name).includeKeys

        override val keyValuesRules
            get() = selfCommon?.keyValuesRules
                ?: globalCommon?.keyValuesRules
                ?: createCommonGenerateConfig(name).keyValuesRules

        override val excludeNonStringValue
            get() = selfCommon?.excludeNonStringValue
                ?: globalCommon?.excludeNonStringValue
                ?: createCommonGenerateConfig(name).excludeNonStringValue

        override val useTypeAutoConversion
            get() = selfCommon?.useTypeAutoConversion
                ?: globalCommon?.useTypeAutoConversion
                ?: createCommonGenerateConfig(name).useTypeAutoConversion

        override val useValueInterpolation
            get() = selfCommon?.useValueInterpolation
                ?: globalCommon?.useValueInterpolation
                ?: createCommonGenerateConfig(name).useValueInterpolation

        override val locations
            get() = selfCommon?.locations
                ?: globalCommon?.locations
                ?: createCommonGenerateConfig(name).locations
    }

    fun createJvmGenerateConfig(
        name: String,
        selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
        globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
    ) = object : GropifyConfig.JvmGenerateConfig {

        override val name get() = name

        override val generateDirPath get() = GropifyConfig.DEFAULT_COMMON_CODE_GENERATE_DIR_PATH

        override val sourceSetName get() = GropifyConfig.DEFAULT_ANDROID_JVM_SOURCE_SET_NAME

        override val useKotlin get() = true

        override val packageName get() = ""

        override val className get() = ""

        override val isRestrictedAccessEnabled get() = false

        override val isIsolationEnabled get() = true

        override val isEnabled
            get() = selfCommon?.isEnabled
                ?: globalCommon?.isEnabled
                ?: createCommonGenerateConfig(name).isEnabled

        override val existsPropertyFiles
            get() = selfCommon?.existsPropertyFiles
                ?: globalCommon?.existsPropertyFiles
                ?: createCommonGenerateConfig(name).existsPropertyFiles

        override val permanentKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val replacementKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val excludeKeys
            get() = selfCommon?.excludeKeys
                ?: globalCommon?.excludeKeys
                ?: createCommonGenerateConfig(name).excludeKeys

        override val includeKeys
            get() = selfCommon?.includeKeys
                ?: globalCommon?.includeKeys
                ?: createCommonGenerateConfig(name).includeKeys

        override val keyValuesRules
            get() = selfCommon?.keyValuesRules
                ?: globalCommon?.keyValuesRules
                ?: createCommonGenerateConfig(name).keyValuesRules

        override val excludeNonStringValue
            get() = selfCommon?.excludeNonStringValue
                ?: globalCommon?.excludeNonStringValue
                ?: createCommonGenerateConfig(name).excludeNonStringValue

        override val useTypeAutoConversion
            get() = selfCommon?.useTypeAutoConversion
                ?: globalCommon?.useTypeAutoConversion
                ?: createCommonGenerateConfig(name).useTypeAutoConversion

        override val useValueInterpolation
            get() = selfCommon?.useValueInterpolation
                ?: globalCommon?.useValueInterpolation
                ?: createCommonGenerateConfig(name).useValueInterpolation

        override val locations
            get() = selfCommon?.locations
                ?: globalCommon?.locations
                ?: createCommonGenerateConfig(name).locations
    }

    fun createKmpGenerateConfig(
        name: String,
        selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
        globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
    ) = object : GropifyConfig.KmpGenerateConfig {

        override val name get() = name

        override val generateDirPath get() = GropifyConfig.DEFAULT_COMMON_CODE_GENERATE_DIR_PATH

        override val sourceSetName get() = GropifyConfig.DEFAULT_KMP_COMMON_SOURCE_SET_NAME

        override val packageName get() = ""

        override val className get() = ""

        override val isRestrictedAccessEnabled get() = false

        override val isIsolationEnabled get() = true

        override val isEnabled
            get() = selfCommon?.isEnabled
                ?: globalCommon?.isEnabled
                ?: createCommonGenerateConfig(name).isEnabled

        override val existsPropertyFiles
            get() = selfCommon?.existsPropertyFiles
                ?: globalCommon?.existsPropertyFiles
                ?: createCommonGenerateConfig(name).existsPropertyFiles

        override val permanentKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val replacementKeyValues
            get() = selfCommon?.permanentKeyValues
                ?: globalCommon?.permanentKeyValues
                ?: createCommonGenerateConfig(name).permanentKeyValues

        override val excludeKeys
            get() = selfCommon?.excludeKeys
                ?: globalCommon?.excludeKeys
                ?: createCommonGenerateConfig(name).excludeKeys

        override val includeKeys
            get() = selfCommon?.includeKeys
                ?: globalCommon?.includeKeys
                ?: createCommonGenerateConfig(name).includeKeys

        override val keyValuesRules
            get() = selfCommon?.keyValuesRules
                ?: globalCommon?.keyValuesRules
                ?: createCommonGenerateConfig(name).keyValuesRules

        override val excludeNonStringValue
            get() = selfCommon?.excludeNonStringValue
                ?: globalCommon?.excludeNonStringValue
                ?: createCommonGenerateConfig(name).excludeNonStringValue

        override val useTypeAutoConversion
            get() = selfCommon?.useTypeAutoConversion
                ?: globalCommon?.useTypeAutoConversion
                ?: createCommonGenerateConfig(name).useTypeAutoConversion

        override val useValueInterpolation
            get() = selfCommon?.useValueInterpolation
                ?: globalCommon?.useValueInterpolation
                ?: createCommonGenerateConfig(name).useValueInterpolation

        override val locations
            get() = selfCommon?.locations
                ?: globalCommon?.locations
                ?: createCommonGenerateConfig(name).locations
    }

    private fun createCommonGenerateConfig(name: String) = object : GropifyConfig.CommonGenerateConfig {
        override val name get() = name
        override val isEnabled get() = true
        override val existsPropertyFiles get() = mutableListOf(GropifyConfig.DEFAULT_EXISTS_PROPERTY_FILE)
        override val permanentKeyValues get() = mutableMapOf<String, Any>()
        override val replacementKeyValues get() = mutableMapOf<String, Any>()
        override val excludeKeys get() = mutableListOf<Any>()
        override val includeKeys get() = mutableListOf<Any>()
        override val keyValuesRules get() = mutableMapOf<String, PropertyValueRule>()
        override val excludeNonStringValue get() = true
        override val useTypeAutoConversion get() = true
        override val useValueInterpolation get() = true
        override val locations get() = GropifyConfig.defaultLocations
    }
}