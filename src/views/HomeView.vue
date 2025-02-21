<script setup lang="ts">
import { ref, onUnmounted } from 'vue'

import CounterComponent from '../components/CounterComponent.vue'

const message = ref('Hello')

const intervalId = setInterval(() => {
  message.value = `Hello, now is ${new Date().toISOString()}.`
}, 1000)

onUnmounted(() => {
  clearInterval(intervalId)
})
</script>

<template>
  <div>
    <h1>{{ message }}</h1>
    <div>
      <CounterComponent />
    </div>
  </div>
</template>

<style scoped>
@import '../assets/h1.css';
</style>
