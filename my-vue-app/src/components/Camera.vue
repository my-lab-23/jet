<script setup lang="ts">
import { Camera, CameraResultType } from '@capacitor/camera';
import { ref } from 'vue';

// Variabili reattive per immagazzinare l'URI dell'immagine e gli errori
const imageUri = ref<string | null>(null);
const errorMessage = ref<string | null>(null);

// Funzione per acquisire la foto
const takePicture = async () => {
  try {
    // Resetta eventuali errori precedenti
    errorMessage.value = null;

    // Acquisisci la foto
    const picture = await Camera.getPhoto({
      resultType: CameraResultType.Uri
    });

    // Assegna l'URI dell'immagine
    imageUri.value = picture.webPath ?? null;
  } catch (error) {
    // Gestisce gli errori e li visualizza
    errorMessage.value = `Errore: ${error instanceof Error ? error.message : 'Errore sconosciuto'}`;
  }
};
</script>

<template>
  <div>
    <!-- Pulsante per scattare una foto -->
    <button @click="takePicture">Scatta una Foto</button>

    <!-- Visualizza l'immagine se esiste -->
    <div v-if="imageUri">
      <img :src="imageUri" alt="Immagine acquisita" />
    </div>

    <!-- Visualizza il messaggio di errore se presente -->
    <div v-if="errorMessage" class="error-message">
      <p>{{ errorMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
/* Stile per l'immagine */
img {
  max-width: 100%;
  height: auto;
}

/* Stile per il messaggio di errore */
.error-message {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}
</style>
