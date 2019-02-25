<template>
  <div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',
  mounted () {
    this.$router.push('/Home');
  }
};
</script>

<style>
  .ant-layout-header {
    padding: 0!important;
    background-color: white!important;
  }
  .ant-layout-content {
    padding: 0;
  }
</style>
