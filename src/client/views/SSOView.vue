<template>
  <div>
    <el-select v-model="loginName" placeholder="user" size="large" style="width: 240px">
      <el-option
        v-for="(item, index) in userList"
        :key="index"
        :label="JSON.stringify(item)"
        :value="item.loginName"
      />
    </el-select>
    <el-button type="primary" @click="gotoOA">登录到OA</el-button>
  </div>
</template>

<script setup lang="ts">
import seeyonRest from '@/utils/seeyonRest'
import { ref } from 'vue'
import { ElButton, ElInput } from 'element-plus'
import { it } from 'element-plus/es/locales.mjs'

// OA 中的用户名
const loginName = ref('')

const userList = [
  {
    localUser: 'u2',
    loginName: 'user2',
  },
  {
    localUser: 'u3',
    loginName: 'user3',
  },
]

function gotoOA() {
  // 调用 OA 登录接口
  // 这里是客户端直接向OA发送请求的示例，实际应用中应向这个应用的服务端发送请求
}
</script>

<style scoped></style>
