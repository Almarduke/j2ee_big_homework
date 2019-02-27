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

export default {
  name: 'home',
  components: {
    CommonHeader
  },
  data () {
    return {
      theme: 'light'
    };
  },
  mounted () {
    // this.$router.push('/login');
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
    margin: 20px 64px 0 64px;
  }
</style>
