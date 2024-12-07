import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css' // <-- here

createApp(App).use(router).mount('#app')
