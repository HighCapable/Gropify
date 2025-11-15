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
@file:Suppress("unused", "MemberVisibilityCanBePrivate", "PropertyName", "DeprecatedCallableAddReplaceWith", "FunctionName")

package com.highcapable.gropify.plugin.extension.dsl.configure

import com.highcapable.gropify.debug.error
import com.highcapable.gropify.debug.require
import com.highcapable.gropify.gradle.api.extension.isUnSafeExtName
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.config.extension.create
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.config.type.GropifyLocation
import com.highcapable.gropify.plugin.generator.extension.PropertyValueMapper
import com.highcapable.gropify.plugin.generator.extension.PropertyValueRule
import com.highcapable.gropify.utils.KeywordsDetector
import com.highcapable.gropify.utils.extension.isStartsWithLetter
import org.gradle.api.Action
import org.gradle.api.initialization.Settings
import kotlin.reflect.KClass

/**
 * Configure extension for Gropify.
 */
open class GropifyConfigureExtension internal constructor() {

    companion object {

        /** Extension name. */
        const val NAME = "gropify"

        private const val ROOT_PROJECT_TAG = "<ROOT_PROJECT>"
    }

    private val globalConfigure = GenerateConfigureScope()
    private val projectConfigures = mutableMapOf<String, GenerateConfigureScope>()

    /**
     * Whether to enable this plugin.
     *
     * If you want to disable this plugin, just set it here.
     */
    var isEnabled = true
        @JvmName("enabled") set

    /**
     * Whether to enable debug mode.
     *
     * You can help us identify the problem by enabling this option
     * to print more debugging information in the logs.
     *
     * - Note: THIS IS ONLY FOR DEBUGGING!
     *   The debug log will contain your local environment,
     *   which may contain sensitive information. Please be sure to protect this information.
     */
    var debugMode = false
        @JvmName("debugMode") set

    /**
     * Configure global.
     */
    fun global(action: Action<GenerateConfigureScope>) = action.execute(globalConfigure)

    /**
     * Configure root project.
     */
    fun rootProject(action: Action<GenerateConfigureScope>) = configureProject(ROOT_PROJECT_TAG, action)

    /**
     * Configure each project.
     * @param names the project names.
     */
    fun projects(vararg names: String, action: Action<GenerateConfigureScope>) = names.forEach { configureProject(it, action) }

    private fun configureProject(name: String, action: Action<GenerateConfigureScope>) =
        action.execute(GenerateConfigureScope().also { projectConfigures[name] = it })

    open inner class GenerateConfigureScope internal constructor(
        internal var commonConfigure: CommonGenerateConfigureScope? = null,
        internal var buildscriptConfigure: BuildscriptGenerateConfigureScope? = null,
        internal var androidConfigure: AndroidGenerateConfigureScope? = null,
        internal var jvmConfigure: JvmGenerateConfigureScope? = null,
        internal var kmpConfigure: KmpGenerateConfigureScope? = null
    ) {

        /**
         * Please use [common], [buildscript], [android], [jvm], [kmp] to configure it.
         * @throws IllegalStateException
         */
        @Suppress("unused")
        @Deprecated(
            message = "Please use `common`, `buildscript`, `android`, `jvm`, `kmp` to configure it.",
            level = DeprecationLevel.ERROR
        )
        val isEnabled: Boolean get() = Gropify.error("No getter available.")

        /**
         * Please call it from top level [GropifyConfigureExtension].
         * @throws IllegalStateException
         */
        @Suppress("unused")
        @Deprecated(
            message = "Please call it from top level `GropifyConfigureExtension`.",
            level = DeprecationLevel.ERROR
        )
        val debugMode: Boolean get() = Gropify.error("No getter available.")

        /**
         * Configure common.
         *
         * The configuration here will be applied downward to [buildscript], [android], [jvm], [kmp].
         */
        fun common(action: Action<CommonGenerateConfigureScope>) {
            commonConfigure = CommonGenerateConfigureScope().also { action.execute(it) }
        }

        /**
         * Configure buildscript.
         */
        fun buildscript(action: Action<BuildscriptGenerateConfigureScope>) {
            buildscriptConfigure = BuildscriptGenerateConfigureScope().also { action.execute(it) }
        }

        /**
         * Configure if this is an Android project.
         */
        fun android(action: Action<AndroidGenerateConfigureScope>) {
            androidConfigure = AndroidGenerateConfigureScope().also { action.execute(it) }
        }

        /**
         * Configure if this is a Java or Kotlin project.
         */
        fun jvm(action: Action<JvmGenerateConfigureScope>) {
            jvmConfigure = JvmGenerateConfigureScope().also { action.execute(it) }
        }

        /**
         * Configure if this is a Kotlin Multiplatform project.
         */
        fun kmp(action: Action<KmpGenerateConfigureScope>) {
            kmpConfigure = KmpGenerateConfigureScope().also { action.execute(it) }
        }
    }

