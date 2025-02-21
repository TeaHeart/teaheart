import axios, { type AxiosRequestConfig } from 'axios'
import EnvConfig from '@/config'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: EnvConfig.baseUrl,
  timeout: 8000,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
})

service.interceptors.request.use((request) => {
  return request
})

service.interceptors.response.use((response) => {
  const { code, message, data } = response.data
  if (code === 200) {
    return data
  }
  if (code === 403) {
    const msg = message || '认证错误'
    ElMessage.error(msg)
    return Promise.reject(msg)
  }
  const msg = message || '请求错误'
  ElMessage.error(msg)
  return Promise.reject(msg)
})

function request(config: AxiosRequestConfig<object>) {
  config.method ||= 'get'
  if (config.method.toLowerCase() === 'get') {
    config.params = config.data
  }
  if (EnvConfig.env === 'prod') {
    service.defaults.baseURL = EnvConfig.baseUrl
  } else {
    service.defaults.baseURL = EnvConfig.mock ? EnvConfig.mockUrl : EnvConfig.baseUrl
  }
  return service(config)
}

// ['get', 'post', 'put', 'delete', 'patch'].forEach((item) => {
//   request[item] = (url, data, config) => {
//     return request({
//       url,
//       data,
//       method: item,
//       ...config,
//     })
//   }
// })

export default request
