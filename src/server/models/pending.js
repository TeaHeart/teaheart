import mongoose from 'mongoose'
import process from 'process'
import moment from 'moment'

const pendingSchema = new mongoose.Schema({
  title: { type: String, required: true, trim: true, minLength: 1 },
  classify: { type: String, trim: true, default: null },
  contentType: { type: String, trim: true, default: null },
  state: { type: Number, enum: [0, 1, 2], default: 0, required: true },
  subState: { type: Number, enum: [0, 1, 2, 4], default: null },
  createDate: {
    type: Date,
    required: true,
    default: Date.now,
  },
  sender: {
    type: mongoose.Types.ObjectId,
    required: true,
    ref: 'User',
  },
  receiver: {
    type: mongoose.Types.ObjectId,
    required: true,
    ref: 'User',
  },
})

pendingSchema.methods.getOAPending = function (username) {
  const target = encodeURIComponent(`/#/pending/${this._id.toString()}`)
  return {
    registerCode: process.env.VITE_APP_CODE,
    taskId: this._id.toString(),
    title: this.title,
    senderName: username,
    classify: this.classify,
    contentType: this.contentType,
    state: this.state,
    thirdSenderId: this.sender.toString(),
    thirdReceiverId: this.receiver.toString(),
    creationDate: moment(this.createDate).format('YYYY-MM-DD HH:mm:ss'),
    content: null, // app url
    h5url: null,
    url: `${process.env.VITE_SERVER_URL}/api/sso/entry?target=${target}`,
    noneBindingSender: null,
    noneBindingReceiver: null,
  }
}

pendingSchema.methods.getOAUpdatePendingState = function () {
  return {
    registerCode: process.env.VITE_APP_CODE,
    taskId: this._id.toString(),
    state: this.state,
    subState: this.subState,
  }
}

const Pending = mongoose.model('Pending', pendingSchema)

export default Pending
