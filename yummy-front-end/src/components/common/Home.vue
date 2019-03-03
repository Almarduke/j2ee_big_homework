<template>
  <div style="width: 100%; height: 100%">
    <a-layout style="width: 100%; height: 100%">
      <a-layout-header><common-header/></a-layout-header>
      <a-layout-content>
        <div class="main-view">
          <router-view/>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
import CommonHeader from './CommonHeader';
import { mapGetters } from 'vuex';

export default {
  name: 'home',
  components: {
    CommonHeader
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  data () {
    return {
      theme: 'light'
    };
  },
  mounted () {
    if (!this.userInfo.isLogin) {
      this.$router.push('/login');
    } else if (this.userInfo.userType === 'MEMBER') {
      this.$router.push('/MainPage');
    } else if (this.userInfo.userType === 'RESTAURANT') {
      this.$router.push('/RestaurantInfo');
    } else if (this.userInfo.userType === 'ADMIN') {
      this.$router.push('/AdminInfo');
    }
  }
};
</script>

<style scoped>
  .ant-layout-header {
    padding: 0!important;
    background-color: white!important;
  }
  .ant-layout-content {
    padding: 0;
  }
  .main-view {
    margin: 20px 64px;
  }
</style>
