import Home from '@/components/common/Home';
import Login from '@/components/login/Login';

import MainPage from '@/components/member/MainPage';
import MemberOrder from '@/components/member/MemberOrder';
import ShoppingCart from '@/components/member/ShoppingCart';
import MemberInfo from '@/components/member/MemberInfo';

import ManageFood from '@/components/restaurant/ManageFood';
import RestaurantOrder from '@/components/restaurant/RestaurantOrder';
import RestaurantInfo from '@/components/restaurant/RestaurantInfo';

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
        path: '/ManageFood',
        component: ManageFood,
        name: 'ManageFood'
      },
      {
        path: '/RestaurantOrder',
        component: RestaurantOrder,
        name: 'RestaurantOrder'
      },
      {
        path: '/RestaurantInfo',
        component: RestaurantInfo,
        name: 'RestaurantInfo'
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
