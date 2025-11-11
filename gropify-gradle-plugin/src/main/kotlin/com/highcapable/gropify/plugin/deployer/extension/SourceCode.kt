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
package com.highcapable.gropify.plugin.deployer.extension

import com.highcapable.gropify.gradle.api.extension.get
import com.highcapable.gropify.gradle.api.extension.hasExtension
import com.highcapable.gropify.gradle.api.extension.toClassOrNull
import com.highcapable.kavaref.extension.isSubclassOf
import org.gradle.api.Project

/**
 * Resolve current project type.
 * @receiver [Project]
 * @return [ProjectType]
 */
internal fun Project.resolveType() = when {
    hasKmpPlugin() -> ProjectType.KMP
    hasExtension(ExtensionName.ANDROID) -> ProjectType.Android
    hasExtension(ExtensionName.KOTLIN) -> ProjectType.Kotlin
    hasExtension(ExtensionName.JAVA) -> ProjectType.Java
    else -> ProjectType.Unknown
}

/**
 * Project extension names.
 */
internal object ExtensionName {

    const val ANDROID = "android"
    const val JAVA = "java"
    const val KOTLIN = "kotlin"

    const val KMP_EXTENSION_CLASS = "org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension"
}

/**
 * Check if current project has Kotlin Multiplatform plugin applied.
 * @receiver [Project]
 * @return [Boolean]
 */
internal fun Project.hasKmpPlugin(): Boolean {
    // The extension names of Kotlin and KMP are the same.
    val hasKotlin = hasExtension(ExtensionName.KOTLIN)
    // The KMP extensions must inherit from [KMP_EXTENSION_CLASS].
    val kmpClass = ExtensionName.KMP_EXTENSION_CLASS.toClassOrNull(this)
    val hasKmpExtension = if (hasKotlin && kmpClass != null)
        get(ExtensionName.KOTLIN).javaClass isSubclassOf kmpClass
    else false

    return hasKmpExtension
}

internal enum class ProjectType {
    Android,
    Kotlin,
    Java,
    KMP, // Kotlin Multiplatform
    Unknown
}