<template>
  <div class="home-page">
    <section class="banner-section">
      <el-carousel :interval="5000" trigger="click" height="500px" v-if="banners.length">
        <el-carousel-item v-for="item in banners" :key="item.id">
          <a :href="item.linkUrl || 'javascript:void(0)'" :target="item.linkTarget || '_self'" class="banner-link">
            <img :src="item.imageUrl" :alt="locale === 'zh' ? item.title : item.titleEn" />
            <div class="banner-content" v-if="item.title || item.description">
              <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
              <p>{{ locale === 'zh' ? item.description : item.descriptionEn }}</p>
            </div>
          </a>
        </el-carousel-item>
      </el-carousel>
      <div v-else class="banner-placeholder">
        <div class="placeholder-content">
          <h2>MC Trade Global</h2>
          <p>{{ locale === 'zh' ? '专业的外贸企业服务商' : 'Professional Foreign Trade Service Provider' }}</p>
        </div>
      </div>
    </section>

    <section class="intro-section">
      <div class="container">
        <h2 class="section-title">{{ locale === 'zh' ? '公司简介' : 'Company Profile' }}</h2>
        <div class="intro-content">
          <div class="intro-text">
            <p>{{ locale === 'zh' ? '我们是领先的国际贸易企业，致力于为全球客户提供优质的产品和服务。多年来，我们凭借卓越的品质、合理的价格和完善的服务，赢得了众多客户的信赖。' : 'We are a leading international trade company committed to providing high-quality products and services to global customers. Over the years, we have earned the trust of numerous customers with our excellent quality, reasonable prices, and comprehensive service.' }}</p>
            <router-link to="/about">
              <el-button type="primary" size="large">{{ locale === 'zh' ? '了解更多' : 'Learn More' }}</el-button>
            </router-link>
          </div>
          <div class="intro-image">
            <img src="https://placehold.co/600x400/667eea/ffffff?text=Company" alt="Company" />
          </div>
        </div>
      </div>
    </section>

    <section class="advantages-section">
      <div class="container">
        <h2 class="section-title">{{ locale === 'zh' ? '我们的优势' : 'Our Advantages' }}</h2>
        <div class="advantages-grid" v-if="advantages.length">
          <div v-for="item in advantages" :key="item.id" class="advantage-card">
            <div class="advantage-icon">
              <img v-if="item.icon" :src="item.icon" :alt="item.title" />
              <el-icon v-else><Goods /></el-icon>
            </div>
            <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
            <p>{{ locale === 'zh' ? item.description : item.descriptionEn }}</p>
          </div>
        </div>
        <div class="advantages-grid" v-else>
          <div v-for="i in 6" :key="i" class="advantage-card">
            <div class="advantage-icon">
              <el-icon><Goods /></el-icon>
            </div>
            <h3>{{ locale === 'zh' ? `优势${i}` : `Advantage ${i}` }}</h3>
            <p>{{ locale === 'zh' ? '优势描述内容' : 'Advantage description' }}</p>
          </div>
        </div>
      </div>
    </section>

    <section class="products-section">
      <div class="container">
        <h2 class="section-title">{{ locale === 'zh' ? '推荐产品' : 'Recommended Products' }}</h2>
        <div class="products-grid" v-if="products.length">
          <router-link 
            v-for="item in products" 
            :key="item.id" 
            :to="`/product/${item.id}`"
            class="product-card"
          >
            <div class="product-image">
              <img v-if="item.mainImage" :src="item.mainImage" :alt="item.productName" />
              <img v-else src="https://placehold.co/300x300/4facfe/ffffff?text=Product" :alt="item.productName" />
            </div>
            <div class="product-info">
              <h3>{{ locale === 'zh' ? item.productName : item.productNameEn }}</h3>
              <p class="ellipsis-2">{{ locale === 'zh' ? item.shortDescription : item.shortDescriptionEn }}</p>
              <span class="product-price" v-if="item.price">{{ item.price }}</span>
            </div>
          </router-link>
        </div>
        <div class="products-grid" v-else>
          <router-link v-for="i in 4" :key="i" to="/product" class="product-card">
            <div class="product-image">
              <img src="https://placehold.co/300x300/4facfe/ffffff?text=Product" alt="Product" />
            </div>
            <div class="product-info">
              <h3>{{ locale === 'zh' ? `产品${i}` : `Product ${i}` }}</h3>
              <p class="ellipsis-2">{{ locale === 'zh' ? '产品简短描述' : 'Product short description' }}</p>
            </div>
          </router-link>
        </div>
        <div class="section-action">
          <router-link to="/product">
            <el-button type="primary" plain size="large">{{ locale === 'zh' ? '查看全部产品' : 'View All Products' }}</el-button>
          </router-link>
        </div>
      </div>
    </section>

    <section class="news-section">
      <div class="container">
        <h2 class="section-title">{{ locale === 'zh' ? '最新动态' : 'Latest News' }}</h2>
        <div class="news-list" v-if="articles.length">
          <router-link 
            v-for="item in articles" 
            :key="item.id" 
            :to="`/article/${item.id}`"
            class="news-item"
          >
            <div class="news-image" v-if="item.coverImage">
              <img :src="item.coverImage" :alt="item.title" />
            </div>
            <div class="news-image" v-else>
              <img src="https://placehold.co/400x225/43e97b/ffffff?text=News" alt="News" />
            </div>
            <div class="news-content">
              <span class="news-date">{{ formatDate(item.createTime) }}</span>
              <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
              <p class="ellipsis-2">{{ locale === 'zh' ? item.summary : item.summaryEn }}</p>
            </div>
          </router-link>
        </div>
        <div class="news-list" v-else>
          <router-link v-for="i in 3" :key="i" to="/article" class="news-item">
            <div class="news-image">
              <img src="https://placehold.co/400x225/43e97b/ffffff?text=News" alt="News" />
            </div>
            <div class="news-content">
              <span class="news-date">{{ new Date().toLocaleDateString() }}</span>
              <h3>{{ locale === 'zh' ? `新闻标题${i}` : `News Title ${i}` }}</h3>
              <p class="ellipsis-2">{{ locale === 'zh' ? '新闻摘要内容...' : 'News summary content...' }}</p>
            </div>
          </router-link>
        </div>
        <div class="section-action">
          <router-link to="/article">
            <el-button type="primary" plain size="large">{{ locale === 'zh' ? '查看更多新闻' : 'View More News' }}</el-button>
          </router-link>
        </div>
      </div>
    </section>

    <section class="contact-section">
      <div class="container">
        <h2 class="section-title">{{ locale === 'zh' ? '联系我们' : 'Contact Us' }}</h2>
        <div class="contact-form-wrapper">
          <h3>{{ locale === 'zh' ? '在线留言' : 'Send Message' }}</h3>
          <el-form ref="formRef" :model="form" :rules="rules" class="contact-form">
            <el-form-item prop="name">
              <el-input 
                v-model="form.name" 
                :placeholder="t('form.placeholder.name')"
                prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="email">
              <el-input 
                v-model="form.email" 
                :placeholder="t('form.placeholder.email')"
                prefix-icon="Message"
              />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input 
                v-model="form.phone" 
                :placeholder="t('form.placeholder.phone')"
                prefix-icon="Phone"
              />
            </el-form-item>
            <el-form-item prop="content">
              <el-input 
                v-model="form.content" 
                type="textarea" 
                :rows="5"
                :placeholder="t('form.placeholder.message')"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" @click="submitForm" :loading="loading" class="submit-btn">
                {{ t('common.submit') }}
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { homeApi, messageApi } from '@/api'
import { ElMessage } from 'element-plus'
import { Goods } from '@element-plus/icons-vue'

