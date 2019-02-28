<template>
  <a-layout style="height: 700px; padding: 24px; background-color: white">
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
              mode="inline"
              :defaultSelectedKeys="['1']"
              :defaultOpenKeys="['sub1']"
              style="height: 100%"
      >
        <a-menu-item key="1" @click="test">option1</a-menu-item>
        <a-menu-item key="2">option2</a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
      <a-list itemLayout="horizontal" :bordered="true">
        <a-list-item v-for="order in ongoingOrders" :key="order.id">
          <a-list-item-meta description="2019.8.13 元和饭店">
            <a slot="title" >{{13423494334}}</a>
          </a-list-item-meta>
          <div style="margin-right: 20px">234元</div>
          <div>
            <a slot="actions" class="plain-text">详情</a>
            <a slot="actions" class="plain-text">结算</a>
          </div>
        </a-list-item>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'Order',
  data () {
    return {
      ongoingOrders: [1, 3, 43],
      canceledOrders: [],
      finishedOrders: []
    };
  },
  methods: {
    test () {
      alert('来了');
    }
  }
};
</script>

<style scoped>
  .plain-text {
    margin: 0 5px;
    width: 20px;
    font-size: 14px;
  }
</style>
