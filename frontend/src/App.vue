<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
import { useI18n } from 'vue-i18n'
import { watch } from 'vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'

const { locale } = useI18n()

watch(locale, (newLocale) => {
  ElementPlus.use(newLocale === 'zh' ? zhCn : en)
})
</script>

<style>
#app {
  font-family: 'Helvetica Neue', Helvetica, Arial, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
