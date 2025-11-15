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
package com.highcapable.gropify.gradle.api.extension

import com.highcapable.gropify.debug.error
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.utils.extension.camelcase
import com.highcapable.kavaref.extension.classOf
import org.gradle.api.Action
import org.gradle.api.plugins.ExtensionAware

/**
 * Create or get extension.
 * @receiver [ExtensionAware]
 * @param name the name, auto called by [toSafeExtName].
 * @param target the target class.
 * @param args the constructor arguments.
 * @return [ExtensionAware]
 */
internal fun ExtensionAware.getOrCreate(name: String, target: Class<*>, vararg args: Any) = name.toSafeExtName().let { sName ->
    runCatching { extensions.create(sName, target, *args).asExtension() }.getOrElse {
        if (!(it is IllegalArgumentException && it.message?.startsWith("Cannot add extension with name.") == true)) throw it
        runCatching { extensions.getByName(sName).asExtension() }.getOrNull() ?: Gropify.error("Create or get extension failed with name \"$sName\".")
    }
}

/**
 * Create or get extension [T].
 * @receiver [ExtensionAware]
 * @param name the name, auto called by [toSafeExtName].
 * @param args the constructor arguments.
 * @return [ExtensionAware]
 */
internal inline fun <reified T : Any> ExtensionAware.getOrCreate(name: String, vararg args: Any) = name.toSafeExtName().let { sName ->
    runCatching { extensions.create(sName, classOf<T>(), *args) }.getOrElse {
        if (!(it is IllegalArgumentException && it.message?.startsWith("Cannot add extension with name.") == true)) throw it
        runCatching { extensions.getByName(sName) as? T? }.getOrNull() ?: Gropify.error("Create or get extension failed with name \"$sName\".")
    }
}

/**
 * Get extension.
 * @receiver [ExtensionAware]
 * @param name the name.
 * @return [ExtensionAware]
 */
internal fun ExtensionAware.get(name: String) = runCatching {
    extensions.getByName(name).asExtension()
}.getOrNull() ?: Gropify.error("Could not get extension with name \"$name\".")

/**
 * Get extension or null if not exists.
 * @receiver [ExtensionAware]
 * @param name the name.
 * @return [ExtensionAware] or null.
 */
internal fun ExtensionAware.getOrNull(name: String) = runCatching {
    extensions.getByName(name).asExtension()
}.getOrNull()

/**
 * Get extension, target [T].
 * @receiver [ExtensionAware]
 * @param name the name.
 * @return [T]
 */
internal inline fun <reified T> ExtensionAware.get(name: String) = runCatching {
    extensions.getByName(name) as T
}.getOrNull() ?: Gropify.error("Could not get extension with name \"$name\".")

/**
 * Get extension, target [T].
 * @receiver [ExtensionAware]
 * @return [T]
 */
internal inline fun <reified T : Any> ExtensionAware.get() = runCatching {
    extensions.getByType(classOf<T>())
}.getOrNull() ?: Gropify.error("Could not get extension with type ${classOf<T>()}.")

/**
 * Configure extension, target [T].
 * @receiver [ExtensionAware]
 * @param name the name.
 * @param configure the configure action.
 */
internal inline fun <reified T : Any> ExtensionAware.configure(name: String, configure: Action<T>) = extensions.configure(name, configure)

/**
 * Detect whether the extension exists.
 * @receiver [ExtensionAware]
 * @param name the name.
 * @return [Boolean]
 */
internal fun ExtensionAware.hasExtension(name: String) = runCatching { extensions.getByName(name); true }.getOrNull() ?: false

/**
 * Convert to [ExtensionAware].
 * @receiver [Any]
 * @return [ExtensionAware]
 * @throws IllegalStateException when the instance is not a valid [ExtensionAware].
 */
internal fun Any.asExtension() = this as? ExtensionAware? ?: Gropify.error("This instance \"$this\" is not a valid ExtensionAware.")

/**
 * Since Gradle has an [ExtensionAware] extension,
 * this function is used to detect if the current string is a keyword name used by Gradle.
 * @receiver [String]
 * @return [Boolean]
 */
internal fun String.isUnSafeExtName() = camelcase().let {
    it == "ext" ||
        it == "extra" ||
        it == "extraProperties" ||
        it == "extensions" ||
        it == "libs" ||
        it == "versionCatalogs"
}

/**
 * Since Gradle has an [ExtensionAware] extension,
 * this function is used to convert non-conforming strings to "{string}s"
 * @receiver [String]
 * @return [String]
 */
internal fun String.toSafeExtName() = if (isUnSafeExtName()) "${this}s" else this