import express from 'express'
import multer from 'multer'
import seeyonApi from '../api/seeyon.js'

const router = express.Router()

const upload = multer({
  storage: multer.memoryStorage(),
  limits: { fileSize: 1024 * 1024 * 5 }, // 5MB
})

router.post('/upload', upload.single('file'), async (req, res, next) => {
  try {
    if (!req.file) {
      throw new Error('No file uploaded.')
    }
    const formData = new FormData()
    formData.append('file', new Blob([req.file.buffer]), req.file.originalname)
    const json = await seeyonApi.attachment(formData)
    console.log(json)
    res.send({
      code: 200,
      message: 'ok',
      data: json,
    })
  } catch (e) {
    next(e)
  }
})

export default router
