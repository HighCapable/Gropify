# Quick Start

> Integrate `Gropify` into your project.

## Deploy Plugin

![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.gropify/com.highcapable.gropify.gradle.plugin?logo=apachemaven&logoColor=orange&style=flat-square)
<span style="margin-left: 5px"/>
![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Frefs%2Fheads%2Fmain%2Frepository%2Freleases%2Fcom%2Fhighcapable%2Fgropify%2Fcom.highcapable.gropify.gradle.plugin%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=highcapable-maven-releases&style=flat-square)

`Gropify` dependencies are published on **Maven Central** and our public repository. You can configure the repository as follows.

We recommend using Gradle version `7.x.x` or higher, and recommend using Kotlin DSL as the Gradle build script language. This documentation will no longer detail how to use it in Groovy DSL.

We recommend using the new `pluginManagement` method for deployment, which is a feature added since Gradle version `7.x.x`.

If your project is still using `buildscript` for management, we recommend migrating to the new method. Instructions for the old version will no longer be provided here.

First, configure the plugin repository in your project's `settings.gradle.kts`.

> The following example

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal() // Optional
        google() // Optional
        mavenCentral() // Required
        // (Optional) You can add this URL to use our public repository
        // This repository is added as an alternative when Sonatype-OSS fails to publish dependencies
        // For details, please visit: https://github.com/HighCapable/maven-repository
        maven("https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/releases")
    }
}
```

Then add the `Gropify` plugin dependency in `plugin` in `settings.gradle.kts`. Please note **do not** add `apply false` after it.

> The following example

```kotlin
plugins {
    id("com.highcapable.gropify") version "<version>"
}
```

Please replace `<version>` with the version shown at the top of this section.

After completing the above configuration, run Gradle sync once.

`Gropify` will automatically search for `gradle.properties` files in the root project and each subproject, read the property key-values in them, and generate corresponding code for each project.

::: warning

`Gropify` can only be applied to `settings.gradle.kts`, configuring it once will take effect globally. Please do not apply it to `build.gradle.kts`, otherwise the functionality will be invalid.

:::

## Feature Configuration

You can configure `Gropify` to implement customization and personalized features.

`Gropify` provides relatively rich customizable features. Below are the descriptions and configuration methods for these features.

Please add the `gropify` method block in your `settings.gradle.kts` to start configuring `Gropify`.

To use in Groovy DSL, please change all variable `=` to spaces, remove `is` before `Enabled`.

If you encounter a `Gradle DSL method not found` error, the solution is to migrate to Kotlin DSL.

If you don't want to use Kotlin DSL entirely, you can also migrate only `settings.gradle` to `settings.gradle.kts`.

> The following example

```kotlin
gropify {
    // Enable Gropify, setting it to `false` will disable all features.
    isEnabled = true

    // Whether to enable debug mode.
    //
    // You can help us identify the problem by enabling this option
    // to print more debugging information in the logs.
    //
    // - Note: THIS IS ONLY FOR DEBUGGING!
    //   The debug log will contain your local environment,
    //   which may contain sensitive information.
    //   Please be sure to protect this information.
    debugMode = false
}
```

`Gropify`'s configuration mode is divided into three types: `global` global configuration, and `rootProject`, `projects` root project and subproject configuration.

You can continue to configure and integrate the configuration of the parent project in the child code blocks.

All configurations below are performed in the `gropify` method block.

> The following example

```kotlin
// Global configuration.
//
// You can modify configurations in all projects in the global configuration.
// Configurations not declared in each project will use the global configuration.
// The functions in each configuration method block are exactly the same.
//
// You can refer to the configuration methods of root project and subprojects below.
global {
    // Common configuration.
    common {
        // Configure "common".
    }

    // Build script configuration.
    buildscript {
        // Configure "buildscript".
    }

    // Android project configuration.
    android {
        // Configure "android".
    }

    // JVM project configuration.
    jvm {
        // Configure "jvm".
    }

    // Kotlin Multiplatform project configuration.
    kmp {
        // Configure "kmp".
    }
}

