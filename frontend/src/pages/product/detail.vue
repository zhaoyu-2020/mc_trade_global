<template>
  <div class="product-detail">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">{{ locale === 'zh' ? '首页' : 'Home' }}</router-link>
        <span class="breadcrumb-sep">/</span>
        <router-link to="/product">{{ locale === 'zh' ? '产品中心' : 'Products' }}</router-link>
        <span class="breadcrumb-sep">/</span>
        <span class="current">{{ locale === 'zh' ? product.productName : product.productNameEn }}</span>
      </div>

      <div class="product-content" v-if="product.id">
        <div class="product-gallery">
          <div class="main-image" @click="previewVisible = true">
            <img v-if="currentImage" :src="currentImage" :alt="product.productName" />
            <img v-else src="https://placehold.co/600x600/4facfe/ffffff?text=Product" :alt="product.productName" />
            <div class="zoom-hint">
              <el-icon><ZoomIn /></el-icon>
              <span>{{ locale === 'zh' ? '点击放大' : 'Click to zoom' }}</span>
            </div>
          </div>
          <div class="thumbnail-list" v-if="images.length > 1">
            <div 
              v-for="(img, index) in images" 
              :key="index"
              class="thumbnail-item"
              :class="{ active: currentImage === img }"
              @click="currentImage = img"
            >
              <img :src="img" :alt="`Image ${index + 1}`" />
            </div>
          </div>
        </div>

        <div class="product-info">
          <h1>{{ locale === 'zh' ? product.productName : product.productNameEn }}</h1>
          <p class="model" v-if="product.model">
            {{ locale === 'zh' ? '型号' : 'Model' }}: {{ locale === 'zh' ? product.model : product.modelEn }}
          </p>
          <p class="price" v-if="product.price">{{ product.price }}</p>
          <div class="short-desc">
            <p>{{ locale === 'zh' ? product.shortDescription : product.shortDescriptionEn }}</p>
          </div>
          
          <div class="product-meta">
            <div class="meta-item" v-if="product.categoryName">
              <span class="meta-label">{{ locale === 'zh' ? '分类' : 'Category' }}:</span>
              <span class="meta-value">{{ locale === 'zh' ? product.categoryName : product.categoryNameEn }}</span>
            </div>
            <div class="meta-item" v-if="product.stock">
              <span class="meta-label">{{ locale === 'zh' ? '库存' : 'Stock' }}:</span>
              <span class="meta-value">{{ product.stock }} {{ locale === 'zh' ? '件' : 'pcs' }}</span>
            </div>
          </div>

          <div class="actions">
            <el-button type="primary" size="large" @click="$router.push('/contact')" class="contact-btn">
              <el-icon><Message /></el-icon>
              {{ locale === 'zh' ? '在线咨询' : 'Online Inquiry' }}
            </el-button>
            <el-button size="large" @click="$router.push('/contact')">
              <el-icon><Phone /></el-icon>
              {{ locale === 'zh' ? '联系我们' : 'Contact Us' }}
            </el-button>
          </div>

          <div class="share-section">
            <span class="share-label">{{ locale === 'zh' ? '分享到' : 'Share to' }}:</span>
            <div class="share-icons">
              <span class="share-icon">Facebook</span>
              <span class="share-icon">LinkedIn</span>
              <span class="share-icon">Twitter</span>
            </div>
          </div>
        </div>
      </div>

      <el-tabs v-if="product.id" class="product-tabs">
        <el-tab-pane :label="locale === 'zh' ? '详细参数' : 'Specifications'">
          <div class="params-table" v-if="parameters.length">
            <table>
              <tbody>
                <tr v-for="(param, index) in parameters" :key="index">
                  <td class="param-name">{{ param.name || param.nameEn }}</td>
                  <td class="param-value">{{ param.value }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <el-empty v-else :description="locale === 'zh' ? '暂无参数信息' : 'No specifications available'" />
        </el-tab-pane>
        <el-tab-pane :label="locale === 'zh' ? '产品详情' : 'Product Details'">
          <div class="rich-content" v-html="locale === 'zh' ? product.description : product.descriptionEn">
          </div>
          <el-empty v-if="!product.description && !product.descriptionEn" :description="locale === 'zh' ? '暂无详情信息' : 'No details available'" />
        </el-tab-pane>
      </el-tabs>

      <section class="related-products" v-if="relatedProducts.length">
        <h2>{{ locale === 'zh' ? '相关产品' : 'Related Products' }}</h2>
        <div class="related-grid">
          <router-link 
            v-for="item in relatedProducts" 
            :key="item.id" 
            :to="`/product/${item.id}`"
            class="related-card"
          >
            <div class="related-image">
              <img v-if="item.mainImage" :src="item.mainImage" :alt="item.productName" />
              <img v-else src="https://placehold.co/300x300/4facfe/ffffff?text=Product" :alt="item.productName" />
            </div>
            <h3>{{ locale === 'zh' ? item.productName : item.productNameEn }}</h3>
            <p v-if="item.price" class="related-price">{{ item.price }}</p>
          </router-link>
        </div>
      </section>
    </div>

    <el-image-viewer 
      v-if="previewVisible" 
      :url-list="[currentImage]" 
      @close="previewVisible = false"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ZoomIn, Message, Phone } from '@element-plus/icons-vue'
