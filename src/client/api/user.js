import request from '../utils/request.js'

const prefix = '/api/user'

function add(user) {
  return request({
    method: 'post',
    url: prefix,
    data: user,
  })
}

function deleteById(_id) {
  return request({
    method: 'delete',
    url: `${prefix}/${_id}`,
  })
}

function updateById(_id, user) {
  return request({
    method: 'put',
    url: `${prefix}/${_id}`,
    data: user,
  })
}

function list() {
  return request({
    method: 'get',
    url: prefix,
  })
}

function getById(_id) {
  return request({
    method: 'get',
    url: `${prefix}/${_id}`,
  })
}

export default {
  add,
  deleteById,
  updateById,
  list,
  getById,
}
