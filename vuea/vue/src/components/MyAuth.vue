<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: "MyAuth",
  setup() {
    const email = ref<string | null>(null);
    const authCode = ref<string | null>(null); // Per mostrare il codice
    const isAuthenticated = ref<boolean>(false);
    const isCodeCopied = ref<boolean>(false);

    // Funzione per verificare l'autenticazione
    const checkAuth = async () => {
      try {
        const response = await axios.get('https://n2342.it/ktest/api/auth/status'); // Sostituisci con il tuo endpoint
        if (response.status === 200 && response.data.email) {
          email.value = response.data.email; // Salva l'email
          authCode.value = response.data.androidKey || "Nessun codice disponibile"; // Salva il codice
          isAuthenticated.value = true;
        }
      } catch (error) {
        console.error("Errore durante la verifica dell'autenticazione:", error);
      }
    };

    // Funzione per copiare il codice negli appunti
    const copyToClipboard = () => {
      if (authCode.value) {
        navigator.clipboard.writeText(authCode.value).then(() => {
          isCodeCopied.value = true;
          setTimeout(() => isCodeCopied.value = false, 2000); // Mostra "Copiato" per 2 secondi
        }).catch(err => console.error("Errore durante la copia del codice:", err));
      }
    };

    // Esegui il check all'avvio del componente
    onMounted(() => {
      checkAuth();
    });

    return { email, authCode, isAuthenticated, isCodeCopied, copyToClipboard };
  }
});
</script>

<template>
  <div class="auth-container">
    <div v-if="isAuthenticated" class="authenticated">
      <h2>Benvenuto, utente autenticato!</h2>
      <p class="email">Email: <span>{{ email }}</span></p>
      <div class="code-container">
        <p>Codice di autenticazione:</p>
        <div class="code">
          <span>{{ authCode }}</span>
          <button @click="copyToClipboard" class="copy-btn">Copia</button>
        </div>
        <p v-if="isCodeCopied" class="copied-message">Codice copiato!</p>
      </div>
    </div>
    <div v-else class="unauthenticated">
      <h2>Non sei autenticato</h2>
      <p>Per favore, accedi per visualizzare il contenuto.</p>
    </div>
  </div>
  <a href="https://www.dropbox.com/scl/fi/f3g16i5t3vty3oeogzhu4/app-debug.apk?rlkey=6c1phwl9zdw3jdaymnes9ndz5&st=2e8reibs&dl=0">Apk</a>
</template>

<style scoped>
.auth-container {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 8px;
  background-color: #2c2f38; /* Dark background */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  text-align: center;
  color: #e0e0e0; /* Light text color */
}

h2 {
  font-size: 24px;
  color: #ffffff; /* White text */
}

.email {
  font-size: 18px;
  margin-top: 10px;
  color: #cccccc; /* Light grey for email */
}

span {
  font-weight: bold;
  color: #007BFF;
}

.authenticated {
  background-color: lightseagreen;
  padding: 20px;
  border-radius: 8px;
}

.unauthenticated {
  background-color: #9e1c24; /* Dark red */
  padding: 20px;
  border-radius: 8px;
}

.unauthenticated p {
  font-size: 16px;
  color: #f8d7da; /* Lighter text color */
}

.authenticated h2, .unauthenticated h2 {
  margin-bottom: 15px;
  color: #ffffff; /* White text */
}

.email {
  font-size: 18px;
  margin-top: 10px;
  color: #FFA500; /* Keep orange color */
}

.login {
  font-size: 18px;
  margin-top: 10px;
  color: #f2f2f2; /* Light color for login text */
}

.code-container {
  margin-top: 20px;
}

.code {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.copy-btn {
  padding: 5px 10px;
  font-size: 14px;
  color: white;
  background-color: #007BFF;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.copy-btn:hover {
  background-color: #0056b3;
}

.copied-message {
  margin-top: 10px;
  font-size: 14px;
  color: #FFA500; /* Keep orange color */
}
</style>
