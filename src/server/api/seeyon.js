import axios from 'axios'
import process from 'process'
import seeyonRest from '../utils/seeyon-rest.js'

function loginSSO(ticket) {
  return axios({
    method: 'post',
    url: `${process.env.SEEYON_URL}/seeyon/login/sso`,
    params: {
      from: process.env.APP_NAME,
      ticket,
    },
  }).then((res) => res.data)
}

function thirdpartyController(ticket) {
  return axios({
    method: 'post',
    url: `${process.env.SEEYON_URL}/seeyon/thirdpartyController.do`,
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

export default {
  loginSSO,
  thirdpartyController,
  bindUser,
  sendMessage,
  sendPending,
  updatePendingState,
}
