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
package com.highcapable.gropify.plugin

import com.highcapable.gropify.debug.Logger
import com.highcapable.gropify.debug.error
import com.highcapable.gropify.gradle.api.GradleDescriptor
import com.highcapable.gropify.gradle.api.extension.getOrCreate
import com.highcapable.gropify.gradle.api.plugin.PluginLifecycle
import com.highcapable.gropify.plugin.extension.dsl.configure.GropifyConfigureExtension
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

/**
 * Lifecycle for Gropify.
 */
internal class GropifyLifecycle : PluginLifecycle {

    private var configure: GropifyConfigureExtension? = null

    override fun onCreate(settings: Settings) {
        GradleDescriptor.init(settings)

        configure = settings.getOrCreate<GropifyConfigureExtension>(GropifyConfigureExtension.NAME)
    }

    override fun onSettingsEvaluated(settings: Settings) {
        val config = configure?.build(settings) ?: Gropify.error("Extension \"${GropifyConfigureExtension.NAME}\" create failed.")

        Logger.debugMode = config.debugMode
        Logger.debug("Gropify ${Gropify.VERSION} running on Gradle ${GradleDescriptor.version}")
        Logger.debug("Loaded configuration.")

        DefaultDeployer.init(settings, config)
    }

    override fun beforeProjectEvaluate(rootProject: Project) {
        Logger.debug("Before project evaluate: $rootProject")

        DefaultDeployer.resolve(rootProject)
    }

    override fun afterProjectEvaluate(rootProject: Project) {
        Logger.debug("After project evaluate: $rootProject")

        DefaultDeployer.deploy(rootProject)
    }
}