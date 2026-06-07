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
        get() = createBuildscriptGenerateConfig(
            name = name,
            self = this@create.buildscriptConfigure,
            global = global.buildscriptConfigure,
            selfCommon = this@create.commonConfigure,
            globalCommon = global.commonConfigure
        )

    override val sourceCode
        get() = createSourceCodeGenerateConfig(
            name = name,
            self = listOf(this@create.sourceCodeConfigure),
            global = listOf(global.sourceCodeConfigure),
            selfCommon = this@create.commonConfigure,
            globalCommon = global.commonConfigure,
            default = DefaultConfig.createSourceCodeGenerateConfig(name, this@create.commonConfigure, global.commonConfigure)
        )

    override val android
        get() = createAndroidGenerateConfig(
            name = name,
            self = this@create.androidConfigure,
            global = global.androidConfigure,
            selfSourceCode = this@create.sourceCodeConfigure,
            globalSourceCode = global.sourceCodeConfigure,
            selfCommon = this@create.commonConfigure,
            globalCommon = global.commonConfigure
        )

    override val jvm
        get() = createJvmGenerateConfig(
            name = name,
            self = this@create.jvmConfigure,
            global = global.jvmConfigure,
            selfSourceCode = this@create.sourceCodeConfigure,
            globalSourceCode = global.sourceCodeConfigure,
            selfCommon = this@create.commonConfigure,
            globalCommon = global.commonConfigure
        )

    override val kmp
        get() = createKmpGenerateConfig(
            name = name,
            self = this@create.kmpConfigure,
            global = global.kmpConfigure,
            selfSourceCode = this@create.sourceCodeConfigure,
            globalSourceCode = global.sourceCodeConfigure,
            selfCommon = this@create.commonConfigure,
            globalCommon = global.commonConfigure
        )
}

private fun createBuildscriptGenerateConfig(
    name: String,
    self: GropifyConfigureExtension.BuildscriptGenerateConfigureScope? = null,
    global: GropifyConfigureExtension.BuildscriptGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.BuildscriptGenerateConfig, GropifyConfig.CommonGenerateConfig by createCommonGenerateConfig(
    name = name,
    self = listOf(self),
    global = listOf(global),
    selfCommon = selfCommon,
    globalCommon = globalCommon,
    default = DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon)
) {

    override val extensionName
        get() = self?.extensionName?.ifBlank { null }
            ?: global?.extensionName?.ifBlank { null }
            ?: DefaultConfig.createBuildscriptGenerateConfig(name, selfCommon, globalCommon).extensionName
}

private fun createSourceCodeGenerateConfig(
    name: String,
    self: List<GropifyConfigureExtension.SourceCodeGenerateConfigureScope?> = emptyList(),
    global: List<GropifyConfigureExtension.SourceCodeGenerateConfigureScope?> = emptyList(),
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    default: GropifyConfig.SourceCodeGenerateConfig
) = object : GropifyConfig.SourceCodeGenerateConfig, GropifyConfig.CommonGenerateConfig by createCommonGenerateConfig(
    name = name,
    self = self,
    global = global,
    selfCommon = selfCommon,
    globalCommon = globalCommon,
    default = default
) {

    override val name get() = name

    override val generateDirPath
        get() = self.firstNotNullOfOrNull { it?.generateDirPath?.ifBlank { null } }
            ?: global.firstNotNullOfOrNull { it?.generateDirPath?.ifBlank { null } }
            ?: default.generateDirPath

    override val sourceSetName
        get() = self.firstNotNullOfOrNull { it?.sourceSetName?.ifBlank { null } }
            ?: global.firstNotNullOfOrNull { it?.sourceSetName?.ifBlank { null } }
            ?: default.sourceSetName

    override val packageName
        get() = self.firstNotNullOfOrNull { it?.packageName?.ifBlank { null } }
            ?: global.firstNotNullOfOrNull { it?.packageName?.ifBlank { null } }
            ?: default.packageName

    override val className
        get() = self.firstNotNullOfOrNull { it?.className?.ifBlank { null } }
            ?: global.firstNotNullOfOrNull { it?.className?.ifBlank { null } }
            ?: default.className

    override val isRestrictedAccessEnabled
        get() = self.firstNotNullOfOrNull { it?.isRestrictedAccessEnabled }
            ?: global.firstNotNullOfOrNull { it?.isRestrictedAccessEnabled }
            ?: default.isRestrictedAccessEnabled

    override val isIsolationEnabled
        get() = self.firstNotNullOfOrNull { it?.isIsolationEnabled }
            ?: global.firstNotNullOfOrNull { it?.isIsolationEnabled }
            ?: default.isIsolationEnabled
}

