export const storage = useStorage(import.meta.env.VITE_APP_NAME)

export default function useStorage(namespace) {
  return {
    getStorage() {
      return JSON.parse(window.localStorage.getItem(namespace) || '{}')
    },
    setItem(key, value) {
      const storage = this.getStorage()
      storage[key] = value
      window.localStorage.setItem(namespace, JSON.stringify(storage))
    },
    getItem(key) {
      return this.getStorage()[key]
    },
    removeItem(key) {
      const storage = this.getStorage()
      delete storage[key]
      window.localStorage.setItem(namespace, JSON.stringify(storage))
    },
    clear() {
      window.localStorage.removeItem(namespace)
    },
  }
}
