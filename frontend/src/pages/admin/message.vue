<template>
  <div class="message-management">
    <el-card>
      <template #header>
        <span>{{ t('admin.message.title') }}</span>
      </template>

      <el-table :data="messageList" v-loading="loading">
        <el-table-column prop="name" :label="t('admin.message.name')" width="120" />
        <el-table-column prop="email" :label="t('admin.message.email')" width="180" />
        <el-table-column prop="phone" :label="t('admin.message.phone')" width="150" />
        <el-table-column prop="content" :label="t('admin.message.content')" show-overflow-tooltip />
        <el-table-column prop="isRead" :label="t('admin.message.isRead')" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isRead === 1 ? 'success' : 'warning'">
              {{ row.isRead === 1 ? t('admin.message.isRead') : t('admin.message.unread') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column :label="t('common.edit')" width="250">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">{{ t('admin.message.viewDetail') }}</el-button>
            <el-button v-if="row.isRead === 0" size="small" @click="handleMarkRead(row)">{{ t('admin.message.markRead') }}</el-button>
            <el-button size="small" type="primary" @click="handleReply(row)">{{ t('admin.message.reply') }}</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="fetchMessages"
        />
      </div>
    </el-card>

    <el-dialog v-model="detailVisible" :title="t('admin.message.viewDetail')" width="600px">
      <div class="message-detail">
        <div class="detail-item">
          <label>{{ t('admin.message.name') }}:</label>
          <span>{{ currentMessage.name }}</span>
        </div>
        <div class="detail-item">
          <label>{{ t('admin.message.email') }}:</label>
          <span>{{ currentMessage.email }}</span>
        </div>
        <div class="detail-item">
          <label>{{ t('admin.message.phone') }}:</label>
          <span>{{ currentMessage.phone }}</span>
        </div>
        <div class="detail-item">
          <label>{{ t('admin.message.content') }}:</label>
          <p>{{ currentMessage.content }}</p>
        </div>
        <div v-if="currentMessage.replyContent" class="detail-item reply">
          <label>{{ t('admin.message.replyContent') }}:</label>
          <p>{{ currentMessage.replyContent }}</p>
        </div>
      </div>
    </el-dialog>

    <el-dialog v-model="replyVisible" title="回复留言" width="500px">
      <el-form ref="replyFormRef" :model="replyForm" label-width="100px">
        <el-form-item :label="t('admin.message.replyContent')" prop="replyContent">
          <el-input v-model="replyForm.replyContent" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyVisible = false">{{ t('common.cancel') }}</el-button>
        <el-button type="primary" @click="submitReply">{{ t('common.save') }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { messageApi } from '@/api'
import { ElMessage } from 'element-plus'

const { t } = useI18n()

const messageList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const detailVisible = ref(false)
const replyVisible = ref(false)
const currentMessage = ref({})
const replyFormRef = ref(null)
const replyForm = reactive({ replyContent: '' })

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString()
}

const handleView = (row) => {
  currentMessage.value = row
  detailVisible.value = true
}

const handleMarkRead = async (row) => {
  try {
    await messageApi.reply(row.id, { isRead: 1 })
    ElMessage.success(t('common.success'))
    fetchMessages()
  } catch (e) {
    console.error(e)
  }
}

const handleReply = (row) => {
  currentMessage.value = row
  replyForm.replyContent = row.replyContent || ''
  replyVisible.value = true
}

const submitReply = async () => {
  try {
    await messageApi.reply(currentMessage.value.id, { replyContent: replyForm.replyContent, isRead: 1 })
    ElMessage.success(t('common.success'))
    replyVisible.value = false
    fetchMessages()
  } catch (e) {
    console.error(e)
  }
}

const fetchMessages = async () => {
  loading.value = true
  try {
    const res = await messageApi.list({ current: currentPage.value, size: pageSize.value })
    messageList.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchMessages()
})
</script>

<style lang="scss" scoped>
.message-management {
  .pagination-wrapper {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .message-detail {
    .detail-item {
      margin-bottom: 15px;

      label {
        font-weight: bold;
        margin-right: 10px;
      }

      p {
        margin-top: 10px;
        padding: 10px;
        background: #f9f9f9;
        border-radius: 4px;
        line-height: 1.6;
      }

      &.reply p {
        background: #e8f4fd;
      }
    }
  }
}
</style>
