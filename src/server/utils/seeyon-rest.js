import axios from 'axios'
import process from 'process'

const service = axios.create({
  baseURL: process.env.VITE_SEEYON_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
})

function getToken() {
  return axios({
    method: 'post',
    url: `${process.env.VITE_SEEYON_URL}/seeyon/rest/token`,
    data: {
      userName: process.env.VITE_SEEYON_REST_USERNAME,
      password: process.env.VITE_SEEYON_REST_PASSWORD,
      loginName: process.env.VITE_SEEYON_REST_LOGINNAME,
    },
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  }).then((res) => res.data)
}

service.interceptors.request.use(async (request) => {
  request.method ||= 'get'
  if (request.method.toLowerCase() === 'get') {
    request.params = request.data
  }
  const token = await getToken()
  request.params ||= {}
  request.params.token = token.id
  console.log(request.method, request.url, {
    params: request.params,
    data: request.data,
  })
  return request
})

service.interceptors.response.use((response) => {
  return response.data
})

export default service
