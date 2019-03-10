<template>
  <div>
    <a-layout style="height: 750px; padding: 24px; background-color: white">
      <a-layout-sider width="200" style="background: #fff">
        <a-menu mode="inline" :defaultSelectedKeys="[TOPAY]" style="height: 100%" @click="handleClick">
          <a-menu-item :key="TOPAY" >未支付</a-menu-item>
          <a-menu-item :key="PAYED">已支付</a-menu-item>
          <a-menu-item :key="DISTRIBUTING">派送中</a-menu-item>
          <a-menu-item :key="FINISHED">已完结</a-menu-item>
          <a-menu-item :key="CANCELLED">已取消</a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content v-if="showWhichMainPane===pane.CommonOrderList" :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-list itemLayout="horizontal" :bordered="true">
          <a-list-item v-for="(order, index) in orderList" :key="order.id">
            <a-list-item-meta :description="`${order.time} ${order.restaurantName}`">
              <a slot="title" >{{order.id}}</a>
            </a-list-item-meta>
            <div style="margin-right: 20px">{{order.actualAmount}}元</div>
            <div>
              <a slot="actions" class="plain-text" @click="showOrderInfo(order.id)">详情</a>
              <a slot="actions" class="plain-text" v-if="payButtonVisible" @click="updateOrder(order.id, index, 'payOrder')">支付</a>
              <a slot="actions" class="plain-text" v-if="finishButtonVisible" @click="updateOrder(order.id, index, 'finishOrder')">确认</a>
              <a slot="actions" class="plain-text" v-if="cancelButtonVisible" @click="updateOrder(order.id, index, 'cancelOrder')">取消</a>
            </div>
          </a-list-item>
        </a-list>
      </a-layout-content>

      <a-layout-content v-if="showWhichMainPane===pane.FinishedOrderStatistics" style="height: 600px; padding: 0 80px; background-color: white">
        <a-tabs :defaultActiveKey="finishedOrderList.defaultKey">
          <a-tab-pane tab="按金额分类" key="money">
            <a-list itemLayout="horizontal" :bordered="true" class="statistics-info-list">
              <a-list-item>
                <a-list-item-meta><a slot="title">50元以内</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('money', 'fifty')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">50～100元</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('money', 'oneHundred')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">100～300元</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('money', 'threeHundred')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">其他</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('money', 'other')">查看详情</a>
                </div>
              </a-list-item>
            </a-list>
          </a-tab-pane>
          <a-tab-pane tab="按时间分类" key="time">
            <a-list itemLayout="horizontal" :bordered="true" class="statistics-info-list">
              <a-list-item>
                <a-list-item-meta><a slot="title">1周之内</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('time', 'oneWeek')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">1个月之内</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('time', 'oneMonth')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">3个月以内</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('time', 'threeMonths')">查看详情</a>
                </div>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta><a slot="title">其他</a></a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showFinishedOrderList('time', 'other')">查看详情</a>
                </div>
              </a-list-item>
            </a-list>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>

      <a-layout-content v-if="showWhichMainPane===pane.FinishedOrderList" :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-breadcrumb style="margin: 0 16px 16px">
          <a-breadcrumb-item>
            <a @click="loadOrders(FINISHED)">订单分类</a>
          </a-breadcrumb-item>
          <a-breadcrumb-item>
            <span>统计信息</span>
          </a-breadcrumb-item>
        </a-breadcrumb>
        <a-list itemLayout="horizontal" :bordered="true">
          <a-list-item v-for="order in finishedOrderList.selected" :key="order.id">
            <a-list-item-meta :description="`${order.time} ${order.restaurantName}`">
              <a slot="title" >{{order.id}}</a>
            </a-list-item-meta>
            <div style="margin-right: 20px">{{order.actualAmount}}元</div>
            <div>
              <a slot="actions" class="plain-text" @click="showOrderInfo(order.id)">详情</a>
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
import { TOPAY, PAYED, DISTRIBUTING, FINISHED, CANCELLED } from '@/utils/status/PayStatus';
import OrderInfoPage from '@/components/common/OrderInfoPage';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'MemberOrder',
  components: { OrderInfoPage },
  data () {
    return {
      TOPAY: TOPAY,
      PAYED: PAYED,
      DISTRIBUTING: DISTRIBUTING,
      FINISHED: FINISHED,
      CANCELLED: CANCELLED,
      orderList: [],
      payButtonVisible: false,
      finishButtonVisible: false,
      cancelButtonVisible: false,
      visible: false,
      selectedOrderId: '',
      finishedOrderList: {
        time: {
          oneWeek: [],
          oneMonth: [],
          threeMonths: [],
          other: []
        },
        money: {
          fifty: [],
          oneHundred: [],
          threeHundred: []
        },
        selected: [],
        defaultKey: 'money'
      },
      showWhichMainPane: 1,
      pane: {
        CommonOrderList: 1,
        FinishedOrderStatistics: 2,
        FinishedOrderList: 3
      }
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
      this.showWhichMainPane = (orderStatus === FINISHED) ? this.pane.FinishedOrderStatistics : this.pane.CommonOrderList;
      this.payButtonVisible = (orderStatus === TOPAY);
      this.finishButtonVisible = (orderStatus === DISTRIBUTING);
      this.cancelButtonVisible = (orderStatus === TOPAY) || (orderStatus === PAYED) || (orderStatus === DISTRIBUTING);
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
          if (orderStatus === FINISHED) {
            this.finishedOrderList = {
              time: {
                oneWeek: [],
                oneMonth: [],
                threeMonths: [],
                other: []
              },
              money: {
                fifty: [],
                oneHundred: [],
                threeHundred: [],
                other: []
              }
            };
            const ONE_DAY = 60 * 60 * 24;
            const CURRENT_TIME = Date.parse(new Date()) / 1000;
            this.orderList.forEach((order) => {
              const orderTime = Date.parse(order.time) / 1000;
              if (orderTime > CURRENT_TIME - 7 * ONE_DAY) {
                this.finishedOrderList.time.oneWeek.push(order);
              }
              if (orderTime > CURRENT_TIME - 31 * ONE_DAY) {
                this.finishedOrderList.time.oneMonth.push(order);
              }
              if (orderTime > CURRENT_TIME - 90 * ONE_DAY) {
                this.finishedOrderList.time.threeMonths.push(order);
              } else {
                this.finishedOrderList.time.other.push(order);
              }

              if (order.actualAmount > 300) {
                this.finishedOrderList.money.other.push(order);
              } else if (order.actualAmount > 100) {
                this.finishedOrderList.money.threeHundred.push(order);
              } else if (order.actualAmount > 50) {
                this.finishedOrderList.money.oneHundred.push(order);
              } else {
                this.finishedOrderList.money.fifty.push(order);
              }
            });
          }
        }
      });
    },
    showFinishedOrderList (type, key) {
      this.finishedOrderList.defaultKey = type;
      this.finishedOrderList.selected = this.finishedOrderList[type][key];
      this.showWhichMainPane = this.pane.FinishedOrderList;
    },
    handleClick (item) {
      this.loadOrders(item.key);
    },
    showOrderInfo (orderId) {
      this.selectedOrderId = orderId;
      this.visible = true;
    },
    updateOrder (id, index, key) {
      this.$http({
        url: `${this.baseUrl}/order/${key}/${id}`,
        method: 'POST'
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
  .statistics-info-list .ant-list-item {
    height: 80px;
  }
</style>
