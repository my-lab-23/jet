const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  publicPath: process.env.VUE_APP_BUILD_TARGET === 'ktor' ? '/ktest/vue/' : '/',
  outputDir: process.env.VUE_APP_BUILD_TARGET === 'ktor' 
    ? '/home/ema/Scrivania/vue/kv/vuea/ktor-sample/src/main/resources/static/'
    : 'dist',
  transpileDependencies: true,

  // Configurazione PWA
  pwa: {
    name: "Vuea",
    short_name: "Vuea",
    themeColor: "#4DBA87",
    backgroundColor: "#000000",
    display: "standalone",
    start_url: ".",
    manifestOptions: {
      icons: [
        {
          src: "./img/icons/android-chrome-192x192.png",
          sizes: "192x192",
          type: "image/png"
        },
        {
          src: "./img/icons/android-chrome-512x512.png",
          sizes: "512x512",
          type: "image/png"
        },
        {
          src: "./img/icons/android-chrome-maskable-192x192.png",
          sizes: "192x192",
          type: "image/png",
          purpose: "maskable"
        },
        {
          src: "./img/icons/android-chrome-maskable-512x512.png",
          sizes: "512x512",
          type: "image/png",
          purpose: "maskable"
        }
      ],
      description: "Vue PWA"
    }
  }
});
