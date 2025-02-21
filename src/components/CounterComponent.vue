<script setup lang="ts">
import { useCounterStore } from '../stores/counter'
import { storeToRefs } from 'pinia'

const counterStore = useCounterStore()
const { count, doubleCount } = storeToRefs(counterStore)
const { increment } = counterStore
</script>

<template>
  <div>
    <h1 @click="increment">Count is {{ count }} and doubleCount is {{ doubleCount }}.</h1>
  </div>
</template>

<style scoped>
@import '../assets/h1.css';
</style>