import { productApi } from '@/api'

const { locale } = useI18n()
const route = useRoute()

const product = ref({})
const relatedProducts = ref([])
const currentImage = ref('')
const previewVisible = ref(false)

const images = computed(() => {
  if (!product.value.images) return []
  try {
    return JSON.parse(product.value.images)
  } catch {
    return product.value.mainImage ? [product.value.mainImage] : []
  }
})

const parameters = computed(() => {
  if (!product.value.parameters) return []
  try {
    return JSON.parse(product.value.parameters)
  } catch {
    return []
  }
})

watch(() => route.params.id, async (newId) => {
  if (newId) {
    await fetchProduct(newId)
  }
}, { immediate: true })

const fetchProduct = async (id) => {
  try {
    const res = await productApi.getById(id || route.params.id)
    product.value = res.data
    currentImage.value = product.value.mainImage || ''
  } catch (e) {
    console.error(e)
  }
}

onMounted(async () => {
  await fetchProduct()
})
</script>

<style lang="scss" scoped>
.product-detail {
  padding: 40px 0;
  background: #f9f9f9;

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
    }
  }

  .product-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 50px;
    background: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 40px;
  }

  .product-gallery {
    .main-image {
      position: relative;
      aspect-ratio: 1;
      border: 1px solid #eee;
      border-radius: 8px;
      overflow: hidden;
      cursor: zoom-in;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .zoom-hint {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 15px;
        background: linear-gradient(transparent, rgba(0,0,0,0.5));
        color: #fff;
        display: flex;
        align-items: center;
        gap: 8px;
        font-size: 14px;
        opacity: 0;
        transition: opacity 0.3s;
      }

      &:hover .zoom-hint {
        opacity: 1;
      }
    }

    .thumbnail-list {
      display: flex;
      gap: 10px;
      margin-top: 15px;
      flex-wrap: wrap;

      .thumbnail-item {
        width: 80px;
        height: 80px;
        border: 2px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        cursor: pointer;
        transition: all 0.3s;

        &.active,
        &:hover {
          border-color: #667eea;
        }

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
    }
  }

  .product-info {
    h1 {
      font-size: 28px;
      margin-bottom: 15px;
      color: #333;
    }

    .model {
      color: #999;
      margin-bottom: 15px;
      font-size: 14px;
    }

    .price {
      font-size: 32px;
      color: #f56c6c;
      margin-bottom: 20px;
      font-weight: bold;
    }

    .short-desc {
      padding: 20px;
      background: #f9f9f9;
      border-radius: 8px;
      margin-bottom: 25px;

      p {
        color: #666;
        line-height: 1.8;
      }
    }

    .product-meta {
      margin-bottom: 30px;

      .meta-item {
        display: flex;
        align-items: center;
        margin-bottom: 10px;

        .meta-label {
          color: #999;
          margin-right: 10px;
        }

        .meta-value {
          color: #333;
        }
      }
    }

    .actions {
      display: flex;
      gap: 15px;
      margin-bottom: 30px;

      .contact-btn {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
      }
    }

    .share-section {
      display: flex;
      align-items: center;
      gap: 15px;

      .share-label {
        color: #999;
        font-size: 14px;
      }

      .share-icons {
        display: flex;
        gap: 10px;

        .share-icon {
          padding: 5px 15px;
          background: #f0f0f0;
          border-radius: 20px;
          font-size: 12px;
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
  }

  .product-tabs {
    background: #fff;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 40px;
  }

  .params-table {
    table {
      width: 100%;
      border-collapse: collapse;

      tr {
        border-bottom: 1px solid #eee;

        td {
          padding: 15px;
        }

        .param-name {
          width: 200px;
          background: #f9f9f9;
          font-weight: 600;
          color: #333;
        }

        .param-value {
          color: #666;
        }
      }
    }
  }

  .rich-content {
    line-height: 1.9;
    color: #666;

    :deep(img) {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
      margin: 20px 0;
    }
  }

  .related-products {
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

    .related-grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 20px;
    }

    .related-card {
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
        aspect-ratio: 1;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          transition: transform 0.3s;
        }
      }

      h3 {
        padding: 15px 15px 5px;
        font-size: 14px;
        color: #333;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .related-price {
        padding: 0 15px 15px;
        color: #f56c6c;
        font-weight: bold;
      }
    }
  }
}

@media (max-width: 992px) {
  .product-content {
    grid-template-columns: 1fr !important;
  }

  .related-grid {
    grid-template-columns: repeat(2, 1fr) !important;
  }
}

@media (max-width: 768px) {
  .related-grid {
    grid-template-columns: 1fr !important;
  }
}
</style>
