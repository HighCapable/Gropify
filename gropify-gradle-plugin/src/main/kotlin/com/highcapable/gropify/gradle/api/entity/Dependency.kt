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
package com.highcapable.gropify.gradle.api.entity

/**
 * Dependency entity.
 * @param groupId the group ID.
 * @param artifactId the artifact ID.
 * @param version the current version.
 */
internal data class Dependency(
    val groupId: String,
    val artifactId: String,
    val version: String
) {

    /**
     * Get [Dependency] relative path.
     * @return [String]
     */
    val relativePath get() = "${groupId.toPathName()}/$artifactId/$version"

    private fun String.toPathName() = trim()
        .replace(".", "/")
        .replace("_", "/")
        .replace(":", "/")
        .replace("-", "/")

    override fun toString() = "$groupId:$artifactId:$version"
}