import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

// Importa i componenti
import MyAuth from '@/components/MyAuth.vue'
import MyVPS from '@/components/MyVPS.vue'
import SaveAndroidKey from "@/components/SaveAndroidKey.vue";  // Aggiungi il componente SaveAndroidKey
import UseAndroidKey from "@/components/UseAndroidKey.vue";
import MySpeed from "@/components/MySpeed.vue";
import MyPlugIn from "@/components/MyPlugIn.vue";
import MyJson from "@/components/MyJson.vue";
import MyBsky from "@/components/MyBsky.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/auth',
    name: 'MyAuth',
    component: MyAuth
  },
  {
    path: '/vps',
    name: 'MyVPS',
    component: MyVPS
  },
  {
    path: '/save-android-key',  // Aggiungi la route per il componente SaveAndroidKey
    name: 'SaveAndroidKey',
    component: SaveAndroidKey
  },
  {
    path: '/use-android-key',   // Aggiungi la route per il componente UseAndroidKey
    name: 'UseAndroidKey',
    component: UseAndroidKey
  },
  {
    path: '/speed',
    name: 'MySpeed',
    component: MySpeed
  },
  {
    path: '/plugin',
    name: 'MyPlugIn',
    component: MyPlugIn
  },
  {
    path: '/json',
    name: 'MyJson',
    component: MyJson
  },
  {
    path: '/bsky',
    name: 'MyBsky',
    component: MyBsky
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
