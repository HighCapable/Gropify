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
 * This file is created by fankes on 2025/10/16.
 */
@file:Suppress("unused", "LoggingStringTemplateAsArgument")

package com.highcapable.gropify.internal

import com.highcapable.gropify.plugin.Gropify
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import kotlin.properties.Delegates

/**
 * Gropify logger.
 */
internal object Logger {

    private var logger by Delegates.notNull<Logger>()

    /**
     * Initialize logger with project.
     * @param project the project.
     * @return [Logger]
     */
    fun init(project: Project) = apply {
        logger = project.logger
    }

    internal fun debug(msg: Any) = logger.debug("[${Gropify.TAG}] $msg")
    internal fun info(msg: Any) = logger.info("[${Gropify.TAG}] $msg")
    internal fun warn(msg: Any) = logger.warn("[${Gropify.TAG}] $msg")
    internal fun error(msg: Any) = logger.error("[${Gropify.TAG}] $msg")
}