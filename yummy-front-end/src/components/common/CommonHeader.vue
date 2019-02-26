<template>
  <div>
    <a-menu v-model="current" mode="horizontal" class="menu" @click="handleClick">
      <a-menu-item key="/TestPage">Yummy!</a-menu-item>
      <a-menu-item key="/Order">点单</a-menu-item>
      <a-menu-item key="/UserInfo">用户信息</a-menu-item>
    </a-menu>
  </div>
</template>
<script>
export default {
  data () {
    return {
      current: ['/Order']
    };
  },
  methods: {
    handleClick (item) {
      this.$router.push(item.key);
    }
  }
};
</script>

<style scoped>
  a-menu-item {
    font-size: 18px;
  }
  .menu {
    line-height: 64px;
    height: 64px;
    background-color: #F8F8F8;
  }
</style>
