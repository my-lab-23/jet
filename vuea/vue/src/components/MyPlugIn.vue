<script lang="ts">
import { defineComponent, ref } from 'vue';
import { Plugins } from '@capacitor/core';

const { HelloWorld } = Plugins;

export default defineComponent({
  name: 'MyPlugIn',
  setup() {
    const inputValue = ref<string>('');
    const resultValue = ref<string>('');

    const callPlugin = async () => {
      try {
        const result = await HelloWorld.echo({ value: inputValue.value });
        resultValue.value = result.value;
      } catch (error) {
        resultValue.value = error instanceof Error ? error.message : 'Errore sconosciuto';
        console.error('Error calling HelloWorld plugin:', error);
      }
    };

    return {
      inputValue,
      resultValue,
      callPlugin,
    };
  },
});
</script>

<template>
  <div>
    <h1>Capacitor HelloWorld Plugin</h1>
    <input
        v-model="inputValue"
        type="text"
        placeholder="Enter a value"
    />
    <button @click="callPlugin">Call Plugin</button>
    <p v-if="resultValue">Plugin Result: {{ resultValue }}</p>
  </div>
</template>

<style scoped>
h1 {
  font-size: 1.5em;
  margin-bottom: 1em;
}

input {
  padding: 0.5em;
  margin-right: 0.5em;
  font-size: 1em;
}

button {
  padding: 0.5em 1em;
  font-size: 1em;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
}

button:hover {
  background-color: #0056b3;
}

p {
  margin-top: 1em;
  font-size: 1.2em;
  color: white;
}
</style>
