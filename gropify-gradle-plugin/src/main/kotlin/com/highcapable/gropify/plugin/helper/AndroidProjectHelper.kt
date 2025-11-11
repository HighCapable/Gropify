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
 * This file is created by fankes on 2025/10/23.
 */
package com.highcapable.gropify.plugin.helper

import com.highcapable.gropify.gradle.api.extension.getFullName
import com.highcapable.gropify.gradle.api.extension.getOrNull
import com.highcapable.gropify.gradle.api.extension.hasExtension
import com.highcapable.gropify.internal.Logger
import com.highcapable.gropify.plugin.deployer.extension.ExtensionName
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import com.highcapable.kavaref.KavaRef.Companion.asResolver
import org.gradle.api.Project
import tools.jackson.module.kotlin.jacksonObjectMapper
import tools.jackson.module.kotlin.readValue

/**
 * Android (AGP) project helper.
 *
 * Why we need this? Because the namespace provided by AGP may become unstable
 * after the Gradle daemon is restarted, we manually maintain a file-level cache.
 */
internal object AndroidProjectHelper {

    private const val ANDROID_PROJECT_NAMESPACES_FILE = "android-project-namespaces.json"

    private val jsonMapper = jacksonObjectMapper()

    /**
     * Get Android project namespace.
     * @receiver [Project]
     * @param project the current project.
     * @return [String] or null.
     */
    fun getNamespace(project: Project): String? {
        // If not an Android project, return null directly.
        if (!project.hasExtension(ExtensionName.ANDROID)) return null

        return project.getConfigNamespace()
    } 

    private fun Project.resolveNamespacesFile() =
        rootProject.file(".gradle")
            .resolve(GropifyConfigureExtension.NAME)
            .resolve(ANDROID_PROJECT_NAMESPACES_FILE)

    private fun Project.getConfigNamespace(): String? {
        val namespacesFile = resolveNamespacesFile()

        if (namespacesFile.parentFile?.exists() == false) namespacesFile.parentFile.mkdirs()
        if (!namespacesFile.exists()) namespacesFile.writeText("{}")

        val namespaces = runCatching {
            jsonMapper.readValue<HashMap<String, String>>(namespacesFile)
        }.onFailure {
            // If file broken, reset it.
            namespacesFile.writeText("{}")
            Logger.warn("Android project namespaces file was broken and has been reset.")
        }.getOrDefault(hashMapOf())

        val namespace = getExtensionNamespace()
        if (namespace != null) {
            namespaces[getFullName()] = namespace
            jsonMapper.writeValue(namespacesFile, namespaces)
        }

        return namespace ?: namespaces[getFullName()]
    }

    private fun Project.getExtensionNamespace(): String? {
        val extension = getOrNull(ExtensionName.ANDROID)

        return extension?.asResolver()?.optional(silent = true)?.firstMethodOrNull {
            name = "getNamespace"
        }?.invokeQuietly<String>()?.ifBlank { null }
    }
}