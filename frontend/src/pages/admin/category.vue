<template>
  <div class="category-management">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="产品分类" name="product">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>产品分类管理</span>
              <el-button type="primary" @click="handleAddProductCategory">新增分类</el-button>
            </div>
          </template>
          <el-table :data="productCategories" row-key="id" default-expand-all>
            <el-table-column prop="categoryName" label="分类名称">
              <template #default="{ row }">
                {{ locale === 'zh' ? row.categoryName : row.categoryNameEn }}
              </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
                  {{ row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="{ row }">
                <el-button size="small" @click="handleEditProductCategory(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteProductCategory(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="文章分类" name="article">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>文章分类管理</span>
              <el-button type="primary" @click="handleAddArticleCategory">新增分类</el-button>
            </div>
          </template>
          <el-table :data="articleCategories" row-key="id" default-expand-all>
            <el-table-column prop="categoryName" label="分类名称">
              <template #default="{ row }">
                {{ locale === 'zh' ? row.categoryName : row.categoryNameEn }}
              </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序" width="100" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
                  {{ row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="{ row }">
                <el-button size="small" @click="handleEditArticleCategory(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeleteArticleCategory(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑分类' : '新增分类'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="activeTab === 'product' ? '分类名称(中)' : '分类名称(中)'" prop="categoryName">
          <el-input v-model="form.categoryName" />
        </el-form-item>
        <el-form-item :label="activeTab === 'product' ? '分类名称(EN)' : '分类名称(EN)'" prop="categoryNameEn">
          <el-input v-model="form.categoryNameEn" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="1" :max="999" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { productCategoryApi, articleCategoryApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const { locale } = useI18n()

const activeTab = ref('product')
const productCategories = ref([])
const articleCategories = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref(null)
const form = reactive({
  categoryName: '',
  categoryNameEn: '',
  sort: 1,
  status: 1
})

const rules = {
  categoryName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

const handleAddProductCategory = () => {
  isEdit.value = false
  currentId.value = null
  Object.assign(form, { categoryName: '', categoryNameEn: '', sort: 1, status: 1 })
  dialogVisible.value = true
}

const handleEditProductCategory = (row) => {
  isEdit.value = true
  currentId.value = row.id
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDeleteProductCategory = async (row) => {
  await ElMessageBox.confirm('确定删除该分类吗？', '提示', { type: 'warning' })
  try {
    await productCategoryApi.delete(row.id)
    ElMessage.success('操作成功')
    fetchProductCategories()
  } catch (e) {
    console.error(e)
  }
}

const handleAddArticleCategory = () => {
  isEdit.value = false
  currentId.value = null
  Object.assign(form, { categoryName: '', categoryNameEn: '', sort: 1, status: 1 })
  dialogVisible.value = true
}

const handleEditArticleCategory = (row) => {
  isEdit.value = true
  currentId.value = row.id
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDeleteArticleCategory = async (row) => {
  await ElMessageBox.confirm('确定删除该分类吗？', '提示', { type: 'warning' })
  try {
    await articleCategoryApi.delete(row.id)
    ElMessage.success('操作成功')
    fetchArticleCategories()
  } catch (e) {
    console.error(e)
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          if (activeTab.value === 'product') {
            await productCategoryApi.update(currentId.value, form)
          } else {
            await articleCategoryApi.update(currentId.value, form)
          }
        } else {
          if (activeTab.value === 'product') {
            await productCategoryApi.add(form)
          } else {
            await articleCategoryApi.add(form)
          }
        }
        ElMessage.success('操作成功')
        dialogVisible.value = false
        if (activeTab.value === 'product') {
          fetchProductCategories()
        } else {
          fetchArticleCategories()
        }
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const fetchProductCategories = async () => {
  try {
    const res = await productCategoryApi.tree()
    productCategories.value = res.data || []
  } catch (e) {
    console.error(e)
  }
}

const fetchArticleCategories = async () => {
  try {
    const res = await articleCategoryApi.tree()
    articleCategories.value = res.data || []
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchProductCategories()
  fetchArticleCategories()
})
</script>

<style lang="scss" scoped>
.category-management {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
