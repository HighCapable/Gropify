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
 * This file is created by fankes on 2025/10/8.
 */
@file:Suppress("unused")

package com.highcapable.gropify.plugin

import com.highcapable.gropify.internal.Logger
import com.highcapable.gropify.internal.error
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.ExtensionAware

/**
 * Entry class for Gropify plugin.
 */
class GropifyPlugin<T : ExtensionAware> internal constructor() : Plugin<T> {

    private val lifecycle = GropifyLifecycle()

    override fun apply(target: T) = when (target) {
        is Settings -> {
            val lifecycle = this.lifecycle

            lifecycle.onCreate(target)

            target.gradle.settingsEvaluated {
                lifecycle.onSettingsEvaluated(target)
            }

            target.gradle.projectsLoaded {
                rootProject.beforeEvaluate {
                    lifecycle.beforeProjectEvaluate(rootProject = this)
                }
                rootProject.afterEvaluate {
                    lifecycle.afterProjectEvaluate(rootProject = this)
                }
            }
        }
        is Project -> Logger.with(target).error(
            "Gropify can only applied in settings.gradle or settings.gradle.kts, but current is $target, stop loading.",
        )
        else -> Gropify.error("Gropify applied to an unknown target: $target, stop loading.")
    }
}