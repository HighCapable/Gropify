/*
 * SweetProperty -  An easy get project properties anywhere Gradle plugin.
 * Copyright (C) 2019 HighCapable
 * https://github.com/HighCapable/SweetProperty
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
 * This file is created by fankes on 2023/8/28.
 */
package com.highcapable.gropify.gradle.api.entity

import com.highcapable.gropify.debug.error
import com.highcapable.gropify.gradle.api.extension.getFullName
import com.highcapable.gropify.plugin.Gropify
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import kotlin.properties.Delegates

/**
 * Project description implementation.
 */
internal class ProjectDescriptor private constructor() {

    internal companion object {

        /**
         * Create [ProjectDescriptor] from [Settings].
         * @param settings the current settings.
         * @param name the project name, leave it blank to get root project.
         * @return [ProjectDescriptor]
         */
        fun create(settings: Settings, name: String = "") = ProjectDescriptor().also {
            val isRootProject = name.isBlank() || name.lowercase() == settings.rootProject.name.lowercase()
            val subProjectNotice = "if this is a sub-project, please set it like \":$name\""

            it.type = Type.Settings
            it.name = name.ifBlank { null } ?: settings.rootProject.name
            it.currentDir = (if (isRootProject) settings.rootProject else settings.findProject(name))?.projectDir
                ?: Gropify.error("Project '$name' not found${if (!name.startsWith(":")) ", $subProjectNotice." else "."}")
            it.rootDir = settings.rootDir
            it.homeDir = settings.gradle.gradleUserHomeDir
        }

        /**
         * Create [ProjectDescriptor] from [Project].
         * @param project the current project.
         * @return [ProjectDescriptor]
         */
        fun create(project: Project) = ProjectDescriptor().also {
            it.type = Type.Project
            it.name = project.getFullName()
            it.currentDir = project.projectDir
            it.rootDir = project.rootDir
            it.homeDir = project.gradle.gradleUserHomeDir
        }
    }

    /** The buildscript type. */
    var type by Delegates.notNull<Type>()

    /** The project name. */
    var name = ""

    /** The current project directory. */
    var currentDir by Delegates.notNull<File>()

    /** The root project directory. */
    var rootDir by Delegates.notNull<File>()

    /** The Gradle home directory. */
    var homeDir by Delegates.notNull<File>()

    /**
     * Project type definition.
     */
    enum class Type {
        Settings,
        Project
    }

    override fun toString() = "ProjectDescriptor(type=$type, name=$name)"
}