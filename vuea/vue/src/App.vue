<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { App } from '@capacitor/app';
import { Device } from '@capacitor/device'; // Importiamo il Device per rilevare la piattaforma

// Stato per mostrare o nascondere i pulsanti
const showButtons = ref(true);
const isAndroid = ref(false); // Stato per determinare se siamo su Android
const isPWA = ref(false); // Stato per determinare se siamo in una PWA

const router = useRouter();
const route = useRoute(); // Per accedere alla route corrente

// Funzione per nascondere i pulsanti
const hideButtons = () => {
  console.log('[DEBUG] Nascondo i pulsanti.');
  showButtons.value = false;
};

// Listener per il tasto cursore sinistro (freccia sinistra) SOLO in PWA
const handleKeydown = (event: KeyboardEvent) => {
  console.log(`[DEBUG] Tasto premuto: ${event.key}`);
  if (isPWA.value && event.key === 'ArrowLeft') { // Solo se siamo in modalità PWA
    console.log('[DEBUG] Rilevata pressione del cursore sinistro in PWA.');
    if (!showButtons.value) {
      console.log('[DEBUG] Mostro i pulsanti e torno alla Home.');
      showButtons.value = true;
      router.push('/');
    } else {
      console.log('[DEBUG] Pulsanti già visibili, nessuna azione.');
    }
  }
};

// Listener per il tasto back di Android (comportamento invariato)
onMounted(async () => {
  // Verifica se la piattaforma è Android
  const platform = await Device.getInfo();
  isAndroid.value = platform.platform === 'android';
  console.log('[DEBUG] Piattaforma:', platform);

  // Verifica se l'app è in modalità PWA
  isPWA.value = window.matchMedia('(display-mode: standalone)').matches;
  console.log('[DEBUG] È una PWA:', isPWA.value);

  // Gestiamo il comportamento del tasto back su Android (comportamento invariato)
  await App.addListener('backButton', () => {
    console.log('[DEBUG] Tasto back Android premuto. showButtons:', showButtons.value);
    if (!showButtons.value) {
      // Torna alla Home mostrando i pulsanti
      console.log('[DEBUG] Mostro i pulsanti e torno alla Home.');
      showButtons.value = true;
      router.push('/');
    } else {
      // Comportamento predefinito (chiusura dell'app)
      console.log('[DEBUG] Chiusura dell\'app.');
      App.exitApp();
    }
  });

  // Aggiungi listener per il cursore sinistro solo se siamo in PWA
  if (isPWA.value) {
    window.addEventListener('keydown', handleKeydown);
    console.log('[DEBUG] Listener per cursore sinistro aggiunto.');
  }

  // Rimuovi il listener quando il componente viene smontato
  onUnmounted(() => {
    console.log('[DEBUG] Rimuovo il listener keydown.');
    if (isPWA.value) {
      window.removeEventListener('keydown', handleKeydown);
    }
  });
});

// Watch per sincronizzare lo stato con la route corrente
watch(
  () => route.path,
  (newPath, oldPath) => {
    console.log(`[DEBUG] Cambiamento route: ${oldPath} -> ${newPath}`);
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
      <!-- Controlliamo se siamo su Android prima di mostrare questi pulsanti -->
      <RouterLink
          v-if="!isAndroid"
          class="btn about" to="/auth" @click="hideButtons">Auth Info</RouterLink>

      <RouterLink v-if="isAndroid" 
        class="btn home" to="/save-android-key" @click="hideButtons">Save Android Key</RouterLink>
      <RouterLink v-if="isAndroid" 
        class="btn home" to="/use-android-key" @click="hideButtons">Use Android Key</RouterLink>
      
      <RouterLink class="btn about" to="/vps" @click="hideButtons">System Info</RouterLink>

      <RouterLink class="btn jwt" to="/bsky" @click="hideButtons">Bsky</RouterLink>
      <RouterLink class="btn jwt" to="/json" @click="hideButtons">Json</RouterLink>
      <RouterLink class="btn jwt" to="/speed" @click="hideButtons">Speed</RouterLink>

      <RouterLink
          v-if="isAndroid"
          class="btn jwt" to="/plugin" @click="hideButtons">PlugIn</RouterLink>

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
