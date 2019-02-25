<template>
  <div>
    <a-layout>
      <a-layout-header><common-header/></a-layout-header>
      <a-layout-content>
        <router-view/>
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
    this.$router.push('/Login');
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
</style>
