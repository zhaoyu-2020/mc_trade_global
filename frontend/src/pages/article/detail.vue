<template>
  <div class="article-detail">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">{{ locale === 'zh' ? '首页' : 'Home' }}</router-link>
        <span class="breadcrumb-sep">/</span>
        <router-link to="/article">{{ locale === 'zh' ? '新闻资讯' : 'News' }}</router-link>
        <span class="breadcrumb-sep">/</span>
        <span class="current">{{ locale === 'zh' ? article.title : article.titleEn }}</span>
      </div>

      <article v-if="article.id" class="article-main">
        <header class="article-header">
          <span class="article-badge top" v-if="article.isTop">{{ locale === 'zh' ? '置顶' : 'Top' }}</span>
          <h1>{{ locale === 'zh' ? article.title : article.titleEn }}</h1>
          <div class="article-meta">
            <span class="meta-item" v-if="article.author">
              <el-icon><User /></el-icon>
              {{ article.author }}
            </span>
            <span class="meta-item">
              <el-icon><Calendar /></el-icon>
              {{ formatDate(article.createTime) }}
            </span>
            <span class="meta-item">
              <el-icon><View /></el-icon>
              {{ article.viewCount || 0 }} {{ locale === 'zh' ? '阅读' : 'views' }}
            </span>
          </div>
        </header>

        <div class="article-cover" v-if="article.coverImage">
          <img :src="article.coverImage" :alt="article.title" />
        </div>

        <div class="article-body">
          <div class="rich-content" v-html="locale === 'zh' ? article.content : article.contentEn"></div>
          <el-empty v-if="!article.content && !article.contentEn" :description="locale === 'zh' ? '暂无正文内容' : 'No content available'" />
        </div>

        <div class="article-share">
          <span class="share-label">{{ locale === 'zh' ? '分享到' : 'Share to' }}:</span>
          <div class="share-icons">
            <span class="share-icon">Facebook</span>
            <span class="share-icon">LinkedIn</span>
            <span class="share-icon">Twitter</span>
          </div>
        </div>

        <div class="article-nav">
          <router-link 
            v-if="prevArticle" 
            :to="`/article/${prevArticle.id}`" 
            class="nav-item nav-prev"
          >
            <span class="nav-label">{{ locale === 'zh' ? '上一篇' : 'Previous' }}</span>
            <span class="nav-title">{{ locale === 'zh' ? prevArticle.title : prevArticle.titleEn }}</span>
          </router-link>
          <div v-else class="nav-item nav-prev nav-empty">
            <span>{{ locale === 'zh' ? '没有更多了' : 'No more articles' }}</span>
          </div>
          <router-link 
            v-if="nextArticle" 
            :to="`/article/${nextArticle.id}`" 
            class="nav-item nav-next"
          >
            <span class="nav-label">{{ locale === 'zh' ? '下一篇' : 'Next' }}</span>
            <span class="nav-title">{{ locale === 'zh' ? nextArticle.title : nextArticle.titleEn }}</span>
          </router-link>
          <div v-else class="nav-item nav-next nav-empty">
            <span>{{ locale === 'zh' ? '没有更多了' : 'No more articles' }}</span>
          </div>
        </div>
      </article>

      <section class="related-articles" v-if="relatedArticles.length">
        <h2>{{ locale === 'zh' ? '相关文章' : 'Related Articles' }}</h2>
        <div class="related-list">
          <router-link 
            v-for="item in relatedArticles" 
            :key="item.id" 
            :to="`/article/${item.id}`"
            class="related-item"
          >
            <div class="related-image" v-if="item.coverImage">
              <img :src="item.coverImage" :alt="item.title" />
            </div>
            <div class="related-content">
              <span class="related-date">{{ formatDate(item.createTime) }}</span>
              <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
            </div>
          </router-link>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { User, Calendar, View } from '@element-plus/icons-vue'
import { articleApi } from '@/api'

const { locale } = useI18n()
const route = useRoute()

const article = ref({})
const relatedArticles = ref([])
const prevArticle = ref(null)
const nextArticle = ref(null)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString()
}

const fetchArticle = async (id) => {
  try {
    const res = await articleApi.getById(id)
    article.value = res.data
  } catch (e) {
    console.error(e)
  }
}

watch(() => route.params.id, async (newId) => {
  if (newId) {
    await fetchArticle(newId)
  }
}, { immediate: true })

onMounted(async () => {
  await fetchArticle(route.params.id)
})
</script>