const { t, locale } = useI18n()

const banners = ref([])
const advantages = ref([])
const products = ref([])
const articles = ref([])
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  name: '',
  email: '',
  phone: '',
  content: ''
})

const rules = {
  name: [{ required: true, message: t('form.validation.nameRequired'), trigger: 'blur' }],
  email: [
    { required: true, message: t('form.validation.emailRequired'), trigger: 'blur' },
    { type: 'email', message: t('form.validation.emailFormat'), trigger: 'blur' }
  ],
  phone: [{ required: true, message: t('form.validation.phoneRequired'), trigger: 'blur' }],
  content: [
    { required: true, message: t('form.validation.messageRequired'), trigger: 'blur' },
    { min: 10, max: 500, message: t('form.validation.messageLength'), trigger: 'blur' }
  ]
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString()
}

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await messageApi.submit(form)
        ElMessage.success(t('form.submitSuccess'))
        formRef.value.resetFields()
      } catch (e) {
        console.error(e)
      } finally {
        loading.value = false
      }
    }
  })
}

const fetchData = async () => {
  try {
    const [bannerRes, productRes, articleRes, advantageRes] = await Promise.all([
      homeApi.getBanners().catch(() => ({ data: [] })),
      homeApi.getRecommendProducts().catch(() => ({ data: [] })),
      homeApi.getLatestArticles().catch(() => ({ data: [] })),
      homeApi.getAdvantages().catch(() => ({ data: [] }))
    ])
    banners.value = bannerRes.data || []
    products.value = productRes.data || []
    articles.value = articleRes.data || []
    advantages.value = advantageRes.data || []
  } catch (e) {
    console.error('Failed to fetch home data:', e)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.home-page {
  .section-title {
    text-align: center;
    font-size: 32px;
    margin-bottom: 40px;
    color: #333;
  }
}

.banner-section {
  .banner-link {
    display: block;
    width: 100%;
    height: 100%;
    position: relative;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .banner-content {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      padding: 40px;
      background: linear-gradient(transparent, rgba(0,0,0,0.7));
      color: #fff;

      h3 {
        font-size: 28px;
        margin-bottom: 10px;
      }

      p {
        font-size: 16px;
      }
    }
  }

  .banner-placeholder {
    height: 500px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    display: flex;
    align-items: center;
    justify-content: center;

    .placeholder-content {
      text-align: center;
      color: #fff;

      h2 {
        font-size: 48px;
        margin-bottom: 20px;
      }

      p {
        font-size: 20px;
        opacity: 0.9;
      }
    }
  }
}

.intro-section {
  padding: 80px 0;
  background: #f9f9f9;

  .intro-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 60px;
    align-items: center;
  }

  .intro-text {
    p {
      font-size: 16px;
      line-height: 1.8;
      color: #666;
      margin-bottom: 30px;
    }
  }

  .intro-image {
    img {
      width: 100%;
      border-radius: 8px;
    }
  }
}

.advantages-section {
  padding: 80px 0;

  .advantages-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;
  }

  .advantage-card {
    text-align: center;
    padding: 40px 30px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    }

    .advantage-icon {
      width: 80px;
      height: 80px;
      margin: 0 auto 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 50%;

      img {
        width: 60%;
        height: 60%;
        object-fit: contain;
      }

      .el-icon {
        font-size: 36px;
        color: #fff;
      }
    }

    h3 {
      font-size: 20px;
      margin-bottom: 15px;
      color: #333;
    }

    p {
      font-size: 14px;
      color: #666;
      line-height: 1.6;
    }
  }
}

