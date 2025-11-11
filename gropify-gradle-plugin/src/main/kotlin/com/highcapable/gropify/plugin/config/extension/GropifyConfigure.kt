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
package com.highcapable.gropify.plugin.config.extension

import com.highcapable.gropify.gradle.api.extension.getFullName
import com.highcapable.gropify.plugin.config.DefaultConfig
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import org.gradle.api.Project

internal fun GropifyConfig.from(project: Project) = projects[project.getFullName()] ?: global

internal fun GropifyConfigureExtension.GenerateConfigureScope.create(
    name: String = "Global",
    global: GropifyConfigureExtension.GenerateConfigureScope = this
) = object : GropifyConfig.GenerateConfig {

    override val buildscript
        get() = this@create.buildscriptConfigure?.create(name, global.buildscriptConfigure, this@create.commonConfigure, global.commonConfigure)
            ?: global.buildscriptConfigure?.create(name, this@create.buildscriptConfigure ?: global.buildscriptConfigure)
            ?: DefaultConfig.createGenerateConfig(name, this@create.commonConfigure ?: global.commonConfigure).buildscript

    override val android
        get() = this@create.androidConfigure?.create(name, global.androidConfigure, this@create.commonConfigure, global.commonConfigure)
            ?: global.androidConfigure?.create(name, this@create.androidConfigure ?: global.androidConfigure)
            ?: DefaultConfig.createGenerateConfig(name, this@create.commonConfigure ?: global.commonConfigure).android

    override val jvm
        get() = this@create.jvmConfigure?.create(name, global.jvmConfigure, this@create.commonConfigure, global.commonConfigure)
            ?: global.jvmConfigure?.create(name, this@create.jvmConfigure ?: global.jvmConfigure)
            ?: DefaultConfig.createGenerateConfig(name, this@create.commonConfigure ?: global.commonConfigure).jvm

    override val kmp
        get() = this@create.kmpConfigure?.create(name, global.kmpConfigure, this@create.commonConfigure, global.commonConfigure)
            ?: global.kmpConfigure?.create(name, this@create.kmpConfigure ?: global.kmpConfigure)
            ?: DefaultConfig.createGenerateConfig(name, this@create.commonConfigure ?: global.commonConfigure).kmp
}

private fun GropifyConfigureExtension.BuildscriptGenerateConfigureScope.create(
    name: String,
    global: GropifyConfigureExtension.BuildscriptGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.BuildscriptGenerateConfig {

    override val name get() = name

    override val extensionName
        get() = this@create.extensionName.ifBlank { null }
            ?: global?.extensionName?.ifBlank { null }
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).extensionName

    override val isEnabled
        get() = this@create.isEnabled
            ?: selfCommon?.isEnabled
            ?: global?.isEnabled
            ?: globalCommon?.isEnabled
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).isEnabled

    override val existsPropertyFiles
        get() = this@create.existsPropertyFiles
            ?: global?.existsPropertyFiles
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).existsPropertyFiles

    override val permanentKeyValues
        get() = this@create.permanentKeyValues
            ?: global?.permanentKeyValues
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).permanentKeyValues

    override val replacementKeyValues
        get() = this@create.replacementKeyValues
            ?: global?.replacementKeyValues
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).replacementKeyValues

    override val excludeKeys
        get() = this@create.excludeKeys
            ?: global?.excludeKeys
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).excludeKeys

    override val includeKeys
        get() = this@create.includeKeys
            ?: global?.includeKeys
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).includeKeys

    override val keyValuesRules
        get() = this@create.keyValuesRules
            ?: global?.keyValuesRules
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).keyValuesRules

    override val excludeNonStringValue
        get() = this@create.excludeNonStringValue
            ?: selfCommon?.excludeNonStringValue
            ?: global?.excludeNonStringValue
            ?: globalCommon?.excludeNonStringValue
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).excludeNonStringValue

    override val useTypeAutoConversion
        get() = this@create.useTypeAutoConversion
            ?: selfCommon?.useTypeAutoConversion
            ?: global?.useTypeAutoConversion
            ?: globalCommon?.useTypeAutoConversion
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).useTypeAutoConversion

    override val useValueInterpolation
        get() = this@create.useValueInterpolation
            ?: selfCommon?.useValueInterpolation
            ?: global?.useValueInterpolation
            ?: globalCommon?.useValueInterpolation
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).useValueInterpolation

    override val locations
        get() = this@create.locations
            ?: selfCommon?.locations
            ?: global?.locations
            ?: globalCommon?.locations
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).locations
}

