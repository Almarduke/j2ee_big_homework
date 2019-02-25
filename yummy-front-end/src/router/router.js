import Home from '@/components/common/Home';
import TestPage from '@/components/common/TestPage';
import Order from '@/components/common/Order';

import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  // {
  //   path:'/viewsegment/:taskType/:taskId',
  //   component:imagesViewSegment,
  //   name:'viewsegment',
  //   meta:{
  //     requireAuth: true,
  //   }
  // },
  // 進入時的頁面
  {
    path: '/Home',
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
  }
];
const router = new VueRouter({
  routes
});

export default router;
