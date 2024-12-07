<script lang="ts">
import { defineComponent, ref } from 'vue';
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

export default defineComponent({
  name: "MyJson",
  components: {
    VueJsonPretty,
  },
  setup() {
    const jsonData = ref({});

    const handleFileUpload = (event: Event) => {
      const file = (event.target as HTMLInputElement).files?.[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          try {
            jsonData.value = JSON.parse(reader.result as string);
          } catch (error) {
            console.error('Errore durante il parsing del file JSON:', error);
          }
        };
        reader.readAsText(file);
      }
    };

    return {
      jsonData,
      handleFileUpload,
    };
  },
});
</script>

<template>
  <div class="json-container">
    <input type="file" accept="application/json" @change="handleFileUpload" />
    <vue-json-pretty :data="jsonData" />
  </div>
</template>

<style scoped>
.json-container {
  margin: 20px auto;
  padding: 20px;
  max-width: 800px;
  background-color: #2d2d2d;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Courier New', Courier, monospace;
  font-size: 16px;
}

input[type="file"] {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}
</style>
