<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/rice.jpg" alt="" slot="cover"/>
        <a-card-meta :title="restaurantInfo.name">
          <p slot="description">{{restaurantInfo.phone}}</p>
          <p slot="description">{{restaurantInfo.address}}</p>
        </a-card-meta>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-breadcrumb style="margin: 0 16px 16px">
          <a-breadcrumb-item>
            <router-link to="/MainPage">主页</router-link>
          </a-breadcrumb-item>
          <a-breadcrumb-item>
            <span>菜品</span>
          </a-breadcrumb-item>
        </a-breadcrumb>
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="菜品列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="food in foodList" :key="food.id">
                <a slot="actions" @click="addFoodToCart(food)">添加</a>
                <a-list-item-meta :description="food.type">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div>{{food.price}}元</div>
              </a-list-item>
            </a-list>
          </a-tab-pane>
          <a-tab-pane tab="购物车" key="2">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="(food, index) in foodInCart" :key="food.id">
                <a-list-item-meta :description="food.type">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div style="margin-right: 20px">{{food.price}}元</div>
                <div>
                  <a slot="actions" @click="minus(index)" class="number-input">-</a>
                  <a slot="description" class="number-input">{{foodNumberList[index]}}份</a>
                  <a slot="actions" @click="plus(index)" class="number-input">+</a>
                </div>
              </a-list-item>
            </a-list>
            <div style="padding: 24px 0 0 24px;" v-if="foodNumberList.length > 0">
              <span style="font-size: 16px">总价：{{this.totalAmount}}元</span>
              <a-button style="float: right" type="primary" @click="submit">结算</a-button>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'ShoppingCart',
  data () {
    return {
      foodList: [],
      foodInCart: [],
      foodNumberList: []
    };
  },
  computed: {
    totalAmount () {
      let sum = 0;
      let length = Math.min(this.foodInCart.length, this.foodNumberList.length);
      for (let i = 0; i < length; i++) {
        sum += this.foodInCart[i].price * this.foodNumberList[i];
      }
      return sum;
    },
    ...mapGetters(['restaurantInfo', 'userInfo', 'baseUrl'])
  },
  mounted () {
    this.$http({
      url: `${this.baseUrl}/food/getAll/${this.restaurantInfo.id}`,
      method: 'GET'
    }).then((response) => {
      let object = response.data;
      this.foodList = object.data;
    });
  },
  methods: {
    addFoodToCart (food) {
      const index = this.foodInCart.indexOf(food);
      if (index < 0) {
        this.foodInCart.push(food);
        this.foodNumberList.push(1);
      } else {
        const newNumber = this.foodNumberList[index] += 1;
        this.foodNumberList.splice(index, 1, newNumber);
      }
    },
    minus (index) {
      if (this.foodNumberList[index] === 1) {
        this.foodInCart.splice(index, 1);
        this.foodNumberList.splice(index, 1);
      } else {
        const newNumber = this.foodNumberList[index] -= 1;
        this.foodNumberList.splice(index, 1, newNumber);
      }
    },
    plus (index) {
      const newNumber = this.foodNumberList[index] += 1;
      this.foodNumberList.splice(index, 1, newNumber);
    },
    submit () {
      this.$http({
        url: this.baseUrl + '/order/submit',
        method: 'POST',
        params: {
          restaurantId: this.restaurantInfo.id,
          memberEmail: this.userInfo.userId,
          totalAmount: this.totalAmount,
          discount: this.totalAmount * this.userInfo.userLevel * 0.01
        },
        data: this.getFoodDetails()
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.foodInCart = [];
          this.foodNumberList = [];
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    getFoodDetails () {
      let result = [];
      for (let i = 0; i < this.foodInCart.length; i++) {
        result.push({
          foodId: this.foodInCart[i].id,
          foodNum: this.foodNumberList[i],
          price: this.foodInCart[i].price
        });
      }
      return result;
    }
  }
};
</script>

<style scoped>
  .number-input {
    margin: 0 5px;
    width: 20px;
    font-size: 16px;
  }
</style>
