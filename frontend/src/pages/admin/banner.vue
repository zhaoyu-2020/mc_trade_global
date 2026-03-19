<template>
  <div class="banner-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ t('admin.banner.title') }}</span>
          <el-button type="primary" @click="handleAdd">{{ t('admin.banner.addBanner') }}</el-button>
        </div>
      </template>

      <el-table :data="bannerList" v-loading="loading">
        <el-table-column prop="imageUrl" :label="t('admin.banner.image')" width="200">
          <template #default="{ row }">
            <el-image :src="row.imageUrl" style="width: 100px; height: 60px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="title" :label="t('admin.banner.titleField')" width="150">
          <template #default="{ row }">
            {{ locale === 'zh' ? row.title : row.titleEn }}
          </template>
        </el-table-column>
        <el-table-column prop="description" :label="t('admin.banner.description')" show-overflow-tooltip />
        <el-table-column prop="linkUrl" :label="t('admin.banner.linkUrl')" show-overflow-tooltip />
        <el-table-column prop="sort" :label="t('admin.banner.sort')" width="80" />
        <el-table-column prop="status" :label="t('admin.banner.status')" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="t('common.edit')" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">{{ t('common.edit') }}</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">{{ t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? t('admin.banner.editBanner') : t('admin.banner.addBanner')" width="700px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="t('admin.banner.image')" prop="imageUrl">
          <el-upload
            class="banner-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
          >
            <img v-if="form.imageUrl" :src="form.imageUrl" class="banner-img" />
            <el-icon v-else class="banner-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item :label="t('admin.banner.titleField') + ' (中)'" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.titleField') + ' (EN)'" prop="titleEn">
          <el-input v-model="form.titleEn" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.description') + ' (中)'">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.description') + ' (EN)'">
          <el-input v-model="form.descriptionEn" type="textarea" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.linkUrl')">
          <el-input v-model="form.linkUrl" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.sort')" prop="sort">
          <el-input-number v-model="form.sort" :min="1" :max="999" />
        </el-form-item>
        <el-form-item :label="t('admin.banner.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="handleSubmit">{{ t('common.save') }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { bannerApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const { t, locale } = useI18n()

const bannerList = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const currentId = ref(null)

const form = reactive({
  imageUrl: '',
  title: '',
  titleEn: '',
  description: '',
  descriptionEn: '',
  linkUrl: '',
  linkTarget: '_blank',
  sort: 1,
  status: 1
})

const rules = {
  imageUrl: [{ required: true, message: '请上传图片', trigger: 'change' }]
}

const handleUploadSuccess = (res) => {
  form.imageUrl = res.data.url
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, { imageUrl: '', title: '', titleEn: '', description: '', descriptionEn: '', linkUrl: '', sort: 1, status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  currentId.value = row.id
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await bannerApi.update(currentId.value, form)
        } else {
          await bannerApi.add(form)
        }
        ElMessage.success(t('common.success'))
        dialogVisible.value = false
        fetchBanners()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该轮播图吗？', '提示', { type: 'warning' })
  try {
    await bannerApi.delete(row.id)
    ElMessage.success(t('common.success'))
    fetchBanners()
  } catch (e) {
    console.error(e)
  }
}

const fetchBanners = async () => {
  loading.value = true
  try {
    const res = await bannerApi.list()
    bannerList.value = res.data.records || []
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchBanners()
})
</script>

<style lang="scss" scoped>
.banner-management {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .banner-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    width: 200px;
    height: 120px;
    cursor: pointer;
    overflow: hidden;

    &:hover {
      border-color: #409eff;
    }

    .banner-img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .banner-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}
</style>
