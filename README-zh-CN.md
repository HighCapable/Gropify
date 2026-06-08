# Gropify

[![GitHub license](https://img.shields.io/github/license/HighCapable/Gropify?color=blue&style=flat-square)](https://github.com/HighCapable/Gropify/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram&style=flat-square)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion-QQ-blue.svg?logo=tencent-qq&logoColor=red&style=flat-square)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.svg" width = "100" height = "100" alt="LOGO"/>

一个类型安全且现代化的 Gradle 属性插件。

[English](README.md) | 简体中文

| <img src="https://github.com/HighCapable/.github/blob/main/img-src/logo.jpg?raw=true" width = "30" height = "30" alt="LOGO"/> | [HighCapable](https://github.com/HighCapable) |
|-------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|

这个项目属于上述组织，**点击上方链接关注这个组织**，发现更多好项目。

## 这是什么

这是一个为 Gradle 构建脚本设计的插件，旨在将类似 `gradle.properties` 文件中的属性以类型安全的方式引入到构建脚本中，从而避免硬编码字符串可能带来的问题。

项目图标由 [MaiTungTM](https://github.com/Lagrio) 设计，名称取自 **G**radleP**ropify**，意为针对 Gradle 属性的插件。

## 产品对比

这不是又一个普通的 `BuildConfig` 插件，以下是 `Gropify` 与社区现有方案的对比：

| 功能维度               | 官方做法                                              | 传统插件                            | 相关产品                                                                                    | Gropify                                      |
|--------------------|---------------------------------------------------|---------------------------------|-----------------------------------------------------------------------------------------|----------------------------------------------|
| **Buildscript 访问** | ❌ 需手写硬编码字符串 `providers.gradleProperty("foo.bar")` | ❌ 不支持，仍需在 `.kts` 里手动读取          | -                                                                                       | **✅ 自动生成链式访问器** `gropify.foo.bar` (带 IDE 补全) |
| **源码常量生成**         | ❌ 仅 Android 支持 `buildConfigField`，KMP/JVM 不原生支持   | ✅ 支持生成多平台常量，但必须在 `.kts` 里显式声明字段 | [gmazzo/gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin) | **✅ 零手动声明**，直接根据 properties 自动推断并生成挂载        |
| **KMP 源码多平台**      | ❌ 无法原生映射                                          | ✅ 支持通过 expect/actual 生成常量       | [yshrsmz/BuildKonfig](https://github.com/yshrsmz/BuildKonfig)                           | **✅ 完美覆盖**，自动挂载至 `commonMain` 源码集            |
| **配置冗余度**          | **❌ 高** (Key 散落在各处字符串中)                           | **⚠️ 中** (必须在 `.kts` 里手写映射关系)   | -                                                                                       | **✅ 极低** (Properties 即 Schema)               |
| **设计哲学**           | 散装的能力碎片                                           | CodeGen-First (代码生成工具)          | -                                                                                       | Properties-Centric (属性中心化驱动)                 |

## 功能一览

`Gropify` 主要针对 Kotlin DSL 构建脚本设计，Groovy 语言可以直接将 `gradle.properties` 文件中的属性作为变量使用，但是你也可以通过 `Gropify` 来实现类型安全的属性访问。

`Gropify` 同时支持将类似 `gradle.properties` 文件中的属性以类型安全的方式生成到 Kotlin、Java、Android 项目的源码中以供应用程序运行时使用，功能类似
Android 的 `BuildConfig` 中的 `buildConfigField` 功能。

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

## 开始使用

| <img src="img-src/icon.svg" width = "30" height = "30" alt="LOGO"/> | [Gropify 文档](https://highcapable.github.io/Gropify/zh-cn) |
|---------------------------------------------------------------------|-----------------------------------------------------------|

你可以前往文档页面查看更多详细教程和内容。

### 下一步做什么？

1. **应用插件**: 将 `Gropify` 插件 ID 添加到你的 `settings.gradle.kts` 中。
2. **配置属性**: 在 `gradle.properties` 中定义你的常量。
3. **同步项目**: 在 Gradle 同步后，即可开始享受类型安全的属性访问。

在打开的页面中，选择侧边栏的 **快速开始** 章节以继续阅读。

## 更多项目

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h2>嘿，还请君留步！👋</h2>
    <h3>如果你觉得这个项目能给你提供帮助，不妨继续往下看看我的更多项目吧！</h3>
    <h3>如果这些项目能为你提供帮助，不妨为我点个关注或者 star ⭐️ 吧！</h3>
    <h1><a href="https://github.com/fankes/fankes/blob/main/project-promote/README-zh-CN.md">→ 查看更多关于我的项目，请点击这里 ←</a></h1>
</div>

## 星标历史

![Star History Chart](https://api.star-history.com/svg?repos=HighCapable/Gropify&type=Date)

## 许可证

- [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Apache License Version 2.0

Copyright (C) 2019 HighCapable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

版权所有 © 2019 HighCapable