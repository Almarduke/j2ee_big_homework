import Vue from 'vue';
import router from './router/router';
import store from './store';
import App from './App';
import axios from 'axios';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

Vue.config.productionTip = false;

Vue.use(Antd);
Vue.use(store);

Vue.prototype.$store = store;
Vue.prototype.$http = axios;

/* eslint-disable no-new */
new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
