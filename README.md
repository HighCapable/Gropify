# Gropify

[![GitHub license](https://img.shields.io/github/license/HighCapable/Gropify?color=blue&style=flat-square)](https://github.com/HighCapable/Gropify/blob/main/LICENSE)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram&style=flat-square)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion-QQ-blue.svg?logo=tencent-qq&logoColor=red&style=flat-square)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.svg" width = "100" height = "100" alt="LOGO"/>

A type-safe and modern properties plugin for Gradle.

English | [简体中文](README-zh-CN.md)

| <img src="https://github.com/HighCapable/.github/blob/main/img-src/logo.jpg?raw=true" width = "30" height = "30" alt="LOGO"/> | [HighCapable](https://github.com/HighCapable) |
|-------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|

This project belongs to the organization above. **Click the link to follow us** and discover more awesome projects.

## What's this

This plugin is designed for Gradle build scripts. It aims to bring properties similar to those in the `gradle.properties` file into build scripts in a
type-safe way, avoiding problems that hard-coded strings might cause.

The project icon was designed by [MaiTungTM](https://github.com/Lagrio). The name comes from **G**radleP**ropify**, meaning a plugin for Gradle
properties.

## Alternatives Comparison

This isn't just another ordinary `BuildConfig` plugin. Here is how `Gropify` stacks up against existing solutions in the community:

| Feature Dimension            | Official Approach                                                          | Traditional Plugins                                                         | Alternatives                                                                            | Gropify                                                                              |
|------------------------------|----------------------------------------------------------------------------|-----------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| **Buildscript Access**       | ❌ Hard-coded strings like `providers.gradleProperty("foo.bar")`            | ❌ Unsupported, requires manual `.kts` binding                               | -                                                                                       | **✅ Auto-generated chainable accessors** `gropify.foo.bar` (with IDE autocompletion) |
| **Source Code Constants**    | ❌ Only Android supports `buildConfigField`, lacking native KMP/JVM support | ✅ Supports multi-platform constants, but needs explicit `.kts` declarations | [gmazzo/gradle-buildconfig-plugin](https://github.com/gmazzo/gradle-buildconfig-plugin) | **✅ Zero manual declarations**, infers and mounts directly from properties           |
| **KMP Multiplatform**        | ❌ No native mapping                                                        | ✅ Supports expect/actual generation                                         | [yshrsmz/BuildKonfig](https://github.com/yshrsmz/BuildKonfig)                           | **✅ Flawlessly integrated**, automatically mounted to the `commonMain` source set    |
| **Configuration Redundancy** | **❌ High** (Keys scattered as strings across files)                        | **⚠️ Medium** (Requires boilerplate mappings in `.kts`)                     | -                                                                                       | **✅ Ultra-low** (Properties act as the Schema)                                       |
| **Design Philosophy**        | Fragmented capabilities                                                    | CodeGen-First (Code generation tooling)                                     | -                                                                                       | Properties-Centric (Single source of truth)                                          |

## Features Overview

`Gropify` is mainly designed for Kotlin DSL build scripts. Groovy can directly use properties from the `gradle.properties` file as variables, but you
can still use `Gropify` to achieve type-safe property access.

`Gropify` also supports generating properties (similar to those defined in a `gradle.properties` file) in a type-safe way into the source code of
Kotlin, Java, and Android projects for use at application runtime—similar to Android's `BuildConfig`'s `buildConfigField` feature.

Suppose we have the following `gradle.properties` file.

> The following example

```properties
project.app.name=Gropify-Demo
project.app.version=1.0.0
```

Here is an example of calling the code automatically generated by `Gropify`.

> Build Script (Kotlin DSL, Groovy DSL)

```kotlin
val appName = gropify.project.app.name
val appVersion = gropify.project.app.version
```

```groovy
def appName = gropify.project.app.name
def appVersion = gropify.project.app.version
```

> Source Code (Kotlin, Java)

```kotlin
val appName = MyAppProperties.PROJECT_APP_NAME
val appVersion = MyAppProperties.PROJECT_APP_VERSION
```

```java
var appName = MyAppProperties.PROJECT_APP_NAME;
var appVersion = MyAppProperties.PROJECT_APP_VERSION;
```

`Gropify` also supports Kotlin Multiplatform projects, and you can use the generated property classes in the `commonMain` source set.

## Get Started

| <img src="img-src/icon.svg" width = "30" height = "30" alt="LOGO"/> | [Gropify Documentation](https://highcapable.github.io/Gropify/en) |
|---------------------------------------------------------------------|-------------------------------------------------------------------|

You can go to the documentation page for more detailed tutorials and content.

### What's next?

1. **Apply the plugin**: Add the `Gropify` plugin ID to your `settings.gradle.kts`.
2. **Configure properties**: Define your properties in `gradle.properties`.
3. **Sync the project**: After a Gradle sync, you can start enjoying type-safe property access.

In the opened page, select the **Quick Start** section in the sidebar to continue reading.

## More Projects

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h2>Hey, wait a second! 👋</h2>
    <h3>If this project was helpful, why not stick around and check out more of my work below?</h3>
    <h3>Feel free to leave a follow or a star ⭐️ if they bring you value!</h3>
    <h1><a href="https://github.com/fankes/fankes/blob/main/project-promote/README.md">→ Click here to discover more of my projects ←</a></h1>
</div>

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=HighCapable/Gropify&type=Date)

## License

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

Copyright © 2019 HighCapable