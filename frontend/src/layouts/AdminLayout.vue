<template>
  <div class="admin-layout">
    <aside class="sidebar" :class="{ collapsed }">
      <div class="sidebar-header">
        <div class="logo">
          <img v-if="!collapsed" src="/admin-logo.png" alt="Admin" />
          <span v-if="!collapsed">MC Trade</span>
        </div>
      </div>
      <el-menu
        :default-active="route.path"
        :collapse="collapsed"
        :router="true"
        class="sidebar-menu"
      >
        <el-menu-item index="/admin/dashboard">
          <span>控制台</span>
        </el-menu-item>
        <el-sub-menu index="content">
          <template #title>
            <span>内容管理</span>
          </template>
          <el-menu-item index="/admin/menu">导航菜单</el-menu-item>
          <el-menu-item index="/admin/banner">轮播图</el-menu-item>
          <el-menu-item index="/admin/category">分类管理</el-menu-item>
          <el-menu-item index="/admin/advantage">优势管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="product">
          <template #title>
            <span>产品管理</span>
          </template>
          <el-menu-item index="/admin/product">产品列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="article">
          <template #title>
            <span>文章管理</span>
          </template>
          <el-menu-item index="/admin/article">文章列表</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/message">
          <span>留言管理</span>
        </el-menu-item>
        <el-sub-menu index="system">
          <template #title>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/admin/user">用户管理</el-menu-item>
          <el-menu-item index="/admin/config">系统设置</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </aside>
    
    <div class="main-wrapper">
      <header class="navbar">
        <div class="navbar-left">
          <el-button 
            :icon="collapsed ? 'Expand' : 'Fold'" 
            @click="collapsed = !collapsed"
            text
          />
        </div>
        <div class="navbar-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" icon="User" />
              <span class="username">{{ userInfo?.username || 'Admin' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      
      <main class="admin-main">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const collapsed = ref(false)
const userInfo = computed(() => userStore.userInfo)

const handleCommand = async (command) => {
  if (command === 'logout') {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      type: 'warning'
    })
    userStore.logout()
    router.push('/admin/login')
  }
}
</script>

<style lang="scss" scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 220px;
  background: #304156;
  transition: width 0.3s;

  &.collapsed {
    width: 64px;
  }
}

.sidebar-header {
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 20px;
  background: #263445;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  white-space: nowrap;

  img {
    width: 32px;
    height: 32px;
  }
}

.sidebar-menu {
  border-right: none;
  background: transparent;

  :deep(.el-menu-item) {
    color: #bfcbd9;

    &:hover {
      background: #263445;
      color: #fff;
    }

    &.is-active {
      background: #409eff;
      color: #fff;
    }
  }
}

.main-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.navbar {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.navbar-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.username {
  font-size: 14px;
  color: #333;
}

.admin-main {
  flex: 1;
  padding: 20px;
  background: #f0f2f5;
}
</style>
