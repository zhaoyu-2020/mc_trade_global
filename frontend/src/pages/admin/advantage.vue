<template>
  <div class="advantage-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>企业优势管理</span>
          <el-button type="primary" @click="handleAdd">新增优势</el-button>
        </div>
      </template>

      <el-table :data="advantageList" v-loading="loading">
        <el-table-column prop="icon" label="图标" width="100">
          <template #default="{ row }">
            <el-image v-if="row.icon" :src="row.icon" style="width: 50px; height: 50px" fit="contain" />
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题">
          <template #default="{ row }">
            {{ locale === 'zh' ? row.title : row.titleEn }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip>
          <template #default="{ row }">
            {{ locale === 'zh' ? row.description : row.descriptionEn }}
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
        <el-table-column :label="t('common.edit')" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">{{ t('common.edit') }}</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">{{ t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑优势' : '新增优势'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="图标">
          <el-upload
            class="icon-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleIconSuccess"
          >
            <img v-if="form.icon" :src="form.icon" class="preview-icon" />
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="标题 (中)" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="标题 (EN)" prop="titleEn">
          <el-input v-model="form.titleEn" />
        </el-form-item>
        <el-form-item label="描述 (中)">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="描述 (EN)">
          <el-input v-model="form.descriptionEn" type="textarea" :rows="3" />
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
        <el-button @click="dialogVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="handleSubmit">{{ t('common.save') }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { advantageApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const { t, locale } = useI18n()

const advantageList = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const formRef = ref(null)

const form = reactive({
  icon: '',
  title: '',
  titleEn: '',
  description: '',
  descriptionEn: '',
  sort: 1,
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  titleEn: [{ required: true, message: '请输入英文标题', trigger: 'blur' }]
}

const handleIconSuccess = (res) => {
  form.icon = res.data.url
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    icon: '',
    title: '',
    titleEn: '',
    description: '',
    descriptionEn: '',
    sort: 1,
    status: 1
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
          await advantageApi.update(currentId.value, form)
        } else {
          await advantageApi.add(form)
        }
        ElMessage.success(t('common.success'))
        dialogVisible.value = false
        fetchAdvantages()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该优势吗？', '提示', { type: 'warning' })
  try {
    await advantageApi.delete(row.id)
    ElMessage.success(t('common.success'))
    fetchAdvantages()
  } catch (e) {
    console.error(e)
  }
}

const fetchAdvantages = async () => {
  loading.value = true
  try {
    const res = await advantageApi.list()
    advantageList.value = res.data || []
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchAdvantages()
})
</script>

<style lang="scss" scoped>
.advantage-management {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .icon-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    width: 80px;
    height: 80px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

    &:hover {
      border-color: #409eff;
    }

    .preview-icon {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }

    .uploader-icon {
      font-size: 28px;
      color: #8c939d;
    }
  }
}
</style>
