<template>
  <div class="menu-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ t('admin.menu.title') }}</span>
          <el-button type="primary" @click="handleAdd">{{ t('admin.menu.addMenu') }}</el-button>
        </div>
      </template>

      <el-table :data="menuList" row-key="id" default-expand-all>
        <el-table-column prop="menuName" :label="t('admin.menu.menuName')" width="200">
          <template #default="{ row }">
            {{ locale === 'zh' ? row.menuName : row.menuNameEn }}
          </template>
        </el-table-column>
        <el-table-column prop="menuType" :label="t('admin.menu.menuType')" width="120">
          <template #default="{ row }">
            {{ row.menuType === 1 ? t('admin.menu.level1') : t('admin.menu.level2') }}
          </template>
        </el-table-column>
        <el-table-column prop="path" :label="t('admin.menu.path')" />
        <el-table-column prop="sort" :label="t('admin.menu.sort')" width="80" />
        <el-table-column prop="status" :label="t('admin.menu.status')" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="t('common.edit')" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">{{ t('common.edit') }}</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">{{ t('common.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? t('admin.menu.editMenu') : t('admin.menu.addMenu')" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item :label="t('admin.menu.menuType')" prop="menuType">
          <el-select v-model="form.menuType" @change="handleTypeChange">
            <el-option :value="1" :label="t('admin.menu.level1')" />
            <el-option :value="2" :label="t('admin.menu.level2')" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.menuType === 2" :label="t('admin.menu.parentMenu')" prop="parentId">
          <el-select v-model="form.parentId">
            <el-option v-for="item in parentMenus" :key="item.id" :value="item.id" :label="item.menuName" />
          </el-select>
        </el-form-item>
        <el-form-item :label="t('admin.menu.menuName') + ' (中)'" prop="menuName">
          <el-input v-model="form.menuName" />
        </el-form-item>
        <el-form-item :label="t('admin.menu.menuName') + ' (EN)'" prop="menuNameEn">
          <el-input v-model="form.menuNameEn" />
        </el-form-item>
        <el-form-item :label="t('admin.menu.path')" prop="path">
          <el-input v-model="form.path" />
        </el-form-item>
        <el-form-item :label="t('admin.menu.sort')" prop="sort">
          <el-input-number v-model="form.sort" :min="1" :max="999" />
        </el-form-item>
        <el-form-item :label="t('admin.menu.status')" prop="status">
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
import { ref, reactive, computed, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { menuApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const { t, locale } = useI18n()

const menuList = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const currentId = ref(null)

const form = reactive({
  menuType: 1,
  parentId: null,
  menuName: '',
  menuNameEn: '',
  path: '',
  sort: 1,
  status: 1
})

const rules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuNameEn: [{ required: true, message: '请输入英文名称', trigger: 'blur' }],
  path: [{ required: true, message: '请输入链接地址', trigger: 'blur' }]
}

const parentMenus = computed(() => menuList.value.filter(item => item.menuType === 1))

const handleTypeChange = () => {
  if (form.menuType === 1) form.parentId = null
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, { menuType: 1, parentId: null, menuName: '', menuNameEn: '', path: '', sort: 1, status: 1 })
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
          await menuApi.update(currentId.value, form)
        } else {
          await menuApi.add(form)
        }
        ElMessage.success(t('common.success'))
        dialogVisible.value = false
        fetchMenu()
      } catch (e) {
        console.error(e)
      }
    }
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定删除该菜单吗？', '提示', { type: 'warning' })
  try {
    await menuApi.delete(row.id)
    ElMessage.success(t('common.success'))
    fetchMenu()
  } catch (e) {
    console.error(e)
  }
}

const fetchMenu = async () => {
  try {
    const res = await menuApi.list()
    menuList.value = res.data || []
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  fetchMenu()
})
</script>

<style lang="scss" scoped>
.menu-management {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
