import mongoose from 'mongoose'
import process from 'process'
import moment from 'moment'

const messageSchema = new mongoose.Schema({
  content: { type: String, required: true, trim: true, minLength: 1 },
  creationDate: {
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

messageSchema.methods.getOAMessage = function () {
  const target = encodeURIComponent(`/#/message/${this._id.toString()}`)
  return {
    thirdpartyRegisterCode: process.env.APP_CODE,
    thirdpartyMessageId: this._id.toString(),
    messageContent: this.content,
    creation_date: moment(this.creationDate).format(process.env.VITE_DATE_FORMAT),
    downloadUrl: null,
    thirdpartySenderId: this.sender.toString(),
    thirdpartyReceiverId: this.receiver.toString(),
    messageH5URL: null,
    messageURL: `${process.env.VITE_SERVER_URL}/api/sso/entry?target=${target}`,
    noneBindingSender: null,
    noneBindingReceiver: null,
  }
}

const Message = mongoose.model('Message', messageSchema)

export default Message
