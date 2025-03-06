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
import { ElButton, ElInput, ElForm, ElFormItem } from 'element-plus'

import useAuthStore from '../stores/auth.js'

const authStore = useAuthStore()
const { register, login, logout, me } = authStore
const { user, loginUser } = storeToRefs(authStore)

onMounted(() => {
  me()
})
</script>

<style scoped></style>