    open inner class BuildscriptGenerateConfigureScope internal constructor() : CommonGenerateConfigureScope() {

        /**
         * Custom buildscript extension name.
         *
         * Default is "gropify".
         */
        var extensionName = ""
            @JvmName("extensionName") set
    }

    open inner class AndroidGenerateConfigureScope internal constructor() : JvmGenerateConfigureScope() {

        /**
         * Whether to use manifest placeholders' generation.
         *
         * Disabled by default, when enabled will synchronize the properties' key-values
         * to the `manifestPlaceholders` in the `android` configuration method block.
         */
        var manifestPlaceholders: Boolean? = null
            @JvmName("manifestPlaceholders") set
    }

    open inner class JvmGenerateConfigureScope internal constructor() : CommonCodeGenerateConfigureScope() {

        /**
         * Whether to use Kotlin language generation.
         *
         * Enabled by default, when enabled will generate Kotlin code, disabled will generate Java code.
         *
         * - Note: This option will be disabled when this project is a pure Java project.
         */
        var useKotlin: Boolean? = null
            @JvmName("useKotlin") set
    }

    open inner class KmpGenerateConfigureScope internal constructor() : CommonCodeGenerateConfigureScope()

    abstract inner class CommonCodeGenerateConfigureScope internal constructor() : SourceCodeGenerateConfigureExtension() {

        /**
         * Custom deployment `sourceSet` name.
         *
         * If your project source code deployment name is not default, you can customize it here.
         *
         * Default is "main", if this project is a Kotlin Multiplatform project, the default is "commonMain".
         */
        var sourceSetName = ""
            @JvmName("sourceSetName") set

        /**
         * Custom generated package name.
         *
         * Android projects use the `namespace` in the `android` configuration method block by default.
         *
         * Java, Kotlin or Kotlin Multiplatform projects use the `project.group` of the project settings by default.
         *
         * In a Kotlin Multiplatform project, if the AGP plugin is also applied,
         * the `namespace` will still be used as the package name by default.
         *
         * The "generated" is a fixed suffix that avoids conflicts with your own namespaces,
         * if you don't want this suffix, you can refer to [isIsolationEnabled].
         */
        var packageName = ""
            @JvmName("packageName") set

        /**
         * Custom generated class name.
         *
         * Default is use the name of the current project.
         *
         * The "Properties" is a fixed suffix to distinguish it from your own class names.
         */
        var className = ""
            @JvmName("className") set

        /**
         * Whether to enable restricted access.
         *
         * Disabled by default, when enabled will add the `internal` modifier to generated Kotlin classes or
         * remove the `public` modifier to generated Java classes.
         */
        var isRestrictedAccessEnabled: Boolean? = null
            @JvmName("restrictedAccessEnabled") set

        /**
         * Whether to enable code isolation.
         *
         * Enabled by default, when enabled will generate code in an isolated package suffix "generated"
         * to avoid conflicts with other projects that also use or not only Gropify to generate code.
         *
         * - Note: If you disable this option, please make sure that there are no other projects
         *   that also use or not only Gropify to generate code to avoid conflicts.
         */
        var isIsolationEnabled: Boolean? = null
            @JvmName("isolationEnabled") set
    }

    abstract inner class SourceCodeGenerateConfigureExtension internal constructor() : CommonGenerateConfigureScope() {

        /**
         * Custom generated directory path.
         *
         * You can fill in the path relative to the current project.
         *
         * Format example: "path/to/your/src/main", the "src/main" is a fixed suffix.
         *
         * The `android`, `jvm` and `kmp` default is "build/generated/gropify/src/main".
         *
         * We recommend that you set the generated path under the "build" directory,
         * which is ignored by version control systems by default.
         */
        var generateDirPath = ""
            @JvmName("generateDirPath") set
    }

