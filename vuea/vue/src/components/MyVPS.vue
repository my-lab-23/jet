<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { fetchSystemData, fetchSystemDataWithAuth, SystemData } from '@/utils/systemData';
import { loadAndroidKey } from "@/utils/aux";
import { Capacitor } from '@capacitor/core';

export default defineComponent({
  name: 'MyVPS',
  setup() {
    const systemData = ref<SystemData | null>(null);
    const error = ref<string | null>(null);
    const platform = ref<string>(''); // Memorizza la piattaforma

    const fetchData = async () => {
      try {
        if (platform.value === 'android') {
          const androidKey = loadAndroidKey();
          if (!androidKey) {
            error.value = 'Chiave di autenticazione mancante.';
            return;
          }
          systemData.value = await fetchSystemDataWithAuth('https://n2342.it/ktest/android/api/system-info', androidKey);
        } else {
          systemData.value = await fetchSystemData('https://n2342.it/ktest/api/system-info');
        }
      } catch {
        error.value = 'Errore nel recupero dei dati';
      }
    };

    onMounted(async () => {
      platform.value = Capacitor.getPlatform(); // Determina la piattaforma
      await fetchData();
    });

    return {
      systemData,
      error,
      platform
    };
  },
});
</script>

<template>
  <div v-if="error" class="error">{{ error }}</div>
  <div v-else-if="systemData" class="system-info">
    <h3>Informazioni sulla VPS</h3>
    <ul>
      <li><strong>OS:</strong> {{ systemData.os }}</li>
      <li><strong>Rete:</strong> {{ systemData.network }}</li>
    </ul>
  </div>
  <br>
  <div class="system-info">
    <h3>Informazioni sulla Platform</h3>
    <ul>
      <li><strong>Platform:</strong> {{ platform }}</li>
    </ul>
  </div>
</template>

<style scoped>
.error {
  color: #ff4d4d;
  background-color: #5a1a1a;
  padding: 10px;
  border: 1px solid #ff6666;
  border-radius: 5px;
  margin-bottom: 20px;
  font-size: 14px;
  font-weight: bold;
}

.system-info {
  background-color: #333;
  padding: 15px;
  border: 1px solid #444;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  width: 300px;
}

.system-info h3 {
  color: #f5f5f5;
  font-size: 18px;
  margin-bottom: 10px;
}

.system-info ul {
  list-style-type: none;
  padding: 0;
}

.system-info li {
  color: #ccc;
  font-size: 14px;
  margin-bottom: 5px;
}

.system-info strong {
  color: #fff;
}
</style>
