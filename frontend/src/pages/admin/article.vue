<template>
  <div class="article-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ t('admin.article.title') }}</span>
          <div class="header-actions">
            <el-select v-model="categoryFilter" placeholder="筛选分类" clearable style="width: 150px; margin-right: 10px;">
              <el-option v-for="cat in categories" :key="cat.id" :label="cat.categoryName" :value="cat.id" />
            </el-select>
            <el-button type="primary" @click="handleAdd">{{ t('admin.article.addArticle') }}</el-button>
          </div>
        </div>
      </template>

      <el-table :data="articleList" v-loading="loading">
        <el-table-column prop="coverImage" label="封面" width="120">
          <template #default="{ row }">
            <el-image v-if="row.coverImage" :src="row.coverImage" style="width: 80px; height: 60px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="title" :label="t('admin.article.titleField')">
          <template #default="{ row }">
            {{ locale === 'zh' ? row.title : row.titleEn }}
          </template>
        </el-table-column>
        <el-table-column prop="author" :label="t('admin.article.author')" width="120" />
        <el-table-column prop="isPublish" :label="t('admin.article.publishStatus')" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isPublish === 1 ? 'success' : 'info'" size="small">
              {{ row.isPublish === 1 ? t('admin.article.published') : t('admin.article.draft') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="viewCount" :label="t('admin.article.viewCount')" width="100" />
        <el-table-column prop="isTop" :label="t('admin.article.top')" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isTop === 1" type="warning" size="small">置顶</el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="t('common.edit')" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">{{ t('common.edit') }}</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">{{ t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="fetchArticles"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? t('admin.article.editArticle') : t('admin.article.addArticle')" width="900px" fullscreen>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('admin.article.titleField') + ' (中)'" prop="title">
              <el-input v-model="form.title" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('admin.article.titleField') + ' (EN)'" prop="titleEn">
              <el-input v-model="form.titleEn" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类">
              <el-select v-model="form.categoryId" placeholder="请选择分类" clearable style="width: 100%">
                <el-option v-for="cat in categories" :key="cat.id" :label="cat.categoryName" :value="cat.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('admin.article.author')">
              <el-input v-model="form.author" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="封面图">
          <el-upload
            class="image-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
          >
            <img v-if="form.coverImage" :src="form.coverImage" class="preview-image" />
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="摘要 (中)">
              <el-input v-model="form.summary" type="textarea" :rows="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="摘要 (EN)">
              <el-input v-model="form.summaryEn" type="textarea" :rows="2" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="正文 (中)">
              <el-input v-model="form.content" type="textarea" :rows="8" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="正文 (EN)">
              <el-input v-model="form.contentEn" type="textarea" :rows="8" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item :label="t('admin.article.publishStatus')">
              <el-switch v-model="form.isPublish" :active-value="1" :inactive-value="0" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="t('admin.article.top')">
              <el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item :label="t('admin.menu.sort')">
              <el-input-number v-model="form.sort" :min="1" :max="999" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="handleSubmit">{{ t('common.save') }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import { articleApi, articleCategoryApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const { t, locale } = useI18n()

const articleList = ref([])
const categories = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref(null)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const categoryFilter = ref(null)

const form = reactive({
  categoryId: null,
  title: '',
  titleEn: '',
  author: '',
  coverImage: '',
  summary: '',
  summaryEn: '',
  content: '',
  contentEn: '',
  isPublish: 1,
  isTop: 0,
  sort: 1
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
}

watch(categoryFilter, () => {
  currentPage.value = 1
  fetchArticles()
})

const handleCoverSuccess = (res) => {
  form.coverImage = res.data.url
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    categoryId: null,
    title: '',
    titleEn: '',
    author: '',
    coverImage: '',
    summary: '',
    summaryEn: '',
    content: '',
    contentEn: '',
    isPublish: 1,
    isTop: 0,
    sort: 1
  })
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
          await articleApi.update(currentId.value, form)
        } else {
          await articleApi.add(form)
        }
        ElMessage.success(t('common.success'))
        dialogVisible.value = false
        fetchArticles()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该文章吗？', '提示', { type: 'warning' })
  try {
    await articleApi.delete(row.id)
    ElMessage.success(t('common.success'))
    fetchArticles()
  } catch (e) {
    console.error(e)
  }
}

const fetchArticles = async () => {
  loading.value = true
  try {
    const res = await articleApi.list({ 
      categoryId: categoryFilter.value,
      current: currentPage.value, 
      size: pageSize.value 
    })
    articleList.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
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
.article-management {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .image-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    width: 120px;
    height: 120px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      border-color: #409eff;
    }

    .preview-image {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .uploader-icon {
      font-size: 28px;
      color: #8c939d;
    }
  }
}
</style>
