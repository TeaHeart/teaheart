import axios from 'axios'
import process from 'process'
import seeyonRest from '../utils/seeyon-rest.js'

function loginSSO(ticket) {
  return axios({
    method: 'post',
    url: `${process.env.VITE_SEEYON_URL}/seeyon/login/sso`,
    params: {
      from: process.env.VITE_APP_NAME,
      ticket,
    },
  }).then((res) => res.data)
}

function thirdpartyController(ticket) {
  return axios({
    method: 'post',
    url: `${process.env.VITE_SEEYON_URL}/seeyon/thirdpartyController.do`,
    params: { ticket },
  }).then((res) => res.data)
}

function bindUser(bindInfo) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/thirdpartyUserMapper/binding/singleUser',
    data: bindInfo,
  })
}

function sendMessage(message) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/thirdpartyMessage/receive/singleMessage',
    data: message,
  })
}

function sendPending(pending) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/thirdpartyPending/receive',
    data: pending,
  })
}

function updatePendingState(pendingState) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/thirdpartyPending/updatePendingState',
    data: pendingState,
  })
}

function attachment(formData) {
  return seeyonRest({
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    method: 'post',
    url: '/seeyon/rest/attachment',
    data: formData,
  })
}

function bpmProcessStart(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/start',
    data,
  })
}

function bpmProcessStop(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/stop',
    data,
  })
}

function bpmProcessRepeal(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/repeal',
    data,
  })
}

function bpmProcessDiagramImg(processId, workItemId) {
  return seeyonRest({
    responseType: 'arraybuffer',
    method: 'get',
    url: `/seeyon/rest/bpm/process/diagramImg?isRunning=true&processId=${processId}&workitemId=${workItemId}&caseId=-1`,
  })
}

function bpmProcessAddNode(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/addNode',
    data,
  })
}

function bpmProcessDeleteNode(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/deleteNode',
    data,
  })
}

function bpmProcessFreeReplaceNode(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/freeReplaceNode',
    data,
  })
}

function bpmProcessReplaceItem(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/process/replaceItem',
    data,
  })
}

function bpmWorkitemFinish(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/workitem/finish',
    data,
  })
}

function bpmWorkitemTakeBack(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/workitem/takeback',
    data,
  })
}

function bpmWorkitemStepBack(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/workitem/stepBack',
    data,
  })
}

function bpmWorkitemSpecifyback(data) {
  return seeyonRest({
    method: 'post',
    url: '/seeyon/rest/bpm/workitem/specifyback',
    data,
  })
}

export default {
  loginSSO,
  thirdpartyController,
  bindUser,
  sendMessage,
  sendPending,
  updatePendingState,
  attachment,
  bpmProcessStart,
  bpmProcessStop,
  bpmProcessRepeal,
  bpmProcessDiagramImg,
  bpmProcessAddNode,
  bpmProcessDeleteNode,
  bpmProcessFreeReplaceNode,
  bpmProcessReplaceItem,
  bpmWorkitemFinish,
  bpmWorkitemTakeBack,
  bpmWorkitemStepBack,
  bpmWorkitemSpecifyback,
}
