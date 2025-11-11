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
 * This file is created by fankes on 2025/10/20.
 */
package com.highcapable.gropify.utils

/**
 * Java keywords detector.
 */
internal object KeywordsDetector {

    private val javaKeywords = setOf(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
        "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
        "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
        "interface", "long", "native", "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void", "volatile", "while"
    )

    private val invalidPattern = "^(\\d.*|.*[^A-Za-z0-9_\$].*)$".toRegex()

    /**
     * Verify if the name is a valid Java package name.
     * @param name the name to verify.
     * @return [Boolean] `true` if valid, `false` otherwise.
     */
    fun verifyPackage(name: String) = name !in javaKeywords && !invalidPattern.matches(name) ||
        (name.contains(".") && !name.contains("..") &&
            name.split(".").all { it !in javaKeywords && !invalidPattern.matches(it) })

    /**
     * Verify if the name is a valid Java class name.
     * @param name the name to verify.
     * @return [Boolean] `true` if valid, `false` otherwise.
     */
    fun verifyClass(name: String) = name !in javaKeywords && !invalidPattern.matches(name)
}