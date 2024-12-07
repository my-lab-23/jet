import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import Jwt from '@/components/Jwt.vue';
import Posizione from '@/components/Posizione.vue';
import Camera from "@/components/Camera.vue";

const router = createRouter({
  history: createWebHashHistory(), // Cambiato da createWebHistory a createWebHashHistory
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/jwt',
      name: 'jwt',
      component: Jwt,
    },
    {
      path: '/posizione',
      name: 'posizione',
      component: Posizione,
    },
    {
      path: '/camera',
      name: 'camera',
      component: Camera,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
  ],
});

export default router;
