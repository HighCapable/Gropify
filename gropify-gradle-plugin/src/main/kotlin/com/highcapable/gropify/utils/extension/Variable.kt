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
package com.highcapable.gropify.utils.extension

private val numericRegex = """^-?(\d+(\.\d+)?|\.\d+)$""".toRegex()

/**
 * Convert the current [Map] key value to string type.
 * @receiver [Map]<[K], [V]>
 * @return [Map]<[String], [String]>
 */
internal inline fun <reified K, V> Map<K, V>.toStringMap() = mapKeys { e -> e.key.toString() }.mapValues { e -> e.value.toString() }

/**
 * Remove surrounding single and double quotes from a string.
 * @receiver [String]
 * @return [String]
 */
internal fun String.removeSurroundingQuotes() = removeSurrounding("\"").removeSurrounding("'")

/**
 * Flatten string processing.
 *
 * Remove all spaces and convert to lowercase letters.
 * @receiver [String]
 * @return [String]
 */
internal fun String.flatted() = replace(" ", "").lowercase()

/**
 * Camelcase, "-", "." are converted to uppercase and underline names.
 * @receiver [String]
 * @return [String]
 */
internal fun String.underscore() = replace(".", "_")
    .replace("-", "_")
    .replace(" ", "_")
    .replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")
    .uppercase()

/**
 * Convert underline, separator, dot, and space named strings to camelcase named strings.
 * @receiver [String]
 * @return [String]
 */
internal fun String.camelcase() = runCatching {
    split("_", ".", "-", " ").map { it.replaceFirstChar { e -> e.titlecase() } }.let { words ->
        words.first().replaceFirstChar { it.lowercase() } + words.drop(1).joinToString("")
    }
}.getOrNull() ?: this

/**
 * Convert underline, separator, dot, and space named strings to camelCase named string.
 * @receiver [String]
 * @return [String]
 */
internal fun String.upperCamelcase() = camelcase().capitalize()

/**
 * Capitalize the first letter of a string.
 * @receiver [String]
 * @return [String]
 */
internal fun String.capitalize() = replaceFirstChar { it.uppercaseChar() }

/**
 * Lowercase the first letter of string.
 * @receiver [String]
 * @return [String]
 */
internal fun String.uncapitalize() = replaceFirstChar { it.lowercaseChar() }

/**
 * Converts the first digit of a string to approximately uppercase letters.
 * @receiver [String]
 * @return [String]
 */
internal fun String.firstNumberToLetter() =
    if (isNotBlank()) (mapOf(
        '0' to 'O', '1' to 'I',
        '2' to 'Z', '3' to 'E',
        '4' to 'A', '5' to 'S',
        '6' to 'G', '7' to 'T',
        '8' to 'B', '9' to 'P'
    )[first()] ?: first()) + substring(1)
    else this

/**
 * Whether the string is a numeric type.
 * @receiver [String]
 * @return [Boolean]
 */
internal fun String.isNumeric() = numericRegex.matches(this)

/**
 * Whether the first character of the string is a letter.
 * @receiver [String]
 * @return [Boolean]
 */
internal fun String.isStartsWithLetter() = firstOrNull()?.let {
    it in 'A'..'Z' || it in 'a'..'z'
} == true

/**
 * Whether the interpolation symbol `${...}` exists in the string.
 * @receiver [String]
 * @return [Boolean]
 */
internal fun String.hasInterpolation() = contains("\${") && contains("}")

/**
 * Replace interpolation symbols `${...}` in a string.
 * @receiver [String]
 * @param result call the result.
 * @return [String]
 */
internal fun String.replaceInterpolation(result: (groupValue: String) -> CharSequence) =
    "\\$\\{(.+?)}".toRegex().replace(this) { result(it.groupValues[1]) }