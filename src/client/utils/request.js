import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: import.meta.env.VITE_SERVER_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
})

service.interceptors.request.use((request) => {
  request.method ||= 'get'
  if (request.method.toLowerCase() === 'get') {
    request.params = request.data
  }
  console.log(request.method, request.url, {
    params: request.params,
    data: request.data,
  })
  return request
})

service.interceptors.response.use((response) => {
  const json = response.data
  if (json.code !== 200) {
    ElMessage.error(json.message)
    return Promise.reject(new Error(json.message))
  }
  return json
})

export default service
