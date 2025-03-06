import express from 'express'
import User from '../models/user.js'

const router = express.Router()

router.post('/register', async (req, res, next) => {
  try {
    await new User(req.body).save()
    res.json({
      code: 200,
      message: 'ok',
    })
  } catch (e) {
    next(e)
  }
})

router.post('/login', async (req, res, next) => {
  try {
    const user = await User.findOne({
      username: req.body.username,
      password: req.body.password,
    })
    if (!user) {
      throw new Error('username or password is incorrect')
    }
    req.session.user = user
    res.json({
      code: 200,
      message: 'ok',
      data: user,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/logout', async (req, res, next) => {
  try {
    req.session.user = null
    res.json({
      code: 200,
      message: 'ok',
    })
  } catch (e) {
    next(e)
  }
})

router.get('/me', async (req, res, next) => {
  try {
    let user = req.session.user
    if (user) {
      user = await User.findById(user._id)
      req.session.user = user
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
