import axios, { type AxiosRequestConfig } from 'axios'

const baseURL = 'http://127.0.0.1:8008/seeyon/rest'

const service = axios.create({
  baseURL,
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

interface Token {
  bindingUser: string
  userName: string
  id: string
}

let token: Token | null = null

export default async function seeyonRest(config: AxiosRequestConfig<object>) {
  if (token === null) {
    token = await axios({
      url: `${baseURL}/token`,
      method: 'post',
      data: {
        userName: 'rest',
        password: '4efdb259-63d5-4377-9426-97ca1cfc3398',
        loginUser: 'rest',
      },
    }).then((res) => res.data)
  }
  config.method ||= 'get'
  config.params ||= {}
  config.params.token = token?.id
  if (config.method.toLowerCase() === 'get') {
    config.params = config.data
  }
  return service(config)
}
