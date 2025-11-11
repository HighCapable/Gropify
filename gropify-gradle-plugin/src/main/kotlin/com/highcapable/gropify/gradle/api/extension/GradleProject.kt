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
package com.highcapable.gropify.gradle.api.extension

import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.utils.extension.toFile
import com.highcapable.kavaref.extension.toClassOrNull
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.repositories

/**
 * Get the full name of the specified project.
 * @receiver [Project]
 * @param useColon whether to use a colon before sub-items, default is true.
 * @return [String]
 */
internal fun Project.getFullName(useColon: Boolean = true): String {
    val isRoot = this == rootProject
    val baseNames = mutableListOf<String>()

    fun fetchChild(project: Project) {
        project.parent?.also { if (it != it.rootProject) fetchChild(it) }
        baseNames.add(project.name)
    }

    fetchChild(project = this)

    return buildString {
        baseNames.onEach { append(":$it") }.clear()
    }.let { if (useColon && !isRoot) it else it.drop(1) }
}

/**
 * Add custom dependency to the buildscript.
 * @receiver [Project]
 * @param repositoryPath the repository path.
 * @param dependency the dependency entity.
 */
internal fun Project.addDependencyToBuildscript(repositoryPath: String, dependency: Dependency) {
    buildscript {
        repositories {
            maven {
                url = repositoryPath.toFile().toURI()
                mavenContent { includeGroup(dependency.groupId) }
            }
        }

        dependencies {
            classpath(dependency.toString())
        }
    }
}

/**
 * Convert string to class or null by project buildscript classloader.
 * @receiver [String]
 * @param project the target [Project].
 * @return [Class] or null.
 */
internal fun String.toClassOrNull(project: Project) = toClassOrNull(project.buildscript.classLoader)