    open inner class CommonGenerateConfigureScope internal constructor(
        internal var existsPropertyFiles: MutableList<String>? = null,
        internal var permanentKeyValues: MutableMap<String, Any>? = null,
        internal var replacementKeyValues: MutableMap<String, Any>? = null,
        internal var excludeKeys: MutableList<Any>? = null,
        internal var includeKeys: MutableList<Any>? = null,
        internal var keyValuesRules: MutableMap<String, PropertyValueRule>? = null,
        internal var locations: List<GropifyLocation>? = null
    ) {

        /**
         * Whether to generate code.
         *
         * Enabled by default.
         */
        var isEnabled: Boolean? = null
            @JvmName("enabled") set

        /**
         * Whether to exclude the non-string type key-values content.
         *
         * Enabled by default, when enabled, key-values and content that are not string types will be excluded from properties' key-values.
         */
        var excludeNonStringValue: Boolean? = null
            @JvmName("excludeNonStringValue") set

        /**
         * Whether to use type auto conversion.
         *
         * Enabled by default, when enabled, the type in the properties' key-values will be
         * automatically identified and converted to the corresponding type.
         *
         * After enabling, if you want to force the content of a key-values to be a string type,
         * you can use single quotes or double quotes to wrap the entire string.
         *
         * - Note: After disabled this function, the functions mentioned above will also be invalid.
         */
        var useTypeAutoConversion: Boolean? = null
            @JvmName("useTypeAutoConversion") set

        /**
         * Whether to use key-values content interpolation.
         *
         * Enabled by default, after enabling it will automatically identify
         * the `${...}` content in the properties' key-values content and replace it.
         *
         * Note: The interpolated content will only be looked up from the
         * current (current configuration file) properties' key-values list.
         */
        var useValueInterpolation: Boolean? = null
            @JvmName("useValueInterpolation") set

        /**
         * Set exists property files.
         *
         * The property files will be automatically obtained from the root directory
         * of the current root project, subproject and user directory according to the file name you set.
         *
         * By default, will add "gradle.properties" if [addDefault] is `true`.
         *
         * You can add multiple sets of property files name, they will be read in order.
         *
         * - Note: Generally there is no need to modify this setting,
         *   an incorrect file name will result in obtaining empty key-values content.
         * @param fileNames the file names.
         * @param addDefault whether to add a default property file name, default is true.
         */
        @JvmOverloads
        fun existsPropertyFiles(vararg fileNames: String, addDefault: Boolean = true) {
            Gropify.require(fileNames.isNotEmpty() && fileNames.all { it.isNotBlank() }) {
                "Property file names must not be empty or have blank contents."
            }

            existsPropertyFiles = fileNames.distinct().toMutableList()
            if (addDefault) existsPropertyFiles?.add(0, GropifyConfig.DEFAULT_EXISTS_PROPERTY_FILE)
        }

        /**
         * Set a permanent list of properties' key-values.
         *
         * Here you can set some key-values that must exist, these key-values will be generated regardless of
         * whether they can be obtained from the properties' key-values.
         *
         * These keys use the content of the properties' key if it exists, use the content set here if it does not exist.
         *
         * - Note: Special symbols and spaces cannot exist in properties' key names, otherwise the generation may fail.
         * @param pairs the key-values array.
         */
        @JvmName("`kotlin-dsl-only-permanentKeyValues`")
        fun permanentKeyValues(vararg pairs: Pair<String, Any>) {
            Gropify.require(pairs.isNotEmpty() && pairs.all { it.first.isNotBlank() }) {
                "Permanent key-values must not be empty or have blank contents."
            }

            permanentKeyValues = mutableMapOf(*pairs)
        }

        /**
         * Set a permanent list of properties' key-values. (Groovy compatible function)
         *
         * Here you can set some key-values that must exist, these key-values will be generated regardless of
         * whether they can be obtained from the properties' key-values.
         *
         * These keys use the content of the properties' key if it exists, use the content set here if it does not exist.
         *
         * - Note: Special symbols and spaces cannot exist in properties' key names, otherwise the generation may fail.
         * @param keyValues the key-value array.
         */
        fun permanentKeyValues(keyValues: Map<String, Any>) {
            Gropify.require(keyValues.isNotEmpty() && keyValues.all { it.key.isNotBlank() }) {
                "Permanent key-values must not be empty or have blank contents."
            }

            permanentKeyValues = keyValues.toMutableMap()
        }

        /**
         * Set a replacement list of properties' key-values.
         *
         * Here you can set some key-values that need to be replaced, these key-values will be replaced
         * the existing properties' key-values, if not exist, they will be ignored.
         *
         * The key-values set here will also overwrite the key-values set in [permanentKeyValues].
         * @param pairs the key-values array.
         */
        @JvmName("`kotlin-dsl-only-replacementKeyValues`")
        fun replacementKeyValues(vararg pairs: Pair<String, Any>) {
            Gropify.require(pairs.isNotEmpty() && pairs.all { it.first.isNotBlank() }) {
                "Replacement key-values must not be empty or have blank contents."
            }

            replacementKeyValues = mutableMapOf(*pairs)
        }

        /**
         * Set a replacement list of properties' key-values. (Groovy compatible function)
         *
         * Here you can set some key-values that need to be replaced, these key-values will be replaced
         * the existing properties' key-values, if not exist, they will be ignored.
         *
         * The key-values set here will also overwrite the key-values set in [permanentKeyValues].
         * @param keyValues the key-value array.
         */
        fun replacementKeyValues(keyValues: Map<String, Any>) {
            Gropify.require(keyValues.isNotEmpty() && keyValues.all { it.key.isNotBlank() }) {
                "Replacement key-values must not be empty or have blank contents."
            }

            replacementKeyValues = keyValues.toMutableMap()
        }

        /**
         * Set a key list of properties' key-values name that need to be excluded.
         *
         * Here you can set some key names that you want to exclude from known properties' key-values.
         *
         * These keys are excluded if they are present in the properties' key, will not appear in the generated code.
         *
         * - Note: If you exclude key-values set in [permanentKeyValues], then they will only change to the
         *   initial key-values content you set and continue to exist.
         * @param keys the key names, you can pass in [Regex] or use [String.toRegex] to use regex functionality.
         */
        fun excludeKeys(vararg keys: Any) {
            Gropify.require(keys.isNotEmpty() && keys.all { it.toString().isNotBlank() }) {
                "Excluded keys must not be empty or have blank contents."
            }

            excludeKeys = keys.distinct().toMutableList()
        }

        /**
         * Set a key list of properties' key-values name that need to be included.
         *
         * Here you can set some key value names that you want to include from known properties' key-values.
         *
         * These keys are included if the properties' key exists, unincluded keys will not appear in the generated code.
         * @param keys the key names, you can pass in [Regex] or use [String.toRegex] to use regex functionality.
         */
        fun includeKeys(vararg keys: Any) {
            Gropify.require(keys.isNotEmpty() && keys.all { it.toString().isNotBlank() }) {
                "Included keys must not be empty or have blank contents."
            }

            includeKeys = keys.distinct().toMutableList()
        }

        /**
         * Set properties' key-values rules.
         *
         * You can set up a set of key-values rules,
         * use [ValueRule] to create new rule for parsing the obtained key-values content.
         *
         * Usage:
         *
         * ```kotlin
         * keyValuesRules(
         *     "some.key1" to ValueRule { if (it.contains("_")) it.replace("_", "-") else it },
         *     "some.key2" to ValueRule { "$it-value" },
         *     // You can also specify the expected type class,
         *     // the type you specify will be used during generation,
         *     // and an exception will be thrown if the type cannot be converted correctly.
         *     // If the [useTypeAutoConversion] is not enabled, this parameter will be ignored.
         *     "some.key3" to ValueRule(Int::class)
         * )
         * ```
         *
         * These key-values rules are applied when properties' keys exist.
         * @param pairs the key-values array.
         */
        @JvmName("`kotlin-dsl-only-keyValuesRules`")
        fun keyValuesRules(vararg pairs: Pair<String, PropertyValueRule>) {
            Gropify.require(pairs.isNotEmpty() && pairs.all { it.first.isNotBlank() }) {
                "Key-values rules must not be empty or have blank contents."
            }

            keyValuesRules = mutableMapOf(*pairs)
        }

        /**
         * Set properties' key-values rules. (Groovy compatible function)
         *
         * You can set up a set of key-values rules,
         * use [ValueRule] to create new rule for parsing the obtained key-values content.
         *
         * These key-values rules are applied when properties' keys exist.
         * @param rules the key-values array.
         */
        fun keyValuesRules(rules: Map<String, PropertyValueRule>) {
            Gropify.require(rules.isNotEmpty() && rules.all { it.key.isNotBlank() }) {
                "Key-values rules must not be empty or have blank contents."
            }

            keyValuesRules = rules.toMutableMap()
        }

        /**
         * Create a new properties' values rule.
         * @param type specify the expected type class, or null to auto-detect,
         * if the [useTypeAutoConversion] is not enabled, this parameter will be ignored.
         * @param rule callback current rule.
         * @return [PropertyValueRule]
         */
        @JvmOverloads
        fun ValueRule(type: KClass<*>? = null, rule: PropertyValueMapper = { it }) = rule to type

        /**
         * Set where to find properties' key-values.
         *
         * Defaults are [GropifyLocation.CurrentProject], [GropifyLocation.RootProject].
         *
         * You can set this up using the following types.
         *
         * - [GropifyLocation.CurrentProject]
         * - [GropifyLocation.RootProject]
         * - [GropifyLocation.Global]
         * - [GropifyLocation.System]
         * - [GropifyLocation.SystemEnv]
         *
         * We will generate properties' key-values in sequence from the locations you set,
         * the order of the generation locations follows the order you set.
         *
         * - Risk warning: [GropifyLocation.Global], [GropifyLocation.System],
         *   [GropifyLocation.SystemEnv] may have keys and certificates,
         *   please manage the generated code carefully.
         * @param types the location type array.
         */
        fun locations(vararg types: GropifyLocation) {
            locations = types.toList()
        }
    }

