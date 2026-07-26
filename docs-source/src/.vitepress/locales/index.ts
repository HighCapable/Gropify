import { defineConfig } from 'vitepress';
import en from './en';
import zhCn from './zh-cn';

/** Localized VitePress site definitions keyed by stable route segments. */
export default defineConfig({
    locales: {
        en: {
            label: 'English',
            link: '/en/',
            lang: en.lang,
            description: en.description,
            themeConfig: en.themeConfig
        },
        'zh-cn': {
            label: '简体中文',
            link: '/zh-cn/',
            lang: zhCn.lang,
            description: zhCn.description,
            themeConfig: zhCn.themeConfig
        }
    }
});