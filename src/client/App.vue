<template>
  <el-container class="layout-container">
    <el-aside>
      <el-scrollbar>
        <el-menu :router="true" :default-active="$route.path">
          <el-menu-item index="/auth"> auth </el-menu-item>
          <el-menu-item index="/user"> user </el-menu-item>
          <el-menu-item index="/message"> message </el-menu-item>
          <el-menu-item index="/pending"> pending </el-menu-item>
          <el-menu-item index="/bpm"> bpm </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header>
        <el-row>
          <el-col :span="4"> {{ title }} </el-col>
          <el-col :span="16"> seeyon-app </el-col>
          <el-col :span="2">
            <el-tag>hi, {{ loginUser.username }}</el-tag>
          </el-col>
          <el-col :span="2">
            <el-tag @click="logout">logout</el-tag>
          </el-col>
        </el-row>
      </el-header>

      <el-main>
        <el-scrollbar>
          <router-view />
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, RouterView } from 'vue-router'
import {
  ElMenu,
  ElMenuItem,
  ElScrollbar,
  ElMain,
  ElAside,
  ElContainer,
  ElHeader,
  ElTag,
  ElRow,
  ElCol,
} from 'element-plus'
import { storeToRefs } from 'pinia'
import useAuthStore from './stores/auth.js'

const route = useRoute()
const title = computed(() => route.path?.slice(1))

const authStore = useAuthStore()
const { logout } = authStore
const { loginUser } = storeToRefs(authStore)
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  width: 200px;
  border-right: 1px solid #cfcfcf;
}

.el-menu {
  border-right: none;
}

.el-header {
  padding: 0;
}

.el-header .el-row {
  background-color: #409eff;
  color: white;
  text-align: center;
  line-height: 60px;
}

span:hover {
  cursor: pointer;
}
</style>
