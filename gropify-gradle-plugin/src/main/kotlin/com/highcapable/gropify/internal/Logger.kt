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

/**
 * Gropify logger.
 */
internal class Logger private constructor() {

    internal companion object {

        /**
         * Create logger with project.
         * @param project the project.
         * @return [Logger]
         */
        fun with(project: Project) = Logger().apply {
            logger = project.logger
        }

        /**
         * Create empty logger instance.
         * @return [Logger]
         */
        fun get() = Logger()
    }

    private var logger: Logger? = null

    internal fun debug(msg: Any) = "[${Gropify.TAG}][DEBUG] $msg".let { logger?.debug(it) ?: println(it) }
    internal fun info(msg: Any) = "[${Gropify.TAG}][INFO] $msg".let { logger?.info(it) ?: println(it) }
    internal fun warn(msg: Any) = "[${Gropify.TAG}][WARN] $msg".let { logger?.warn(it) ?: println(it) }
    internal fun error(msg: Any) = "[${Gropify.TAG}][ERROR] $msg".let { logger?.error(it) ?: println(it) }
}