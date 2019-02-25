<template>
  <div class="login-page">
    <a-card class = "login-card" :tabList="tabList" :activeTabKey="activeKey" @tabChange="tabSelected">
      <MemberLogin v-if="activeKey === 'Member'"/>
      <Order v-else/>
    </a-card>
  </div>
</template>

<script>
import MemberLogin from '@/components/Login/MemberLogin';
import Order from '@/components/common/Order';

export default {
  name: 'Login',
  components: { MemberLogin, Order },
  data () {
    return {
      tabList: [{
        key: 'Member',
        tab: '会员用户'
      }, {
        key: 'Restaurant',
        tab: '餐厅用户'
      }],
      activeKey: 'Member'
    };
  },
  methods: {
    tabSelected (selectedkey) {
      this.activeKey = selectedkey;
    }
  }
};
</script>

<style scoped>
  .login-page {
    background-image: url(../../assets/background.jpg);
    background-repeat: no-repeat;
    background-size: cover, cover;
    width: 100%;
    height: 100%;
    display:flex;/*Flex布局*/
    display: -webkit-flex; /* Safari */
    align-items:center;/*指定垂直居中*/
  }
  .login-card {
    width: 360px;
    height: 320px;
    margin: auto;
  }
</style>
