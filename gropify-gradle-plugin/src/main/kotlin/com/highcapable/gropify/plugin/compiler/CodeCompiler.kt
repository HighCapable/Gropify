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
package com.highcapable.gropify.plugin.compiler

import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.internal.error
import com.highcapable.gropify.internal.require
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.utils.extension.deleteEmptyRecursively
import com.highcapable.gropify.utils.extension.toFile
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import java.io.File
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardLocation
import javax.tools.ToolProvider

/**
 * Java code compiler.
 */
internal object CodeCompiler {

    private const val MAVEN_MODEL_VERSION = "4.0.0"

    /**
     * Compile [JavaFileObject] as a dependency.
     * @param dependency the dependency entity.
     * @param outputDirPath the compile output directory path.
     * @param files the compile [JavaFileObject] array.
     * @param compileOnlyFiles the compile only [JavaFileObject] array.
     * @throws IllegalStateException if compilation fails.
     */
    fun compile(
        dependency: Dependency,
        outputDirPath: String,
        files: List<JavaFileObject>,
        compileOnlyFiles: List<JavaFileObject> = mutableListOf()
    ) {
        val outputDir = outputDirPath.toFile()

        if (files.isEmpty()) {
            if (outputDir.exists()) outputDir.deleteRecursively()

            return
        } else outputDir.also { if (!it.exists()) it.mkdirs() }

        val outputBuildDir = "$outputDirPath/build".toFile().also {
            if (it.exists()) it.deleteRecursively()
            it.mkdirs()
        }

        val outputClassesDir = "${outputBuildDir.absolutePath}/classes".toFile().apply { mkdirs() }
        val outputSourcesDir = "${outputBuildDir.absolutePath}/sources".toFile().apply { mkdirs() }

        val compiler = ToolProvider.getSystemJavaCompiler()
        val diagnostics = DiagnosticCollector<JavaFileObject>()

        val fileManager = compiler.getStandardFileManager(diagnostics, null, null)
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(outputClassesDir))

        val task = compiler.getTask(null, fileManager, diagnostics, null, null, compileOnlyFiles + files)

        val result = task.call()
        var diagnosticsMessage = ""

        diagnostics.diagnostics?.forEach { diagnostic ->
            diagnosticsMessage += "  > Error on line ${diagnostic.lineNumber} in ${diagnostic.source?.toUri()}\n"
            diagnosticsMessage += "    ${diagnostic.getMessage(null)}\n"
        }

        runCatching { fileManager.close() }

        compileOnlyFiles.forEach {
            "${outputClassesDir.absolutePath}/${it.name}"
                .replace(".java", ".class")
                .toFile()
                .delete()
        }

        files.forEach {
            it.toFiles(outputSourcesDir).also { (sourceDir, sourceFile) ->
                sourceDir.mkdirs()
                sourceFile.writeText(it.getCharContent(true).toString())
            }
        }

        if (result) {
            outputClassesDir.deleteEmptyRecursively()

            writeMetaInf(outputClassesDir)
            writeMetaInf(outputSourcesDir)

            createJar(dependency, outputDir, outputBuildDir, outputClassesDir, outputSourcesDir)
        } else Gropify.error("Failed to compile java files into path: $outputDirPath\n$diagnosticsMessage")
    }

    private fun createJar(dependency: Dependency, outputDir: File, buildDir: File, classesDir: File, sourcesDir: File) {
        val dependencyDir = outputDir.resolve(dependency.relativePath).also { if (!it.exists()) it.mkdirs() }

        packageJar(classesDir, dependencyDir, dependency, sourcesJar = false)
        packageJar(sourcesDir, dependencyDir, dependency, sourcesJar = true)
        writeDependency(dependencyDir, dependency)

        buildDir.deleteRecursively()
    }

    private fun writeMetaInf(dir: File) {
        val metaInfDir = dir.resolve("META-INF").apply { mkdirs() }
        metaInfDir.resolve("MANIFEST.MF").writeText("Manifest-Version: 1.0")
    }

    private fun writeDependency(dir: File, dependency: Dependency) {
        dir.resolve("${dependency.artifactId}-${dependency.version}.pom").writeText(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <project
                xmlns="http://maven.apache.org/POM/$MAVEN_MODEL_VERSION"
                xsi:schemaLocation="http://maven.apache.org/POM/$MAVEN_MODEL_VERSION https://maven.apache.org/xsd/maven-$MAVEN_MODEL_VERSION.xsd"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <modelVersion>$MAVEN_MODEL_VERSION</modelVersion>
                <groupId>${dependency.groupId}</groupId>
                <artifactId>${dependency.artifactId}</artifactId>
                <version>${dependency.version}</version>
              </project>
            """.trimIndent()
        )
    }

    private fun JavaFileObject.toFiles(outputDir: File): Pair<File, File> {
        val outputDirPath = outputDir.absolutePath
        val separator = if (name.contains("/")) "/" else "\\"
        val names = name.split(separator)

        val fileName = names[names.lastIndex]
        val folderName = name.replace(fileName, "")

        return "$outputDirPath/$folderName".toFile() to "$outputDirPath/$name".toFile()
    }

    private fun packageJar(buildDir: File, outputDir: File, dependency: Dependency, sourcesJar: Boolean) {
        Gropify.require(buildDir.exists()) {
            "Build directory not found: ${buildDir.absolutePath}."
        }

        val jarFile = outputDir.resolve("${dependency.artifactId}-${dependency.version}${if (sourcesJar) "-sources" else ""}.jar")
        if (jarFile.exists()) jarFile.delete()

        ZipFile(jarFile).addFolder(buildDir, ZipParameters().apply { isIncludeRootFolder = false })
    }
}