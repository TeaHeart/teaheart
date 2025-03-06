import { ref, reactive } from 'vue'
import { defineStore } from 'pinia'
import { ElMessage } from 'element-plus'

import authApi from '../api/auth.js'

export default defineStore('auth', () => {
  const user = reactive({})
  const loginUser = ref({})

  function register() {
    authApi.register(user).then((json) => {
      ElMessage.success(json.message)
    })
  }

  function login() {
    authApi.login(user).then((json) => {
      ElMessage.success(json.message)
      me()
    })
  }

  function logout() {
    authApi.logout().then((json) => {
      ElMessage.success(json.message)
      me()
    })
  }

  function me() {
    authApi.me().then((json) => {
      ElMessage.success(json.message)
      loginUser.value = json.data || {}
    })
  }

  return {
    user,
    loginUser,
    register,
    login,
    logout,
    me,
  }
})
