import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: `${import.meta.env.VITE_SERVER_URL}/api`,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
})

service.interceptors.request.use(
  (request) => {
    request.method ||= 'get'
    if (request.method.toLowerCase() === 'get') {
      request.params = request.data
    }
    console.log(request.method, request.url, {
      params: request.params,
      data: request.data,
    })
    return request
  },
  (error) => {
    console.error(error)
    ElMessage.error(error.message)
    return Promise.reject(error)
  },
)

service.interceptors.response.use(
  (response) => {
    const data = response.data
    console.log(data)
    if (data.code && data.code !== 200) {
      ElMessage.error(data.message)
      return Promise.reject(new Error(data.message))
    }
    return data
  },
  (error) => {
    console.error(error)
    const json = error.response.data
    ElMessage.error(json.message)
    return Promise.reject(error)
  },
)

export default service
