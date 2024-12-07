<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: "SaveAndroidKey",
  setup() {
    const androidKey = ref<string | null>(null);
    const errorMessage = ref<string | null>(null);
    const isLoading = ref<boolean>(false);

    // Funzione per salvare il codice nel localStorage
    const saveAndroidKey = () => {
      if (androidKey.value && androidKey.value.trim() !== "") {
        localStorage.setItem('androidKey', androidKey.value.trim());
        androidKey.value = '';  // Pulisce il campo dopo aver salvato il codice
        errorMessage.value = null;
      } else {
        errorMessage.value = "Inserisci un codice valido prima di salvare.";
      }
    };

    return {
      androidKey,
      errorMessage,
      isLoading,
      saveAndroidKey
    };
  }
});
</script>

<template>
  <div class="auth-container">
    <h2>Salva Codice Android</h2>
    <div class="key-entry">
      <textarea v-model="androidKey" placeholder="Incolla qui il codice" :disabled="isLoading"></textarea>
      <button @click="saveAndroidKey" class="save-key" :disabled="isLoading">Salva Codice</button>

      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </div>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  text-align: center;
  background-color: #2c2f38; /* Dark background */
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  color: #e0e0e0; /* Light text */
}

textarea {
  width: 100%;
  height: 80px;
  margin-top: 10px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #555; /* Darker border */
  border-radius: 4px;
  resize: none;
  background-color: #444; /* Dark background for textarea */
  color: #e0e0e0; /* Light text in textarea */
}

button {
  display: block;
  width: 100%;
  margin-top: 15px;
  padding: 10px;
  font-size: 16px;
  color: #fff;
  background-color: #007BFF;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.8; /* Slightly less opacity on hover */
}

.error {
  color: #ff4d4d; /* Red error text */
  margin-top: 10px;
  font-size: 14px;
}
</style>
