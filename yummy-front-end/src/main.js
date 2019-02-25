import Vue from 'vue';
import router from 'vue-router';
import { Button } from 'ant-design-vue';
import App from './App';

Vue.component(Button.name, Button);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
