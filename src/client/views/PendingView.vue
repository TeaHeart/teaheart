<template>
  <div>
    <div>
      <el-form :model="pending">
        <el-form-item label="title">
          <el-input v-model="pending.title" />
        </el-form-item>
        <el-form-item label="classify">
          <el-input v-model="pending.classify" />
        </el-form-item>
        <el-form-item label="contentType">
          <el-input v-model="pending.contentType" />
        </el-form-item>
        <el-form-item label="receiver">
          <el-select v-model="pending.receiver">
            <el-option
              v-for="item in userList"
              :key="item._id"
              :label="item.username"
              :value="item._id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addPending">addPending</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-table :data="pendingList">
        <el-table-column label="_id">
          <template #default="scope">
            {{ scope.row._id }}
          </template>
        </el-table-column>
        <el-table-column label="title">
          <template #default="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="state">
          <template #default="scope">
            {{ scope.row.state }}
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
            <el-button type="danger" @click="deletePending(scope.row._id)">delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog v-model="dialogVisible" title="edit">
        <el-form :model="pending">
          <el-form-item label="_id">
            <el-input disabled v-model="pending._id" />
          </el-form-item>
          <el-form-item label="title">
            <el-input disabled v-model="pending.title" />
          </el-form-item>
          <el-form-item label="classify">
            <el-input disabled v-model="pending.classify" />
          </el-form-item>
          <el-form-item label="contentType">
            <el-input disabled v-model="pending.contentType" />
          </el-form-item>
          <el-form-item label="state">
            <el-input disabled v-model="pending.state" />
          </el-form-item>
          <el-form-item label="creationDate">
            <el-input disabled v-model="pending.creationDate" />
          </el-form-item>
          <el-form-item label="sender">
            <el-select disabled v-model="pending.sender">
              <el-option
                v-for="item in userList"
                :key="item._id"
                :label="item.username"
                :value="item._id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="receiver">
            <el-select disabled v-model="pending.receiver">
              <el-option
                v-for="item in userList"
                :key="item._id"
                :label="item.username"
                :value="item._id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="subState">
            <el-select v-model="pending.subState">
              <el-option
                v-for="item in subStateOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>

        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="updatePendingState">update</el-button>
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

import pendingApi from '../api/pending.js'
import userApi from '../api/user.js'

const DATE_FORMAT = import.meta.env.VITE_DATE_FORMAT

const pending = ref({
  title: null,
  classify: null,
  contentType: null,
  receiver: null,
})
const pendingList = ref([])
const dialogVisible = ref(false)
const userList = ref([])

const subStateOptions = [
  {
    value: 0,
    label: 'Agreed',
  },
  {
    value: 1,
    label: 'Disagree',
  },
  {
    value: 2,
    label: 'Cannel',
  },
  {
    value: 3,
    label: 'Reject',
  },
]

function addPending() {
  pendingApi.add(pending.value).then((json) => {
    ElMessage.success(json.message)
    listPending()
  })
}

function deletePending(_id) {
  pendingApi.deleteById(_id).then((json) => {
    ElMessage.success(json.message)
    listPending()
  })
}

function updatePendingState() {
  pendingApi.updateState(pending.value._id, pending.value.subState).then((json) => {
    ElMessage.success(json.message)
    listPending()
  })
}

function listPending() {
  return pendingApi.list().then((json) => {
    ElMessage.success(json.message)
    pendingList.value = json.data
    hideEditDialog()
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
  pending.value = row
}

function hideEditDialog() {
  dialogVisible.value = false
}

const route = useRoute()

onMounted(async () => {
  await listUser()
  await listPending()
  watch(
    () => route.params.id,
    (newId, oldId) => {
      if (newId) {
        pendingApi.getById(newId).then((json) => {
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
