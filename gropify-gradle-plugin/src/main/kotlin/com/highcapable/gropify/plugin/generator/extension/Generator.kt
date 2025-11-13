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
package com.highcapable.gropify.plugin.generator.extension

import com.highcapable.gropify.utils.extension.isNumeric
import com.highcapable.gropify.utils.extension.underscore
import kotlin.reflect.KClass

internal typealias PropertyMap = MutableMap<String, Any>
internal typealias PropertyOptimizeMap = MutableMap<String, Pair<String, Any>>
internal typealias PropertyValueRule = (value: String) -> String

/**
 * Create typed value from [Any] value.
 * @param autoConversion whether to enable auto conversion.
 * @return [Pair]<[KClass], [String]>
 */
internal fun Any.createTypedValue(autoConversion: Boolean): Pair<KClass<*>, String> {
    var isStringType = false
    val valueString = toString()
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\\", "\\\\")
        .let {
            if (autoConversion && (it.startsWith("\"") && it.endsWith("\"") || it.startsWith("'") && it.endsWith("'"))) {
                isStringType = true
                it.drop(1).dropLast(1)
            } else it.replace("\"", "\\\"")
        }

    if (!autoConversion) return String::class to "\"$valueString\""

    val trimmed = valueString.trim()
    val typeSpec = when {
        isStringType -> String::class
        trimmed.toBooleanStrictOrNull() != null -> Boolean::class
        trimmed.isNumeric() ->
            if (!trimmed.contains(".")) {
                val longValue = trimmed.toLongOrNull()
                when (longValue) {
                    null -> String::class
                    in Int.MIN_VALUE..Int.MAX_VALUE -> Int::class
                    else -> Long::class
                }
            } else {
                val doubleValue = trimmed.toDoubleOrNull()
                if (doubleValue == null || doubleValue.isInfinite()) 
                    String::class
                else Double::class
            }
        else -> String::class
    }
    val finalValue = when (typeSpec) {
        String::class -> "\"$valueString\""
        Long::class -> if (trimmed.endsWith("L")) trimmed else "${trimmed}L"
        else -> trimmed
    }

    return typeSpec to finalValue
}

/**
 * Optimize property keys for code generation.
 * @receiver [PropertyMap]
 * @return [PropertyOptimizeMap]
 */
internal fun PropertyMap.toOptimize(): PropertyOptimizeMap {
    val newMap: PropertyOptimizeMap = mutableMapOf()
    var uniqueNumber = 1

    forEach { (key, value) ->
        var newKey = key.replace("\\W".toRegex(), "_")
        while (newMap.containsKey(newKey))
            newKey = "$newKey${++uniqueNumber}"

        newMap[newKey] = key to value
    }

    return newMap
}

/**
 * Optimize property keys to underscores for code generation.
 * @receiver [PropertyOptimizeMap]
 * @return [PropertyOptimizeMap]
 */
internal fun PropertyOptimizeMap.toUnderscores(): PropertyOptimizeMap {
    val newMap: PropertyOptimizeMap = mutableMapOf()
    var uniqueNumber = 1

    forEach { (key, value) ->
        var newKey = key.underscore()
        while (newMap.containsKey(newKey))
            newKey = "$newKey${++uniqueNumber}"

        newMap[newKey] = value.first to value.second
    }

    return newMap
}

/**
 * Replace spaces to middle dot for code generation.
 * @receiver [String]
 * @return [String]
 */
internal fun String.toPoetSpace() = replace(" ", "·")

/**
 * Escape percentage signs for code generation.
 * @receiver [String]
 * @return [String]
 */
internal fun String.toPoetNoEscape() = replace("%", "%%")