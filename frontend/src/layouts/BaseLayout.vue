<template>
  <div class="base-layout">
    <header class="header">
      <div class="container">
        <div class="header-content">
          <router-link to="/" class="logo">
            <img :src="logoUrl" alt="MC Trade Global" />
          </router-link>
          <nav class="nav" :class="{ 'nav-mobile-open': mobileMenuOpen }">
            <div class="nav-list">
              <div 
                v-for="item in menuList" 
                :key="item.id" 
                class="nav-item"
                @mouseenter="activeMenu = item.id"
                @mouseleave="activeMenu = null"
              >
                <router-link 
                  :to="item.path" 
                  class="nav-link"
                  :class="{ active: isActive(item.path) }"
                >
                  {{ locale === 'zh' ? item.menuName : item.menuNameEn }}
                </router-link>
                <div v-if="item.children && item.children.length" class="nav-dropdown">
                  <router-link 
                    v-for="child in item.children" 
                    :key="child.id"
                    :to="child.path"
                    class="dropdown-link"
                  >
                    {{ locale === 'zh' ? child.menuName : child.menuNameEn }}
                  </router-link>
                </div>
              </div>
            </div>
          </nav>
          <div class="header-actions">
            <el-button 
              class="lang-btn" 
              size="small"
              @click="toggleLocale"
            >
              {{ locale === 'zh' ? 'EN' : '中' }}
            </el-button>
            <button class="hamburger" @click="mobileMenuOpen = !mobileMenuOpen">
              <span></span>
              <span></span>
              <span></span>
            </button>
          </div>
        </div>
      </div>
    </header>
    
    <main class="main">
      <router-view />
    </main>
    
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-info">
            <p class="copyright">
              © {{ currentYear }} MC Trade Global {{ locale === 'zh' ? '保留所有权利' : 'All Rights Reserved' }}
            </p>
            <p class="contact-info">
              {{ locale === 'zh' ? '地址' : 'Address' }}: {{ config.company_address || '' }}
            </p>
            <p class="contact-info">
              {{ locale === 'zh' ? '电话' : 'Tel' }}: {{ config.company_phone || '' }}
            </p>
            <p class="contact-info">
              Email: {{ config.company_email || '' }}
            </p>
          </div>
          <div class="footer-social">
            <a v-if="config.facebook_url" :href="config.facebook_url" target="_blank" rel="noopener">
              <span class="social-icon facebook">Facebook</span>
            </a>
            <a v-if="config.linkedin_url" :href="config.linkedin_url" target="_blank" rel="noopener">
              <span class="social-icon linkedin">LinkedIn</span>
            </a>
          </div>
        </div>
        <div v-if="config.icp_number" class="footer-icp">
          {{ config.icp_number }}
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useAppStore } from '@/store/app'
import { homeApi } from '@/api'

const route = useRoute()
const { locale, toggleLocale } = useI18n()
const appStore = useAppStore()

const mobileMenuOpen = ref(false)
const activeMenu = ref(null)
const menuList = ref([])
const config = ref({})

const currentYear = computed(() => new Date().getFullYear())
const logoUrl = computed(() => config.value.website_logo || '/logo.png')

const isActive = (path) => {
  if (path === '/') return route.path === '/'
  return route.path.startsWith(path)
}

onMounted(async () => {
  try {
    const [menuRes, configRes] = await Promise.all([
      homeApi.getBanners().catch(() => ({ data: [] })),
      homeApi.getConfig().catch(() => ({ data: {} }))
    ])
    config.value = configRes.data || {}
    appStore.setConfig(config.value)
  } catch (e) {
    console.error('Failed to load config:', e)
  }
})
</script>

<style lang="scss" scoped>
.base-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  position: sticky;
  top: 0;
  z-index: 100;
  background: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
}

.logo img {
  height: 40px;
}

.nav {
  flex: 1;
  display: flex;
  justify-content: center;
}

.nav-list {
  display: flex;
  gap: 40px;
}

.nav-item {
  position: relative;
}

.nav-link {
  display: block;
  padding: 10px 0;
  font-size: 16px;
  color: #333;
  transition: color 0.3s;

  &:hover,
  &.active {
    color: #409eff;
  }
}

.nav-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  min-width: 150px;
  background: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  opacity: 0;
  visibility: hidden;
  transform: translateY(10px);
  transition: all 0.3s;
}

.nav-item:hover .nav-dropdown {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

.dropdown-link {
  display: block;
  padding: 10px 20px;
  color: #333;
  
  &:hover {
    background: #f5f5f5;
    color: #409eff;
  }
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.lang-btn {
  padding: 6px 12px;
  font-size: 14px;
}

.hamburger {
  display: none;
  flex-direction: column;
  gap: 5px;
  padding: 5px;
  background: none;
  border: none;
  cursor: pointer;

  span {
    display: block;
    width: 24px;
    height: 2px;
    background: #333;
  }
}

.main {
  flex: 1;
}

.footer {
  background: #2c3e50;
  color: #fff;
  padding: 40px 0 20px;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 20px;
}

.footer-info {
  p {
    margin-bottom: 10px;
    font-size: 14px;
    opacity: 0.9;
  }
}

.copyright {
  font-size: 16px;
  margin-bottom: 20px;
}

.footer-social {
  display: flex;
  gap: 20px;
}

.social-icon {
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
  font-size: 14px;
  transition: background 0.3s;

  &:hover {
    background: rgba(255, 255, 255, 0.2);
  }
}

.footer-icp {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  text-align: center;
  font-size: 12px;
  opacity: 0.7;
}

@media (max-width: 768px) {
  .nav {
    position: fixed;
    top: 70px;
    left: 0;
    right: 0;
    background: #fff;
    padding: 20px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    transform: translateY(-100%);
    opacity: 0;
    visibility: hidden;
    transition: all 0.3s;
  }

  .nav-mobile-open {
    transform: translateY(0);
    opacity: 1;
    visibility: visible;
  }

  .nav-list {
    flex-direction: column;
    gap: 0;
  }

  .nav-item:hover .nav-dropdown {
    display: none;
  }

  .hamburger {
    display: flex;
  }

  .footer-content {
    flex-direction: column;
    text-align: center;
  }
}
</style>
