plugins {
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
}

group = gropify.project.groupName
version = gropify.project.version

java {
    withSourcesJar()
}

dependencies {
    implementation(libs.jackson.module.kotlin)

    implementation(platform(libs.kavaref.bom))
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.jvm)
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