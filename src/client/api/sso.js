import request from '../utils/request.js'

const prefix = '/sso'

function gotoSeeyon() {
  return request({
    method: 'get',
    url: `${prefix}/goto/seeyon`,
  })
}

function bindSeeyonForce(oaLoginName) {
  return request({
    method: 'post',
    url: `${prefix}/bind/seeyon/force`,
    data: { oaLoginName },
  })
}

function bindSeeyon() {
  return request({
    method: 'get',
    url: `${prefix}/bind/seeyon`,
  })
}

export default {
  gotoSeeyon,
  bindSeeyonForce,
  bindSeeyon,
}
