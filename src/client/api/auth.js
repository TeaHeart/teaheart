import request from '../utils/request.js'

const prefix = '/auth'

function register(user) {
  return request({
    method: 'post',
    url: `${prefix}/register`,
    data: user,
  })
}

function login(user) {
  return request({
    method: 'post',
    url: `${prefix}/login`,
    data: user,
  })
}

function logout() {
  return request({
    method: 'post',
    url: `${prefix}/logout`,
  })
}

function me() {
  return request({
    method: 'get',
    url: `${prefix}/me`,
  })
}

export default { register, login, logout, me }
