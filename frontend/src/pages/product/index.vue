<template>
  <div class="product-page">
    <div class="page-header">
      <h1>{{ locale === 'zh' ? '产品中心' : 'Products' }}</h1>
      <p>{{ locale === 'zh' ? '浏览我们的精选产品' : 'Browse our selected products' }}</p>
    </div>

    <div class="container">
      <div class="product-layout">
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
            <h3>{{ locale === 'zh' ? '产品分类' : 'Categories' }}</h3>
            <ul>
              <li 
                :class="{ active: !categoryId }"
                @click="categoryId = null; fetchProducts()"
              >
                <span class="category-name">{{ locale === 'zh' ? '全部产品' : 'All Products' }}</span>
                <span class="category-count">{{ total }}</span>
              </li>
              <li 
                v-for="cat in categories" 
                :key="cat.id"
                :class="{ active: categoryId === cat.id }"
                @click="categoryId = cat.id; fetchProducts()"
              >
                <span class="category-name">{{ locale === 'zh' ? cat.categoryName : cat.categoryNameEn }}</span>
              </li>
            </ul>
          </div>
        </aside>

        <main class="product-list">
          <div class="list-header">
            <div class="result-info">
              {{ locale === 'zh' ? '共' : 'Total' }} <strong>{{ total }}</strong> {{ locale === 'zh' ? '个产品' : 'products' }}
            </div>
            <div class="sort-box">
              <span>{{ locale === 'zh' ? '排序' : 'Sort' }}:</span>
              <el-select v-model="sortType" @change="handleSortChange" style="width: 120px;">
                <el-option value="default" :label="locale === 'zh' ? '默认' : 'Default'" />
                <el-option value="newest" :label="locale === 'zh' ? '最新' : 'Newest'" />
                <el-option value="name" :label="locale === 'zh' ? '名称' : 'Name'" />
              </el-select>
            </div>
          </div>

          <div class="product-grid" v-if="products.length">
            <router-link 
              v-for="item in products" 
              :key="item.id" 
              :to="`/product/${item.id}`"
              class="product-card"
            >
              <div class="product-image">
                <img v-if="item.mainImage" :src="item.mainImage" :alt="item.productName" />
                <img v-else src="https://placehold.co/300x300/4facfe/ffffff?text=Product" :alt="item.productName" />
                <span class="product-badge" v-if="item.isRecommend">{{ locale === 'zh' ? '推荐' : 'Hot' }}</span>
              </div>
              <div class="product-info">
                <h3>{{ locale === 'zh' ? item.productName : item.productNameEn }}</h3>
                <p class="model" v-if="item.model">{{ locale === 'zh' ? '型号' : 'Model' }}: {{ locale === 'zh' ? item.model : item.modelEn }}</p>
                <p class="ellipsis-2 description">{{ locale === 'zh' ? item.shortDescription : item.shortDescriptionEn }}</p>
                <div class="product-footer">
                  <span class="product-price" v-if="item.price">{{ item.price }}</span>
                  <el-button type="primary" size="small" plain>{{ locale === 'zh' ? '查看详情' : 'View Details' }}</el-button>
                </div>
              </div>
            </router-link>
          </div>

          <div class="empty-state" v-else>
            <el-empty :description="locale === 'zh' ? '未找到相关产品' : 'No products found'">
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
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { Search } from '@element-plus/icons-vue'
import { productApi, productCategoryApi } from '@/api'

const { t, locale } = useI18n()
const route = useRoute()

const keyword = ref('')
const categoryId = ref(null)
const categories = ref([])
const products = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const sortType = ref('default')

const handleSearch = () => {
  currentPage.value = 1
  fetchProducts()
}

const handleSortChange = () => {
  currentPage.value = 1
  fetchProducts()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchProducts()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const resetFilters = () => {
  keyword.value = ''
  categoryId.value = null
  sortType.value = 'default'
  fetchProducts()
}

const fetchProducts = async () => {
  try {
    const res = await productApi.list({
      keyword: keyword.value || undefined,
      categoryId: categoryId.value || undefined,
      current: currentPage.value,
      size: pageSize.value
    })
    products.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error(e)
  }
}

const fetchCategories = async () => {
  try {
    const res = await productCategoryApi.list()
    categories.value = res.data || []
  } catch (e) {
    console.error(e)
  }
}

watch(() => route.query.category, (newCategory) => {
  if (newCategory) {
    categoryId.value = parseInt(newCategory)
  }
}, { immediate: true })

onMounted(() => {
  fetchProducts()
  fetchCategories()
})
</script>

<style lang="scss" scoped>
.product-page {
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

  .product-layout {
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
          padding: 12px 0;
          cursor: pointer;
          color: #666;
          transition: all 0.3s;
          border-radius: 4px;
          padding: 10px 15px;

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

  .product-list {
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

      .sort-box {
        display: flex;
        align-items: center;
        gap: 10px;
        color: #666;
      }
    }
  }

  .product-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 25px;
  }

  .product-card {
    background: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);

      .product-image img {
        transform: scale(1.05);
      }
    }

    .product-image {
      position: relative;
      aspect-ratio: 1;
      overflow: hidden;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s;
      }

      .product-badge {
        position: absolute;
        top: 15px;
        left: 15px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: #fff;
        padding: 5px 12px;
        border-radius: 20px;
        font-size: 12px;
      }
    }

    .product-info {
      padding: 20px;

      h3 {
        font-size: 16px;
        margin-bottom: 8px;
        color: #333;
      }

      .model {
        font-size: 12px;
        color: #999;
        margin-bottom: 8px;
      }

      .description {
        font-size: 14px;
        color: #666;
        margin-bottom: 15px;
        min-height: 40px;
      }

      .product-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .product-price {
          font-size: 18px;
          color: #f56c6c;
          font-weight: bold;
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

@media (max-width: 1200px) {
  .product-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 992px) {
  .product-layout {
    grid-template-columns: 1fr;
  }

  .sidebar {
    .category-nav {
      ul {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;

        li {
          padding: 8px 15px;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .product-grid {
    grid-template-columns: 1fr;
  }
}
</style>
