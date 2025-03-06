import express from 'express'
import seeyonApi from '../api/seeyon.js'
import Message from '../models/message.js'

const router = express.Router()

router.post('/', async (req, res, next) => {
  try {
    const user = req.session.user
    const message = new Message({
      ...req.body,
      sender: user._id,
    })
    await message.save()
    const result = await seeyonApi.sendMessage(message.getOAMessage())
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

router.delete('/:_id', async (req, res, next) => {
  try {
    await Message.deleteOne({ _id: req.params._id })
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
    const messageList = await Message.find().sort({ createDate: -1 })
    res.json({
      code: 200,
      message: 'ok',
      data: messageList,
    })
  } catch (e) {
    next(e)
  }
})

router.get('/:_id', async (req, res, next) => {
  try {
    const message = await Message.findById(req.params._id)
    res.json({
      code: 200,
      message: 'ok',
      data: message,
    })
  } catch (e) {
    next(e)
  }
})

export default router
