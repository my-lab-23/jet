<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps<{
  message: string;
}>();
</script>

<template>
  <div class="placeholder">{{ props.message }}</div>
</template>

<style scoped>
.placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #121212;
  color: #fff;
  font-size: 2rem;
  animation: blink 1s infinite;
}

@keyframes blink {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
}
</style>
