# Changelog

> The version update history of `Gropify` is recorded here.

::: danger

We will only maintain the latest API version. If you are using an outdated API version, you voluntarily renounce any possibility of maintenance.

:::

::: warning

To avoid translation time consumption, Changelog will use **Google Translation** from **Chinese** to **English**, please refer to the original text for actual reference.

Time zone of version release date: **UTC+8**

:::

### 1.0.2 | 2026.06.08 &ensp;<Badge type="tip" text="latest" vertical="middle" />

- Optimized the buildscript accessors cache generation process, fixed the issue where multiple Gradle Daemons may read incomplete artifacts during simultaneous initialization
- Optimized the integrity check for the buildscript accessors cache to prevent damaged `jar`, `pom`, or unready cache from being added to the buildscript classpath
- Added the `sourceCode` configuration method block to configure source code options shared by `android`, `jvm`, and `kmp`
- Optimized the source code configuration structure, merged `CommonCodeGenerateConfig` into `SourceCodeGenerateConfig`, and unified the configuration override order
- Fixed the issue where `android-project-namespaces.json` may not be regenerated when generated source code already exists
- Fixed the issue where property interpolation may resolve to an empty string when `includeKeys` filtering is enabled and the referenced key is not included in the generated result
- Optimized integer boundary detection for automatic property type conversion to avoid unreachable checks or implicit range matching between `Int` and `Long`

### 1.0.1 | 2025.11.16 &ensp;<Badge type="warning" text="stale" vertical="middle" />

- Fixed the issue where `permanentKeyValues` was incorrectly configured to `replacementKeyValues`
- Optimized the automatic type conversion function for property key-values, fixed the problem of negative long integers being converted to integers
- Optimized log output function, added tags and text colors for each type of log
- Added `keyValueRules` to manually specify the type of property key-values, effective when `useTypeAutoConversion` is enabled
- Enhanced debugging function, added detailed log output in debug mode
- Fixed the issue where `extensionName` was judged as empty and illegal under default settings
- Added judgment content for default extension method names that may cause conflicts
- Added `manifestPlaceholders` property key-value synchronization function to `android` configuration method block
- Fixed the problem of Javapoet and Kotlinpoet processing special escape characters in source code generation
- Fixed other issues that may cause build script compilation failure

### 1.0.0 | 2025.11.11 &ensp;<Badge type="warning" text="stale" vertical="middle" />

- The first version is submitted to Maven