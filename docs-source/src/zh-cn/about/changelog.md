# 更新日志

> 这里记录了 `Gropify` 的版本更新历史。

::: danger

我们只会对最新的 API 版本进行维护，若你正在使用过时的 API 版本则代表你自愿放弃一切维护的可能性。

:::

### 1.0.1 | 2025.11.16 &ensp;<Badge type="tip" text="最新" vertical="middle" />

- 修复 `permanentKeyValues` 被错误地配置到 `replacementKeyValues` 的问题
- 优化属性键值的类型自动转换功能，修复负数长整型被转换为整型的问题
- 优化日志输出功能，加入每种日志的标签和文字颜色
- 新增 `keyValueRules` 可手动指定属性键值的类型，在启用了 `useTypeAutoConversion` 时生效
- 增强调试功能，加入了调试模式下的详细日志输出
- 修复 `extensionName` 在默认设置情况下被判断为空格式非法的问题
- 增加了可能造成冲突的默认扩展方法名称判断内容
- `android` 配置方法块新增 `manifestPlaceholders` 属性键值同步功能
- 修复源代码生成 Javapoet 和 Kotlinpoet 处理特殊转义字符的问题
- 修复其它可能导致构建脚本编译失败的问题

### 1.0.0 | 2025.11.11 &ensp;<Badge type="warning" text="过旧" vertical="middle" />

- 首个版本提交至 Maven