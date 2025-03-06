export function logger(req, res, next) {
  console.log(req.socket.remoteAddress, req.socket.remotePort, req.method, req.path, {
    query: req.query,
    body: req.body,
    params: req.params,
  })
  next()
}

export function authentication(req, res, next) {
  try {
    if (req.session.user) {
      next()
      return
    }
    throw new Error('please login first')
  } catch (e) {
    next(e)
  }
}

export function errorHandler(err, req, res, next) {
  console.error(err.stack)
  res.json({
    code: err.code || 500,
    message: err.message,
  })
}
