import axios, { type AxiosRequestConfig } from 'axios'
import EnvConfig from '../config'

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
  return response.data
})

export default function request(config: AxiosRequestConfig<object>) {
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
