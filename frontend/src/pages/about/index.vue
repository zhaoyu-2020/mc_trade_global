<template>
  <div class="about-page">
    <div class="page-header">
      <h1>{{ locale === 'zh' ? '关于我们' : 'About Us' }}</h1>
      <p>{{ locale === 'zh' ? '了解我们如何帮助您的业务发展' : 'Learn how we help your business grow' }}</p>
    </div>
    
    <section class="intro-section">
      <div class="container">
        <h2>{{ locale === 'zh' ? '企业介绍' : 'Company Introduction' }}</h2>
        <div class="rich-content">
          <p>{{ locale === 'zh' ? 'MC Trade Global 是一家专业从事国际贸易的企业成立于2010年，总部位于中国深圳。我们专注于为全球客户提供高品质的产品和优质的服务。' : 'MC Trade Global is a professional international trade company established in 2010, headquartered in Shenzhen, China. We specialize in providing high-quality products and excellent service to global customers.' }}</p>
          <p>{{ locale === 'zh' ? '多年来，我们凭借卓越的产品品质、合理的价格和完善的售后服务，赢得了众多客户的信赖。产品远销美国、欧洲、东南亚、中东等100多个国家和地区。' : 'Over the years, we have earned the trust of numerous customers with our excellent product quality, reasonable prices, and comprehensive after-sales service. Our products are sold to more than 100 countries and regions including the United States, Europe, Southeast Asia, and the Middle East.' }}</p>
          <div class="intro-stats">
            <div class="stat-item">
              <span class="stat-number">14+</span>
              <span class="stat-label">{{ locale === 'zh' ? '年行业经验' : 'Years Experience' }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-number">100+</span>
              <span class="stat-label">{{ locale === 'zh' ? '个国家地区' : 'Countries' }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-number">5000+</span>
              <span class="stat-label">{{ locale === 'zh' ? '家企业客户' : 'Enterprise Clients' }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-number">99%</span>
              <span class="stat-label">{{ locale === 'zh' ? '客户满意度' : 'Satisfaction Rate' }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="timeline-section">
      <div class="container">
        <h2>{{ locale === 'zh' ? '发展历程' : 'Development History' }}</h2>
        <div class="timeline" v-if="timeline.length">
          <div v-for="(item, index) in timeline" :key="item.id" class="timeline-item" :class="{ 'timeline-right': index % 2 === 1 }">
            <div class="timeline-year">{{ item.year }}</div>
            <div class="timeline-content">
              <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
              <p>{{ locale === 'zh' ? item.description : item.descriptionEn }}</p>
              <img v-if="item.imageUrl" :src="item.imageUrl" :alt="item.title" />
            </div>
          </div>
        </div>
        <div class="timeline" v-else>
          <div v-for="(item, index) in defaultTimeline" :key="index" class="timeline-item" :class="{ 'timeline-right': index % 2 === 1 }">
            <div class="timeline-year">{{ item.year }}</div>
            <div class="timeline-content">
              <h3>{{ locale === 'zh' ? item.title : item.titleEn }}</h3>
              <p>{{ locale === 'zh' ? item.description : item.descriptionEn }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="culture-section">
      <div class="container">
        <h2>{{ locale === 'zh' ? '企业文化' : 'Corporate Culture' }}</h2>
        <div class="culture-grid">
          <div class="culture-card">
            <div class="culture-icon">
              <el-icon><Aim /></el-icon>
            </div>
            <h3>{{ locale === 'zh' ? '核心价值观' : 'Core Values' }}</h3>
            <p>{{ locale === 'zh' ? '诚信、创新、合作、共赢 - 我们坚持诚实守信的经营理念，不断创新发展，与客户携手共赢。' : 'Integrity, Innovation, Cooperation, Win-win - We adhere to honest and trustworthy business philosophy, continuously innovate and develop, and win-win with customers.' }}</p>
          </div>
          <div class="culture-card">
            <div class="culture-icon">
              <el-icon><Flag /></el-icon>
            </div>
            <h3>{{ locale === 'zh' ? '企业使命' : 'Mission' }}</h3>
            <p>{{ locale === 'zh' ? '为全球客户提供优质的产品和服务，助力企业出海，实现互利共赢。' : 'Provide high-quality products and services to global customers, help enterprises go global, and achieve mutual benefit and win-win outcomes.' }}</p>
          </div>
          <div class="culture-card">
            <div class="culture-icon">
              <el-icon><View /></el-icon>
            </div>
            <h3>{{ locale === 'zh' ? '企业愿景' : 'Vision' }}</h3>
            <p>{{ locale === 'zh' ? '成为全球最具竞争力的国际贸易企业之一。' : 'To become one of the most competitive international trade companies in the world.' }}</p>
          </div>
        </div>
      </div>
    </section>

    <section class="gallery-section">
      <div class="container">
        <h2>{{ locale === 'zh' ? '工厂环境' : 'Factory Environment' }}</h2>
        <div class="gallery-grid">
          <div v-for="i in 6" :key="i" class="gallery-item" @click="previewImage(i)">
            <img :src="`https://placehold.co/400x300/304156/ffffff?text=Factory+${i}`" :alt="`Factory ${i}`" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { Aim, Flag, View } from '@element-plus/icons-vue'

const { locale } = useI18n()
const timeline = ref([])

const defaultTimeline = [
  { year: '2010', title: '公司成立', titleEn: 'Company Founded', description: 'MC Trade Global 在深圳正式成立', descriptionEn: 'MC Trade Global officially established in Shenzhen' },
  { year: '2013', title: '开拓海外市场', titleEn: 'Overseas Market Expansion', description: '产品首次出口欧美市场', descriptionEn: 'Products exported to European and American markets for the first time' },
  { year: '2016', title: '规模扩大', titleEn: 'Scale Expansion', description: '公司规模扩大至500人', descriptionEn: 'Company expanded to 500 employees' },
  { year: '2019', title: '全球布局', titleEn: 'Global Layout', description: '在多个国家设立分支机构', descriptionEn: 'Established branches in multiple countries' },
  { year: '2024', title: '持续发展', titleEn: 'Sustainable Development', description: '继续深耕国际市场', descriptionEn: 'Continue to deepen international market' }
]

const previewImage = (index) => {
  console.log('Preview image:', index)
}

onMounted(async () => {
  try {
    // TODO: 获取发展历程数据
    // const res = await api.getDevelopmentTimeline()
    // timeline.value = res.data
  } catch (e) {
    console.error(e)
  }
})
</script>

<style lang="scss" scoped>
.about-page {
  .page-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    padding: 100px 0;
    text-align: center;

    h1 {
      font-size: 42px;
      margin-bottom: 15px;
    }

    p {
      font-size: 18px;
      opacity: 0.9;
    }
  }

  section {
    padding: 80px 0;

    h2 {
      font-size: 32px;
      margin-bottom: 40px;
      text-align: center;
      color: #333;
    }
  }

  .intro-section {
    background: #f9f9f9;

    .intro-stats {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 30px;
      margin-top: 50px;

      .stat-item {
        text-align: center;
        padding: 30px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

        .stat-number {
          display: block;
          font-size: 42px;
          font-weight: bold;
          color: #667eea;
          margin-bottom: 10px;
        }

        .stat-label {
          font-size: 14px;
          color: #666;
        }
      }
    }
  }

  .rich-content {
    max-width: 900px;
    margin: 0 auto;

    p {
      font-size: 16px;
      line-height: 1.9;
      color: #666;
      margin-bottom: 20px;
      text-indent: 2em;
    }
  }

  .timeline {
    position: relative;
    max-width: 1000px;
    margin: 0 auto;

    &::before {
      content: '';
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      width: 2px;
      height: 100%;
      background: #667eea;
    }

    .timeline-item {
      position: relative;
      width: 50%;
      padding-right: 60px;
      margin-bottom: 50px;

      &.timeline-right {
        margin-left: 50%;
        padding-right: 0;
        padding-left: 60px;
      }

      .timeline-year {
        position: absolute;
        right: -35px;
        top: 0;
        width: 70px;
        height: 70px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: #fff;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 18px;
        font-weight: bold;
        z-index: 1;
      }

      &.timeline-right .timeline-year {
        left: -35px;
        right: auto;
      }

      .timeline-content {
        background: #fff;
        padding: 25px;
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

        h3 {
          font-size: 18px;
          margin-bottom: 10px;
          color: #333;
        }

        p {
          color: #666;
          line-height: 1.6;
        }

        img {
          width: 100%;
          margin-top: 15px;
          border-radius: 4px;
        }
      }
    }
  }

  .culture-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;

    .culture-card {
      background: #fff;
      padding: 40px 30px;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
      text-align: center;
      transition: transform 0.3s, box-shadow 0.3s;

      &:hover {
        transform: translateY(-10px);
        box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
      }

      .culture-icon {
        width: 80px;
        height: 80px;
        margin: 0 auto 25px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;

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
        line-height: 1.7;
      }
    }
  }

  .gallery-section {
    background: #f9f9f9;

    .gallery-grid {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 20px;

      .gallery-item {
        aspect-ratio: 4/3;
        border-radius: 8px;
        overflow: hidden;
        cursor: pointer;
        transition: transform 0.3s;

        &:hover {
          transform: scale(1.02);

          img {
            transform: scale(1.1);
          }
        }

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          transition: transform 0.3s;
        }
      }
    }
  }
}

@media (max-width: 992px) {
  .intro-section .intro-stats {
    grid-template-columns: repeat(2, 1fr);
  }

  .culture-grid {
    grid-template-columns: 1fr !important;
  }

  .timeline {
    &::before {
      left: 35px;
    }

    .timeline-item {
      width: 100%;
      padding-right: 0;
      padding-left: 90px !important;
      margin-left: 0;

      .timeline-year {
        left: 0 !important;
        right: auto !important;
      }

      &.timeline-right {
        margin-left: 0;
      }
    }
  }

  .gallery-grid {
    grid-template-columns: repeat(2, 1fr) !important;
  }
}

@media (max-width: 768px) {
  .intro-section .intro-stats {
    grid-template-columns: 1fr;
  }

  .gallery-grid {
    grid-template-columns: 1fr !important;
  }
}
</style>
