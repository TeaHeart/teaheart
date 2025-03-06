<template>
  <div>
    <div v-if="loginUser?.username">
      <el-form>
        <el-form-item label="username">
          {{ loginUser.username }}
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="me">me</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="logout">logout</el-button>
        </el-form-item>
        <el-form-item label="oaLoginName">
          <el-input v-model="loginUser.oaLoginName" />
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="bindSeeyonForce">bindSeeyonForce</el-button>
        </el-form-item>
        <el-form-item label="need login oa first">
          <el-button type="primary" @click="bindSeeyon">bindSeeyon</el-button>
        </el-form-item>
        <el-form-item label="need bind oa first">
          <el-button type="primary" @click="gotoSeeyon">gotoSeeyon</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div v-else>
      <el-form :model="user">
        <el-form-item label="username">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="password">
          <el-input type="password" v-model="user.password"></el-input>
        </el-form-item>
        <el-button @click="register">register</el-button>
        <el-button type="primary" @click="login">login</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { ElButton, ElInput, ElForm, ElFormItem, ElMessage } from 'element-plus'

import ssoApi from '../api/sso.js'
import useAuthStore from '../stores/auth.js'

const authStore = useAuthStore()
const { register, login, logout, me } = authStore
const { user, loginUser } = storeToRefs(authStore)

function bindSeeyonForce() {
  ssoApi.bindSeeyonForce(loginUser.value.oaLoginName).then((json) => {
    ElMessage.success(json.message)
  })
}

function bindSeeyon() {
  ssoApi.bindSeeyon().then((json) => {
    ElMessage.success(json.message)
    window.open(json.data, '_blank', 'height=900,width=1600,top=100,left=100')
  })
}

function gotoSeeyon() {
  ssoApi.gotoSeeyon().then((json) => {
    ElMessage.success(json.message)
    window.open(json.data, '_blank', 'height=900,width=1600,top=100,left=100')
  })
}

onMounted(() => {
  me()
})
</script>

<style scoped></style>
