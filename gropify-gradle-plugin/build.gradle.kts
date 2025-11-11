plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
}

group = gropify.project.groupName
version = gropify.project.version

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)

    sourceSets.all { languageSettings { languageVersion = "2.0" } }

    compilerOptions {
        freeCompilerArgs = listOf(
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
    }
}

dependencies {
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)
    implementation(libs.kotlinpoet)
    implementation(libs.javapoet)
    implementation(libs.zip4j)
}

gradlePlugin {
    plugins {
        create(gropify.project.moduleName) {
            id = gropify.project.groupName
            implementationClass = gropify.gradle.plugin.implementationClass
        }
    }
}

afterEvaluate {
    configure<PublishingExtension> {
        repositories {
            val repositoryDir = gradle.gradleUserHomeDir
                .resolve("highcapable-maven-repository")
                .resolve("repository")

            maven {
                name = "HighCapableMavenReleases"
                url = repositoryDir.resolve("releases").toURI()
            }
            maven {
                name = "HighCapableMavenSnapShots"
                url = repositoryDir.resolve("snapshots").toURI()
            }
        }
    }
}