.products-section,
.news-section {
  padding: 80px 0;
  background: #f9f9f9;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 30px;
}

.product-card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  }

  .product-image {
    aspect-ratio: 1;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .product-info {
    padding: 20px;

    h3 {
      font-size: 16px;
      margin-bottom: 10px;
      color: #333;
    }

    p {
      font-size: 14px;
      color: #666;
      margin-bottom: 10px;
    }

    .product-price {
      font-size: 18px;
      color: #f56c6c;
      font-weight: bold;
    }
  }
}

.news-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.news-item {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;

  &:hover {
    transform: translateY(-5px);
  }

  .news-image {
    aspect-ratio: 16/9;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .news-content {
    padding: 20px;

    .news-date {
      font-size: 12px;
      color: #999;
    }

    h3 {
      font-size: 16px;
      margin: 10px 0;
      color: #333;
    }

    p {
      font-size: 14px;
      color: #666;
    }
  }
}

.section-action {
  text-align: center;
  margin-top: 40px;
}

.contact-section {
  padding: 80px 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

  .section-title {
    color: #fff;
  }

  .contact-form-wrapper {
    max-width: 600px;
    margin: 0 auto;
    background: #fff;
    padding: 40px;
    border-radius: 8px;

    h3 {
      font-size: 20px;
      margin-bottom: 30px;
      text-align: center;
    }

    .submit-btn {
      width: 100%;
    }
  }
}

@media (max-width: 1200px) {
  .products-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 992px) {
  .intro-section .intro-content {
    grid-template-columns: 1fr;
  }

  .advantages-section .advantages-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .products-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .advantages-section .advantages-grid {
    grid-template-columns: 1fr;
  }

  .news-list {
    grid-template-columns: 1fr;
  }

  .contact-form-wrapper {
    padding: 20px;
  }
}
</style>
