import express from 'express'
import seeyonApi from '../api/seeyon.js'
import User from '../models/user.js'
import crypto from 'crypto'
import process from 'process'

const router = express.Router()

/**
 * OA（致远） 单点登录 app（本应用） 流程：
 * 1. 客户端已经登录了 OA 系统
 * 2. 客户端发送一个想登录 app 请求到 OA 的服务器
 * 3. OA 生成 ticket，并将 ticket 附加到接口URL（CIP 应用集成配置）后，并请求该接口
 *    接口由 app 开发
 *    如：http://{app-server}/api/sso/handshake/seeyon?ticket={OA生成的ticket}
 * 4. OA 的请求到此接口后，拿出 ticket，并向 OA 服务器校验 ticket 的有效性
 *    此接口已经由致远提供，请求该接口后会返回 ticket 对应的 OA 系统用户的登录名
 *    如：http://{seeyon-server}/seeyon/thirdpartyController.do?ticket={OA生成的ticket}
 * 5. 获取到 OA 用户名后，查询用户信息，若存在（app 已经绑定 OA）则标记该 ticket 可用并返回 SSOOK，否则返回 SSOLoginError，
 * 6. OA 服务器得到 SSOOK 的结果后，会向客户端发出重定向（CIP 应用集成配置），并携带 ticket
 * 7. 客户端携带 ticket 直接请求接口并登录
 *    如：http://{app-server}/api/sso/entry?ticket={OA生成的ticket}&target={登录后的目标页面}
 */

const validTicket = {} // 有效 ticket => userInfo {user, logout()}

// 客户端请携带 ticket 尝试登录
router.get('/entry', async (req, res, next) => {
  try {
    const ticket = req.query.v5ticket || req.query.ticket
    const oaLoginName = await seeyonApi.thirdpartyController(ticket)
    console.log('oaLoginName:', oaLoginName)
    if (!oaLoginName) {
      throw new Error('ticket is invalid')
    }
    const user = await User.findOne({ oaLoginName: oaLoginName })
    if (!user) {
      throw new Error('oaLoginName is invalid')
    }
    req.session.user = user
    validTicket[ticket] = {
      user,
      logout() {
        req.session.user = null
        delete validTicket[ticket]
      },
    }
    const target = decodeURIComponent(req.query.target) || '/'
    res.redirect(target)
  } catch (e) {
    next(e)
  }
})

// 由 OA 登录的用户退出后自动调用
router.get('/logout', async (req, res, next) => {
  try {
    const ticket = req.query.v5ticket || req.query.ticket
    const userInfo = validTicket[ticket]
    userInfo?.logout()
    res.setHeader('SSOLogout', 'SSOLogout')
    res.json({
      code: 200,
      message: 'ok',
      data: userInfo,
    })
  } catch (e) {
    next(e)
  }
})

// OA 携带 ticket 进行 SSO 握手
router.get('/handshake/seeyon', async (req, res, next) => {
  try {
    const ticket = req.query.v5ticket || req.query.ticket
    const oaLoginName = await seeyonApi.thirdpartyController(ticket)
    if (!oaLoginName) {
      throw new Error('ticket is invalid')
    }
    const user = await User.findOne({ oaLoginName: oaLoginName })
    if (!user) {
      throw new Error('oaLoginName is invalid')
    }
    res.setHeader('SSOOK', 'SSOOK')
    res.json({
      code: 200,
      message: 'ok',
      data: user,
    })
  } catch (e) {
    res.setHeader('SSOLoginError', 'SSOLoginError')
    next(e)
  }
})

/**
 * app（本应用）单点登录 OA（致远）流程：
 * 1. 客户端已经登录了 app
 * 2. 客户端发送一个想登录 OA 请求到 app 的服务器
 * 3. app 生成 ticket，并将 ticket 附加到 OA 登录接口 URL 后，并请求该接口
 *    接口已经由致远提供
 *    如：http://{seeyon-server}/seeyon/login/sso?from=${OA中握手类注册的名称}&ticket={app生成的ticket}
 * 4. OA 接收到请求后，根据from，转发到对应注册的 handshake 实现类中进行握手
 *    如：public class AppSSOLoginHandshakeImpl extends SSOLoginHandshakeAbstract {...}
 * 5. AppSSOLoginHandshakeImpl::handshake() 会调用 app 的接口获取 ticket 对应的 oaLoginName，然后绑定 ticket 和 oaLoginName 的关系
 *    如：http://{app-server}/api/sso/checkTicket?ticket={app生成的ticket}
 * 6. app 服务器会向客户端发出重定向，并携带 ticket
 * 7. 客户端携带 ticket 直接请求接口并登录
 *    如：http://{seeyon-server}/seeyon/login/sso?from=${OA中握手类注册的名称}&ticket={app生成的ticket}
 */

