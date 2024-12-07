<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios, { AxiosError } from 'axios';
import VueJsonPretty from 'vue-json-pretty';
import "vue-json-pretty/lib/styles.css";
import VueSpeedometer from 'vue-speedometer';

export default defineComponent({
  name: "SearchPosts",
  components: {
    VueJsonPretty,
    VueSpeedometer,
  },
  setup() {
    const jsonData = ref([{ text: "Esempio di testo 1" }, { text: "Esempio di testo 2" }]);
    const responseJson = ref({});
    const query = ref('');
    const limit = ref(12);
    const lang = ref('it');
    const errorMessage = ref('');
    const isLoading = ref(false);
    const showJson = ref(false);
    const showResponseJson = ref(false);
    const speedometerValue = ref(0); // Valore dello speedometer

    // Funzione per gestire la ricerca
    const handleSearch = async () => {
      errorMessage.value = '';
      isLoading.value = true;
      try {
        // Prima richiesta
        const response = await axios.post('/.netlify/functions/bsky', {
          q: query.value,
          limit: limit.value,
          lang: lang.value,
        });
        jsonData.value = response.data;
      } catch (error) {
        console.error('Errore durante la ricerca dei post:', error);

        if (error instanceof AxiosError) {
          errorMessage.value = error.response?.data
              ? (error.response.data as string)
              : "Errore durante la ricerca dei dati.";
        } else {
          errorMessage.value = "Errore sconosciuto.";
        }

        jsonData.value = [{ text: "Esempio di testo 1" }, { text: "Esempio di testo 2" }];
      } finally {
        try {
          // Invio del JSON al secondo server
          const serverResponse = await axios.post('/.netlify/functions/sentiment', {
            inputJson: JSON.stringify(jsonData.value),
            name: query.value,
          });

          // Log della risposta grezza ricevuta dal server
          console.log('Risposta ricevuta dal server:', serverResponse.data);

          responseJson.value = serverResponse.data;

          // Estrazione del valore di n dalla risposta
          if (serverResponse.data && serverResponse.data.response) {
            const responseString = serverResponse.data.response as string;

            // Log per il contenuto di responseString
            console.log('Stringa di risposta per il parsing:', responseString);

            // Verifica se la risposta contiene solo un numero iniziale
            const match = responseString.match(/^(\d+)/); // Trova il primo numero all'inizio della stringa
            if (match && match[1]) {
              console.log('Valore di n:', match[1]);
              const nValue = parseInt(match[1], 10); // Converte il valore in un numero intero
              speedometerValue.value = nValue-50; // Aggiorna il valore dello speedometer
            } else {
              console.error('Impossibile trovare il valore di n nella risposta.');
            }
          }
        } catch (error) {
          console.error('Errore durante l\'invio del JSON al server:', error);
          responseJson.value = { error: "Errore durante l'invio al secondo server." };
        }
        isLoading.value = false;
      }
    };

    // Funzioni per gestire la visibilità del JSON
    const toggleJsonVisibility = () => {
      showJson.value = !showJson.value;
    };

    const toggleResponseJsonVisibility = () => {
      showResponseJson.value = !showResponseJson.value;
    };

    return {
      query,
      limit,
      lang,
      jsonData,
      responseJson,
      errorMessage,
      isLoading,
      showJson,
      showResponseJson,
      speedometerValue,
      handleSearch,
      toggleJsonVisibility,
      toggleResponseJsonVisibility,
    };
  },
});
</script>

<template>
  <div class="search-container">
    <form @submit.prevent="handleSearch" class="search-form">
      <label for="query">Query:</label>
      <input type="text" id="query" v-model="query" placeholder="Inserisci la query" required />

      <label for="limit">Limit:</label>
      <input type="number" id="limit" v-model="limit" min="1" required />

      <label for="lang">Lingua:</label>
      <input type="text" id="lang" v-model="lang" placeholder="es: it" required />

      <button type="submit">Cerca</button>
    </form>

    <div v-if="isLoading" class="loading-spinner">Caricamento...</div>

    <div v-if="errorMessage" class="error-message">Errore: {{ errorMessage }}</div>

    <!-- Speedometer -->
    <div class="speedometer">
      <VueSpeedometer
          :min-value="-50"
          :max-value="50"
          :value="speedometerValue"
          :segments="10"
          :needle-transition-duration="400"
          :needle-transition="easeElastic"
          :current-value-text="`Valore attuale: ${speedometerValue}`"
      />
    </div>

    <button @click="toggleJsonVisibility" class="toggle-json-button">
      {{ showJson ? 'Nascondi Json 1' : 'Mostra Json 1' }}
    </button>
    <vue-json-pretty v-if="showJson" :data="jsonData" />

    <button @click="toggleResponseJsonVisibility" class="toggle-response-button">
      {{ showResponseJson ? 'Nascondi Json 2' : 'Mostra Json 2' }}
    </button>
    <vue-json-pretty v-if="showResponseJson" :data="responseJson" />
  </div>
</template>

<style scoped>
.search-container {
  margin: 20px auto;
  padding: 20px;
  max-width: 800px;
  background-color: #2d2d2d; /* Grigio neutro scuro */
  border: 1px solid #444; /* Bordo leggermente più chiaro */
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.4);
  font-family: Arial, sans-serif;
  color: #f1f1f1; /* Testo chiaro */
}

.search-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
}

.search-form label {
  font-weight: bold;
  color: #f1f1f1; /* Testo chiaro */
}

.search-form input {
  padding: 8px;
  border: 1px solid #555; /* Bordo neutro */
  border-radius: 4px;
  font-size: 14px;
  background-color: #3b3b3b; /* Sfondo input scuro */
  color: #fff; /* Testo chiaro */
}

.search-form input:focus {
  outline: none;
  border-color: #007bff; /* Colore evidenza */
}

.search-form button {
  padding: 10px;
  background-color: #007bff; /* Colore pulsante */
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.search-form button:hover {
  background-color: #0056b3; /* Colore hover pulsante */
}

.loading-spinner {
  margin: 20px 0;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
  color: #f1f1f1; /* Testo chiaro */
}

.error-message {
  color: #ff6b6b; /* Rosso per errori */
  font-weight: bold;
  margin-top: 10px;
}

.speedometer {
  margin: 20px 0;
  text-align: center;
  height: 170px;
}

.toggle-json-button, .toggle-response-button {
  display: block;
  margin: 10px 0;
  padding: 10px;
  background-color: #28a745; /* Verde pulsante */
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.toggle-json-button:hover, .toggle-response-button:hover {
  background-color: #218838; /* Verde scuro hover */
}
</style>
