<template>
  <a-form>
    <a-form-item v-bind="formItemLayout" label="订单编号">
      <span>{{orderInfo.id}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="创建时间">
      <span>{{orderInfo.createTime}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="订单状态">
      <span>{{orderInfo.orderStatus}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="商家名称">
      <span>{{orderInfo.restaurantName}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="用户名称" >
      <span>{{orderInfo.memberName}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="用户手机" >
      <span>{{orderInfo.memberPhone}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="用户地址" >
      <span>{{orderInfo.memberAddress}}</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="订单总额" >
      <span>{{orderInfo.totalAmount}}元</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="折扣金额" >
      <span>{{orderInfo.discount}}元</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="实际金额" >
      <span>{{orderInfo.totalAmount - orderInfo.discount}}元</span>
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="菜品列表">
      <a-list itemLayout="horizontal" :bordered="true">
        <a-list-item v-for="orderDetail in orderInfo.orderDetails" :key="orderDetail.foodName">
          <a-list-item-meta><a slot="title" >{{orderDetail.foodName}}</a></a-list-item-meta>
          <div style="margin-right: 20px">{{orderDetail.price}}元 * {{orderDetail.foodNum}}份</div>
        </a-list-item>
      </a-list>
    </a-form-item>
  </a-form>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'OrderInfoPage',
  props: { orderId: String },
  data () {
    return {
      formItemLayout: {
        labelCol: {
          span: 4
        },
        wrapperCol: {
          span: 20
        }
      },
      orderInfo: {}
    };
  },
  computed: {
    ...mapGetters(['baseUrl'])
  },
  mounted () {
    this.$http({
      url: `${this.baseUrl}/order/getOrderInfo/${this.orderId}`,
      method: 'GET'
    }).then((response) => {
      this.orderInfo = response.data.data;
    });
  }
};
</script>

<style scoped>
  span {
    margin-left: 20px;
    font-size: 14px;
  }
</style>
