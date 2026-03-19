<template>
  <div class="contact-page">
    <div class="page-header">
      <h1>{{ locale === 'zh' ? '联系我们' : 'Contact Us' }}</h1>
      <p>{{ locale === 'zh' ? '期待与您的合作' : 'Looking forward to cooperating with you' }}</p>
    </div>

    <div class="container">
      <div class="contact-layout">
        <div class="contact-info">
          <h2>{{ locale === 'zh' ? '联系方式' : 'Contact Information' }}</h2>
          
          <div class="info-cards">
            <div class="info-card">
              <div class="info-icon">
                <el-icon><Location /></el-icon>
              </div>
              <div class="info-content">
                <h3>{{ locale === 'zh' ? '公司地址' : 'Address' }}</h3>
                <p>{{ locale === 'zh' ? config.company_address : config.company_address_en }}</p>
              </div>
            </div>

            <div class="info-card">
              <div class="info-icon">
                <el-icon><Phone /></el-icon>
              </div>
              <div class="info-content">
                <h3>{{ locale === 'zh' ? '联系电话' : 'Phone' }}</h3>
                <p>{{ config.company_phone || '+86 755 12345678' }}</p>
                <a href="tel:+8675512345678" class="contact-action">{{ locale === 'zh' ? '立即拨打' : 'Call now' }}</a>
              </div>
            </div>

            <div class="info-card">
              <div class="info-icon">
                <el-icon><Message /></el-icon>
              </div>
              <div class="info-content">
                <h3>{{ locale === 'zh' ? '电子邮箱' : 'Email' }}</h3>
                <p>{{ config.company_email || 'info@mctradeglobal.com' }}</p>
                <a href="mailto:info@mctradeglobal.com" class="contact-action">{{ locale === 'zh' ? '发送邮件' : 'Send email' }}</a>
              </div>
            </div>

            <div class="info-card">
              <div class="info-icon">
                <el-icon><Clock /></el-icon>
              </div>
              <div class="info-content">
                <h3>{{ locale === 'zh' ? '工作时间' : 'Working Hours' }}</h3>
                <p>{{ locale === 'zh' ? '周一至周五 9:00 - 18:00' : 'Mon - Fri 9:00 AM - 6:00 PM' }}</p>
              </div>
            </div>
          </div>

          <div class="social-section">
            <h3>{{ locale === 'zh' ? '关注我们' : 'Follow Us' }}</h3>
            <div class="social-links">
              <a v-if="config.facebook_url" :href="config.facebook_url" target="_blank" rel="noopener" class="social-link facebook">
                <span class="social-icon">Facebook</span>
              </a>
              <a v-if="config.linkedin_url" :href="config.linkedin_url" target="_blank" rel="noopener" class="social-link linkedin">
                <span class="social-icon">LinkedIn</span>
              </a>
              <a v-if="config.twitter_url" :href="config.twitter_url" target="_blank" rel="noopener" class="social-link twitter">
                <span class="social-icon">Twitter</span>
              </a>
            </div>
          </div>

          <div class="map-section">
            <h3>{{ locale === 'zh' ? '公司位置' : 'Location' }}</h3>
            <div class="map-placeholder">
              <div class="map-content">
                <el-icon><Location /></el-icon>
                <p>{{ locale === 'zh' ? '地图加载中...' : 'Loading map...' }}</p>
                <a :href="`https://maps.google.com/?q=${encodeURIComponent(config.company_address || 'Shenzhen, China')}`" target="_blank" rel="noopener" class="map-link">
                  {{ locale === 'zh' ? '在 Google Maps 中查看' : 'View on Google Maps' }}
                </a>
              </div>
            </div>
          </div>
        </div>

        <div class="contact-form-wrapper">
          <div class="form-header">
            <h2>{{ locale === 'zh' ? '在线留言' : 'Send Message' }}</h2>
            <p>{{ locale === 'zh' ? '请填写以下表单，我们将尽快与您联系' : 'Please fill in the form below and we will contact you soon' }}</p>
          </div>
          <el-form ref="formRef" :model="form" :rules="rules" class="contact-form" size="large">
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
                :rows="6"
                :placeholder="t('form.placeholder.message')"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm" :loading="loading" class="submit-btn">
                {{ t('common.submit') }}
              </el-button>
            </el-form-item>
          </el-form>
          <div class="form-note">
            <p>{{ locale === 'zh' ? '* 提交表示您同意我们的隐私政策' : '* By submitting, you agree to our privacy policy' }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { Location, Phone, Message, Clock } from '@element-plus/icons-vue'
import { homeApi, messageApi } from '@/api'
import { ElMessage } from 'element-plus'

const { t, locale } = useI18n()

const formRef = ref(null)
const loading = ref(false)
const config = ref({})

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

const fetchConfig = async () => {
  try {
    const res = await homeApi.getConfig()
    config.value = res.data || {}
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchConfig()
})
</script>

<style lang="scss" scoped>
.contact-page {
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

  .contact-layout {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 60px;
    padding: 60px 0;
  }

  .contact-info {
    h2 {
      font-size: 24px;
      margin-bottom: 30px;
      color: #333;
    }

    .info-cards {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
      margin-bottom: 40px;
    }

    .info-card {
      background: #f9f9f9;
      padding: 25px;
      border-radius: 8px;
      transition: all 0.3s;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
      }

      .info-icon {
        width: 50px;
        height: 50px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 15px;

        .el-icon {
          font-size: 24px;
          color: #fff;
        }
      }

      .info-content {
        h3 {
          font-size: 14px;
          color: #999;
          margin-bottom: 8px;
        }

        p {
          color: #333;
          font-size: 15px;
          line-height: 1.6;
          margin-bottom: 10px;
        }

        .contact-action {
          color: #667eea;
          font-size: 14px;
          &:hover { text-decoration: underline; }
        }
      }
    }

    .social-section {
      margin-bottom: 40px;

      h3 {
        font-size: 16px;
        margin-bottom: 15px;
        color: #333;
      }

      .social-links {
        display: flex;
        gap: 15px;

        .social-link {
          padding: 10px 25px;
          border-radius: 25px;
          transition: all 0.3s;

          &:hover {
            transform: translateY(-3px);
          }

          .social-icon {
            font-size: 14px;
          }

          &.facebook {
            background: #1877f2;
            color: #fff;
          }

          &.linkedin {
            background: #0a66c2;
            color: #fff;
          }

          &.twitter {
            background: #1da1f2;
            color: #fff;
          }
        }
      }
    }

    .map-section {
      h3 {
        font-size: 16px;
        margin-bottom: 15px;
        color: #333;
      }

      .map-placeholder {
        aspect-ratio: 16/9;
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;

        .map-content {
          text-align: center;

          .el-icon {
            font-size: 48px;
            color: #667eea;
            margin-bottom: 15px;
          }

          p {
            color: #666;
            margin-bottom: 15px;
          }

          .map-link {
            color: #667eea;
            font-size: 14px;
            &:hover { text-decoration: underline; }
          }
        }
      }
    }
  }

  .contact-form-wrapper {
    background: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);

    .form-header {
      text-align: center;
      margin-bottom: 30px;

      h2 {
        font-size: 24px;
        color: #333;
        margin-bottom: 10px;
      }

      p {
        color: #666;
        font-size: 14px;
      }
    }

    .submit-btn {
      width: 100%;
      height: 50px;
      font-size: 16px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
    }

    .form-note {
      text-align: center;
      margin-top: 15px;

      p {
        font-size: 12px;
        color: #999;
      }
    }
  }
}

@media (max-width: 992px) {
  .contact-layout {
    grid-template-columns: 1fr !important;
  }

  .info-cards {
    grid-template-columns: 1fr !important;
  }
}
</style>
