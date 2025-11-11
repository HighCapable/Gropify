# 快速开始

> 集成 `Gropify` 到你的项目中。

## 部署插件

![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.gropify/gropify-gradle-plugin?logo=apachemaven&logoColor=orange&style=flat-square)
<span style="margin-left: 5px"/>
![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Frefs%2Fheads%2Fmain%2Frepository%2Freleases%2Fcom%2Fhighcapable%2Fgropify%2Fgropify-gradle-plugin%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=highcapable-maven-releases&style=flat-square)

`Gropify` 的依赖发布在 **Maven Central** 和我们的公共存储库中，你可以使用如下方式配置存储库。

我们建议使用不低于 `7.x.x` 版本的 Gradle，并推荐使用 Kotlin DSL 作为 Gradle 构建脚本语言，文档中将不再详细介绍在 Groovy DSL 中的使用方法。

我们推荐使用 `pluginManagement` 新方式进行部署，它是自 Gradle `7.x.x` 版本开始添加的功能。

如果你的项目依然在使用 `buildscript` 的方式进行管理，推荐迁移到新方式，这里将不再提供旧版本的使用方式说明。

首先，在你的项目 `settings.gradle.kts` 中配置插件的存储库。

> 示例如下

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal() // 可选
        google() // 可选
        mavenCentral() // 必须
        // (可选) 你可以添加此 URL 以使用我们的公共存储库
        // 当 Sonatype-OSS 发生故障无法发布依赖时，此存储库作为备选进行添加
        // 详情请前往：https://github.com/HighCapable/maven-repository
        maven("https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/releases")
    }
}
```

然后在 `settings.gradle.kts` 中 `plugin` 添加 `Gropify` 插件依赖，请注意**不要**在后方加入 `apply false`。

> 示例如下

```kotlin
plugins {
    id("com.highcapable.gropify") version "<version>"
}
```

请将 `<version>` 修改为此小结顶部显示的版本。

上述配置完成后，运行一次 Gradle Sync。

`Gropify` 将会自动搜索根项目和每个子项目中的 `gradle.properties` 文件，并读取其中的属性键值，为每个项目生成对应的代码。

::: warning

`Gropify` 只能被应用到 `settings.gradle.kts` 中，配置一次即可全局生效，请勿将其应用到 `build.gradle.kts` 中，否则功能将会无效。

:::

## 功能配置

你可以对 `Gropify` 进行配置来实现自定义和个性化功能。

`Gropify` 为你提供了相对丰富的可自定义功能，下面是这些功能的说明与配置方法。

请在你的 `settings.gradle.kts` 中添加 `gropify` 方法块以开始配置 `Gropify`。

如需在 Groovy DSL 中使用，请将所有变量的 `=` 改为空格，并删除 `Enabled` 前方的 `is` 即可。

如果你遇到了 `Gradle DSL method not found` 错误，解决方案为迁移到 Kotlin DSL。

如果你不想全部使用 Kotlin DSL，你也可以仅将 `settings.gradle` 迁移到 `settings.gradle.kts`。

> 示例如下

```kotlin
gropify {
    // 启用 Gropify，设置为 `false` 将禁用所有功能
    isEnabled = true
}
```

`Gropify` 的配置模式分为 `global` 全局配置和 `rootProject`、`projects` 根项目和子项目配置三种。

你可以在子项的代码块中继续配置和集成顶层项目的配置。

以下配置均在 `gropify` 方法块中进行。

> 示例如下

```kotlin
// 全局配置
//
// 你可以在全局配置中修改所有项目中的配置
// 每个项目中未进行声明的配置将使用全局配置
// 每个配置方法块中的功能完全一致
//
// 你可以参考下方根项目、子项目的配置方法
global {
    // 通用配置
    common {
        // 配置 "common"
    }

    // 构建脚本配置
    buildscript {
        // 配置 "buildscript"
    }

    // Android 项目配置
    android {
        // 配置 "android"
    }

    // JVM 项目配置
    jvm {
        // 配置 "jvm"
    }

    // Kotlin 多平台项目配置
    kmp {
        // 配置 "kmp"
    }
}

