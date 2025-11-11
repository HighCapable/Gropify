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
 * This file is created by fankes on 2025/10/8.
 */
@file:Suppress("unused")

package com.highcapable.gropify.plugin.compiler.extension

import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.plugin.compiler.CodeCompiler
import com.palantir.javapoet.JavaFile
import javax.tools.JavaFileObject

/**
 * Compile [JavaFile] as a dependency.
 * @receiver [JavaFile]
 * @param dependency the dependency entity.
 * @param outputDirPath the compile output directory path.
 * @param compileOnlyFiles the compile only [JavaFile] array.
 * @throws IllegalStateException if compilation fails.
 */
@JvmName("compileWithJavaFile")
internal fun JavaFile.compile(
    dependency: Dependency,
    outputDirPath: String,
    compileOnlyFiles: List<JavaFile> = mutableListOf()
) = CodeCompiler.compile(
    dependency = dependency,
    outputDirPath = outputDirPath,
    files = listOf(toJavaFileObject()),
    compileOnlyFiles = mutableListOf<JavaFileObject>().also {
        compileOnlyFiles.forEach { file ->
            it.add(file.toJavaFileObject())
        }
    }
)

/**
 * Compile [List]<[JavaFile]> as a dependency.
 * @receiver [List]<[JavaFile]>
 * @param dependency the dependency entity.
 * @param outputDirPath the compile output directory path.
 * @param compileOnlyFiles the compile only [JavaFile] array.
 * @throws IllegalStateException if compilation fails.
 */
@JvmName("compileWithJavaFile")
internal fun List<JavaFile>.compile(
    dependency: Dependency,
    outputDirPath: String,
    compileOnlyFiles: List<JavaFile> = mutableListOf()
) = CodeCompiler.compile(
    dependency = dependency,
    outputDirPath = outputDirPath,
    files = mutableListOf<JavaFileObject>().also {
        forEach { file ->
            it.add(file.toJavaFileObject())
        }
    },
    compileOnlyFiles = mutableListOf<JavaFileObject>().also {
        compileOnlyFiles.forEach { file ->
            it.add(file.toJavaFileObject())
        }
    }
)

/**
 * Compile [JavaFileObject] as a dependency.
 * @receiver [JavaFileObject]
 * @param dependency the dependency entity.
 * @param outputDirPath the compile output directory path.
 * @param compileOnlyFiles the compile only [JavaFileObject] array.
 * @throws IllegalStateException if compilation fails.
 */
@JvmName("compileWithJavaFileObject")
internal fun JavaFileObject.compile(
    dependency: Dependency,
    outputDirPath: String,
    compileOnlyFiles: List<JavaFileObject> = mutableListOf()
) = CodeCompiler.compile(dependency, outputDirPath, listOf(this), compileOnlyFiles)

/**
 * Compile [List]<[JavaFileObject]> as a dependency.
 * @receiver [List]<[JavaFileObject]>
 * @param dependency the dependency entity.
 * @param outputDirPath the compile output directory path.
 * @param compileOnlyFiles the compile only [JavaFileObject] array.
 * @throws IllegalStateException if compilation fails.
 */
@JvmName("compileWithJavaFileObject")
internal fun List<JavaFileObject>.compile(
    dependency: Dependency,
    outputDirPath: String,
    compileOnlyFiles: List<JavaFileObject> = mutableListOf()
) = CodeCompiler.compile(dependency, outputDirPath, files = this, compileOnlyFiles)