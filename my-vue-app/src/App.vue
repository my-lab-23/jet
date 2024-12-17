<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { App } from '@capacitor/app';

// Stato per mostrare o nascondere i pulsanti
const showButtons = ref(true);
const router = useRouter();
const route = useRoute(); // Per accedere alla route corrente

// Funzione per nascondere i pulsanti
const hideButtons = () => {
  showButtons.value = false;
};

// Listener per il tasto back di Android
onMounted(() => {
  App.addListener('backButton', () => {
    if (!showButtons.value) {
      // Torna alla Home mostrando i pulsanti
      showButtons.value = true;
      router.push('/');
    } else {
      // Comportamento predefinito (chiusura dell'app)
      App.exitApp();
    }
  });
});

// Watch per sincronizzare lo stato con la route corrente
watch(
    () => route.path,
    (newPath) => {
      // Mostra i pulsanti solo se siamo nella Home
      showButtons.value = newPath === '/';
    }
);
</script>

<template>
  <header>
    <div class="logo-wrapper">
      <img alt="Vue logo" class="logo" src="@/assets/logo.svg" />
    </div>

    <!-- Mostra i pulsanti se showButtons è true -->
    <nav v-if="showButtons">
      <RouterLink class="btn home" to="/home" @click="hideButtons">Home</RouterLink>
      <RouterLink class="btn about" to="/about" @click="hideButtons">About</RouterLink>
      <RouterLink class="btn jwt" to="/jwt" @click="hideButtons">Jwt</RouterLink>
      <RouterLink class="btn jwt" to="/posizione" @click="hideButtons">Posizione</RouterLink>
      <RouterLink class="btn jwt" to="/camera" @click="hideButtons">Camera</RouterLink>
    </nav>

    <!-- Mostra il componente della pagina se showButtons è false -->
    <RouterView v-else />
  </header>
</template>

<style scoped>
/* Stile generale */
header {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh; /* Cambiato da height a min-height */
  padding: 1rem;
  box-sizing: border-box; /* Garantisce che il padding non aumenti l'altezza totale */
  overflow: hidden; /* Prevenzione di overflow */
  margin: 0; /* Rimuove margini indesiderati */
}

/* Logo */
.logo-wrapper {
  margin-bottom: 2rem;
}

.logo {
  width: 100px;
  height: 100px;
}

/* Pulsanti del menu */
nav {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 300px;
}

.btn {
  display: block;
  text-align: center;
  padding: 1rem;
  border-radius: 8px;
  font-size: 1.2rem;
  font-weight: bold;
  color: white;
  text-decoration: none;
}

/* Colori specifici per ogni pulsante */
.btn.home {
  background-color: #4caf50;
}

.btn.about {
  background-color: #2196f3;
}

.btn.jwt {
  background-color: #ff5722;
}

/* Effetto al passaggio del mouse */
.btn:hover {
  opacity: 0.8;
}

/* Stile per schermi più grandi */
@media (min-width: 1024px) {
  .logo {
    width: 125px;
    height: 125px;
  }
}
</style>