    /**
     * Build [GropifyConfig] from current extension.
     * @param settings the Gradle [Settings] instance.
     * @return [GropifyConfig]
     */
    internal fun build(settings: Settings): GropifyConfig {
        fun String.checkingStartWithLetter(description: String) {
            Gropify.require(isBlank() || isStartsWithLetter()) {
                "$description name \"$this\" must start with a letter."
            }
        }

        fun String.checkingPackageName() {
            Gropify.require(isBlank() || KeywordsDetector.verifyPackage(this)) {
                "Illegal package name \"$this\"."
            }
        }

        fun String.checkingClassName() {
            Gropify.require(isBlank() || KeywordsDetector.verifyClass(this)) {
                "Illegal class name \"$this\"."
            }
        }

        fun String.checkingValidExtensionName() {
            checkingStartWithLetter(description = "Extension")
            if (isNotBlank() && isUnSafeExtName()) Gropify.error("This name \"$this\" is a Gradle built-in extension。")
        }

        fun GenerateConfigureScope.checkingNames() {
            buildscriptConfigure?.extensionName?.checkingValidExtensionName()

            androidConfigure?.packageName?.checkingPackageName()
            androidConfigure?.className?.checkingClassName()

            jvmConfigure?.packageName?.checkingPackageName()
            jvmConfigure?.className?.checkingClassName()

            kmpConfigure?.packageName?.checkingPackageName()
            kmpConfigure?.className?.checkingClassName()
        }

        val currentEnabled = isEnabled
        val currentDebugMode = debugMode
        val currentGlobal = globalConfigure.create()
        val currentProjects = mutableMapOf<String, GropifyConfig.GenerateConfig>()
        val rootName = settings.rootProject.name

        globalConfigure.checkingNames()

        Gropify.require(projectConfigures.none { (name, _) -> name.lowercase() == rootName.lowercase() }) {
            "This project name '$rootName' is a root project, please use `rootProject` function to configure it, not `projects(\"$rootName\")`."
        }

        if (projectConfigures.containsKey(ROOT_PROJECT_TAG)) {
            projectConfigures[rootName] = projectConfigures[ROOT_PROJECT_TAG]!!
            projectConfigures.remove(ROOT_PROJECT_TAG)
        }

        projectConfigures.forEach { (name, subConfigure) ->
            name.replaceFirst(":", "").checkingStartWithLetter(description = "Project")
            subConfigure.checkingNames()

            currentProjects[name] = subConfigure.create(name, globalConfigure)
        }

        return object : GropifyConfig {
            override val isEnabled get() = currentEnabled
            override val debugMode get() = currentDebugMode
            override val global get() = currentGlobal
            override val projects get() = currentProjects
        }
    }
}