<template>
  <div class="article-page">
    <div class="page-header">
      <h1>{{ locale === 'zh' ? '新闻资讯' : 'News' }}</h1>
      <p>{{ locale === 'zh' ? '了解更多行业动态和企业新闻' : 'Learn more about industry trends and company news' }}</p>
    </div>

    <div class="container">
      <div class="article-layout">
        <aside class="sidebar">
          <div class="search-box">
            <el-input 
              v-model="keyword" 
              :placeholder="t('common.search')"
              @keyup.enter="handleSearch"
              clearable
            >
              <template #append>
                <el-button :icon="Search" @click="handleSearch" />
              </template>
            </el-input>
          </div>
          <div class="category-nav">
            <h3>{{ locale === 'zh' ? '文章分类' : 'Categories' }}</h3>
            <ul>
              <li 
                :class="{ active: !categoryId }"
                @click="categoryId = null; fetchArticles()"
              >
                <span class="category-name">{{ locale === 'zh' ? '全部文章' : 'All Articles' }}</span>
                <span class="category-count">{{ total }}</span>
              </li>
              <li 
                v-for="cat in categories" 
                :key="cat.id"
                :class="{ active: categoryId === cat.id }"
                @click="categoryId = cat.id; fetchArticles()"
              >
                <span class="category-name">{{ locale === 'zh' ? cat.categoryName : cat.categoryNameEn }}</span>
              </li>
            </ul>
          </div>
        </aside>

        <main class="article-list">
          <div class="list-header">
            <div class="result-info">
              {{ locale === 'zh' ? '共' : 'Total' }} <strong>{{ total }}</strong> {{ locale === 'zh' ? '篇文章' : 'articles' }}
            </div>
          </div>

          <div class="article-grid" v-if="articles.length">
            <router-link 
              v-for="item in articles" 
              :key="item.id" 
              :to="`/article/${item.id}`"
              class="article-card"
            >
              <div class="article-image">
                <img v-if="item.coverImage" :src="item.coverImage" :alt="item.title" />
                <img v-else src="https://placehold.co/400x225/43e97b/ffffff?text=News" :alt="item.title" />
                <span class="article-badge top" v-if="item.isTop">{{ locale === 'zh' ? '置顶' : 'Top' }}</span>
                <span class="article-badge draft" v-if="!item.isPublish">{{ locale === 'zh' ? '草稿' : 'Draft' }}</span>
              </div>
              <div class="article-content">
                <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
                <p class="ellipsis-2">{{ locale === 'zh' ? item.summary : item.summaryEn }}</p>
                <div class="article-meta">
                  <span class="meta-item" v-if="item.author">
                    <el-icon><User /></el-icon>
                    {{ item.author }}
                  </span>
                  <span class="meta-item">
                    <el-icon><Calendar /></el-icon>
                    {{ formatDate(item.createTime) }}
                  </span>
                  <span class="meta-item">
                    <el-icon><View /></el-icon>
                    {{ item.viewCount || 0 }} {{ locale === 'zh' ? '阅读' : 'views' }}
                  </span>
                </div>
              </div>
            </router-link>
          </div>

          <div class="empty-state" v-else>
            <el-empty :description="locale === 'zh' ? '暂无文章' : 'No articles found'">
              <el-button type="primary" @click="resetFilters">{{ locale === 'zh' ? '重置筛选' : 'Reset Filters' }}</el-button>
            </el-empty>
          </div>

          <div class="pagination-wrapper" v-if="total > pageSize">
            <el-pagination
              v-model:current-page="currentPage"
              :page-size="pageSize"
              :total="total"
              layout="prev, pager, next"
              @current-change="handlePageChange"
            />
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { Search, User, Calendar, View } from '@element-plus/icons-vue'
import { articleApi, articleCategoryApi } from '@/api'

const { t, locale } = useI18n()

const keyword = ref('')
const categoryId = ref(null)
const categories = ref([])
const articles = ref([])
const currentPage = ref(1)
const pageSize = ref(9)
const total = ref(0)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString()
}

const handleSearch = () => {
  currentPage.value = 1
  fetchArticles()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchArticles()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const resetFilters = () => {
  keyword.value = ''
  categoryId.value = null
  fetchArticles()
}

const fetchArticles = async () => {
  try {
    const res = await articleApi.list({
      categoryId: categoryId.value || undefined,
      isPublish: 1,
      current: currentPage.value,
      size: pageSize.value
    })
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error(e)
  }
}

const fetchCategories = async () => {
  try {
    const res = await articleCategoryApi.list()
    categories.value = res.data || []
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchArticles()
  fetchCategories()
})
</script>

<style lang="scss" scoped>
.article-page {
  .page-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    padding: 80px 0;
    text-align: center;

    h1 {
      font-size: 36px;
      margin-bottom: 10px;
    }

    p {
      font-size: 16px;
      opacity: 0.9;
    }
  }

  .article-layout {
    display: grid;
    grid-template-columns: 260px 1fr;
    gap: 40px;
    padding: 40px 0;
  }

  .sidebar {
    .search-box {
      margin-bottom: 30px;
    }

    .category-nav {
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

      h3 {
        font-size: 16px;
        margin-bottom: 15px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
        color: #333;
      }

      ul {
        li {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 10px 15px;
          cursor: pointer;
          color: #666;
          transition: all 0.3s;
          border-radius: 4px;

          &:hover {
            background: #f5f7fa;
            color: #409eff;
          }

          &.active {
            background: #667eea;
            color: #fff;

            .category-count {
              background: rgba(255, 255, 255, 0.3);
              color: #fff;
            }
          }

          .category-name {
            flex: 1;
          }

          .category-count {
            background: #f0f0f0;
            padding: 2px 8px;
            border-radius: 10px;
            font-size: 12px;
          }
        }
      }
    }
  }

  .article-list {
    .list-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
      padding: 15px 20px;
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

      .result-info {
        color: #666;
        
        strong {
          color: #667eea;
        }
      }
    }
  }

  .article-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 25px;
  }

  .article-card {
    background: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);

      .article-image img {
        transform: scale(1.05);
      }
    }

    .article-image {
      position: relative;
      aspect-ratio: 16/9;
      overflow: hidden;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s;
      }

      .article-badge {
        position: absolute;
        top: 15px;
        padding: 5px 12px;
        border-radius: 20px;
        font-size: 12px;
        color: #fff;

        &.top {
          left: 15px;
          background: #f56c6c;
        }

        &.draft {
          right: 15px;
          background: #909399;
        }
      }
    }

    .article-content {
      padding: 20px;

      h3 {
        font-size: 18px;
        margin-bottom: 10px;
        color: #333;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }

      p {
        font-size: 14px;
        color: #666;
        margin-bottom: 15px;
        line-height: 1.6;
      }

      .article-meta {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        color: #999;
        font-size: 12px;

        .meta-item {
          display: flex;
          align-items: center;
          gap: 5px;
        }
      }
    }
  }

  .empty-state {
    background: #fff;
    padding: 60px 0;
    border-radius: 8px;
  }

  .pagination-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 40px;
  }
}

@media (max-width: 992px) {
  .article-layout {
    grid-template-columns: 1fr;
  }

  .article-grid {
    grid-template-columns: 1fr;
  }
}
</style>
