import express from 'express'
import seeyonApi from '../api/seeyon.js'
import Pending from '../models/pending.js'

const router = express.Router()

router.post('/', async (req, res, next) => {
  try {
    const user = req.session.user
    const pending = new Pending({
      ...req.body,
      sender: user._id,
    })
    await pending.save()
    const result = await seeyonApi.sendPending(pending.getOAPending(user.username))
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
    await Pending.deleteOne({ _id: req.params._id })
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
    await Pending.updateOne({ _id: req.params._id }, { state: 1, subState: req.body.subState })
    const pending = await Pending.findById(req.params._id)
    const result = await seeyonApi.updatePendingState(pending.getOAUpdatePendingState())
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

router.get('/', async (req, res, next) => {
  try {
    const pendingList = await Pending.find().sort({ createDate: -1 })
    res.json({
      code: 200,
      message: 'ok',
      data: pendingList,
    })
  } catch (e) {
    next(e)
  }
})

router.get('/:_id', async (req, res, next) => {
  try {
    const pending = await Pending.findById(req.params._id)
    res.json({
      code: 200,
      message: 'ok',
      data: pending,
    })
  } catch (e) {
    next(e)
  }
})

export default router
