import{_ as s,o as n,c as a,e as l}from"./app-aH6YcK-Q.js";const e={},p=l(`<h1 id="快速开始" tabindex="-1"><a class="header-anchor" href="#快速开始" aria-hidden="true">#</a> 快速开始</h1><blockquote><p>集成 <code>Gropify</code> 到你的项目中。</p></blockquote><h2 id="部署插件" tabindex="-1"><a class="header-anchor" href="#部署插件" aria-hidden="true">#</a> 部署插件</h2><p><img src="https://img.shields.io/maven-central/v/com.highcapable.gropify/com.highcapable.gropify.gradle.plugin?logo=apachemaven&amp;logoColor=orange&amp;style=flat-square" alt="Maven Central"><span style="margin-left:5px;"></span><img src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Frefs%2Fheads%2Fmain%2Frepository%2Freleases%2Fcom%2Fhighcapable%2Fgropify%2Fcom.highcapable.gropify.gradle.plugin%2Fmaven-metadata.xml&amp;logo=apachemaven&amp;logoColor=orange&amp;label=highcapable-maven-releases&amp;style=flat-square" alt="Maven metadata URL"></p><p><code>Gropify</code> 的依赖发布在 <strong>Maven Central</strong> 和我们的公共存储库中，你可以使用如下方式配置存储库。</p><p>我们建议使用不低于 <code>7.x.x</code> 版本的 Gradle，并推荐使用 Kotlin DSL 作为 Gradle 构建脚本语言，文档中将不再详细介绍在 Groovy DSL 中的使用方法。</p><p>我们推荐使用 <code>pluginManagement</code> 新方式进行部署，它是自 Gradle <code>7.x.x</code> 版本开始添加的功能。</p><p>如果你的项目依然在使用 <code>buildscript</code> 的方式进行管理，推荐迁移到新方式，这里将不再提供旧版本的使用方式说明。</p><p>首先，在你的项目 <code>settings.gradle.kts</code> 中配置插件的存储库。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">pluginManagement</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">repositories</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#DCBDFB;">gradlePluginPortal</span><span style="color:#ADBAC7;">() </span><span style="color:#768390;">// 可选</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#DCBDFB;">google</span><span style="color:#ADBAC7;">() </span><span style="color:#768390;">// 可选</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#DCBDFB;">mavenCentral</span><span style="color:#ADBAC7;">() </span><span style="color:#768390;">// 必须</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// (可选) 你可以添加此 URL 以使用我们的公共存储库</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 当 Sonatype-OSS 发生故障无法发布依赖时，此存储库作为备选进行添加</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 详情请前往：https://github.com/HighCapable/maven-repository</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#DCBDFB;">maven</span><span style="color:#ADBAC7;">(</span><span style="color:#96D0FF;">&quot;https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/releases&quot;</span><span style="color:#ADBAC7;">)</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>然后在 <code>settings.gradle.kts</code> 中 <code>plugin</code> 添加 <code>Gropify</code> 插件依赖，请注意<strong>不要</strong>在后方加入 <code>apply false</code>。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">plugins</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">id</span><span style="color:#ADBAC7;">(</span><span style="color:#96D0FF;">&quot;com.highcapable.gropify&quot;</span><span style="color:#ADBAC7;">) version </span><span style="color:#96D0FF;">&quot;&lt;version&gt;&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>请将 <code>&lt;version&gt;</code> 修改为此小结顶部显示的版本。</p><p>上述配置完成后，运行一次 Gradle Sync。</p><p><code>Gropify</code> 将会自动搜索根项目和每个子项目中的 <code>gradle.properties</code> 文件，并读取其中的属性键值，为每个项目生成对应的代码。</p><div class="custom-container warning"><p class="custom-container-title">注意</p><p><code>Gropify</code> 只能被应用到 <code>settings.gradle.kts</code> 中，配置一次即可全局生效，请勿将其应用到 <code>build.gradle.kts</code> 中，否则功能将会无效。</p></div><h2 id="功能配置" tabindex="-1"><a class="header-anchor" href="#功能配置" aria-hidden="true">#</a> 功能配置</h2><p>你可以对 <code>Gropify</code> 进行配置来实现自定义和个性化功能。</p><p><code>Gropify</code> 为你提供了相对丰富的可自定义功能，下面是这些功能的说明与配置方法。</p><p>请在你的 <code>settings.gradle.kts</code> 中添加 <code>gropify</code> 方法块以开始配置 <code>Gropify</code>。</p><p>如需在 Groovy DSL 中使用，请将所有变量的 <code>=</code> 改为空格，并删除 <code>Enabled</code> 前方的 <code>is</code> 即可。</p><p>如果你遇到了 <code>Gradle DSL method not found</code> 错误，解决方案为迁移到 Kotlin DSL。</p><p>如果你不想全部使用 Kotlin DSL，你也可以仅将 <code>settings.gradle</code> 迁移到 <code>settings.gradle.kts</code>。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">gropify</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 启用 Gropify，设置为 \`false\` 将禁用所有功能</span></span>
<span class="line"><span style="color:#ADBAC7;">    isEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用调试模式</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以通过启用此选项在日志中打印更多调试信息帮助我们定位问题</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 此功能仅用于调试！</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   调试日志将包含你的本地环境，其中可能包含敏感信息，请务必保护好这些信息</span></span>
<span class="line"><span style="color:#ADBAC7;">    debugMode </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">false</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p><code>Gropify</code> 的配置模式分为 <code>global</code> 全局配置和 <code>rootProject</code>、<code>projects</code> 根项目和子项目配置三种。</p><p>你可以在子项的代码块中继续配置和集成顶层项目的配置。</p><p>以下配置均在 <code>gropify</code> 方法块中进行。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#768390;">// 全局配置</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 你可以在全局配置中修改所有项目中的配置</span></span>
<span class="line"><span style="color:#768390;">// 每个项目中未进行声明的配置将使用全局配置</span></span>
<span class="line"><span style="color:#768390;">// 每个配置方法块中的功能完全一致</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 你可以参考下方根项目、子项目的配置方法</span></span>
<span class="line"><span style="color:#DCBDFB;">global</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 通用配置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">common</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;common&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 构建脚本配置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">buildscript</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;buildscript&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// Android 项目配置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">android</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;android&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// JVM 项目配置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">jvm</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;jvm&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// Kotlin 多平台项目配置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">kmp</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;kmp&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span>
<span class="line"><span style="color:#768390;">// 根项目 (Root Project) 配置</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 这是一个特殊的配置方法块，只能用于根项目</span></span>
<span class="line"><span style="color:#DCBDFB;">rootProject</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">common</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;common&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">buildscript</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;buildscript&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">android</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;android&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">jvm</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;jvm&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">kmp</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;kmp&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span>
<span class="line"><span style="color:#768390;">// 其它项目与子项目配置</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 在方法参数中填入需要配置的项目完整名称来配置对应的项目</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 如果当前项目是子项目，你必须填写子项目前面的 &quot;:&quot;，例如 &quot;:app&quot;</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 如果当前项目为嵌套型子项目，例如 app → sub</span></span>
<span class="line"><span style="color:#768390;">// 此时你需要使用 &quot;:&quot; 来分隔多个子项目，例如 &quot;:app:sub&quot;</span></span>
<span class="line"><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#768390;">// 根项目的名称不能直接用来配置子项目，请使用 &quot;rootProject&quot;</span></span>
<span class="line"><span style="color:#768390;">// 你可以同时进行多个项目与子项目配置，在方法参数中填入需要配置的项目完整名称数组来配置每个对应的项目</span></span>
<span class="line"><span style="color:#DCBDFB;">projects</span><span style="color:#ADBAC7;">(</span><span style="color:#96D0FF;">&quot;:app&quot;</span><span style="color:#ADBAC7;">, </span><span style="color:#96D0FF;">&quot;:modules:library1&quot;</span><span style="color:#ADBAC7;">, </span><span style="color:#96D0FF;">&quot;:modules:library2&quot;</span><span style="color:#ADBAC7;">) {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">common</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;common&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">buildscript</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;buildscript&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">android</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;android&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">jvm</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;jvm&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">kmp</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 配置 &quot;kmp&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>你可以继续在下方了解如何配置每个方法块中的功能。</p><h3 id="通用配置" tabindex="-1"><a class="header-anchor" href="#通用配置" aria-hidden="true">#</a> 通用配置</h3><p>在这里你可以同时配置所有配置类型的相关功能，这里的配置会向下应用到 <a href="#%E6%9E%84%E5%BB%BA%E8%84%9A%E6%9C%AC%E9%85%8D%E7%BD%AE">构建脚本配置</a>、<a href="#android-%E9%A1%B9%E7%9B%AE%E9%85%8D%E7%BD%AE">Android 项目配置</a>、<a href="#jvm-%E9%A1%B9%E7%9B%AE%E9%85%8D%E7%BD%AE">JVM 项目配置</a>、<a href="#kotlin-%E5%A4%9A%E5%B9%B3%E5%8F%B0%E9%A1%B9%E7%9B%AE%E9%85%8D%E7%BD%AE">Kotlin 多平台项目配置</a> 中。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">common</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 启用功能</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以分别对 [buildscript]、[android]、[jvm]、[kmp] 进行设置</span></span>
<span class="line"><span style="color:#ADBAC7;">    isEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否排除非字符串类型的键值内容</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将排除非字符串类型的键值和内容</span></span>
<span class="line"><span style="color:#ADBAC7;">    excludeNonStringValue </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否使用类型自动转换</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将自动识别属性键值中的类型并转换为对应类型</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 启用后，如果你想强制将键值内容设为字符串类型，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 可以使用单引号或双引号包裹整个字符串</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 禁用此功能后，上述功能也将失效</span></span>
<span class="line"><span style="color:#ADBAC7;">    useTypeAutoConversion </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否使用键值内容插值</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将自动识别属性键值内容中的 \`\${...}\` 并进行替换</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 注意: 插值内容仅会从当前 (当前配置文件) 属性键值列表中查找</span></span>
<span class="line"><span style="color:#ADBAC7;">    useValueInterpolation </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置已存在的属性文件</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 属性文件将根据你设置的文件名自动从当前根项目、</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 子项目和用户目录的根目录获取</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认情况下，如果 [addDefault] 为 \`true\`，将添加 &quot;gradle.properties&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以添加多组属性文件名，它们将按顺序读取</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 通常无需修改此设置，错误的文件名将导致获取空键值内容</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">existsPropertyFiles</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some-other-1.properties&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some-other-2.properties&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        addDefault </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置永久属性键值列表</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 在这里你可以设置一些必须存在的键值，无论是否能从属性键值中获取，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 这些键值都将被生成</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 这些键如果存在于属性键中则使用属性键的内容，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果不存在则使用这里设置的内容</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 属性键名称中不能存在特殊符号和空格，否则可能导致生成失败</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">permanentKeyValues</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;permanent.some.key1&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#96D0FF;">&quot;some_value_1&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;permanent.some.key2&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#96D0FF;">&quot;some_value_2&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置替换属性键值列表</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 在这里你可以设置一些需要替换的键值，这些键值</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 将替换现有的属性键值，如果不存在则忽略</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 这里设置的键值也会覆盖 [permanentKeyValues] 中设置的键值</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">replacementKeyValues</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some.key1&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#96D0FF;">&quot;new.value1&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some.key2&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#96D0FF;">&quot;new.value2&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置需要排除的属性键值名称列表</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 在这里你可以设置一些要从已知属性键值中排除的键名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果这些键存在于属性键中则排除它们，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 不会出现在生成的代码中</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 如果你排除了 [permanentKeyValues] 中设置的键值，那么它们将</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   仅更改为你设置的初始键值内容并继续存在</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">excludeKeys</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;exclude.some.key1&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;exclude.some.key2&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置需要包含的属性键值名称列表</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 在这里你可以设置一些要从已知属性键值中包含的键名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果属性键存在则包含这些键，未包含的键不会出现在生成的代码中</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">includeKeys</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;include.some.key1&quot;</span><span style="color:#ADBAC7;">,</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;include.some.key2&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置属性键值规则</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以设置一组键值规则，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 使用 [ValueRule] 创建新规则来解析获取的键值内容</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 当属性键存在时应用这些键值规则</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">keyValuesRules</span><span style="color:#ADBAC7;">(</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some.key1&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#DCBDFB;">ValueRule</span><span style="color:#ADBAC7;"> { </span><span style="color:#F47067;">if</span><span style="color:#ADBAC7;"> (it.</span><span style="color:#DCBDFB;">contains</span><span style="color:#ADBAC7;">(</span><span style="color:#96D0FF;">&quot;_&quot;</span><span style="color:#ADBAC7;">)) it.</span><span style="color:#DCBDFB;">replace</span><span style="color:#ADBAC7;">(</span><span style="color:#96D0FF;">&quot;_&quot;</span><span style="color:#ADBAC7;">, </span><span style="color:#96D0FF;">&quot;-&quot;</span><span style="color:#ADBAC7;">) </span><span style="color:#F47067;">else</span><span style="color:#ADBAC7;"> it },</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some.key2&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#DCBDFB;">ValueRule</span><span style="color:#ADBAC7;"> { </span><span style="color:#96D0FF;">&quot;</span><span style="color:#6CB6FF;">$it</span><span style="color:#96D0FF;">-value&quot;</span><span style="color:#ADBAC7;"> },</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 你还可以指定期望的类型类，生成时将使用你指定的类型，</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 如果类型无法正确转换，将抛出异常</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#768390;">// 如果未启用 [useTypeAutoConversion]，此参数将被忽略</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#96D0FF;">&quot;some.key3&quot;</span><span style="color:#ADBAC7;"> to </span><span style="color:#DCBDFB;">ValueRule</span><span style="color:#ADBAC7;">(Int::</span><span style="color:#DCBDFB;">class</span><span style="color:#ADBAC7;">)</span></span>
<span class="line"><span style="color:#ADBAC7;">    )</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 设置查找属性键值的位置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 [GropifyLocation.CurrentProject]、[GropifyLocation.RootProject]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以使用以下类型进行设置</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - [GropifyLocation.CurrentProject]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - [GropifyLocation.RootProject]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - [GropifyLocation.Global]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - [GropifyLocation.System]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - [GropifyLocation.SystemEnv]</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 我们将按顺序从你设置的位置生成属性键值，生成位置的顺序遵循你设置的顺序</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 风险警告: [GropifyLocation.Global]、[GropifyLocation.System]、</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   [GropifyLocation.SystemEnv] 可能包含密钥和证书，请谨慎管理生成的代码</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">locations</span><span style="color:#ADBAC7;">(GropifyLocation.CurrentProject, GropifyLocation.RootProject)</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="custom-container tip"><p class="custom-container-title">小提示</p><p>在引用 <code>GropifyLocation</code> 时，构建脚本在配合 IDE 自动导入时可能会在构建脚本顶部生成以下内容。</p><div class="language-kotlin" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">import</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">com.highcapable.gropify.plugin.config.type.GropifyLocation</span></span>
<span class="line"></span></code></pre></div><p><code>Gropify</code> 对此做了 alias 处理，你可以直接删除此 import 语句。</p></div><h3 id="构建脚本配置" tabindex="-1"><a class="header-anchor" href="#构建脚本配置" aria-hidden="true">#</a> 构建脚本配置</h3><p>在构建脚本中生成的代码可直接被当前 <code>build.gradle.kts</code>、<code>build.gradle</code> 使用。</p><p>这里的配置包括 <code>common</code> 中的配置，你可以对其进行复写。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">buildscript</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义构建脚本扩展名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;gropify&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    extensionName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;gropify&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="custom-container warning"><p class="custom-container-title">注意</p><p>Gradle 中也有一个 <code>buildscript</code> 方法块，请注意使用正确的 DSL 层级。</p></div><h3 id="android-项目配置" tabindex="-1"><a class="header-anchor" href="#android-项目配置" aria-hidden="true">#</a> Android 项目配置</h3><p>此配置块中的内容仅对存在 AGP 的项目生效。</p><p>这里的配置包括 <code>common</code> 中的配置，你可以对其进行复写。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">android</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的目录路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以填写相对于当前项目的路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 格式示例: &quot;path/to/your/src/main&quot;，&quot;src/main&quot; 是固定后缀</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;build/generated/gropify/src/main&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 我们建议你将生成路径设置在 &quot;build&quot; 目录下，该目录默认被版本控制系统忽略</span></span>
<span class="line"><span style="color:#ADBAC7;">    generateDirPath </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;build/generated/gropify&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义部署 \`sourceSet\` 名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你的项目源代码部署名称不是默认的，可以在此处自定义</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;main&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    sourceSetName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;main&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的包名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// Android 项目默认使用 \`android\` 配置方法块中的 \`namespace\`</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;generated&quot; 是固定后缀，用于避免与你自己的命名空间冲突，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你不想要此后缀，可以参考 [isIsolationEnabled]</span></span>
<span class="line"><span style="color:#ADBAC7;">    packageName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;com.example.mydemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的类名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认使用当前项目的名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;Properties&quot; 是固定后缀，用于与你自己的类名区分</span></span>
<span class="line"><span style="color:#ADBAC7;">    className </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;MyDemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否使用 Kotlin 语言生成</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将生成 Kotlin 代码，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 禁用后将生成 Java 代码</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 当此项目为纯 Java 项目时，此选项将被禁用</span></span>
<span class="line"><span style="color:#ADBAC7;">    useKotlin </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用受限访问</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认禁用，启用后将为生成的 Kotlin 类添加 \`internal\` 修饰符，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 或为生成的 Java 类移除 \`public\` 修饰符</span></span>
<span class="line"><span style="color:#ADBAC7;">    isRestrictedAccessEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用代码隔离</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将在隔离的包后缀 &quot;generated&quot; 中生成代码，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   Gropify 生成代码的项目，以避免冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    isIsolationEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否使用清单占位符的生成。</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// </span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认禁用，启用后将同步属性的键值到 \`android\` 配置方法块中的 \`manifestPlaceholders\`</span></span>
<span class="line"><span style="color:#ADBAC7;">    manifestPlaceholders </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">false</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="jvm-项目配置" tabindex="-1"><a class="header-anchor" href="#jvm-项目配置" aria-hidden="true">#</a> JVM 项目配置</h3><p>此配置块中的内容仅对纯 JVM 项目生效 (包括 Kotlin、Java 项目)，如果是 Android 项目请参考 <a href="#android-%E9%A1%B9%E7%9B%AE%E9%85%8D%E7%BD%AE">Android 项目配置</a> 进行配置。</p><p>这里的配置包括 <code>common</code> 中的配置，你可以对其进行复写。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">jvm</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的目录路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以填写相对于当前项目的路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 格式示例: &quot;path/to/your/src/main&quot;，&quot;src/main&quot; 是固定后缀</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;build/generated/gropify/src/main&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 我们建议你将生成路径设置在 &quot;build&quot; 目录下，该目录默认被版本控制系统忽略</span></span>
<span class="line"><span style="color:#ADBAC7;">    generateDirPath </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;build/generated/gropify&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义部署 \`sourceSet\` 名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你的项目源代码部署名称不是默认的，可以在此处自定义</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;main&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    sourceSetName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;main&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的包名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// Java、Kotlin 项目默认使用项目设置的 \`project.group\`</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;generated&quot; 是固定后缀，用于避免与你自己的命名空间冲突，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你不想要此后缀，可以参考 [isIsolationEnabled]</span></span>
<span class="line"><span style="color:#ADBAC7;">    packageName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;com.example.mydemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的类名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认使用当前项目的名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;Properties&quot; 是固定后缀，用于与你自己的类名区分</span></span>
<span class="line"><span style="color:#ADBAC7;">    className </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;MyDemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否使用 Kotlin 语言生成</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将生成 Kotlin 代码，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 禁用后将生成 Java 代码</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 当此项目为纯 Java 项目时，此选项将被禁用</span></span>
<span class="line"><span style="color:#ADBAC7;">    useKotlin </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用受限访问</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认禁用，启用后将为生成的 Kotlin 类添加 \`internal\` 修饰符，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 或为生成的 Java 类移除 \`public\` 修饰符</span></span>
<span class="line"><span style="color:#ADBAC7;">    isRestrictedAccessEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用代码隔离</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将在隔离的包后缀 &quot;generated&quot; 中生成代码，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   Gropify 生成代码的项目，以避免冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    isIsolationEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="kotlin-多平台项目配置" tabindex="-1"><a class="header-anchor" href="#kotlin-多平台项目配置" aria-hidden="true">#</a> Kotlin 多平台项目配置</h3><p>此配置块中的内容仅对含有 Kotlin Multiplatform 插件的项目生效。</p><p>这里的配置包括 <code>common</code> 中的配置，你可以对其进行复写。</p><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">kmp</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的目录路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 你可以填写相对于当前项目的路径</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 格式示例: &quot;path/to/your/src/main&quot;，&quot;src/main&quot; 是固定后缀</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;build/generated/gropify/src/main&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 我们建议你将生成路径设置在 &quot;build&quot; 目录下，该目录默认被版本控制系统忽略</span></span>
<span class="line"><span style="color:#ADBAC7;">    generateDirPath </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;build/generated/gropify&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义部署 \`sourceSet\` 名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你的项目源代码部署名称不是默认的，可以在此处自定义。</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认为 &quot;commonMain&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">    sourceSetName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;commonMain&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的包名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// Kotlin 多平台项目默认使用项目设置的 \`project.group\`</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 在 Kotlin 多平台项目中，如果同时应用了 AGP 插件，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 仍将默认使用 \`namespace\` 作为包名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;generated&quot; 是固定后缀，用于避免与你自己的命名空间冲突，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 如果你不想要此后缀，可以参考 [isIsolationEnabled]</span></span>
<span class="line"><span style="color:#ADBAC7;">    packageName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;com.example.mydemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 自定义生成的类名</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认使用当前项目的名称</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// &quot;Properties&quot; 是固定后缀，用于与你自己的类名区分</span></span>
<span class="line"><span style="color:#ADBAC7;">    className </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#96D0FF;">&quot;MyDemo&quot;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用受限访问</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认禁用，启用后将为生成的 Kotlin 类添加 \`internal\` 修饰符</span></span>
<span class="line"><span style="color:#ADBAC7;">    isRestrictedAccessEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">false</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 是否启用代码隔离</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 默认启用，启用后将在隔离的包后缀 &quot;generated&quot; 中生成代码，</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// 以避免与其他同样使用或不仅使用 Gropify 生成代码的项目冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">// - 注意: 如果你禁用此选项，请确保没有其他同样使用或不仅使用</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#768390;">//   Gropify 生成代码的项目，以避免冲突</span></span>
<span class="line"><span style="color:#ADBAC7;">    isIsolationEnabled </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">true</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="使用示例" tabindex="-1"><a class="header-anchor" href="#使用示例" aria-hidden="true">#</a> 使用示例</h2><p>下面是一个项目的 <code>gradle.properties</code> 配置文件。</p><blockquote><p>示例如下</p></blockquote><div class="language-properties line-numbers-mode" data-ext="properties"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">project.groupName</span><span style="color:#ADBAC7;">=com.highcapable.gropifydemo</span></span>
<span class="line"><span style="color:#F47067;">project.description</span><span style="color:#ADBAC7;">=Hello Gropify Demo!</span></span>
<span class="line"><span style="color:#F47067;">project.version</span><span style="color:#ADBAC7;">=1.0.0</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>在构建脚本 <code>build.gradle.kts</code> 中，我们就可以如下所示这样直接去使用这些键值。</p><p>这里以 Maven 发布的配置部分举例。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">publications</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">create</span><span style="color:#ADBAC7;">&lt;</span><span style="color:#F69D50;">MavenPublication</span><span style="color:#ADBAC7;">&gt;(</span><span style="color:#96D0FF;">&quot;maven&quot;</span><span style="color:#ADBAC7;">) {</span></span>
<span class="line"><span style="color:#ADBAC7;">        groupId </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.groupName</span></span>
<span class="line"><span style="color:#ADBAC7;">        version </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.version</span></span>
<span class="line"><span style="color:#ADBAC7;">        pom.description.</span><span style="color:#DCBDFB;">set</span><span style="color:#ADBAC7;">(gropify.project.description)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#DCBDFB;">from</span><span style="color:#ADBAC7;">(components[</span><span style="color:#96D0FF;">&quot;java&quot;</span><span style="color:#ADBAC7;">])</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>同样地，你也可以在当前项目中调用生成的键值。</p><blockquote><p>Kotlin</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">val</span><span style="color:#ADBAC7;"> groupName </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_GROUP_NAME</span></span>
<span class="line"><span style="color:#F47067;">val</span><span style="color:#ADBAC7;"> description </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_DESCRIPTION</span></span>
<span class="line"><span style="color:#F47067;">val</span><span style="color:#ADBAC7;"> version </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_VERSION</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><blockquote><p>Java</p></blockquote><div class="language-java line-numbers-mode" data-ext="java"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">var</span><span style="color:#F69D50;"> </span><span style="color:#ADBAC7;">groupName</span><span style="color:#F69D50;"> </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_GROUP_NAME;</span></span>
<span class="line"><span style="color:#F47067;">var</span><span style="color:#F69D50;"> </span><span style="color:#ADBAC7;">description</span><span style="color:#F69D50;"> </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_DESCRIPTION;</span></span>
<span class="line"><span style="color:#F47067;">var</span><span style="color:#F69D50;"> </span><span style="color:#ADBAC7;">version</span><span style="color:#F69D50;"> </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> GropifyDemoProperties.PROJECT_VERSION;</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>下面再以 Android 项目举例。</p><p>在 Android 项目中通常需要配置很多重复、固定的属性，例如 <code>targetSdk</code>。</p><blockquote><p>示例如下</p></blockquote><div class="language-properties line-numbers-mode" data-ext="properties"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">project.namespace</span><span style="color:#ADBAC7;">=com.highcapable.gropifydemo</span></span>
<span class="line"><span style="color:#F47067;">project.appName</span><span style="color:#ADBAC7;">=Gropify Demo</span></span>
<span class="line"><span style="color:#F47067;">project.compileSdk</span><span style="color:#ADBAC7;">=36</span></span>
<span class="line"><span style="color:#F47067;">project.targetSdk</span><span style="color:#ADBAC7;">=36</span></span>
<span class="line"><span style="color:#F47067;">project.minSdk</span><span style="color:#ADBAC7;">=26</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>当你设置了 <code>useTypeAutoConversion = true</code> 时，<code>Gropify</code> 在生成实体类过程在默认配置下将尝试将其转换为对应的类型。</p><p>例如下方所使用的键值，其类型可被识别为字符串和整型，可被项目配置直接使用。</p><blockquote><p>示例如下</p></blockquote><div class="language-kotlin line-numbers-mode" data-ext="kt"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#DCBDFB;">android</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">    namespace </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.namespace</span></span>
<span class="line"><span style="color:#ADBAC7;">    compileSdk </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.compileSdk</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    </span><span style="color:#DCBDFB;">defaultConfig</span><span style="color:#ADBAC7;"> {</span></span>
<span class="line"><span style="color:#ADBAC7;">        minSdk </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.minSdk</span></span>
<span class="line"><span style="color:#ADBAC7;">        targetSdk </span><span style="color:#F47067;">=</span><span style="color:#ADBAC7;"> gropify.project.targetSdk</span></span>
<span class="line"><span style="color:#ADBAC7;">    }</span></span>
<span class="line"><span style="color:#ADBAC7;">}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>当你设置了 <code>manifestPlaceholders = true</code> 时，<code>Gropify</code> 将自动将这些属性键值同步到 <code>android</code> 配置方法块中的 <code>manifestPlaceholders</code>。</p><p>此时你可以直接在 <code>AndroidManifest.xml</code> 中使用这些占位符。</p><blockquote><p>示例如下</p></blockquote><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#ADBAC7;">&lt;</span><span style="color:#8DDB8C;">manifest</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">xmlns:android</span><span style="color:#ADBAC7;">=</span><span style="color:#96D0FF;">&quot;http://schemas.android.com/apk/res/android&quot;</span><span style="color:#ADBAC7;">&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">    &lt;</span><span style="color:#8DDB8C;">application</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#6CB6FF;">android:label</span><span style="color:#ADBAC7;">=</span><span style="color:#96D0FF;">&quot;\${project.appName}&quot;</span></span>
<span class="line"><span style="color:#ADBAC7;">        </span><span style="color:#6CB6FF;">android:icon</span><span style="color:#ADBAC7;">=</span><span style="color:#96D0FF;">&quot;@mipmap/ic_launcher&quot;</span><span style="color:#ADBAC7;">&gt;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#ADBAC7;">        ...</span></span>
<span class="line"><span style="color:#ADBAC7;">    &lt;/</span><span style="color:#8DDB8C;">application</span><span style="color:#ADBAC7;">&gt;</span></span>
<span class="line"><span style="color:#ADBAC7;">&lt;/</span><span style="color:#8DDB8C;">manifest</span><span style="color:#ADBAC7;">&gt;</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>你可以无需再使用 <code>buildConfigField</code> 向 <code>BuildConfig</code> 添加代码，有了 <code>Gropify</code> 生成的属性键值代码，你可以更加灵活地管理你的项目。</p><p>你还可以在属性键值中使用插值 <code>\${...}</code> 互相引用其中的内容，但不允许递归引用。</p><p>当你设置了 <code>useValueInterpolation = true</code> 时，<code>Gropify</code> 将自动合并这些引用的内容到对应位置。</p><blockquote><p>示例如下</p></blockquote><div class="language-properties line-numbers-mode" data-ext="properties"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#F47067;">project.name</span><span style="color:#ADBAC7;">=MyDemo</span></span>
<span class="line"><span style="color:#F47067;">project.developer.name</span><span style="color:#ADBAC7;">=myname</span></span>
<span class="line"><span style="color:#F47067;">project.url</span><span style="color:#ADBAC7;">=https://github.com/\${project.developer.name}/\${project.name}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>如果你在 <code>locations</code> 中添加了 <code>GropifyLocation.SystemEnv</code>，你还可以直接引用系统环境变量。</p><blockquote><p>示例如下</p></blockquote><div class="language-properties line-numbers-mode" data-ext="properties"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#768390;"># Linux 或 macOS 系统中使用 $USER 环境变量可以获取当前用户名</span></span>
<span class="line"><span style="color:#F47067;">project.developer.name</span><span style="color:#ADBAC7;">=\${USER}</span></span>
<span class="line"><span style="color:#768390;"># 假设你有一个名为 SECRET_KEY 的系统环境变量 (请确保安全)</span></span>
<span class="line"><span style="color:#F47067;">project.secretKey</span><span style="color:#ADBAC7;">=\${SECRET_KEY}</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="custom-container warning"><p class="custom-container-title">注意</p><p>这个特性是 <code>Gropify</code> 提供的，原生的 <code>gradle.properties</code> 并不支持此功能。</p><p>插值内容通过 <code>locations</code> 的层级自上而下进行查找替换，如果存在重复的键值名称，将使用最后查找到的内容进行替换。</p></div><h2 id="可能遇到的问题" tabindex="-1"><a class="header-anchor" href="#可能遇到的问题" aria-hidden="true">#</a> 可能遇到的问题</h2><p>如果你的项目仅存在一个根项目，且没有导入任何子项目，此时如果扩展方法不能正常生成， 你可以将你的根项目迁移至子项目并在 <code>settings.gradle.kts</code> 中导入这个子项目，这样即可解决此问题。</p><p>我们一般推荐将项目的功能进行分类，根项目仅用来管理插件和一些配置。</p><h2 id="局限性说明" tabindex="-1"><a class="header-anchor" href="#局限性说明" aria-hidden="true">#</a> 局限性说明</h2><p><code>Gropify</code> 无法生成 <code>settings.gradle.kts</code> 中的扩展方法，因为这属于 <code>Gropify</code> 的上游。</p>`,97),o=[p];function i(c,r){return n(),a("div",null,o)}const d=s(e,[["render",i],["__file","quick-start.html.vue"]]);export{d as default};
