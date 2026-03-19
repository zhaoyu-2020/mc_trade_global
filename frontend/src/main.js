import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'
import App from './App.vue'
import router from './router'
import i18n from './locales'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import './assets/styles/main.scss'

const app = createApp(App)
const pinia = createPinia()

NProgress.configure({ showSpinner: false })

router.beforeEach((to, from, next) => {
  NProgress.start()
  next()
})

router.afterEach(() => {
  NProgress.done()
})

app.use(pinia)
app.use(router)
app.use(ElementPlus, { locale: i18n.global.locale.value === 'zh' ? zhCn : en })
app.use(i18n)

app.mount('#app')