<style lang="scss" scoped>
.article-detail {
  padding: 40px 0;
  background: #f9f9f9;
  min-height: 100vh;

  .breadcrumb {
    margin-bottom: 30px;
    font-size: 14px;

    a {
      color: #667eea;
      &:hover { text-decoration: underline; }
    }

    .breadcrumb-sep {
      margin: 0 10px;
      color: #999;
    }

    .current {
      color: #666;
      max-width: 300px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

  .article-main {
    background: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 40px;
  }

  .article-header {
    text-align: center;
    margin-bottom: 30px;
    position: relative;

    .article-badge {
      display: inline-block;
      padding: 5px 15px;
      background: #f56c6c;
      color: #fff;
      border-radius: 20px;
      font-size: 12px;
      margin-bottom: 15px;
    }

    h1 {
      font-size: 32px;
      margin-bottom: 20px;
      color: #333;
      line-height: 1.4;
    }

    .article-meta {
      display: flex;
      justify-content: center;
      gap: 25px;
      color: #999;
      font-size: 14px;

      .meta-item {
        display: flex;
        align-items: center;
        gap: 5px;
      }
    }
  }

  .article-cover {
    max-width: 800px;
    margin: 0 auto 30px;

    img {
      width: 100%;
      border-radius: 8px;
    }
  }

  .article-body {
    max-width: 800px;
    margin: 0 auto;
    padding-bottom: 30px;
    border-bottom: 1px solid #eee;

    .rich-content {
      line-height: 2;
      color: #333;
      font-size: 16px;

      :deep(p) {
        margin-bottom: 20px;
      }

      :deep(img) {
        max-width: 100%;
        height: auto;
        border-radius: 8px;
        margin: 20px 0;
      }

      :deep(h1), :deep(h2), :deep(h3), :deep(h4), :deep(h5), :deep(h6) {
        margin: 30px 0 15px;
        color: #333;
      }

      :deep(a) {
        color: #667eea;
        &:hover { text-decoration: underline; }
      }

      :deep(blockquote) {
        margin: 20px 0;
        padding: 15px 20px;
        background: #f9f9f9;
        border-left: 4px solid #667eea;
        border-radius: 4px;
      }

      :deep(code) {
        background: #f5f5f5;
        padding: 2px 6px;
        border-radius: 3px;
        font-family: monospace;
      }

      :deep(pre) {
        background: #f5f5f5;
        padding: 20px;
        border-radius: 8px;
        overflow-x: auto;
        margin: 20px 0;

        code {
          background: none;
          padding: 0;
        }
      }
    }
  }

  .article-share {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 15px;
    padding: 30px 0;

    .share-label {
      color: #666;
      font-size: 14px;
    }

    .share-icons {
      display: flex;
      gap: 10px;

      .share-icon {
        padding: 8px 20px;
        background: #f0f0f0;
        border-radius: 20px;
        font-size: 13px;
        color: #666;
        cursor: pointer;
        transition: all 0.3s;

        &:hover {
          background: #667eea;
          color: #fff;
        }
      }
    }
  }

  .article-nav {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;

    .nav-item {
      padding: 20px;
      background: #f9f9f9;
      border-radius: 8px;
      transition: all 0.3s;

      &:hover {
        background: #f0f0f0;
      }

      &.nav-empty {
        text-align: center;
        color: #999;
        font-size: 14px;
      }

      .nav-label {
        display: block;
        font-size: 12px;
        color: #999;
        margin-bottom: 5px;
      }

      .nav-title {
        display: block;
        color: #333;
        font-size: 14px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }

    .nav-prev {
      text-align: left;
    }

    .nav-next {
      text-align: right;
    }
  }

  .related-articles {
    background: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

    h2 {
      font-size: 24px;
      margin-bottom: 30px;
      text-align: center;
      color: #333;
    }

    .related-list {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 20px;
    }

    .related-item {
      background: #f9f9f9;
      border-radius: 8px;
      overflow: hidden;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-5px);

        img {
          transform: scale(1.05);
        }
      }

      .related-image {
        aspect-ratio: 16/9;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          transition: transform 0.3s;
        }
      }

      .related-content {
        padding: 15px;

        .related-date {
          font-size: 12px;
          color: #999;
        }

        h3 {
          font-size: 14px;
          color: #333;
          margin-top: 5px;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
        }
      }
    }
  }
}

@media (max-width: 992px) {
  .article-nav {
    grid-template-columns: 1fr !important;
  }

  .related-list {
    grid-template-columns: repeat(2, 1fr) !important;
  }
}

@media (max-width: 768px) {
  .related-list {
    grid-template-columns: 1fr !important;
  }
}
</style>
