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
 * This file is created by fankes on 2026/6/8.
 */
package com.highcapable.gropify.plugin.repository

import com.highcapable.gropify.debug.require
import com.highcapable.gropify.gradle.api.entity.Dependency
import com.highcapable.gropify.plugin.Gropify
import com.highcapable.gropify.plugin.compiler.CodeCompileSpec
import java.io.File
import java.nio.channels.FileChannel
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.util.UUID
import java.util.jar.JarFile

/**
 * Repository for generated buildscript accessors Maven artifacts.
 */
internal class BuildscriptAccessorsRepository(private val repositoryDir: File, private val dependency: Dependency) {

    private companion object {
        const val LOCK_FILE_NAME = ".gropify-accessors.lock"
        const val READY_FILE_NAME = ".gropify-accessors.ready"
        const val FINGERPRINT_FILE_NAME = ".gropify-accessors.fingerprint"
        const val TEMP_DIR_NAME = ".tmp"
    }

    /**
     * Execute action with repository lock.
     * @param action the action to execute.
     */
    fun withLock(action: () -> Unit) {
        val lockFile = repositoryDir.resolve(LOCK_FILE_NAME)
        lockFile.parentFile?.mkdirs()

        FileChannel.open(lockFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE).use { channel ->
            channel.lock().use { action() }
        }
    }

    /**
     * Publish compile spec to the Maven repository.
     * @param spec the compile spec.
     * @param fingerprint the expected fingerprint.
     * @param expectedClassNames the expected class names in the compiled jar.
     */
    fun publish(spec: CodeCompileSpec, fingerprint: String, expectedClassNames: List<String>) {
        val dependencyDir = dependencyDir()
        val readyFile = dependencyDir.resolve(READY_FILE_NAME)
        val fingerprintFile = dependencyDir.resolve(FINGERPRINT_FILE_NAME)

        readyFile.delete()
        fingerprintFile.delete()

        if (spec.isEmpty) {
            dependencyDir.deleteRecursively()
            return
        }

        val tempDir = repositoryDir.resolve(TEMP_DIR_NAME)
            .resolve("${System.currentTimeMillis()}-${UUID.randomUUID()}")

        try {
            spec.compile(dependency, tempDir.absolutePath)

            val tempDependencyDir = tempDir.resolve(dependency.relativePath)
            Gropify.require(isArtifactComplete(tempDependencyDir, expectedClassNames)) {
                "Generated buildscript accessors artifact is incomplete: ${tempDependencyDir.absolutePath}."
            }

            dependencyDir.mkdirs()
            tempDependencyDir.listFiles()?.forEach { source ->
                moveReplacing(source, dependencyDir.resolve(source.name))
            }

            fingerprintFile.writeText(fingerprint)
            readyFile.writeText("READY")
        } finally {
            tempDir.deleteRecursively()
        }
    }

    /**
     * Whether repository has ready artifact matching fingerprint.
     * @param fingerprint the expected fingerprint.
     * @param expectedClassNames the expected class names in the compiled jar.
     * @return [Boolean]
     */
    fun isReady(fingerprint: String, expectedClassNames: List<String>): Boolean {
        val dependencyDir = dependencyDir()
        val readyFile = dependencyDir.resolve(READY_FILE_NAME)
        val fingerprintFile = dependencyDir.resolve(FINGERPRINT_FILE_NAME)

        if (!readyFile.isFile || !fingerprintFile.isFile) return false
        if (fingerprintFile.readText() != fingerprint) return false

        return isArtifactComplete(dependencyDir, expectedClassNames)
    }

    /**
     * Whether repository has any ready artifact.
     * @return [Boolean]
     */
    fun isReady(): Boolean {
        val dependencyDir = dependencyDir()
        return dependencyDir.resolve(READY_FILE_NAME).isFile && isArtifactComplete(dependencyDir)
    }

    private fun moveReplacing(source: File, target: File) {
        target.parentFile?.mkdirs()
        runCatching {
            Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        }.recoverCatching {
            if (it is AtomicMoveNotSupportedException)
                Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
            else throw it
        }.getOrThrow()
    }

    private fun isArtifactComplete(dependencyDir: File, expectedClassNames: List<String> = emptyList()): Boolean {
        val artifactId = dependency.artifactId
        val version = dependency.version
        val jarFile = dependencyDir.resolve("$artifactId-$version.jar")
        val pomFile = dependencyDir.resolve("$artifactId-$version.pom")

        if (!jarFile.isFile || !pomFile.isFile) return false

        return runCatching {
            JarFile(jarFile).use { jar ->
                expectedClassNames.all { className ->
                    jar.getEntry("${className.replace(".", "/")}.class") != null
                }
            }
        }.getOrDefault(false)
    }

    private fun dependencyDir() = repositoryDir.resolve(dependency.relativePath)
}