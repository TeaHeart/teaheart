<template>
  <div>
    <div>
      <el-form :model="user">
        <el-form-item label="username">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="password">
          <el-input type="password" v-model="user.password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addUser">add</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-table :data="userList">
        <el-table-column label="_id">
          <template #default="scope">
            {{ scope.row._id }}
          </template>
        </el-table-column>
        <el-table-column label="username">
          <template #default="scope">
            {{ scope.row.username }}
          </template>
        </el-table-column>
        <el-table-column label="operation">
          <template #default="scope">
            <el-button type="warning" @click="showEditDialog(scope.row)">edit</el-button>
            <el-button type="danger" @click="deleteUser(scope.row._id)">delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog v-model="dialogVisible" title="edit">
        <el-form :model="user">
          <el-form-item label="_id">
            <el-input disabled v-model="user._id" />
          </el-form-item>
          <el-form-item label="username">
            <el-input v-model="user.username" />
          </el-form-item>
          <el-form-item label="password">
            <el-input type="password" v-model="user.password" />
          </el-form-item>
          <el-form-item label="oaLoginName">
            <el-input disabled v-model="user.oaLoginName" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="warning" @click="updateUser">update</el-button>
            <el-button @click="hideEditDialog()">cancel</el-button>
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
  ElButton,
  ElInput,
  ElMessage,
  ElTable,
  ElTableColumn,
  ElForm,
  ElDialog,
  ElFormItem,
} from 'element-plus'

import userApi from '../api/user.js'

const user = ref({
  _id: null,
  username: null,
  password: null,
  oaLoginName: null,
})
const userList = ref([])
const dialogVisible = ref(false)

function addUser() {
  userApi.add(user.value).then((json) => {
    ElMessage.success(json.message)
    listUser()
  })
}

function deleteUser(_id) {
  userApi.deleteById(_id).then((json) => {
    ElMessage.success(json.message)
    listUser()
  })
}

function updateUser() {
  userApi.updateById(user.value._id, user.value).then((json) => {
    ElMessage.success(json.message)
    listUser()
  })
}

function listUser() {
  return userApi.list().then((json) => {
    ElMessage.success(json.message)
    userList.value = json.data
    hideEditDialog()
  })
}

function showEditDialog(row) {
  dialogVisible.value = true
  user.value = row
}

function hideEditDialog() {
  dialogVisible.value = false
}

const route = useRoute()

onMounted(async () => {
  await listUser()
  watch(
    () => route.params.id,
    (newId, oldId) => {
      if (newId) {
        userApi.getById(newId).then((json) => {
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
