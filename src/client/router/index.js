import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/auth',
    },
    {
      path: '/auth',
      name: 'auth',
      component: () => import('../views/AuthView.vue'),
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/UserView.vue'),
      children: [
        {
          path: ':id',
          component: () => import('../views/UserView.vue'),
        },
      ],
    },
    {
      path: '/message',
      name: 'message',
      component: () => import('../views/MessageView.vue'),
      children: [
        {
          path: ':id',
          component: () => import('../views/MessageView.vue'),
        },
      ],
    },
    {
      path: '/pending',
      name: 'pending',
      component: () => import('../views/PendingView.vue'),
      children: [
        {
          path: ':id',
          component: () => import('../views/PendingView.vue'),
        },
      ],
    },
    {
      path: '/bpm',
      name: 'bpm',
      component: () => import('../views/BPMView.vue'),
    },
  ],
})

export default router
