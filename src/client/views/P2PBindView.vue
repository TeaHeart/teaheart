<template>
  <div>
    <el-input v-model="userBindInfo.thirdUserId" placeholder="thirdUserId" />
    <el-input v-model="userBindInfo.thirdLoginName" placeholder="thirdLoginName" />
    <el-button type="primary" @click="p2pBind">点对点绑定</el-button>
  </div>
</template>

<script setup lang="ts">
import seeyonRest from '../utils/seeyonRest'
import { reactive } from 'vue'
import { ElButton, ElInput, ElMessage } from 'element-plus'

interface UserBindInfo {
  registerCode: string
  thirdUserId: string
  thirdLoginName: string
  thirdName: string
  thirdCode: string
  thirdMobile: string
  thirdEmail: string
}

const userBindInfo = reactive({
  // OA注册第三方应用的代码
  registerCode: '3001',
} as UserBindInfo)

function p2pBind() {
  seeyonRest({
    url: '/thirdpartyUserMapper/binding/singleUser',
    method: 'post',
    data: {
      ...userBindInfo,
    },
  }).then((data) => {
    ElMessage.info(JSON.stringify(data))
  })
}
</script>

<style scoped></style>
