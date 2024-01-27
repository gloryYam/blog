import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/views/HomeView.vue'
import WriteView from '@/views/views/WriteView.vue'
import ReadView from '@/views/views/ReadView.vue'
import EditView from '@/views/views/EditView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/write",
      name: "write",
      component: WriteView,
    },
    {
      path: "/read/:postId",
      name: "read",
      component: ReadView,
      props: true,
    },
    {
      path: "/edit/:postId",
      name: "edit",
      component: EditView,
      props: true,
    },

//     {
//       path: '/about',
//       name: 'about',
//       // route level code-splitting
//       // this generates a separate chunk (About.[hash].js) for this route
//       // which is lazy-loaded when the route is visited.
//       component: () => import('../views/AboutView.vue')
//     },
  ],
});

export default router
