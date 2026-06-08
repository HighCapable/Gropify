# 介绍

> `Gropify` 是一个类型安全且现代化的 Gradle 属性插件。

## 背景

这是一个为 Gradle 构建脚本设计的插件，旨在将类似 `gradle.properties` 文件中的属性以类型安全的方式引入到构建脚本中，从而避免硬编码字符串可能带来的问题。

项目图标由 [MaiTungTM](https://github.com/Lagrio) 设计，名称取自 **G**radleP**ropify**，意为针对 Gradle 属性的插件。

它是基于 [SweetProperty](https://github.com/HighCapable/SweetProperty) 重构的全新项目，借鉴了以往的设计方案，使得其在原有基础上更加完善和易用。

`Gropify` 的配置方案与 `SweetProperty` 类似，如果你正在使用 `SweetProperty`，你可以考虑将其迁移到 `Gropify`。

## 产品对比

这不是又一个普通的 `BuildConfig` 插件，以下是 `Gropify` 与社区现有方案的对比：

| 功能维度             | 官方做法                                                   | 传统插件                                             | 相关产品                                                                                | Gropify                                                  |
| -------------------- | ---------------------------------------------------------- | ---------------------------------------------------- | --------------------------------------------------------------------------------------- | -------------------------------------------------------- |
| **Buildscript 访问** | ❌ 需手写硬编码字符串 `providers.gradleProperty("foo.bar")` | ❌ 不支持，仍需在 `.kts` 里手动读取                   | -                                                                                       | **✅ 自动生成链式访问器** `gropify.foo.bar` (带 IDE 补全) |
| **源码常量生成**     | ❌ 仅 Android 支持 `buildConfigField`，KMP/JVM 不原生支持   | ✅ 支持生成多平台常量，但必须在 `.kts` 里显式声明字段 | [gmazzo/gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin) | **✅ 零手动声明**，直接根据 properties 自动推断并生成挂载 |
| **KMP 源码多平台**   | ❌ 无法原生映射                                             | ✅ 支持通过 expect/actual 生成常量                    | [yshrsmz/BuildKonfig](https://github.com/yshrsmz/BuildKonfig)                           | **✅ 完美覆盖**，自动挂载至 `commonMain` 源码集           |
| **配置冗余度**       | **❌ 高** (Key 散落在各处字符串中)                          | **⚠️ 中** (必须在 `.kts` 里手写映射关系)              | -                                                                                       | **✅ 极低** (Properties 即 Schema)                        |
| **设计哲学**         | 散装的能力碎片                                             | CodeGen-First (代码生成工具)                         | -                                                                                       | Properties-Centric (属性中心化驱动)                      |

## 功能一览

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