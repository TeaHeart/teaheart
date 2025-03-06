import mongoose from 'mongoose'

const userSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    index: true,
    unique: true,
    trim: true,
    minLength: 1,
  },
  password: {
    type: String,
    required: true,
    trim: true,
    minLength: 1,
  },
})

const User = mongoose.model('User', userSchema)

export default User
