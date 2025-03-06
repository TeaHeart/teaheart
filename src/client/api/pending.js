import request from '../utils/request.js'

const prefix = '/api/pending'

function add(pending) {
  return request({
    method: 'post',
    url: prefix,
    data: pending,
  })
}

function deleteById(_id) {
  return request({
    method: 'delete',
    url: `${prefix}/${_id}`,
  })
}

function updateState(_id, subState) {
  return request({
    method: 'put',
    url: `${prefix}/${_id}`,
    data: { subState },
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
  updateState,
  list,
  getById,
}
