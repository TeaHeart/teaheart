import request from '../utils/request.js'

const prefix = '/bpm'

function processStart(data) {
  return request({
    url: `${prefix}/process/start`,
    method: 'post',
    data,
  })
}

function processStop(data) {
  return request({
    url: `${prefix}/process/stop`,
    method: 'post',
    data,
  })
}

function processRepeal(data) {
  return request({
    url: `${prefix}/process/repeal`,
    method: 'post',
    data,
  })
}

function processDiagramImg(data) {
  return request({
    url: `${prefix}/process/diagramImg`,
    method: 'get',
    data,
    responseType: 'blob',
  })
}

function processAddNode(data) {
  return request({
    url: `${prefix}/process/addNode`,
    method: 'post',
    data,
  })
}

function processDeleteNode(data) {
  return request({
    url: `${prefix}/process/deleteNode`,
    method: 'post',
    data,
  })
}

function processFreeReplaceNode(data) {
  return request({
    url: `${prefix}/process/freeReplaceNode`,
    method: 'post',
    data,
  })
}

function processReplaceItem(data) {
  return request({
    url: `${prefix}/process/replaceItem`,
    method: 'post',
    data,
  })
}

function workitemFinish(data) {
  return request({
    url: `${prefix}/workitem/finish`,
    method: 'post',
    data,
  })
}

function workitemTakeBack(data) {
  return request({
    url: `${prefix}/workitem/takeback`,
    method: 'post',
    data,
  })
}

function workitemStepBack(data) {
  return request({
    url: `${prefix}/workitem/stepBack`,
    method: 'post',
    data,
  })
}

function workitemSpecifyback(data) {
  return request({
    url: `${prefix}/workitem/specifyback`,
    method: 'post',
    data,
  })
}

export default {
  processStart,
  processStop,
  processRepeal,
  processDiagramImg,
  processAddNode,
  processDeleteNode,
  processFreeReplaceNode,
  processReplaceItem,
  workitemFinish,
  workitemTakeBack,
  workitemStepBack,
  workitemSpecifyback,
}
