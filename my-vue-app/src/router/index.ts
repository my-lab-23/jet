import { createRouter, createWebHashHistory } from 'vue-router';
import Jwt from '@/components/Jwt.vue';
import Posizione from '@/components/Posizione.vue';
import Camera from "@/components/Camera.vue";
import PlaceHolder from "@/components/PlaceHolder.vue";

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
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
      path: '/home',
      name: 'home',
      component: PlaceHolder,
      props: { message: 'Welcome to the Home page!' },
    },
    {
      path: '/about',
      name: 'about',
      component: PlaceHolder,
      props: { message: 'Learn more About us.' },
    }
  ],
});

export default router;
