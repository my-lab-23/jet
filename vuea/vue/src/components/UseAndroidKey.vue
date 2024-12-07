<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios'; // Importa Axios per gestire le richieste HTTP

export default defineComponent({
  name: "UseAndroidKey",
  setup() {
    const androidKey = ref<string | null>(null);
    const userEmail = ref<string | null>(null);
    const errorMessage = ref<string | null>(null);
    const errorCode = ref<string | null>(null);
    const isLoading = ref<boolean>(false);

    // Funzione per recuperare il codice dal localStorage
    const loadAndroidKey = () => {
      const savedKey = localStorage.getItem('androidKey');
      if (savedKey) {
        androidKey.value = savedKey;
      }
    };

    // Funzione per inviare il codice recuperato
    const sendAndroidKey = async () => {
      if (androidKey.value && androidKey.value.trim() !== "") {
        isLoading.value = true;
        errorMessage.value = null;
        errorCode.value = null;

        try {
          const response = await axios.post('https://n2342.it/ktest/api/auth/status',
              new URLSearchParams({
                androidKey: androidKey.value.trim()
              }).toString(),
              { headers: { "Content-Type": "application/x-www-form-urlencoded" } });

          if (response.status === 200) {
            userEmail.value = response.data.email;
          } else {
            throw new Error(response.data.message || "Errore sconosciuto");
          }
        } catch (error: any) {
          if (error.response && error.response.data) {
            errorMessage.value = error.response.data.message || "Errore durante l'autenticazione.";
            errorCode.value = error.response.status.toString();
          } else {
            errorMessage.value = "Errore di rete o server.";
          }
          console.error(errorMessage.value);
        } finally {
          isLoading.value = false;
        }
      } else {
        errorMessage.value = "Codice non valido.";
      }
    };

    onMounted(loadAndroidKey); // Carica il codice quando il componente è montato

    return {
      androidKey,
      userEmail,
      errorMessage,
      errorCode,
      isLoading,
      sendAndroidKey
    };
  }
});
</script>

<template>
  <div class="auth-container">
    <h2>Utilizza Codice Android</h2>

    <!-- Se il codice è stato recuperato -->
    <div v-if="androidKey" class="key-saved">
      <p>Codice recuperato: <span>{{ androidKey }}</span></p>
      <button @click="sendAndroidKey" class="save-key" :disabled="isLoading">Invia Codice</button>
    </div>

    <!-- Risultato dell'autenticazione -->
    <div v-if="userEmail" class="key-saved">
      <p>Autenticazione riuscita!</p>
      <p>Email associata: <span>{{ userEmail }}</span></p>
    </div>

    <!-- Errori -->
    <div v-if="errorMessage" class="error">
      <p>{{ errorMessage }}</p>
      <p v-if="errorCode" class="error-code">Codice errore: {{ errorCode }}</p>
    </div>

    <!-- Fase di caricamento -->
    <div v-if="isLoading" class="loading">Caricamento...</div>
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

.key-saved p, .key-entry p {
  margin: 10px 0;
  font-size: 16px;
  color: #e0e0e0; /* Light text */
}

.key-saved span {
  font-weight: bold;
  color: #007BFF; /* Keep the blue color */
}

.error {
  color: #ff4d4d; /* Red error text */
  margin-top: 10px;
  font-size: 14px;
}

.error-code {
  font-size: 12px;
  color: #ff6666; /* Lighter red for error code */
}

.loading {
  margin-top: 10px;
  font-size: 16px;
  color: #007BFF; /* Blue loading text */
}
</style>