private fun createCommonGenerateConfig(
    name: String,
    self: List<GropifyConfigureExtension.CommonGenerateConfigureScope?> = emptyList(),
    global: List<GropifyConfigureExtension.CommonGenerateConfigureScope?> = emptyList(),
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    default: GropifyConfig.CommonGenerateConfig
) = object : GropifyConfig.CommonGenerateConfig {

    override val name get() = name

    override val isEnabled
        get() = self.firstNotNullOfOrNull { it?.isEnabled }
            ?: selfCommon?.isEnabled
            ?: global.firstNotNullOfOrNull { it?.isEnabled }
            ?: globalCommon?.isEnabled
            ?: default.isEnabled

    override val existsPropertyFiles
        get() = self.firstNotNullOfOrNull { it?.existsPropertyFiles }
            ?: selfCommon?.existsPropertyFiles
            ?: global.firstNotNullOfOrNull { it?.existsPropertyFiles }
            ?: globalCommon?.existsPropertyFiles
            ?: default.existsPropertyFiles

    override val permanentKeyValues
        get() = self.firstNotNullOfOrNull { it?.permanentKeyValues }
            ?: selfCommon?.permanentKeyValues
            ?: global.firstNotNullOfOrNull { it?.permanentKeyValues }
            ?: globalCommon?.permanentKeyValues
            ?: default.permanentKeyValues

    override val replacementKeyValues
        get() = self.firstNotNullOfOrNull { it?.replacementKeyValues }
            ?: selfCommon?.replacementKeyValues
            ?: global.firstNotNullOfOrNull { it?.replacementKeyValues }
            ?: globalCommon?.replacementKeyValues
            ?: default.replacementKeyValues

    override val excludeKeys
        get() = self.firstNotNullOfOrNull { it?.excludeKeys }
            ?: selfCommon?.excludeKeys
            ?: global.firstNotNullOfOrNull { it?.excludeKeys }
            ?: globalCommon?.excludeKeys
            ?: default.excludeKeys

    override val includeKeys
        get() = self.firstNotNullOfOrNull { it?.includeKeys }
            ?: selfCommon?.includeKeys
            ?: global.firstNotNullOfOrNull { it?.includeKeys }
            ?: globalCommon?.includeKeys
            ?: default.includeKeys

    override val keyValuesRules
        get() = self.firstNotNullOfOrNull { it?.keyValuesRules }
            ?: selfCommon?.keyValuesRules
            ?: global.firstNotNullOfOrNull { it?.keyValuesRules }
            ?: globalCommon?.keyValuesRules
            ?: default.keyValuesRules

    override val excludeNonStringValue
        get() = self.firstNotNullOfOrNull { it?.excludeNonStringValue }
            ?: selfCommon?.excludeNonStringValue
            ?: global.firstNotNullOfOrNull { it?.excludeNonStringValue }
            ?: globalCommon?.excludeNonStringValue
            ?: default.excludeNonStringValue

    override val useTypeAutoConversion
        get() = self.firstNotNullOfOrNull { it?.useTypeAutoConversion }
            ?: selfCommon?.useTypeAutoConversion
            ?: global.firstNotNullOfOrNull { it?.useTypeAutoConversion }
            ?: globalCommon?.useTypeAutoConversion
            ?: default.useTypeAutoConversion

    override val useValueInterpolation
        get() = self.firstNotNullOfOrNull { it?.useValueInterpolation }
            ?: selfCommon?.useValueInterpolation
            ?: global.firstNotNullOfOrNull { it?.useValueInterpolation }
            ?: globalCommon?.useValueInterpolation
            ?: default.useValueInterpolation

    override val locations
        get() = self.firstNotNullOfOrNull { it?.locations }
            ?: selfCommon?.locations
            ?: global.firstNotNullOfOrNull { it?.locations }
            ?: globalCommon?.locations
            ?: default.locations
}

private fun createAndroidGenerateConfig(
    name: String,
    self: GropifyConfigureExtension.AndroidGenerateConfigureScope? = null,
    global: GropifyConfigureExtension.AndroidGenerateConfigureScope? = null,
    selfSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    globalSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.AndroidGenerateConfig, GropifyConfig.SourceCodeGenerateConfig by createSourceCodeGenerateConfig(
    name = name,
    self = listOf(self, selfSourceCode),
    global = listOf(global, globalSourceCode),
    selfCommon = selfCommon,
    globalCommon = globalCommon,
    default = DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon)
) {

    override val useKotlin
        get() = self?.useKotlin
            ?: global?.useKotlin
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).useKotlin

    override val manifestPlaceholders
        get() = self?.manifestPlaceholders
            ?: global?.manifestPlaceholders
            ?: DefaultConfig.createAndroidGenerateConfig(name, selfCommon, globalCommon).manifestPlaceholders
}

private fun createJvmGenerateConfig(
    name: String,
    self: GropifyConfigureExtension.JvmGenerateConfigureScope? = null,
    global: GropifyConfigureExtension.JvmGenerateConfigureScope? = null,
    selfSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    globalSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.JvmGenerateConfig, GropifyConfig.SourceCodeGenerateConfig by createSourceCodeGenerateConfig(
    name = name,
    self = listOf(self, selfSourceCode),
    global = listOf(global, globalSourceCode),
    selfCommon = selfCommon,
    globalCommon = globalCommon,
    default = DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon)
) {

    override val useKotlin
        get() = self?.useKotlin
            ?: global?.useKotlin
            ?: DefaultConfig.createJvmGenerateConfig(name, selfCommon, globalCommon).useKotlin
}

private fun createKmpGenerateConfig(
    name: String,
    self: GropifyConfigureExtension.KmpGenerateConfigureScope? = null,
    global: GropifyConfigureExtension.KmpGenerateConfigureScope? = null,
    selfSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    globalSourceCode: GropifyConfigureExtension.SourceCodeGenerateConfigureScope? = null,
    selfCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null,
    globalCommon: GropifyConfigureExtension.CommonGenerateConfigureScope? = null
) = object : GropifyConfig.KmpGenerateConfig, GropifyConfig.SourceCodeGenerateConfig by createSourceCodeGenerateConfig(
    name = name,
    self = listOf(self, selfSourceCode),
    global = listOf(global, globalSourceCode),
    selfCommon = selfCommon,
    globalCommon = globalCommon,
    default = DefaultConfig.createKmpGenerateConfig(name, selfCommon, globalCommon)
) {}