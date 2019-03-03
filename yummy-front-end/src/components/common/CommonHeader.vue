<template>
  <div>
    <a-menu mode="horizontal" class="menu" @click="handleClick">
      <a-menu-item key="/MainPage" v-if="userInfo.userType === 'MEMBER'">Yummy!</a-menu-item>
      <a-menu-item key="/MemberOrder" v-if="userInfo.userType === 'MEMBER'">订单管理</a-menu-item>
      <a-menu-item key="/MemberInfo" v-if="userInfo.userType === 'MEMBER'">用户信息</a-menu-item>

      <a-menu-item key="/ManageFood" v-if="userInfo.userType === 'RESTAURANT'">菜品管理</a-menu-item>
      <a-menu-item key="/RestaurantOrder" v-if="userInfo.userType === 'RESTAURANT'">订单管理</a-menu-item>
      <a-menu-item key="/RestaurantInfo" v-if="userInfo.userType === 'RESTAURANT'">餐厅信息</a-menu-item>

      <a-menu-item key="/AdminInfo" v-if="userInfo.userType === 'ADMIN'">管理员界面</a-menu-item>

      <a-menu-item key="/Login" class="logout-icon-menu-item" @click="logout">登出</a-menu-item>
    </a-menu>
  </div>
</template>

<script>
import * as actionTypes from '@/utils/store/action-types';
import { mapGetters, mapActions } from 'vuex';

export default {
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    handleClick (item) {
      this.$router.push(item.key);
    },
    logout () {
      this[actionTypes.LOGOUT]();
    },
    ...mapActions([actionTypes.LOGOUT])
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
  .logout-icon-menu-item {
    float: right;
  }
</style>
