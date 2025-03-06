import express from 'express'
import User from '../models/user.js'

const router = express.Router()

router.post('/', async (req, res, next) => {
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

router.delete('/:_id', async (req, res, next) => {
  try {
    await User.deleteOne({ _id: req.params._id })
    res.json({
      code: 200,
      message: 'ok',
    })
  } catch (e) {
    next(e)
  }
})

router.put('/:_id', async (req, res, next) => {
  try {
    await User.updateOne({ _id: req.params._id }, req.body)
    res.json({
      code: 200,
      message: 'ok',
    })
  } catch (e) {
    next(e)
  }
})

router.get('/', async (req, res, next) => {
  try {
    const userList = await User.find()
    res.json({
      code: 200,
      message: 'ok',
      data: userList,
    })
  } catch (e) {
    next(e)
  }
})

router.get('/:_id', async (req, res, next) => {
  try {
    const user = await User.findById(req.params._id)
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
