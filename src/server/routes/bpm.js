import express from 'express'
import seeyonApi from '../api/seeyon.js'

const router = express.Router()

router.post('/process/start', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessStart(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/process/stop', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessStop(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/process/repeal', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessRepeal(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.get('/process/diagramImg', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessDiagramImg(req.query.processId, req.query.workitemId)
    res.set({
      'Content-disposition': 'attachment;filename=workflowImg.png',
      'Content-Type': 'application/octet-stream',
      'Content-Length': data.length,
    })
    res.send(data)
  } catch (e) {
    next(e)
  }
})

router.post('/process/addNode', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessAddNode(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/process/deleteNode', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessDeleteNode(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/process/freeReplaceNode', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessFreeReplaceNode(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/process/replaceItem', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmProcessReplaceItem(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/workitem/finish', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmWorkitemFinish(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/workitem/takeback', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmWorkitemTakeBack(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/workitem/stepBack', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmWorkitemStepBack(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

router.post('/workitem/specifyback', async (req, res, next) => {
  try {
    const data = await seeyonApi.bpmWorkitemSpecifyback(req.body)
    console.log(data)
    res.json({
      code: 200,
      message: 'ok',
      data,
    })
  } catch (e) {
    next(e)
  }
})

export default router
