<template>
  <div>
    <div>
      <el-form :model="message">
        <el-form-item label="content">
          <el-input v-model="message.content" />
        </el-form-item>
        <el-form-item label="receiver">
          <el-select v-model="message.receiver">
            <el-option
              v-for="item in userList"
              :key="item._id"
              :label="item.username"
              :value="item._id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addMessage">addMessage</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-table :data="messageList">
        <el-table-column label="_id">
          <template #default="scope">
            {{ scope.row._id }}
          </template>
        </el-table-column>
        <el-table-column label="content">
          <template #default="scope">
            {{ scope.row.content }}
          </template>
        </el-table-column>
        <el-table-column label="createDate">
          <template #default="scope">
            {{ moment(scope.row.createDate).format(DATE_FORMAT) }}
          </template>
        </el-table-column>
        <el-table-column label="operation">
          <template #default="scope">
            <el-button type="warning" @click="showEditDialog(scope.row)">edit</el-button>
            <el-button type="danger" @click="deleteMessage(scope.row._id)">delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog v-model="dialogVisible" title="edit">
        <el-form :model="message">
          <el-form-item label="_id">
            <el-input disabled v-model="message._id" />
          </el-form-item>
          <el-form-item label="content">
            <el-input disabled v-model="message.content" />
          </el-form-item>
          <el-form-item label="creationDate">
            <el-input disabled v-model="message.creationDate" />
          </el-form-item>
          <el-form-item label="sender">
            <el-select disabled v-model="message.sender">
              <el-option
                v-for="item in userList"
                :key="item._id"
                :label="item.username"
                :value="item._id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="receiver">
            <el-select disabled v-model="message.receiver">
              <el-option
                v-for="item in userList"
                :key="item._id"
                :label="item.username"
                :value="item._id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="warning" @click="updateMessage">update</el-button>
            <el-button @click="hideEditDialog">cancel</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import {
  ElSelect,
  ElOption,
  ElButton,
  ElInput,
  ElMessage,
  ElTable,
  ElTableColumn,
  ElForm,
  ElDialog,
  ElFormItem,
} from 'element-plus'
import moment from 'moment'

import userApi from '../api/user.js'
import messageApi from '../api/message.js'

const DATE_FORMAT = import.meta.env.VITE_DATE_FORMAT

const message = ref({
  content: null,
  receiver: null,
})
const messageList = ref([])
const dialogVisible = ref(false)
const userList = ref([])

function addMessage() {
  messageApi.add(message.value).then((json) => {
    ElMessage.success(json.message)
    listMessage()
  })
}

function deleteMessage(_id) {
  messageApi.deleteById(_id).then((json) => {
    ElMessage.success(json.message)
    listMessage()
  })
}

function updateMessage() {
  ElMessage.error('unsupport update')
}

function listMessage() {
  return messageApi.list().then((json) => {
    ElMessage.success(json.message)
    messageList.value = json.data
    hideEditDialog()
  })
}

function listUser() {
  return userApi.list().then((json) => {
    userList.value = json.data
    ElMessage.success(json.message)
  })
}

function showEditDialog(row) {
  dialogVisible.value = true
  message.value = row
}

function hideEditDialog() {
  dialogVisible.value = false
}

const route = useRoute()

onMounted(async () => {
  await listUser()
  await listMessage()
  watch(
    () => route.params.id,
    (newId, oldId) => {
      if (newId) {
        messageApi.getById(newId).then((json) => {
          showEditDialog(json.data)
        })
      }
    },
    {
      immediate: true,
    },
  )
})
</script>

<style scoped></style>
