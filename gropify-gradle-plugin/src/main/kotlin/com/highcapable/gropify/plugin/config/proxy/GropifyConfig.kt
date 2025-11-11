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
package com.highcapable.gropify.plugin.config.proxy

import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.config.type.GropifyLocation
import com.highcapable.gropify.plugin.generator.extension.PropertyValueRule

/**
 * Configuration interface for Gropify.
 */
internal interface GropifyConfig {

    companion object {

        internal const val ARTIFACTS_NAME = "artifacts"
        internal const val ACCESSORS_NAME = "gropify-accessors"

        internal const val DEFAULT_PACKAGE_NAME = "${Gropify.GROUP_NAME}.properties.default"

        internal const val DEFAULT_COMMON_CODE_GENERATE_DIR_PATH = "build/generated/gropify"
        internal const val DEFAULT_ANDROID_JVM_SOURCE_SET_NAME = "main"
        internal const val DEFAULT_KMP_COMMON_SOURCE_SET_NAME = "commonMain"

        internal const val DEFAULT_EXISTS_PROPERTY_FILE = "gradle.properties"
        internal const val DEFAULT_EXTENSION_NAME = "gropify"

        internal val defaultLocations = listOf(GropifyLocation.CurrentProject, GropifyLocation.RootProject)
    }

    /** Whether to enable this plugin. */
    val isEnabled: Boolean

    /** Whether to enable debug mode. */
    val debugMode: Boolean

    /** Configure global. */
    val global: GenerateConfig

    /** Configure projects. */
    val projects: MutableMap<String, GenerateConfig>

    /**
     * Generate configuration interface.
     */
    interface GenerateConfig {

        val buildscript: BuildscriptGenerateConfig

        val android: AndroidGenerateConfig

        val jvm: JvmGenerateConfig

        val kmp: KmpGenerateConfig
    }

    /**
     * Buildscript generation code configuration interface.
     */
    interface BuildscriptGenerateConfig : CommonGenerateConfig {

        /** Custom buildscript extension name. */
        val extensionName: String
    }

    /**
     * Android project generate configuration interface.
     */
    interface AndroidGenerateConfig : JvmGenerateConfig

    /**
     * Jvm project generate configuration interface.
     */
    interface JvmGenerateConfig : CommonCodeGenerateConfig {

        /** Whether to use Kotlin language generation. */
        val useKotlin: Boolean
    }

    /**
     * Kotlin Multiplatform project generate configuration interface.
     */
    interface KmpGenerateConfig : CommonCodeGenerateConfig

    /**
     * Project common code generate configuration interface.
     */
    interface CommonCodeGenerateConfig : SourceCodeGenerateConfig {

        /** Custom deployment `sourceSet` name. */
        val sourceSetName: String

        /** Custom generated package name. */
        val packageName: String

        /** Custom generated class name. */
        val className: String

        /** Whether to enable restricted access. */
        val isRestrictedAccessEnabled: Boolean

        /** Whether to enable code isolation. */
        val isIsolationEnabled: Boolean
    }

    /**
     * Project code generate configuration interface.
     */
    interface SourceCodeGenerateConfig : CommonGenerateConfig {

        /** Custom generated directory path. */
        val generateDirPath: String
    }

    /**
     * Common generate configuration interface.
     */
    interface CommonGenerateConfig {

        /** The config name (project name). */
        val name: String

        /** Whether to generate code. */
        val isEnabled: Boolean

        /** Exists property files. */
        val existsPropertyFiles: MutableList<String>

        /** Permanent list of properties' key-values. */
        val permanentKeyValues: MutableMap<String, Any>

        /** Replacement list of properties' key-values. */
        val replacementKeyValues: MutableMap<String, Any>

        /** Key list of properties' key-values name that need to be excluded. */
        val excludeKeys: MutableList<Any>

        /** Key list of properties' key-values name that need to be included. */
        val includeKeys: MutableList<Any>

        /** Properties' key-values rules. */
        val keyValuesRules: MutableMap<String, PropertyValueRule>

        /** Whether to exclude the non-string type key-values content. */
        val excludeNonStringValue: Boolean

        /** Whether to use automatic type conversion. */
        val useTypeAutoConversion: Boolean

        /** Whether to use key-values content interpolation. */
        val useValueInterpolation: Boolean

        /** Locations. */
        val locations: List<GropifyLocation>
    }
}