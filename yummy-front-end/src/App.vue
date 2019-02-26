<template>
  <div style="width: 100%; height: 100%">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app'
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
