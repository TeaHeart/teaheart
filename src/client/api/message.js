import request from '../utils/request.js'

const prefix = '/api/message'

function add(message) {
  return request({
    url: prefix,
    method: 'post',
    data: message,
  })
}

function deleteById(_id) {
  return request({
    url: `${prefix}/${_id}`,
    method: 'delete',
  })
}

function list() {
  return request({
    url: prefix,
    method: 'get',
  })
}
function getById(_id) {
  return request({
    url: `${prefix}/${_id}`,
    method: 'get',
  })
}

export default {
  add,
  deleteById,
  list,
  getById,
}
