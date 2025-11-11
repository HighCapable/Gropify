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
@file:Suppress("unused")

package com.highcapable.gropify.utils.extension

import java.io.File

/**
 * Convert string path to file.
 *
 * Auto called by [parseFileSeparator].
 * @receiver [String]
 * @return [File]
 */
internal fun String.toFile() = File(parseFileSeparator())

/**
 * Format to the file delimiter of the current operating system.
 * @receiver [String]
 * @return [String]
 */
internal fun String.parseFileSeparator() = replace("/", File.separator).replace("\\", File.separator)

/**
 * File delimiters formatted to Unix operating systems.
 * @receiver [String]
 * @return [String]
 */
internal fun String.parseUnixFileSeparator() = replace("\\", "/")

/**
 * Check if directory is empty.
 *
 * If not a directory (possibly a file), returns true.
 *
 * If the file does not exist, returns true.
 * @receiver [File]
 * @return [Boolean]
 */
internal fun File.isEmpty() = !exists() || !isDirectory || listFiles().isNullOrEmpty()

/**
 * Delete empty subdirectories under a directory.
 * @receiver [File]
 */
internal fun File.deleteEmptyRecursively() {
    listFiles { file -> file.isDirectory }?.forEach { subDir ->
        subDir.deleteEmptyRecursively()
        if (subDir.listFiles()?.isEmpty() == true) subDir.delete()
    }
}