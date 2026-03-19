<template>
  <div class="product-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ t('admin.product.title') }}</span>
          <div class="header-actions">
            <el-select v-model="categoryFilter" placeholder="筛选分类" clearable style="width: 150px; margin-right: 10px;">
              <el-option v-for="cat in categories" :key="cat.id" :label="cat.categoryName" :value="cat.id" />
            </el-select>
            <el-button type="primary" @click="handleAdd">{{ t('admin.product.addProduct') }}</el-button>
          </div>
        </div>
      </template>

      <el-table :data="productList" v-loading="loading" row-key="id">
        <el-table-column prop="mainImage" label="图片" width="120">
          <template #default="{ row }">
            <el-image :src="row.mainImage" style="width: 80px; height: 80px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="productName" :label="t('admin.product.productName')">
          <template #default="{ row }">
            {{ locale === 'zh' ? row.productName : row.productNameEn }}
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="price" :label="t('admin.product.price')" width="120" />
        <el-table-column prop="isRecommend" :label="t('admin.product.recommend')" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isRecommend === 1 ? 'success' : 'info'" size="small">
              {{ row.isRecommend === 1 ? t('admin.product.recommendYes') : t('admin.product.recommendNo') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" :label="t('admin.product.status')" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
              {{ row.status === 1 ? '上架' : '下架' }}
            </el-tag>
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
          @current-change="fetchProducts"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? t('admin.product.editProduct') : t('admin.product.addProduct')" width="900px" fullscreen>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item :label="t('admin.product.productName') + ' (中)'" prop="productName">
              <el-input v-model="form.productName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('admin.product.productName') + ' (EN)'" prop="productNameEn">
              <el-input v-model="form.productNameEn" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
                <el-option v-for="cat in categories" :key="cat.id" :label="cat.categoryName" :value="cat.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="t('admin.product.price')">
              <el-input v-model="form.price" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="型号 (中)">
              <el-input v-model="form.model" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号 (EN)">
              <el-input v-model="form.modelEn" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="主图">
          <el-upload
            class="image-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleMainImageSuccess"
          >
            <img v-if="form.mainImage" :src="form.mainImage" class="preview-image" />
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item label="图集">
          <div class="images-list">
            <div v-for="(img, index) in imageList" :key="index" class="image-item">
              <img :src="img" />
              <span class="remove-btn" @click="removeImage(index)">×</span>
            </div>
            <el-upload
              class="image-uploader add-btn"
              action="/api/upload"
              :show-file-list="false"
              :on-success="handleImageSuccess"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
          </div>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="简短描述 (中)">
              <el-input v-model="form.shortDescription" type="textarea" :rows="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简短描述 (EN)">
              <el-input v-model="form.shortDescriptionEn" type="textarea" :rows="2" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="详细描述 (中)">
              <el-input v-model="form.description" type="textarea" :rows="4" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="详细描述 (EN)">
              <el-input v-model="form.descriptionEn" type="textarea" :rows="4" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="推荐">
              <el-switch v-model="form.isRecommend" :active-value="1" :inactive-value="0" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态">
              <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
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
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import { productApi, productCategoryApi, uploadApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const { t, locale } = useI18n()

const productList = ref([])
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
const imageList = ref([])

const form = reactive({
  categoryId: null,
  productName: '',
  productNameEn: '',
  model: '',
  modelEn: '',
  shortDescription: '',
  shortDescriptionEn: '',
  description: '',
  descriptionEn: '',
  price: '',
  mainImage: '',
  isRecommend: 0,
  status: 1,
  sort: 1
})

const rules = {
  productName: [{ required: true, message: '请输入产品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const handleMainImageSuccess = (res) => {
  form.mainImage = res.data.url
}

const handleImageSuccess = (res) => {
  imageList.value.push(res.data.url)
  form.images = JSON.stringify(imageList.value)
}

const removeImage = (index) => {
  imageList.value.splice(index, 1)
  form.images = JSON.stringify(imageList.value)
}

watch(categoryFilter, () => {
  currentPage.value = 1
  fetchProducts()
})

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    categoryId: null,
    productName: '',
    productNameEn: '',
    model: '',
    modelEn: '',
    shortDescription: '',
    shortDescriptionEn: '',
    description: '',
    descriptionEn: '',
    price: '',
    mainImage: '',
    images: '',
    isRecommend: 0,
    status: 1,
    sort: 1
  })
  imageList.value = []
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  currentId.value = row.id
  Object.assign(form, row)
  try {
    imageList.value = row.images ? JSON.parse(row.images) : []
  } catch {
    imageList.value = []
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await productApi.update(currentId.value, form)
        } else {
          await productApi.add(form)
        }
        ElMessage.success(t('common.success'))
        dialogVisible.value = false
        fetchProducts()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该产品吗？', '提示', { type: 'warning' })
  try {
    await productApi.delete(row.id)
    ElMessage.success(t('common.success'))
    fetchProducts()
  } catch (e) {
    console.error(e)
  }
}

const fetchProducts = async () => {
  loading.value = true
  try {
    const res = await productApi.list({ 
      categoryId: categoryFilter.value,
      current: currentPage.value, 
      size: pageSize.value 
    })
    productList.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
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

onMounted(() => {
  fetchProducts()
  fetchCategories()
})
</script>

<style lang="scss" scoped>
.product-management {
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

  .images-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;

    .image-item {
      position: relative;
      width: 120px;
      height: 120px;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 4px;
      }

      .remove-btn {
        position: absolute;
        top: -8px;
        right: -8px;
        width: 20px;
        height: 20px;
        background: #f56c6c;
        color: #fff;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        font-size: 14px;
      }
    }

    .add-btn {
      border-style: dashed;
    }
  }
}
</style>
