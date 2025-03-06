import express from 'express'
import session from 'express-session'
import cors from 'cors'
import process from 'process'
import ViteExpress from 'vite-express'
import mongoose from 'mongoose'

import { logger, authentication, errorHandler } from './utils/middleware.js'
import authRouter from './routes/auth.js'
import userRouter from './routes/user.js'

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
  }),
)

app.use('/api', logger)
app.use('/api/auth', authRouter)
app.use('/api', authentication)
app.use('/api/user', userRouter)
app.use(errorHandler)

ViteExpress.listen(app, process.env.VITE_PORT, () => {
  console.log(`Server is running on ${process.env.VITE_SERVER_URL}...`)
})
