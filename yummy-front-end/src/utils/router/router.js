import Home from '@/components/common/Home';
import Login from '@/components/login/Login';

import MainPage from '@/components/member/MainPage';
import MemberOrder from '@/components/member/MemberOrder';
import ShoppingCart from '@/components/member/ShoppingCart';
import MemberInfo from '@/components/member/MemberInfo';

import RestaurantOrder from '@/components/restaurant/RestaurantOrder';
import RestaurantInfo from '@/components/restaurant/RestaurantInfo';

import AdminInfo from '@/components/admin/AdminInfo';

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
        path: '/ShoppingCart',
        component: ShoppingCart,
        name: 'ShoppingCart'
      },
      {
        path: '/MemberOrder',
        component: MemberOrder,
        name: 'MemberOrder'
      },
      {
        path: '/MemberInfo',
        component: MemberInfo,
        name: 'MemberInfo'
      },

      {
        path: '/RestaurantInfo',
        component: RestaurantInfo,
        name: 'RestaurantInfo'
      },
      {
        path: '/RestaurantOrder',
        component: RestaurantOrder,
        name: 'RestaurantOrder'
      },

      {
        path: '/AdminInfo',
        component: AdminInfo,
        name: '/AdminInfo'
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
