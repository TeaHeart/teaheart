import mongoose from 'mongoose'
import process from 'process'

const userSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    index: true,
    unique: true,
    trim: true,
    minLength: 1,
  },
  password: { type: String, required: true, trim: true, minLength: 1 },
  oaLoginName: { type: String, index: true, trim: true, default: null },
})

userSchema.methods.getOABindInfo = function () {
  return {
    registerCode: process.env.APP_CODE,
    thirdUserId: this._id.toString(),
    thirdLoginName: this.oaLoginName,
    thirdName: null,
    thirdCode: null,
    thirdMobile: null,
    thirdEmail: null,
    param0: null,
    param1: null,
  }
}

const User = mongoose.model('User', userSchema)

export default User
