import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.vuea',
  appName: 'vuea',
  webDir: 'dist',
  server: {
    allowNavigation: [
      "*"
    ]
  }
};

export default config;