// Root project configuration.
//
// This is a special configuration method block that can only be used for the root project.
rootProject {
    common {
        // Configure "common".
    }
    buildscript {
        // Configure "buildscript".
    }
    android {
        // Configure "android".
    }
    jvm {
        // Configure "jvm".
    }
    kmp {
        // Configure "kmp".
    }
}

// Other projects and subprojects configuration.
//
// Fill in the full name of the project you need to configure in the
// method parameters to configure the corresponding project.
//
// If the current project is a subproject, you must include the ":" before
// the subproject name, such as ":app".
//
// If the current project is a nested subproject, such as app → sub,
// you need to use ":" to separate multiple subprojects, such as ":app:sub".
//
// The name of the root project cannot be used directly to configure subprojects,
// please use "rootProject".
//
// You can configure multiple projects and subprojects at the same time by filling
// in an array of full project names in the method parameters to
// configure each corresponding project.
projects(":app", ":modules:library1", ":modules:library2") {
    common {
        // Configure "common".
    }
    buildscript {
        // Configure "buildscript".
    }
    android {
        // Configure "android".
    }
    jvm {
        // Configure "jvm".
    }
    kmp {
        // Configure "kmp".
    }
}
```

You can continue below to learn how to configure the features in each method block.

### Common Configuration

Here you can configure related features for all configuration types at the same time. The configurations here will be applied down to [Build Script Configuration](#build-script-configuration), [Android Project Configuration](#android-project-configuration), [JVM Project Configuration](#jvm-project-configuration), [Kotlin Multiplatform Project Configuration](#kotlin-multiplatform-project-configuration).

> The following example

```kotlin
common {
    // Enable feature.
    //
    // You can set [buildscript], [android], [jvm], [kmp] separately.
    isEnabled = true

    // Whether to exclude the non-string type key-values content.
    //
    // Enabled by default, when enabled, key-values and content that are not
    // string types will be excluded from properties' key-values.
    excludeNonStringValue = true

    // Whether to use type auto conversion.
    //
    // Enabled by default, when enabled, the type in the properties' key-values will be
    // automatically identified and converted to the corresponding type.
    //
    // After enabling, if you want to force the content of a key-values to be a string type,
    // you can use single quotes or double quotes to wrap the entire string.
    //
    // - Note: After disabled this function, the functions mentioned above will also be invalid.
    useTypeAutoConversion = true

    // Whether to use key-values content interpolation.
    //
    // Enabled by default, after enabling it will automatically identify
    // the `${...}` content in the properties' key-values content and replace it.
    //
    // Note: The interpolated content will only be looked up from the
    // current (current configuration file) properties' key-values list.
    useValueInterpolation = true

    // Set exists property files.
    //
    // The property files will be automatically obtained from the root directory
    // of the current root project,
    // subproject and user directory according to the file name you set.
    //
    // By default, will add "gradle.properties" if [addDefault] is `true`.
    //
    // You can add multiple sets of property files name, they will be read in order.
    //
    // - Note: Generally there is no need to modify this setting,
    //   an incorrect file name will result in obtaining empty key-values content.
    existsPropertyFiles(
        "some-other-1.properties",
        "some-other-2.properties",
        addDefault = true
    )

    // Set a permanent list of properties' key-values.
    //
    // Here you can set some key-values that must exist, these key-values will be
    // generated regardless of whether they can be obtained from the properties' key-values.
    //
    // These keys use the content of the properties' key if it exists,
    // use the content set here if it does not exist.
    //
    // - Note: Special symbols and spaces cannot exist in properties' key names,
    //   otherwise the generation may fail.
    permanentKeyValues(
        "permanent.some.key1" to "some_value_1",
        "permanent.some.key2" to "some_value_2"
    )

    // Set a replacement list of properties' key-values.
    //
    // Here you can set some key-values that need to be replaced, these key-values
    // will be replaced the existing properties' key-values, if not exist, they will be ignored.
    //
    // The key-values set here will also overwrite the key-values set in [permanentKeyValues].
    replacementKeyValues(
        "some.key1" to "new.value1",
        "some.key2" to "new.value2"
    )

    // Set a key list of properties' key-values name that need to be excluded.
    //
    // Here you can set some key names that you want to exclude from
    // known properties' key-values.
    //
    // These keys are excluded if they are present in the properties' key,
    // will not appear in the generated code.
    //
    // - Note: If you exclude key-values set in [permanentKeyValues], then they will
    //   only change to the initial key-values content you set and continue to exist.
    excludeKeys(
        "exclude.some.key1",
        "exclude.some.key2"
    )

    // Set a key list of properties' key-values name that need to be included.
    //
    // Here you can set some key value names that you want to include from
    // known properties' key-values.
    //
    // These keys are included if the properties' key exists,
    // unincluded keys will not appear in the generated code.
    includeKeys(
        "include.some.key1",
        "include.some.key2"
    )

    // Set properties' key-values rules.
    //
    // You can set up a set of key-values rules,
    // use [ValueRule] to create new rule for parsing the obtained key-values content.
    //
    // These key-values rules are applied when properties' keys exist.
    keyValuesRules(
        "some.key1" to ValueRule { if (it.contains("_")) it.replace("_", "-") else it },
        "some.key2" to ValueRule { "$it-value" },
        // You can also specify the expected type class,
        // the type you specify will be used during generation,
        // and an exception will be thrown if the type cannot be converted correctly.
        // If the [useTypeAutoConversion] is not enabled, this parameter will be ignored.
        "some.key3" to ValueRule(Int::class)
    )

    // Set where to find properties' key-values.
    //
    // Defaults are [GropifyLocation.CurrentProject], [GropifyLocation.RootProject].
    //
    // You can set this up using the following types.
    //
    // - [GropifyLocation.CurrentProject]
    // - [GropifyLocation.RootProject]
    // - [GropifyLocation.Global]
    // - [GropifyLocation.System]
    // - [GropifyLocation.SystemEnv]
    //
    // We will generate properties' key-values in sequence from the locations you set,
    // the order of the generation locations follows the order you set.
    //
    // - Risk warning: [GropifyLocation.Global], [GropifyLocation.System],
    //   [GropifyLocation.SystemEnv] may have keys and certificates,
    //   please manage the generated code carefully.
    locations(GropifyLocation.CurrentProject, GropifyLocation.RootProject)
}
```

::: tip

When referencing `GropifyLocation`, the build script may generate the following at the top of the build script when used with IDE auto-import.

```kotlin :no-line-numbers
import com.highcapable.gropify.plugin.config.type.GropifyLocation
```

`Gropify` does alias processing for this, you can directly delete this import statement.

:::

### Build Script Configuration

The code generated in the build script can be directly used by the current `build.gradle.kts`, `build.gradle`.

The configuration here includes the configuration in `common`, and you can override it.

> The following example

```kotlin
buildscript {
    // Custom buildscript extension name.
    //
    // Default is "gropify".
    extensionName = "gropify"
}
```

::: warning

Gradle also has a `buildscript` method block, please be careful to use the correct DSL level.

:::

### Android Project Configuration

The content in this configuration block only takes effect for projects with AGP.

The configuration here includes the configuration in `common`, and you can override it.

> The following example

```kotlin
android {
    // Custom generated directory path.
    //
    // You can fill in the path relative to the current project.
    //
    // Format example: "path/to/your/src/main", the "src/main" is a fixed suffix.
    //
    // Default is "build/generated/gropify/src/main".
    //
    // We recommend that you set the generated path under the "build" directory,
    // which is ignored by version control systems by default.
    generateDirPath = "build/generated/gropify"

    // Custom deployment `sourceSet` name.
    //
    // If your project source code deployment name is not default, you can customize it here.
    //
    // Default is "main".
    sourceSetName = "main"

    // Custom generated package name.
    //
    // Android projects use the `namespace` in the `android` configuration method block
    // by default.
    //
    // The "generated" is a fixed suffix that avoids conflicts with your own namespaces,
    // if you don't want this suffix, you can refer to [isIsolationEnabled].
    packageName = "com.example.mydemo"

    // Custom generated class name.
    //
    // Default is use the name of the current project.
    //
    // The "Properties" is a fixed suffix to distinguish it from your own class names.
    className = "MyDemo"

    // Whether to use Kotlin language generation.
    //
    // Enabled by default, when enabled will generate Kotlin code,
    // disabled will generate Java code.
    //
    // - Note: This option will be disabled when this project is a pure Java project.
    useKotlin = true

    // Whether to enable restricted access.
    //
    // Disabled by default, when enabled will add the `internal` modifier to
    // generated Kotlin classes or remove the `public` modifier to generated Java classes.
    isRestrictedAccessEnabled = false

    // Whether to enable code isolation.
    //
    // Enabled by default, when enabled will generate code in an
    // isolated package suffix "generated" to avoid conflicts with other projects that
    // also use or not only Gropify to generate code.
    //
    // - Note: If you disable this option, please make sure that there are no other projects
    //   that also use or not only Gropify to generate code to avoid conflicts.
    isIsolationEnabled = true
}
```

### JVM Project Configuration

The content in this configuration block only takes effect for pure JVM projects (including Kotlin and Java projects). For Android projects, please refer to [Android Project Configuration](#android-project-configuration) for configuration.

The configuration here includes the configuration in `common`, and you can override it.

> The following example

```kotlin
jvm {
    // Custom generated directory path.
    //
    // You can fill in the path relative to the current project.
    //
    // Format example: "path/to/your/src/main", the "src/main" is a fixed suffix.
    //
    // Default is "build/generated/gropify/src/main".
    //
    // We recommend that you set the generated path under the "build" directory,
    // which is ignored by version control systems by default.
    generateDirPath = "build/generated/gropify"

    // Custom deployment `sourceSet` name.
    //
    // If your project source code deployment name is not default, you can customize it here.
    //
    // Default is "main".
    sourceSetName = "main"

    // Custom generated package name.
    //
    // Java, Kotlin projects use the `project.group` of the project settings by default.
    //
    // The "generated" is a fixed suffix that avoids conflicts with your own namespaces,
    // if you don't want this suffix, you can refer to [isIsolationEnabled].
    packageName = "com.example.mydemo"

    // Custom generated class name.
    //
    // Default is use the name of the current project.
    //
    // The "Properties" is a fixed suffix to distinguish it from your own class names.
    className = "MyDemo"

    // Whether to use Kotlin language generation.
    //
    // Enabled by default, when enabled will generate Kotlin code,
    // disabled will generate Java code.
    //
    // - Note: This option will be disabled when this project is a pure Java project.
    useKotlin = true

    // Whether to enable restricted access.
    //
    // Disabled by default, when enabled will add the `internal` modifier to
    // generated Kotlin classes or remove the `public` modifier to generated Java classes.
    isRestrictedAccessEnabled = false

    // Whether to enable code isolation.
    //
    // Enabled by default, when enabled will generate code in an
    // isolated package suffix "generated" to avoid conflicts with other projects that
    // also use or not only Gropify to generate code.
    //
    // - Note: If you disable this option, please make sure that there are no other projects
    //   that also use or not only Gropify to generate code to avoid conflicts.
    isIsolationEnabled = true
}
```

### Kotlin Multiplatform Project Configuration

The content in this configuration block only takes effect for projects with the Kotlin Multiplatform plugin.

The configuration here includes the configuration in `common`, and you can override it.

> The following example

```kotlin
kmp {
    // Custom generated directory path.
    //
    // You can fill in the path relative to the current project.
    //
    // Format example: "path/to/your/src/main", the "src/main" is a fixed suffix.
    //
    // Default is "build/generated/gropify/src/main".
    //
    // We recommend that you set the generated path under the "build" directory,
    // which is ignored by version control systems by default.
    generateDirPath = "build/generated/gropify"

    // Custom deployment `sourceSet` name.
    //
    // If your project source code deployment name is not default, you can customize it here.
    //
    // Default is "commonMain".
    sourceSetName = "commonMain"

    // Custom generated package name.
    //
    // Kotlin Multiplatform projects use the `project.group` of the project settings
    // by default.
    //
    // In a Kotlin Multiplatform project, if the AGP plugin is also applied,
    // the `namespace` will still be used as the package name by default.
    //
    // The "generated" is a fixed suffix that avoids conflicts with your own namespaces,
    // if you don't want this suffix, you can refer to [isIsolationEnabled].
    packageName = "com.example.mydemo"

    // Custom generated class name.
    //
    // Default is use the name of the current project.
    //
    // The "Properties" is a fixed suffix to distinguish it from your own class names.
    className = "MyDemo"

    // Whether to enable restricted access.
    //
    // Disabled by default, when enabled will add the `internal` modifier to
    // generated Kotlin classes.
    isRestrictedAccessEnabled = false

    // Whether to enable code isolation.
    //
    // Enabled by default, when enabled will generate code in an
    // isolated package suffix "generated" to avoid conflicts with other projects that
    // also use or not only Gropify to generate code.
    //
    // - Note: If you disable this option, please make sure that there are no other projects
    //   that also use or not only Gropify to generate code to avoid conflicts.
    isIsolationEnabled = true
}
```

## Usage Examples

Below is a project's `gradle.properties` configuration file.

> The following example

```properties
project.groupName=com.highcapable.gropifydemo
project.description=Hello Gropify Demo!
project.version=1.0.0
```

In the build script `build.gradle.kts`, we can directly use these key-values as shown below.

Here is an example of the Maven publish configuration section.

> The following example

```kotlin
publications {
    create<MavenPublication>("maven") {
        groupId = gropify.project.groupName
        version = gropify.project.version
        pom.description.set(gropify.project.description)

        from(components["java"])
    }
}
```

Similarly, you can also call the generated key-values in the current project.

> Kotlin

```kotlin
val groupName = GropifyDemoProperties.PROJECT_GROUP_NAME
val description = GropifyDemoProperties.PROJECT_DESCRIPTION
val version = GropifyDemoProperties.PROJECT_VERSION
```

> Java

```java
var groupName = GropifyDemoProperties.PROJECT_GROUP_NAME;
var description = GropifyDemoProperties.PROJECT_DESCRIPTION;
var version = GropifyDemoProperties.PROJECT_VERSION;
```

Let's take another example with an Android project.

In Android projects, many repetitive and fixed properties usually need to be configured, such as `targetSdk`.

> The following example

```properties
project.namespace=com.highcapable.gropifydemo
project.compileSdk=36
project.targetSdk=36
project.minSdk=26
```

When you set `useTypeAutoConversion = true`, `Gropify` will try to convert it to the corresponding type during the entity class generation process under the default configuration.

For example, the key-values used below can be identified as string and integer types, which can be directly used by the project configuration.

> The following example

```kotlin
android {
    namespace = gropify.project.namespace
    compileSdk = gropify.project.compileSdk

    defaultConfig {
        minSdk = gropify.project.minSdk
        targetSdk = gropify.project.targetSdk
    }
}
```

You no longer need to use `buildConfigField` to add code to `BuildConfig`. With the property key-value code generated by `Gropify`, you can manage your project more flexibly.

You can also use interpolation `${...}` in property key-values to reference each other's content, but recursive references are not allowed.

When you set `useValueInterpolation = true`, `Gropify` will automatically merge these referenced contents to the corresponding positions.

> The following example

```properties
project.name=MyDemo
project.developer.name=myname
project.url=https://github.com/${project.developer.name}/${project.name}
```

If you add `GropifyLocation.SystemEnv` to `locations`, you can also directly reference system environment variables.

> The following example

```properties
# Use the $USER environment variable in Linux or macOS systems to get the current username.
project.developer.name=${USER}
# Assume you have a system environment variable called SECRET_KEY (PLEASE BE SURE TO BE SAFE).
project.secretKey=${SECRET_KEY}
```

::: warning

This feature is provided by `Gropify`. Native `gradle.properties` does not support this feature.

The interpolated content is searched and replaced from top to bottom through the `locations` hierarchy.
If there are duplicate key names, the last found content will be used for replacement.

:::

## Possible Issues

If your project only has a root project and no subprojects are imported, and the extension method cannot be generated normally at this time,
you can migrate your root project to a subproject and import this subproject in `settings.gradle.kts`, which can solve this problem.

We generally recommend categorizing the functions of the project, with the root project only used to manage plugins and some configurations.

## Limitations

`Gropify` cannot generate extension methods in `settings.gradle.kts` because it is upstream of `Gropify`.