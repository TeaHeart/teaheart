import EnvConfig from '@/config'

export default {
  getStorage() {
    return JSON.parse(window.localStorage.getItem(EnvConfig.namespace) || '{}')
  },
  setItem(key: string, value: unknown) {
    const storage = this.getStorage()
    storage[key] = value
    window.localStorage.setItem(EnvConfig.namespace, JSON.stringify(storage))
  },
  getItem(key: string) {
    return this.getStorage()[key]
  },
  removeItem(key: string) {
    const storage = this.getStorage()
    delete storage[key]
    window.localStorage.setItem(EnvConfig.namespace, JSON.stringify(storage))
  },
  clear() {
    window.localStorage.removeItem(EnvConfig.namespace)
  },
}
