import Home from '../../components/common/Home';
import MainPage from '../../components/member/MainPage';
import Order from '../../components/common/Order';
import Login from '../../components/login/Login';

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
        path: '/MainPage',
        component: MainPage,
        name: 'MainPage'
      },
      {
        path: '/Order',
        component: Order,
        name: 'Order'
      }
    ]
  },
  {
    path: '/login',
    component: Login,
    name: 'Login'
  }
];
const router = new VueRouter({
  routes
});

export default router;
