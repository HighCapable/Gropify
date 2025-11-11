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
 * This file is created by fankes on 2025/10/11.
 */
package com.highcapable.gropify.plugin.generator

import com.highcapable.gropify.internal.require
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.config.proxy.GropifyConfig
import com.highcapable.gropify.plugin.generator.config.GenerateConfig
import com.highcapable.gropify.plugin.generator.config.SourceCodeSpec
import com.highcapable.gropify.plugin.generator.extension.PropertyMap

/**
 * Source code generator.
 */
internal class SourceCodeGenerator {

    private val java = JavaCodeGenerator()
    private val kotlin = KotlinCodeGenerator()

    /**
     * Build source code specs.
     * @param config the current generate config.
     * @param generateConfig the generate config.
     * @param keyValues the properties' key-values map.
     * @return [List]<[SourceCodeSpec]>
     */
    fun build(config: GropifyConfig.SourceCodeGenerateConfig, generateConfig: GenerateConfig, keyValues: PropertyMap): List<SourceCodeSpec> {
        Gropify.require(config is GropifyConfig.CommonCodeGenerateConfig) {
            "Only Android, Jvm, Kotlin Multiplatform project is supported for now."
        }

        return listOf(
            java.build(config, generateConfig, keyValues),
            kotlin.build(config, generateConfig, keyValues)
        )
    }
}