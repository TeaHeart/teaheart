import express from 'express'
import session from 'cookie-session'
import cors from 'cors'
import process from 'process'
import ViteExpress from 'vite-express'
import mongoose from 'mongoose'
import { parseQuery, logger, authentication, errorHandler } from './utils/middleware.js'
import authRouter from './routes/auth.js'
import userRouter from './routes/user.js'
import ssoRouter from './routes/sso.js'
import messageRouter from './routes/message.js'
import pendingRouter from './routes/pending.js'
import fileRouter from './routes/file.js'
import bpmRouter from './routes/bpm.js'

const app = express()

const db = await mongoose.connect(process.env.MONGO_URL)
if (!db) {
  throw new Error('failed to connect to MongoDB')
}

app.use(cors())
app.use(express.json())
app.use(
  session({
    secret: process.env.VITE_APP_NAME,
    maxAge: 24 * 60 * 60 * 1000,
  }),
)

app.use('/api', parseQuery)
app.use('/api', logger)
app.use('/api/auth', authRouter)
app.use('/api/sso', ssoRouter)
app.use('/api', authentication)
app.use('/api/user', userRouter)
app.use('/api/message', messageRouter)
app.use('/api/pending', pendingRouter)
app.use('/api/file', fileRouter)
app.use('/api/bpm', bpmRouter)
app.use('/api', errorHandler)

ViteExpress.listen(app, process.env.VITE_PORT, () => {
  console.log(`Server is running on ${process.env.VITE_SERVER_URL}...`)
})
