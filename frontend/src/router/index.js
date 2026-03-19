import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress'

NProgress.configure({ showSpinner: false })

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/BaseLayout.vue'),
    children: [
      {
        path: '/',
        name: 'Home',
        component: () => import('@/pages/home/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: '/about',
        name: 'About',
        component: () => import('@/pages/about/index.vue'),
        meta: { title: '关于我们' }
      },
      {
        path: '/product',
        name: 'Product',
        component: () => import('@/pages/product/index.vue'),
        meta: { title: '产品中心' }
      },
      {
        path: '/product/:id',
        name: 'ProductDetail',
        component: () => import('@/pages/product/detail.vue'),
        meta: { title: '产品详情' }
      },
      {
        path: '/article',
        name: 'Article',
        component: () => import('@/pages/article/index.vue'),
        meta: { title: '新闻资讯' }
      },
      {
        path: '/article/:id',
        name: 'ArticleDetail',
        component: () => import('@/pages/article/detail.vue'),
        meta: { title: '文章详情' }
      },
      {
        path: '/contact',
        name: 'Contact',
        component: () => import('@/pages/contact/index.vue'),
        meta: { title: '联系我们' }
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    children: [
      {
        path: '',
        redirect: '/admin/dashboard'
      },
      {
        path: 'login',
        name: 'AdminLogin',
        component: () => import('@/pages/admin/login.vue'),
        meta: { title: '管理员登录' }
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/pages/admin/dashboard.vue'),
        meta: { title: '控制台', requiresAuth: true }
      },
      {
        path: 'menu',
        name: 'MenuManagement',
        component: () => import('@/pages/admin/menu.vue'),
        meta: { title: '导航菜单管理', requiresAuth: true }
      },
      {
        path: 'banner',
        name: 'BannerManagement',
        component: () => import('@/pages/admin/banner.vue'),
        meta: { title: '轮播图管理', requiresAuth: true }
      },
      {
        path: 'product',
        name: 'ProductManagement',
        component: () => import('@/pages/admin/product.vue'),
        meta: { title: '产品管理', requiresAuth: true }
      },
      {
        path: 'article',
        name: 'ArticleManagement',
        component: () => import('@/pages/admin/article.vue'),
        meta: { title: '文章管理', requiresAuth: true }
      },
      {
        path: 'message',
        name: 'MessageManagement',
        component: () => import('@/pages/admin/message.vue'),
        meta: { title: '留言管理', requiresAuth: true }
      },
      {
        path: 'config',
        name: 'ConfigManagement',
        component: () => import('@/pages/admin/config.vue'),
        meta: { title: '系统设置', requiresAuth: true }
      },
      {
        path: 'category',
        name: 'CategoryManagement',
        component: () => import('@/pages/admin/category.vue'),
        meta: { title: '分类管理', requiresAuth: true }
      },
      {
        path: 'user',
        name: 'UserManagement',
        component: () => import('@/pages/admin/user.vue'),
        meta: { title: '用户管理', requiresAuth: true }
      },
      {
        path: 'advantage',
        name: 'AdvantageManagement',
        component: () => import('@/pages/admin/advantage.vue'),
        meta: { title: '优势管理', requiresAuth: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} - MC Trade Global`
  }
  
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next({ name: 'AdminLogin', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})

export default router