const ticketMap = {} // ticket => user

// 给 OA 校验 ticket 的接口
router.get('/checkTicket', async (req, res, next) => {
  try {
    const ticket = req.query.ticket
    res.send(ticketMap[ticket]?.oaLoginName)
  } catch (e) {
    next(e)
  }
})

// 客户端请求去 OA 系统
router.get('/goto/seeyon', async (req, res, next) => {
  try {
    const user = req.session.user
    if (!user) {
      throw new Error('please login first')
    }
    const ticket = crypto.randomUUID()
    ticketMap[ticket] = user
    const result = await seeyonApi.loginSSO(ticket)
    if (!result) {
      throw new Error('seeyon login failed')
    }
    res.json({
      code: 200,
      message: 'ok',
      data: `${process.env.SEEYON_URL}/seeyon/main.do?method=login&ticket=${ticket}&ssoFrom=${process.env.APP_NAME}`,
    })
  } catch (e) {
    next(e)
  }
})

/**
 * app（本应用）绑定 OA（致远）流程：
 * 1. 客户端已经登录了 app 和 OA
 * 2. 客户端发送一个想绑定 OA 请求到 app 的服务器
 * 3. app 生成 ticket，并将 ticket 发送给客户端
 * 4. 客户端使用 ticket 去 oa 进行绑定
 *    此接口自己实现，
 *    如：http://{seeyon-server}/seeyon/app.do?method=bind&ticket=${app生成的ticket}
 *    public class AppController extends BaseController {...}
 * 5. AppController::bind() 会发送 ticket 和 oaLoginName（OA 当前登录的用户名）发送给 app 请求绑定
 *    如：http://localhost:3000/api/sso/bind/seeyon/checkTicket
 * 6. app 拿到 ticket 和 oaLoginName，校验 ticket的有效性后
 *    将 app 当前的用户和 OA 登录的用户进行绑定并存入数据库
 *    如：{ _id: app用户id, username: app用户名, oaLoginName: oa用户名 }
 *    并请求 OA 接口进行绑定用户
 *    如 { registerCode: appCode, thirdUserId: "67d003c6cf8f219f430ad0ca", thirdLoginName: oa用户名, }
 * 7. app 返回绑定成功信息给 OA，OA返回绑定成功信息给客户端
 */

const bindTicketMap = {} // ticket => user

// 强制绑定，用于调试
router.post('/bind/seeyon/force', async (req, res, next) => {
  try {
    let user = req.session.user
    await User.updateOne({ _id: user._id }, { oaLoginName: req.body.oaLoginName })
    user = await User.findById(user._id)
    const result = await seeyonApi.bindUser(user.getOABindInfo())
    if (!result?.success) {
      throw new Error(JSON.stringify(result.errorMsgs))
    }
    res.json({
      code: 200,
      message: 'ok',
    })
  } catch (e) {
    next(e)
  }
})

// 客户端请求绑定 OA 系统
router.get('/bind/seeyon', async (req, res, next) => {
  try {
    const user = req.session.user
    if (!user) {
      throw new Error('please login first')
    }
    const ticket = crypto.randomUUID()
    bindTicketMap[ticket] = user
    res.json({
      code: 200,
      message: 'ok',
      data: `${process.env.SEEYON_URL}/seeyon/app.do?method=bind&ticket=${ticket}`,
    })
  } catch (e) {
    next(e)
  }
})

// 校验 ticket 并绑定 OA 系统
router.get('/bind/seeyon/checkTicket', async (req, res, next) => {
  try {
    const ticket = req.query.ticket
    const oaLoginName = req.query.oaLoginName
    let user = bindTicketMap[ticket]
    if (!user || !oaLoginName) {
      throw new Error('invalid ticket or oaLoginName')
    }
    await User.updateOne({ _id: user._id }, { oaLoginName })
    user = await User.findById(user._id)
    const result = await seeyonApi.bindUser(user.getOABindInfo())
    if (!result?.success) {
      throw new Error(JSON.stringify(result.errorMsgs))
    }
    res.json({
      code: 200,
      message: 'ok',
      data: user,
    })
  } catch (e) {
    next(e)
  }
})

export default router