// 根项目 (Root Project) 配置
//
// 这是一个特殊的配置方法块，只能用于根项目
rootProject {
    common {
        // 配置 "common"
    }
    buildscript {
        // 配置 "buildscript"
    }
    android {
        // 配置 "android"
    }
    jvm {
        // 配置 "jvm"
    }
    kmp {
        // 配置 "kmp"
    }
}

// 其它项目与子项目配置
//
// 在方法参数中填入需要配置的项目完整名称来配置对应的项目
//
// 如果当前项目是子项目，你必须填写子项目前面的 ":"，例如 ":app"
//
// 如果当前项目为嵌套型子项目，例如 app → sub
// 此时你需要使用 ":" 来分隔多个子项目，例如 ":app:sub"
//
// 根项目的名称不能直接用来配置子项目，请使用 "rootProject"
// 你可以同时进行多个项目与子项目配置，在方法参数中填入需要配置的项目完整名称数组来配置每个对应的项目
projects(":app", ":modules:library1", ":modules:library2") {
    common {
        // 配置 "common"
    }
    buildscript {
        // 配置 "buildscript"
    }
    android {
        // 配置 "android"
    }
    jvm {
        // 配置 "jvm"
    }
    kmp {
        // 配置 "kmp"
    }
}
```

你可以继续在下方了解如何配置每个方法块中的功能。

### 通用配置

在这里你可以同时配置所有配置类型的相关功能，这里的配置会向下应用到 [构建脚本配置](#构建脚本配置)、[Android 项目配置](#android-项目配置)、[JVM 项目配置](#jvm-项目配置)、[Kotlin 多平台项目配置](#kotlin-多平台项目配置) 中。

> 示例如下

```kotlin
common {
    // 启用功能
    //
    // 你可以分别对 [buildscript]、[android]、[jvm]、[kmp] 进行设置
    isEnabled = true

    // 是否排除非字符串类型的键值内容
    //
    // 默认启用，启用后将排除非字符串类型的键值和内容
    excludeNonStringValue = true

    // 是否使用类型自动转换
    //
    // 默认启用，启用后将自动识别属性键值中的类型并转换为对应类型
    //
    // 启用后，如果你想强制将键值内容设为字符串类型，
    // 可以使用单引号或双引号包裹整个字符串
    //
    // - 注意: 禁用此功能后，上述功能也将失效
    useTypeAutoConversion = true

    // 是否使用键值内容插值
    //
    // 默认启用，启用后将自动识别属性键值内容中的 `${...}` 并进行替换
    //
    // 注意: 插值内容仅会从当前 (当前配置文件) 属性键值列表中查找
    useValueInterpolation = true

    // 设置已存在的属性文件
    //
    // 属性文件将根据你设置的文件名自动从当前根项目、
    // 子项目和用户目录的根目录获取
    //
    // 默认情况下，如果 [addDefault] 为 `true`，将添加 "gradle.properties"
    //
    // 你可以添加多组属性文件名，它们将按顺序读取
    //
    // - 注意: 通常无需修改此设置，错误的文件名将导致获取空键值内容
    existsPropertyFiles(
        "some-other-1.properties",
        "some-other-2.properties",
        addDefault = true
    )

    // 设置永久属性键值列表
    //
    // 在这里你可以设置一些必须存在的键值，无论是否能从属性键值中获取，
    // 这些键值都将被生成
    //
    // 这些键如果存在于属性键中则使用属性键的内容，
    // 如果不存在则使用这里设置的内容
    //
    // - 注意: 属性键名称中不能存在特殊符号和空格，否则可能导致生成失败
    permanentKeyValues(
        "permanent.some.key1" to "some_value_1",
        "permanent.some.key2" to "some_value_2"
    )

    // 设置替换属性键值列表
    //
    // 在这里你可以设置一些需要替换的键值，这些键值
    // 将替换现有的属性键值，如果不存在则忽略
    //
    // 这里设置的键值也会覆盖 [permanentKeyValues] 中设置的键值
    replacementKeyValues(
        "some.key1" to "new.value1",
        "some.key2" to "new.value2"
    )

    // 设置需要排除的属性键值名称列表
    //
    // 在这里你可以设置一些要从已知属性键值中排除的键名称
    //
    // 如果这些键存在于属性键中则排除它们，
    // 不会出现在生成的代码中
    //
    // - 注意: 如果你排除了 [permanentKeyValues] 中设置的键值，那么它们将
    //   仅更改为你设置的初始键值内容并继续存在
    excludeKeys(
        "exclude.some.key1",
        "exclude.some.key2"
    )

    // 设置需要包含的属性键值名称列表
    //
    // 在这里你可以设置一些要从已知属性键值中包含的键名称
    //
    // 如果属性键存在则包含这些键，未包含的键不会出现在生成的代码中
    includeKeys(
        "include.some.key1",
        "include.some.key2"
    )

    // 设置属性键值规则
    //
    // 你可以设置一组键值规则，
    // 使用 [ValueRule] 创建新规则来解析获取的键值内容
    //
    // 当属性键存在时应用这些键值规则
    keyValuesRules(
        "some.key1" to ValueRule { if (it.contains("_")) it.replace("_", "-") else it },
        "some.key2" to ValueRule { "$it-value" }
    )

    // 设置查找属性键值的位置
    //
    // 默认为 [GropifyLocation.CurrentProject]、[GropifyLocation.RootProject]
    //
    // 你可以使用以下类型进行设置
    //
    // - [GropifyLocation.CurrentProject]
    // - [GropifyLocation.RootProject]
    // - [GropifyLocation.Global]
    // - [GropifyLocation.System]
    // - [GropifyLocation.SystemEnv]
    //
    // 我们将按顺序从你设置的位置生成属性键值，生成位置的顺序遵循你设置的顺序
    //
    // - 风险警告: [GropifyLocation.Global]、[GropifyLocation.System]、
    //   [GropifyLocation.SystemEnv] 可能包含密钥和证书，请谨慎管理生成的代码
    locations(GropifyLocation.CurrentProject, GropifyLocation.RootProject)
}
```

::: tip

在引用 `GropifyLocation` 时，构建脚本在配合 IDE 自动导入时可能会在构建脚本顶部生成以下内容。

```kotlin :no-line-numbers
import com.highcapable.gropify.plugin.config.type.GropifyLocation
```

`Gropify` 对此做了 alias 处理，你可以直接删除此 import 语句。

:::

### 构建脚本配置

在构建脚本中生成的代码可直接被当前 `build.gradle.kts`、`build.gradle` 使用。

这里的配置包括 `common` 中的配置，你可以对其进行复写。

> 示例如下

```kotlin
buildscript {
    // 自定义构建脚本扩展名称
    //
    // 默认为 "gropify"
    extensionName = "gropify"
}
```

::: warning

Gradle 中也有一个 `buildscript` 方法块，请注意使用正确的 DSL 层级。

:::

### Android 项目配置

此配置块中的内容仅对存在 AGP 的项目生效。

这里的配置包括 `common` 中的配置，你可以对其进行复写。

> 示例如下

```kotlin
android {
    // 自定义生成的目录路径
    //
    // 你可以填写相对于当前项目的路径
    //
    // 格式示例: "path/to/your/src/main"，"src/main" 是固定后缀
    //
    // 默认为 "build/generated/gropify/src/main"
    //
    // 我们建议你将生成路径设置在 "build" 目录下，该目录默认被版本控制系统忽略
    generateDirPath = "build/generated/gropify"

    // 自定义部署 `sourceSet` 名称
    //
    // 如果你的项目源代码部署名称不是默认的，可以在此处自定义
    //
    // 默认为 "main"
    sourceSetName = "main"

    // 自定义生成的包名
    //
    // Android 项目默认使用 `android` 配置方法块中的 `namespace`
    //
    // "generated" 是固定后缀，用于避免与你自己的命名空间冲突，
    // 如果你不想要此后缀，可以参考 [isIsolationEnabled]
    packageName = "com.example.mydemo"

    // 自定义生成的类名
    //
    // 默认使用当前项目的名称
    //
    // "Properties" 是固定后缀，用于与你自己的类名区分
    className = "MyDemo"

    // 是否使用 Kotlin 语言生成
    //
    // 默认启用，启用后将生成 Kotlin 代码，
    // 禁用后将生成 Java 代码
    //
    // - 注意: 当此项目为纯 Java 项目时，此选项将被禁用
    useKotlin = true

    // 是否启用受限访问
    //
    // 默认禁用，启用后将为生成的 Kotlin 类添加 `internal` 修饰符，
    // 或为生成的 Java 类移除 `public` 修饰符
    isRestrictedAccessEnabled = false

    // 是否启用代码隔离
    //
    // 默认启用，启用后将在隔离的包后缀 "generated" 中生成代码，
    // 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突
    //
    // - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用
    //   Gropify 生成代码的项目，以避免冲突
    isIsolationEnabled = true
}
```

### JVM 项目配置

此配置块中的内容仅对纯 JVM 项目生效 (包括 Kotlin、Java 项目)，如果是 Android 项目请参考 [Android 项目配置](#android-项目配置) 进行配置。

这里的配置包括 `common` 中的配置，你可以对其进行复写。

> 示例如下

```kotlin
jvm {
    // 自定义生成的目录路径
    //
    // 你可以填写相对于当前项目的路径
    //
    // 格式示例: "path/to/your/src/main"，"src/main" 是固定后缀
    //
    // 默认为 "build/generated/gropify/src/main"
    //
    // 我们建议你将生成路径设置在 "build" 目录下，该目录默认被版本控制系统忽略
    generateDirPath = "build/generated/gropify"

    // 自定义部署 `sourceSet` 名称
    //
    // 如果你的项目源代码部署名称不是默认的，可以在此处自定义
    //
    // 默认为 "main"
    sourceSetName = "main"

    // 自定义生成的包名
    //
    // Java、Kotlin 项目默认使用项目设置的 `project.group`
    //
    // "generated" 是固定后缀，用于避免与你自己的命名空间冲突，
    // 如果你不想要此后缀，可以参考 [isIsolationEnabled]
    packageName = "com.example.mydemo"

    // 自定义生成的类名
    //
    // 默认使用当前项目的名称
    //
    // "Properties" 是固定后缀，用于与你自己的类名区分
    className = "MyDemo"

    // 是否使用 Kotlin 语言生成
    //
    // 默认启用，启用后将生成 Kotlin 代码，
    // 禁用后将生成 Java 代码
    //
    // - 注意: 当此项目为纯 Java 项目时，此选项将被禁用
    useKotlin = true

    // 是否启用受限访问
    //
    // 默认禁用，启用后将为生成的 Kotlin 类添加 `internal` 修饰符，
    // 或为生成的 Java 类移除 `public` 修饰符
    isRestrictedAccessEnabled = false

    // 是否启用代码隔离
    //
    // 默认启用，启用后将在隔离的包后缀 "generated" 中生成代码，
    // 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突
    //
    // - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用
    //   Gropify 生成代码的项目，以避免冲突
    isIsolationEnabled = true
}
```

### Kotlin 多平台项目配置

此配置块中的内容仅对含有 Kotlin Multiplatform 插件的项目生效。

这里的配置包括 `common` 中的配置，你可以对其进行复写。

```kotlin
kmp {
    // 自定义生成的目录路径
    //
    // 你可以填写相对于当前项目的路径
    //
    // 格式示例: "path/to/your/src/main"，"src/main" 是固定后缀
    //
    // 默认为 "build/generated/gropify/src/main"
    //
    // 我们建议你将生成路径设置在 "build" 目录下，该目录默认被版本控制系统忽略
    generateDirPath = "build/generated/gropify"

    // 自定义部署 `sourceSet` 名称
    //
    // 如果你的项目源代码部署名称不是默认的，可以在此处自定义。
    //
    // 默认为 "commonMain"
    sourceSetName = "commonMain"

    // 自定义生成的包名
    //
    // Kotlin 多平台项目默认使用项目设置的 `project.group`
    //
    // 在 Kotlin 多平台项目中，如果同时应用了 AGP 插件，
    // 仍将默认使用 `namespace` 作为包名
    //
    // "generated" 是固定后缀，用于避免与你自己的命名空间冲突，
    // 如果你不想要此后缀，可以参考 [isIsolationEnabled]
    packageName = "com.example.mydemo"

    // 自定义生成的类名
    //
    // 默认使用当前项目的名称
    //
    // "Properties" 是固定后缀，用于与你自己的类名区分
    className = "MyDemo"

    // 是否启用受限访问
    //
    // 默认禁用，启用后将为生成的 Kotlin 类添加 `internal` 修饰符
    isRestrictedAccessEnabled = false

    // 是否启用代码隔离
    //
    // 默认启用，启用后将在隔离的包后缀 "generated" 中生成代码，
    // 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突
    //
    // - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用
    //   Gropify 生成代码的项目，以避免冲突
    isIsolationEnabled = true
}
```

## 使用示例

下面是一个项目的 `gradle.properties` 配置文件。

> 示例如下

```properties
project.groupName=com.highcapable.gropifydemo
project.description=Hello Gropify Demo!
project.version=1.0.0
```

在构建脚本 `build.gradle.kts` 中，我们就可以如下所示这样直接去使用这些键值。

这里以 Maven 发布的配置部分举例。

> 示例如下

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

同样地，你也可以在当前项目中调用生成的键值。

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

下面再以 Android 项目举例。

在 Android 项目中通常需要配置很多重复、固定的属性，例如 `targetSdk`。

> 示例如下

```properties
project.namespace=com.highcapable.gropifydemo
project.compileSdk=36
project.targetSdk=36
project.minSdk=26
```

当你设置了 `useTypeAutoConversion = true` 时，`Gropify` 在生成实体类过程在默认配置下将尝试将其转换为对应的类型。

例如下方所使用的键值，其类型可被识别为字符串和整型，可被项目配置直接使用。

> 示例如下

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

你可以无需再使用 `buildConfigField` 向 `BuildConfig` 添加代码，有了 `Gropify` 生成的属性键值代码，你可以更加灵活地管理你的项目。

你还可以在属性键值中使用插值 `${...}` 互相引用其中的内容，但不允许递归引用。

当你设置了 `useValueInterpolation = true` 时，`Gropify` 将自动合并这些引用的内容到对应位置。

> 示例如下

```properties
project.name=MyDemo
project.developer.name=myname
project.url=https://github.com/${project.developer.name}/${project.name}
```

如果你在 `locations` 中添加了 `GropifyLocation.SystemEnv`，你还可以直接引用系统环境变量。

> 示例如下

```properties
# Linux 或 macOS 系统中使用 $USER 环境变量可以获取当前用户名
project.developer.name=${USER}
# 假设你有一个名为 SECRET_KEY 的系统环境变量 (请确保安全)
project.secretKey=${SECRET_KEY}
```

::: warning

这个特性是 `Gropify` 提供的，原生的 `gradle.properties` 并不支持此功能。

插值内容通过 `locations` 的层级自上而下进行查找替换，如果存在重复的键值名称，将使用最后查找到的内容进行替换。

:::

## 可能遇到的问题

如果你的项目仅存在一个根项目，且没有导入任何子项目，此时如果扩展方法不能正常生成，
你可以将你的根项目迁移至子项目并在 `settings.gradle.kts` 中导入这个子项目，这样即可解决此问题。

我们一般推荐将项目的功能进行分类，根项目仅用来管理插件和一些配置。

## 局限性说明

`Gropify` 无法生成 `settings.gradle.kts` 中的扩展方法，因为这属于 `Gropify` 的上游。