private fun GropifyConfigureExtension.AndroidGenerateConfigureScope.create(
    name: String,
    global: GropifyConfigureExtension.AndroidGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.AndroidGenerateConfig {

    override val name get() = name

    override val isEnabled
        get() = this@create.isEnabled
            ?: selfCommon?.isEnabled
            ?: global?.isEnabled
            ?: globalCommon?.isEnabled
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).isEnabled

    override val generateDirPath
        get() = this@create.generateDirPath.ifBlank { null }
            ?: global?.generateDirPath?.ifBlank { null }
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).generateDirPath

    override val sourceSetName
        get() = this@create.sourceSetName.ifBlank { null }
            ?: global?.sourceSetName?.ifBlank { null }
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).sourceSetName

    override val useKotlin
        get() = this@create.useKotlin
            ?: global?.useKotlin
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).useKotlin

    override val packageName
        get() = this@create.packageName.ifBlank { null }
            ?: global?.packageName?.ifBlank { null }
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).packageName

    override val className
        get() = this@create.className.ifBlank { null }
            ?: global?.className?.ifBlank { null }
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).className

    override val isRestrictedAccessEnabled
        get() = this@create.isRestrictedAccessEnabled
            ?: global?.isRestrictedAccessEnabled
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).isRestrictedAccessEnabled

    override val isIsolationEnabled
        get() = this@create.isIsolationEnabled
            ?: global?.isIsolationEnabled
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).isIsolationEnabled

    override val existsPropertyFiles
        get() = this@create.existsPropertyFiles
            ?: global?.existsPropertyFiles
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).existsPropertyFiles

    override val permanentKeyValues
        get() = this@create.permanentKeyValues
            ?: global?.permanentKeyValues
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).permanentKeyValues

    override val replacementKeyValues
        get() = this@create.replacementKeyValues
            ?: global?.replacementKeyValues
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).replacementKeyValues

    override val excludeKeys
        get() = this@create.excludeKeys
            ?: global?.excludeKeys
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).excludeKeys

    override val includeKeys
        get() = this@create.includeKeys
            ?: global?.includeKeys
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).includeKeys

    override val keyValuesRules
        get() = this@create.keyValuesRules
            ?: global?.keyValuesRules
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).keyValuesRules

    override val excludeNonStringValue
        get() = this@create.excludeNonStringValue
            ?: selfCommon?.excludeNonStringValue
            ?: global?.excludeNonStringValue
            ?: globalCommon?.excludeNonStringValue
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).excludeNonStringValue

    override val useTypeAutoConversion
        get() = this@create.useTypeAutoConversion
            ?: selfCommon?.useTypeAutoConversion
            ?: global?.useTypeAutoConversion
            ?: globalCommon?.useTypeAutoConversion
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).useTypeAutoConversion

    override val useValueInterpolation
        get() = this@create.useValueInterpolation
            ?: selfCommon?.useValueInterpolation
            ?: global?.useValueInterpolation
            ?: globalCommon?.useValueInterpolation
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).useValueInterpolation

    override val locations
        get() = this@create.locations
            ?: selfCommon?.locations
            ?: global?.locations
            ?: globalCommon?.locations
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).locations
}

private fun GropifyConfigureExtension.JvmGenerateConfigureScope.create(
    name: String,
    global: GropifyConfigureExtension.JvmGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.JvmGenerateConfig {

    override val name get() = name

    override val isEnabled
        get() = this@create.isEnabled
            ?: selfCommon?.isEnabled
            ?: global?.isEnabled
            ?: globalCommon?.isEnabled
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).isEnabled

    override val generateDirPath
        get() = this@create.generateDirPath.ifBlank { null }
            ?: global?.generateDirPath?.ifBlank { null }
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).generateDirPath

    override val sourceSetName
        get() = this@create.sourceSetName.ifBlank { null }
            ?: global?.sourceSetName?.ifBlank { null }
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).sourceSetName

    override val useKotlin
        get() = this@create.useKotlin
            ?: global?.useKotlin
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).useKotlin

    override val packageName
        get() = this@create.packageName.ifBlank { null }
            ?: global?.packageName?.ifBlank { null }
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).packageName

    override val className
        get() = this@create.className.ifBlank { null }
            ?: global?.className?.ifBlank { null }
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).className

    override val isRestrictedAccessEnabled
        get() = this@create.isRestrictedAccessEnabled
            ?: global?.isRestrictedAccessEnabled
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).isRestrictedAccessEnabled

    override val isIsolationEnabled
        get() = this@create.isIsolationEnabled
            ?: global?.isIsolationEnabled
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).isIsolationEnabled

    override val existsPropertyFiles
        get() = this@create.existsPropertyFiles
            ?: global?.existsPropertyFiles
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).existsPropertyFiles

    override val permanentKeyValues
        get() = this@create.permanentKeyValues
            ?: global?.permanentKeyValues
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).permanentKeyValues

    override val replacementKeyValues
        get() = this@create.replacementKeyValues
            ?: global?.replacementKeyValues
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).replacementKeyValues

    override val excludeKeys
        get() = this@create.excludeKeys
            ?: global?.excludeKeys
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).excludeKeys

    override val includeKeys
        get() = this@create.includeKeys
            ?: global?.includeKeys
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).includeKeys

    override val keyValuesRules
        get() = this@create.keyValuesRules
            ?: global?.keyValuesRules
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).keyValuesRules

    override val excludeNonStringValue
        get() = this@create.excludeNonStringValue
            ?: selfCommon?.excludeNonStringValue
            ?: global?.excludeNonStringValue
            ?: globalCommon?.excludeNonStringValue
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).excludeNonStringValue

    override val useTypeAutoConversion
        get() = this@create.useTypeAutoConversion
            ?: selfCommon?.useTypeAutoConversion
            ?: global?.useTypeAutoConversion
            ?: globalCommon?.useTypeAutoConversion
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).useTypeAutoConversion

    override val useValueInterpolation
        get() = this@create.useValueInterpolation
            ?: selfCommon?.useValueInterpolation
            ?: global?.useValueInterpolation
            ?: globalCommon?.useValueInterpolation
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).useValueInterpolation

    override val locations
        get() = this@create.locations
            ?: selfCommon?.locations
            ?: global?.locations
            ?: globalCommon?.locations
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).locations
}

