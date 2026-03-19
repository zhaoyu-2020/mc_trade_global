<template>
  <div class="dashboard">
    <div class="dashboard-header">
      <div class="welcome">
        <h1>{{ t('admin.dashboard.title') }}</h1>
        <p>{{ locale === 'zh' ? '欢迎回来' : 'Welcome back' }}，{{ userInfo?.username || 'Admin' }}！</p>
      </div>
      <div class="header-actions">
        <el-button type="primary" @click="$router.push('/admin/product')">
          <el-icon><Plus /></el-icon>
          {{ locale === 'zh' ? '新增产品' : 'Add Product' }}
        </el-button>
        <el-button @click="$router.push('/admin/article')">
          <el-icon><Plus /></el-icon>
          {{ locale === 'zh' ? '新增文章' : 'Add Article' }}
        </el-button>
      </div>
    </div>

    <el-row :gutter="20" class="stat-cards">
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="hover" class="stat-card visits">
          <div class="stat-icon">
            <el-icon><View /></el-icon>
          </div>
          <div class="stat-content">
            <span class="stat-value">{{ stats.todayVisits }}</span>
            <span class="stat-label">{{ locale === 'zh' ? '今日访问' : "Today's Visits" }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="hover" class="stat-card messages">
          <div class="stat-icon">
            <el-icon><Message /></el-icon>
          </div>
          <div class="stat-content">
            <span class="stat-value">{{ stats.unreadMessages }}</span>
            <span class="stat-label">{{ locale === 'zh' ? '未读留言' : 'Unread Messages' }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="hover" class="stat-card products">
          <div class="stat-icon">
            <el-icon><Goods /></el-icon>
          </div>
          <div class="stat-content">
            <span class="stat-value">{{ stats.totalProducts }}</span>
            <span class="stat-label">{{ locale === 'zh' ? '产品总数' : 'Total Products' }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="hover" class="stat-card articles">
          <div class="stat-icon">
            <el-icon><Document /></el-icon>
          </div>
          <div class="stat-content">
            <span class="stat-value">{{ stats.totalArticles }}</span>
            <span class="stat-label">{{ locale === 'zh' ? '文章总数' : 'Total Articles' }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="main-content">
      <el-col :xs="24" :sm="24" :md="16">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <span>{{ locale === 'zh' ? '数据趋势' : 'Data Trends' }}</span>
          </template>
          <div class="chart-placeholder">
            <el-icon><DataAnalysis /></el-icon>
            <p>{{ locale === 'zh' ? '访问量趋势图' : 'Visit Trend Chart' }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8">
        <el-card shadow="hover" class="quick-actions">
          <template #header>
            <span>{{ locale === 'zh' ? '快捷操作' : 'Quick Actions' }}</span>
          </template>
          <div class="actions-list">
            <div class="action-item" @click="$router.push('/admin/banner')">
              <div class="action-icon banner">
                <el-icon><Picture /></el-icon>
              </div>
              <span>{{ locale === 'zh' ? '轮播图管理' : 'Banner Management' }}</span>
            </div>
            <div class="action-item" @click="$router.push('/admin/product')">
              <div class="action-icon product">
                <el-icon><Goods /></el-icon>
              </div>
              <span>{{ locale === 'zh' ? '产品管理' : 'Product Management' }}</span>
            </div>
            <div class="action-item" @click="$router.push('/admin/article')">
              <div class="action-icon article">
                <el-icon><Document /></el-icon>
              </div>
              <span>{{ locale === 'zh' ? '文章管理' : 'Article Management' }}</span>
            </div>
            <div class="action-item" @click="$router.push('/admin/message')">
              <div class="action-icon message">
                <el-icon><Message /></el-icon>
              </div>
              <span>{{ locale === 'zh' ? '留言管理' : 'Message Management' }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12">
        <el-card shadow="hover">
          <template #header>
            <span>{{ locale === 'zh' ? '最新留言' : 'Recent Messages' }}</span>
            <el-button type="text" @click="$router.push('/admin/message')">{{ locale === 'zh' ? '查看全部' : 'View All' }}</el-button>
          </template>
          <div class="message-list">
            <div v-for="msg in recentMessages" :key="msg.id" class="message-item">
              <div class="message-avatar">
                <span>{{ msg.name?.charAt(0) || 'U' }}</span>
              </div>
              <div class="message-content">
                <div class="message-header">
                  <span class="message-name">{{ msg.name }}</span>
                  <el-tag :type="msg.isRead === 1 ? 'success' : 'warning'" size="small">
                    {{ msg.isRead === 1 ? (locale === 'zh' ? '已读' : 'Read') : (locale === 'zh' ? '未读' : 'Unread') }}
                  </el-tag>
                </div>
                <p class="message-text ellipsis">{{ msg.content }}</p>
                <span class="message-time">{{ formatTime(msg.createTime) }}</span>
              </div>
            </div>
            <el-empty v-if="!recentMessages.length" :description="locale === 'zh' ? '暂无留言' : 'No messages'" />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12">
        <el-card shadow="hover">
          <template #header>
            <span>{{ locale === 'zh' ? '最新产品' : 'Recent Products' }}</span>
            <el-button type="text" @click="$router.push('/admin/product')">{{ locale === 'zh' ? '查看全部' : 'View All' }}</el-button>
          </template>
          <div class="product-list">
            <div v-for="product in recentProducts" :key="product.id" class="product-item">
              <img v-if="product.mainImage" :src="product.mainImage" class="product-image" />
              <img v-else src="https://placehold.co/60x60/4facfe/ffffff?text=P" class="product-image" />
              <div class="product-info">
                <span class="product-name ellipsis">{{ locale === 'zh' ? product.productName : product.productNameEn }}</span>
                <span class="product-time">{{ formatTime(product.createTime) }}</span>
              </div>
              <el-tag v-if="product.isRecommend" type="danger" size="small">{{ locale === 'zh' ? '推荐' : 'Hot' }}</el-tag>
            </div>
            <el-empty v-if="!recentProducts.length" :description="locale === 'zh' ? '暂无产品' : 'No products'" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useUserStore } from '@/store/user'
import { View, Message, Goods, Document, Plus, Picture, DataAnalysis } from '@element-plus/icons-vue'
import { productApi, articleApi, messageApi } from '@/api'

const { t, locale } = useI18n()
const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)

const stats = ref({
  todayVisits: 128,
  unreadMessages: 5,
  totalProducts: 48,
  totalArticles: 23
})

const recentMessages = ref([])
const recentProducts = ref([])

const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  const diff = now - date
  if (diff < 60000) return locale.value === 'zh' ? '刚刚' : 'Just now'
  if (diff < 3600000) return `${Math.floor(diff / 60000)}${locale.value === 'zh' ? '分钟前' : 'min ago'}`
  if (diff < 86400000) return `${Math.floor(diff / 3600000)}${locale.value === 'zh' ? '小时前' : 'h ago'}`
  return date.toLocaleDateString()
}

const fetchData = async () => {
  try {
    const [messageRes, productRes] = await Promise.all([
      messageApi.list({ current: 1, size: 5 }),
      productApi.list({ current: 1, size: 5 })
    ])
    recentMessages.value = messageRes.data?.records || []
    recentProducts.value = productRes.data?.records || []
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.dashboard {
  .dashboard-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;

    .welcome {
      h1 {
        font-size: 24px;
        margin-bottom: 5px;
        color: #333;
      }

      p {
        color: #666;
        font-size: 14px;
      }
    }

    .header-actions {
      display: flex;
      gap: 10px;
    }
  }

  .stat-cards {
    margin-bottom: 20px;

    .el-col {
      margin-bottom: 20px;
    }
  }

  .stat-card {
    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28px;
      color: #fff;
    }

    .stat-content {
      display: flex;
      flex-direction: column;
      margin-top: -50px;
      margin-left: 80px;

      .stat-value {
        font-size: 32px;
        font-weight: bold;
        color: #333;
      }

      .stat-label {
        font-size: 14px;
        color: #999;
      }
    }

    &.visits .stat-icon { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
    &.messages .stat-icon { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); }
    &.products .stat-icon { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
    &.articles .stat-icon { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }
  }

  .main-content {
    margin-bottom: 20px;
  }

  .chart-card {
    .chart-placeholder {
      height: 300px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: #f9f9f9;
      border-radius: 8px;
      color: #999;

      .el-icon {
        font-size: 48px;
        margin-bottom: 15px;
      }
    }
  }

  .quick-actions {
    .actions-list {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 15px;
    }

    .action-item {
      display: flex;
      align-items: center;
      gap: 15px;
      padding: 20px;
      background: #f9f9f9;
      border-radius: 8px;
      cursor: pointer;
      transition: all 0.3s;

      &:hover {
        background: #f0f0f0;
        transform: translateY(-3px);
      }

      .action-icon {
        width: 45px;
        height: 45px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 20px;
        color: #fff;

        &.banner { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        &.product { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
        &.article { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }
        &.message { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); }
      }

      span {
        font-size: 14px;
        color: #666;
      }
    }
  }

  .message-list {
    .message-item {
      display: flex;
      gap: 15px;
      padding: 15px 0;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .message-avatar {
        width: 45px;
        height: 45px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #fff;
        font-weight: bold;
      }

      .message-content {
        flex: 1;

        .message-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 5px;

          .message-name {
            font-weight: 600;
            color: #333;
          }
        }

        .message-text {
          font-size: 14px;
          color: #666;
          margin-bottom: 5px;
        }

        .message-time {
          font-size: 12px;
          color: #999;
        }
      }
    }
  }

  .product-list {
    .product-item {
      display: flex;
      align-items: center;
      gap: 15px;
      padding: 12px 0;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .product-image {
        width: 50px;
        height: 50px;
        border-radius: 4px;
        object-fit: cover;
      }

      .product-info {
        flex: 1;
        display: flex;
        flex-direction: column;

        .product-name {
          font-size: 14px;
          color: #333;
          margin-bottom: 3px;
        }

        .product-time {
          font-size: 12px;
          color: #999;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .dashboard-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }

  .stat-card .stat-content {
    margin-left: 70px;
  }
}
</style>
