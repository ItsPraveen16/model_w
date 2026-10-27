
import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../components/HomeView.vue'
import GroceriesView from '../components/GroceriesView.vue'
import AddGroceriesView from '../components/AddGroceriesView.vue'

const routes = [
  {
    path: '/',
    component: HomeView
  },
  {
    path: '/groceries',
    component: GroceriesView
  },
  {
    path: '/add-grocery',
    component: AddGroceriesView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