private fun GropifyConfigureExtension.KmpGenerateConfigureScope.create(
    name: String,
    global: GropifyConfigureExtension.KmpGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.KmpGenerateConfig {

    override val name get() = name

    override val isEnabled
        get() = this@create.isEnabled
            ?: selfCommon?.isEnabled
            ?: global?.isEnabled
            ?: globalCommon?.isEnabled
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).isEnabled

    override val generateDirPath
        get() = this@create.generateDirPath.ifBlank { null }
            ?: global?.generateDirPath?.ifBlank { null }
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).generateDirPath

    override val sourceSetName
        get() = this@create.sourceSetName.ifBlank { null }
            ?: global?.sourceSetName?.ifBlank { null }
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).sourceSetName

    override val packageName
        get() = this@create.packageName.ifBlank { null }
            ?: global?.packageName?.ifBlank { null }
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).packageName

    override val className
        get() = this@create.className.ifBlank { null }
            ?: global?.className?.ifBlank { null }
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).className

    override val isRestrictedAccessEnabled
        get() = this@create.isRestrictedAccessEnabled
            ?: global?.isRestrictedAccessEnabled
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).isRestrictedAccessEnabled

    override val isIsolationEnabled
        get() = this@create.isIsolationEnabled
            ?: global?.isIsolationEnabled
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).isIsolationEnabled

    override val existsPropertyFiles
        get() = this@create.existsPropertyFiles
            ?: global?.existsPropertyFiles
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).existsPropertyFiles

    override val permanentKeyValues
        get() = this@create.permanentKeyValues
            ?: global?.permanentKeyValues
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).permanentKeyValues

    override val replacementKeyValues
        get() = this@create.replacementKeyValues
            ?: global?.replacementKeyValues
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).replacementKeyValues

    override val excludeKeys
        get() = this@create.excludeKeys
            ?: global?.excludeKeys
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).excludeKeys

    override val includeKeys
        get() = this@create.includeKeys
            ?: global?.includeKeys
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).includeKeys

    override val keyValuesRules
        get() = this@create.keyValuesRules
            ?: global?.keyValuesRules
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).keyValuesRules

    override val excludeNonStringValue
        get() = this@create.excludeNonStringValue
            ?: selfCommon?.excludeNonStringValue
            ?: global?.excludeNonStringValue
            ?: globalCommon?.excludeNonStringValue
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).excludeNonStringValue

    override val useTypeAutoConversion
        get() = this@create.useTypeAutoConversion
            ?: selfCommon?.useTypeAutoConversion
            ?: global?.useTypeAutoConversion
            ?: globalCommon?.useTypeAutoConversion
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).useTypeAutoConversion

    override val useValueInterpolation
        get() = this@create.useValueInterpolation
            ?: selfCommon?.useValueInterpolation
            ?: global?.useValueInterpolation
            ?: globalCommon?.useValueInterpolation
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).useValueInterpolation

    override val locations
        get() = this@create.locations
            ?: selfCommon?.locations
            ?: global?.locations
            ?: globalCommon?.locations
            ?: DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon).locations
}