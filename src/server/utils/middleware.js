import http from 'http'
import url from 'url'

export class AppError extends Error {
  constructor(code = 400, message = http.STATUS_CODES[code], cause = undefined) {
    super(message)
    this.code = code
    this.message = message
    this.cause = cause
  }
}

export function parseQuery(req, res, next) {
  for (const key in req.query) {
    const value = req.query[key]
    if (!isNaN(value)) {
      if (value.length > 15 && value.indexOf('.') === -1) {
        continue
      }
      const num = Number(value)
      if (String(num) === value) {
        req.query[key] = num
      }
    } else if (value === 'true' || value === 'false') {
      req.query[key] = value === 'true'
    }
  }
  next()
}

export function logger(req, res, next) {
  const startTime = Date.now()
  res.on('finish', async () => {
    const responseTime = Date.now() - startTime
    let level = 'info'
    if (req.method !== 'GET') {
      level = 'warning'
    }
    const appError = res.getHeader('AppError')
    if (appError) {
      level = 'error'
    }
    console.log({
      user: req.session.user?.id,
      time: startTime,
      operation: `${req.method} ${url.parse(req.originalUrl).pathname}`,
      level,
      data: {
        address: req.socket.remoteAddress,
        port: req.socket.remotePort,
        query: req.query,
        body: req.body,
        params: req.params,
        responseTime,
        code: res.statusCode,
        error: appError,
      },
    })
  })
  next()
}

export function authentication(req, res, next) {
  try {
    const user = req.session.user
    if (!user) {
      throw new AppError(401)
    }
    next()
  } catch (e) {
    next(e)
  }
}

export function errorHandler(err, req, res, next) {
  console.error(err)
  const code = Math.min(err.code, 500) || 500
  const message = err.message || http.STATUS_CODES[code] || 'Internal Server Error'
  const cause = err.cause || undefined
  res.setHeader('AppError', message)
  res.status(code).json({
    code,
    message,
    cause,
  })
}
