import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAppStore = defineStore('app', () => {
  const locale = ref(localStorage.getItem('locale') || 'zh')
  const collapsed = ref(false)
  const menuList = ref([])
  const config = ref({})

  const isZh = computed(() => locale.value === 'zh')

  function setLocale(newLocale) {
    locale.value = newLocale
    localStorage.setItem('locale', newLocale)
  }

  function toggleLocale() {
    locale.value = locale.value === 'zh' ? 'en' : 'zh'
    localStorage.setItem('locale', locale.value)
  }

  function setCollapsed(value) {
    collapsed.value = value
  }

  function setMenuList(list) {
    menuList.value = list
  }

  function setConfig(cfg) {
    config.value = cfg
  }

  return {
    locale,
    collapsed,
    menuList,
    config,
    isZh,
    setLocale,
    toggleLocale,
    setCollapsed,
    setMenuList,
    setConfig
  }
})
