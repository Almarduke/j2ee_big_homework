import Home from '@/components/common/Home';
import TestPage from '@/components/common/TestPage';
import Order from '@/components/common/Order';
import Login from '@/components/Login/Login';

import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: Home,
    name: 'Home',
    children: [
      {
        path: '/TestPage',
        component: TestPage,
        name: 'TestPage'
      },
      {
        path: '/Order',
        component: Order,
        name: 'Order'
      }
    ]
  },
  {
    path: '/Login',
    component: Login,
    name: 'Login'
  }
];
const router = new VueRouter({
  routes
});

export default router;
