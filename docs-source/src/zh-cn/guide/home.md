# 介绍

> `Gropify` 是一个类型安全且现代化的 Gradle 属性插件。

## 背景

这是一个为 Gradle 构建脚本设计的插件，旨在将类似 `gradle.properties` 文件中的属性以类型安全的方式引入到构建脚本中，从而避免硬编码字符串可能带来的问题。

项目图标由 [MaiTungTM](https://github.com/Lagrio) 设计，名称取自 **G**radleP**ropify**，意为针对 Gradle 属性的插件。

它是基于 [SweetProperty](https://github.com/HighCapable/SweetProperty) 重构的全新项目，借鉴了以往的设计方案，使得其在原有基础上更加完善和易用。

`Gropify` 的配置方案与 `SweetProperty` 类似，如果你正在使用 `SweetProperty`，你可以考虑将其迁移到 `Gropify`。

## 用途

`Gropify` 主要针对 Kotlin DSL 构建脚本设计，Groovy 语言可以直接将 `gradle.properties` 文件中的属性作为变量使用，但是你也可以通过 `Gropify` 来实现类型安全的属性访问。

`Gropify` 同时支持将类似 `gradle.properties` 文件中的属性以类型安全的方式生成到 Kotlin、Java、Android 项目的源码中以供应用程序运行时使用，功能类似 Android 的 `BuildConfig` 中的 `buildConfigField` 功能。

假设我们有以下 `gradle.properties` 文件。

> 示例如下

```properties
project.app.name=Gropify-Demo
project.app.version=1.0.0
```

这是 `Gropify` 自动生成的代码调用示例。

> 构建脚本 (Kotlin DSL、Groovy DSL)

```kotlin
val appName = gropify.project.app.name
val appVersion = gropify.project.app.version
```

```groovy
def appName = gropify.project.app.name
def appVersion = gropify.project.app.version
```

> 源代码 (Kotlin、Java)

```kotlin
val appName = MyAppProperties.PROJECT_APP_NAME
val appVersion = MyAppProperties.PROJECT_APP_VERSION
```

```java
var appName = MyAppProperties.PROJECT_APP_NAME;
var appVersion = MyAppProperties.PROJECT_APP_VERSION;
```

`Gropify` 同样支持 Kotlin Multiplatform 项目，你可以在 `commonMain` 源集中使用生成的属性类。

## 语言要求

推荐使用 Kotlin DSL 来配置项目的构建脚本，Groovy 语言同样受支持，但在纯 Groovy 项目中部分配置语法可能存在兼容性问题。

在 Groovy DSL 中使用此插件发生的任何问题，我们都将不再负责排查和修复，并且在后期版本可能会完全不再支持 Groovy DSL。

## 功能贡献

本项目的维护离不开各位开发者的支持和贡献，目前这个项目处于初期阶段，可能依然存在一些问题或者缺少你需要的功能，
如果可能，欢迎提交 PR 为此项目贡献你认为需要的功能或前往 [GitHub Issues](repo://issues) 向我们提出建议。