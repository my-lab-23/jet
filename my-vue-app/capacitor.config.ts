import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.app',
  appName: 'my-vue-app',
  webDir: 'dist',
  server: {
    cleartext: true, // Consente traffico HTTP non sicuro verso qualsiasi server
    "androidScheme": "http"
  }
};

export default config;
