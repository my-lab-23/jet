<template>
  <div>
    <h1>Geolocalizzazione con Capacitor</h1>

    <!-- Mostra la latitudine e longitudine se disponibili, altrimenti mostra il messaggio di errore o di caricamento -->
    <div v-if="latitude !== null && longitude !== null">
      <p>Latitudine: {{ latitude }}</p>
      <p>Longitudine: {{ longitude }}</p>
    </div>
    <div v-else-if="errorMessage">
      <p style="color: red;">{{ errorMessage }}</p>
    </div>
    <div v-else>
      <p>Caricamento della posizione in corso...</p>
    </div>

    <button @click="getCurrentPosition">Aggiorna posizione</button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Geolocation } from '@capacitor/geolocation';
import type { PermissionStatus } from '@capacitor/geolocation';

const latitude = ref<number | null>(null);
const longitude = ref<number | null>(null);
const errorMessage = ref<string | null>(null);

const checkAndRequestPermissions = async () => {
  try {
    const permissionStatus: PermissionStatus = await Geolocation.checkPermissions();
    console.log('Stato dei permessi di geolocalizzazione:', permissionStatus); // Log dello stato dei permessi

    if (permissionStatus.location !== 'granted') {
      const requestStatus: PermissionStatus = await Geolocation.requestPermissions();
      console.log('Stato dei permessi dopo la richiesta:', requestStatus); // Log dello stato dopo la richiesta
      if (requestStatus.location !== 'granted') {
        throw new Error('Permessi non concessi per la geolocalizzazione.');
      }
    }
  } catch (error) {
    console.error(error);
    errorMessage.value = 'Permessi di geolocalizzazione non concessi.';
  }
};

const getCurrentPosition = async () => {
  // Resetta i valori
  latitude.value = null;
  longitude.value = null;
  errorMessage.value = null;

  try {
    await checkAndRequestPermissions();
    const position = await Geolocation.getCurrentPosition();
    latitude.value = position.coords.latitude;
    longitude.value = position.coords.longitude;
  } catch (error) {
    errorMessage.value = 'Impossibile ottenere la posizione. Assicurati che i permessi siano attivi.';
    console.error(error);
  }
};

// Chiama getCurrentPosition quando il componente viene montato
onMounted(() => {
  getCurrentPosition();
});
</script>

<style scoped>
button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #45a049;
}
</style>
