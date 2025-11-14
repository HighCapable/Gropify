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
 * This file is created by fankes on 2025/11/13.
 */
package com.highcapable.gropify.plugin.generator.extension

import com.highcapable.gropify.internal.error
import com.highcapable.gropify.internal.require
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.utils.extension.isNumeric
import kotlin.reflect.KClass

/**
 * Create [PropertyTypeValue] from [String] value.
 * @receiver [String]
 * @param autoConversion whether to enable auto conversion.
 * @param key the property key name.
 * @param type specify the expected type class, or null to auto-detect,
 * if the [autoConversion] is not `true`, this parameter will be ignored.
 * @return [PropertyTypeValue]
 */
internal fun String.createTypeValue(autoConversion: Boolean, key: String, type: KClass<*>? = null): PropertyTypeValue {
    var isStringType = false
    val valueString = replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\\", "\\\\")
        .let {
            if (autoConversion && (it.startsWith("\"") && it.endsWith("\"") || it.startsWith("'") && it.endsWith("'"))) {
                isStringType = true
                it.drop(1).dropLast(1)
            } else it.replace("\"", "\\\"")
        }

    if (!autoConversion) return PropertyTypeValue(this, "\"$valueString\"", String::class)

    val trimmed = valueString.trim()

    if (type != null) {
        val finalValue = when (type) {
            String::class -> "\"$valueString\""
            CharSequence::class -> "\"$valueString\""
            Char::class -> trimmed.firstOrNull()?.let { "'$it'" }
                ?: Gropify.error("The \"$key\" value is empty and cannot be converted to Char type.")
            Boolean::class -> if (trimmed.toBooleanStrictOrNull() != null)
                trimmed
            else ((trimmed.toIntOrNull() ?: 0) > 0).toString()
            Int::class -> {
                val intValue = trimmed.toIntOrNull()
                Gropify.require(intValue != null && intValue in Int.MIN_VALUE..Int.MAX_VALUE) {
                    "The \"$key\" value \"$this\" cannot be converted to Int type."
                }

                trimmed
            }
            Long::class -> {
                Gropify.require(trimmed.toLongOrNull() != null) {
                    "The \"$key\" value \"$this\" cannot be converted to Long type."
                }

                if (trimmed.endsWith("L")) trimmed else "${trimmed}L"
            }
            Double::class -> {
                val doubleValue = trimmed.toDoubleOrNull()
                Gropify.require(doubleValue != null && !doubleValue.isInfinite()) {
                    "The \"$key\" value \"$this\" cannot be converted to Double type."
                }
                    
                trimmed
            }
            Float::class -> {
                val floatValue = trimmed.toFloatOrNull()
                Gropify.require(floatValue != null && !floatValue.isInfinite()) {
                    "The \"$key\" value \"$this\" cannot be converted to Float type."
                }
                    
                if (trimmed.endsWith("f") || trimmed.endsWith("F")) trimmed else "${trimmed}f"
            }
            else -> Gropify.error(
                "Unsupported property \"$key\" value type: ${type.qualifiedName}, " +
                    "only String, CharSequence, Char, Boolean, Int, Long, Float, Double are supported."
            )
        }

        return PropertyTypeValue(this, finalValue, type)
    }

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

    return PropertyTypeValue(this, finalValue, typeSpec)
}

/**
 * Create [PropertyTypeValue] from [Any] value's type.
 * @receiver [Any]
 * @param autoConversion whether to enable auto conversion.
 * @param key the property key name.
 * @return [PropertyTypeValue]
 */
internal fun Any.createTypeValueByType(autoConversion: Boolean, key: String): PropertyTypeValue {
    val typeSpec = this.javaClass.kotlin
    val valueString = toString()
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")

    val trimmed = valueString.trim()
    
    if (!autoConversion) return PropertyTypeValue(this.toString(), "\"$valueString\"", String::class)

    val finalValue = when (typeSpec) {
        String::class, CharSequence::class -> "\"$trimmed\""
        Char::class -> "'$trimmed'"
        Boolean::class -> trimmed
        Long::class -> if (trimmed.endsWith("L")) trimmed else "${trimmed}L"
        Float::class -> if (trimmed.endsWith("f") || trimmed.endsWith("F")) trimmed else "${trimmed}f"
        Int::class, Double::class -> trimmed
        else -> Gropify.error(
            "Unsupported property \"$key\" value type: ${typeSpec.qualifiedName}, " +
                "only String, CharSequence, Char, Boolean, Int, Long, Float, Double are supported."
        )
    }

    return PropertyTypeValue(this.toString(), finalValue, typeSpec)
}

/**
 * Property type value entity.
 */
internal data class PropertyTypeValue(
    val raw: String,
    val codeValue: String,
    val type: KClass<*>
)