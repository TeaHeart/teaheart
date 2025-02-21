import 'reset-css'
import './assets/main.css'
import 'element-plus/dist/index.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'

import App from './App.vue'
import router from './router'

import EnvConfig from './config'
import request from './utils/request'
import storage from './utils/storage'

const app = createApp(App)

app.use(ElementPlus)
app.use(createPinia())
app.use(router)

app.config.globalProperties.$request = request
app.config.globalProperties.$storage = storage

app.mount('#app')

console.log(EnvConfig)
