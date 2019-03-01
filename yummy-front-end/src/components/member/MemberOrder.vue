<template>
  <div>
    <a-layout style="height: 700px; padding: 24px; background-color: white">
      <a-layout-sider width="200" style="background: #fff">
        <a-menu mode="inline" :defaultSelectedKeys="[TOPAY]" style="height: 100%" @click="handleClick">
          <a-menu-item :key="TOPAY" >未支付</a-menu-item>
          <a-menu-item :key="PAYED">已支付</a-menu-item>
          <a-menu-item :key="DISTRIBUTING">派送中</a-menu-item>
          <a-menu-item :key="FINISHED">已完成</a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-list itemLayout="horizontal" :bordered="true">
          <a-list-item v-for="(order, index) in orderList" :key="order.id">
            <a-list-item-meta :description="`${order.time} ${order.restaurantName}`">
              <a slot="title" >{{order.id}}</a>
            </a-list-item-meta>
            <div style="margin-right: 20px">{{order.amount}}元</div>
            <div>
              <a slot="actions" class="plain-text" @click="showOrderInfo(order.id)">详情</a>
              <a slot="actions" class="plain-text" v-if="payButtonVisible" @click="updateOrder(order.id, index)">支付</a>
              <a slot="actions" class="plain-text" v-if="finishButtonVisible" @click="updateOrder(order.id, index)">确认</a>
            </div>
          </a-list-item>
        </a-list>
      </a-layout-content>
    </a-layout>

    <a-modal title="订单详情" :visible="visible" @ok="visible=false" @cancel="visible=false">
      <order-info-page :order-id="selectedOrderId" v-if="visible"></order-info-page>
    </a-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { TOPAY, PAYED, DISTRIBUTING, FINISHED } from '@/utils/status/PayStatus';
import OrderInfoPage from '@/components/common/OrderInfoPage';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'Order',
  components: { OrderInfoPage },
  data () {
    return {
      TOPAY: TOPAY,
      PAYED: PAYED,
      DISTRIBUTING: DISTRIBUTING,
      FINISHED: FINISHED,
      orderList: [],
      payButtonVisible: false,
      finishButtonVisible: false,
      visible: false,
      selectedOrderId: ''
    };
  },
  computed: {
    ...mapGetters(['userInfo', 'baseUrl'])
  },
  mounted () {
    this.loadOrders(TOPAY);
  },
  methods: {
    loadOrders (orderStatus) {
      this.payButtonVisible = (orderStatus === TOPAY);
      this.finishButtonVisible = (orderStatus === DISTRIBUTING);
      this.$http({
        url: this.baseUrl + '/order/getMemberOrders',
        method: 'GET',
        params: {
          email: this.userInfo.userId,
          orderStatus: orderStatus
        }
      }).then((response) => {
        if (response.data.code === OK) {
          this.orderList = response.data.data;
        }
      });
    },
    handleClick (item) {
      this.loadOrders(item.key);
    },
    showOrderInfo (orderId) {
      this.selectedOrderId = orderId;
      this.visible = true;
    },
    updateOrder (id, index) {
      this.$http({
        url: this.baseUrl + '/order/updateOrder',
        method: 'POST',
        params: {
          id: id,
          isMemberEditing: true
        }
      }).then((response) => {
        if (response.data.code === OK) {
          this.orderList.splice(index, 1);
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
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
