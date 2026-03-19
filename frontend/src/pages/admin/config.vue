<template>
  <div class="config-management">
    <el-card>
      <template #header>
        <span>{{ t('admin.config.title') }}</span>
      </template>

      <el-form ref="formRef" :model="form" label-width="150px">
        <el-divider content-position="left">{{ t('admin.config.websiteName') }}</el-divider>
        <el-form-item label="网站名称 (中文)">
          <el-input v-model="form.website_name" />
        </el-form-item>
        <el-form-item label="网站名称 (EN)">
          <el-input v-model="form.website_name_en" />
        </el-form-item>
        <el-form-item label="网站LOGO">
          <el-upload action="/api/upload" :show-file-list="false" :on-success="(res) => form.website_logo = res.data.url">
            <img v-if="form.website_logo" :src="form.website_logo" style="width: 150px; height: 60px; object-fit: contain;" />
            <el-button v-else type="primary">上传LOGO</el-button>
          </el-upload>
        </el-form-item>

        <el-divider content-position="left">{{ t('admin.config.companyName') }}</el-divider>
        <el-form-item label="公司名称 (中文)">
          <el-input v-model="form.company_name" />
        </el-form-item>
        <el-form-item label="公司名称 (EN)">
          <el-input v-model="form.company_name_en" />
        </el-form-item>

        <el-divider content-position="left">{{ t('admin.config.companyAddress') }}</el-divider>
        <el-form-item label="公司地址 (中文)">
          <el-input v-model="form.company_address" />
        </el-form-item>
        <el-form-item label="公司地址 (EN)">
          <el-input v-model="form.company_address_en" />
        </el-form-item>

        <el-divider content-position="left">{{ t('admin.config.contactInfo') }}</el-divider>
        <el-form-item label="联系电话">
          <el-input v-model="form.company_phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.company_email" />
        </el-form-item>

        <el-divider content-position="left">{{ t('admin.config.socialMedia') }}</el-divider>
        <el-form-item label="Facebook链接">
          <el-input v-model="form.facebook_url" />
        </el-form-item>
        <el-form-item label="LinkedIn链接">
          <el-input v-model="form.linkedin_url" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSave" :loading="loading">{{ t('common.save') }}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { configApi } from '@/api'
import { ElMessage } from 'element-plus'

const { t } = useI18n()

const loading = ref(false)
const formRef = ref(null)

const form = reactive({
  website_name: '',
  website_name_en: '',
  website_logo: '',
  company_name: '',
  company_name_en: '',
  company_address: '',
  company_address_en: '',
  company_phone: '',
  company_email: '',
  facebook_url: '',
  linkedin_url: ''
})

const handleSave = async () => {
  loading.value = true
  try {
    for (const [key, value] of Object.entries(form)) {
      const config = configs.value.find(c => c.configKey === key)
      if (config) {
        await configApi.update(config.id, { configValue: value, configValueEn: form[key + '_en'] || value })
      }
    }
    ElMessage.success(t('common.success'))
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const configs = ref([])

const fetchConfig = async () => {
  try {
    const res = await configApi.getAll()
    configs.value = res.data || []
    configs.value.forEach(config => {
      if (form.hasOwnProperty(config.configKey)) {
        form[config.configKey] = config.configValue
      }
    })
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchConfig()
})
</script>

<style lang="scss" scoped>
.config-management {
  :deep(.el-divider__text) {
    font-size: 16px;
    font-weight: bold;
  }
}
</style>
