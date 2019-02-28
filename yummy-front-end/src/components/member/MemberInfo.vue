<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/avator.jpg" alt="" slot="cover"/>
        <a-card-meta title="Europe Street beat">
          <p slot="description">饭店的电话</p>
          <p slot="description">饭店地址</p>
        </a-card-meta>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="菜品列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="food in foodList" :key="food.id">
                <a slot="actions" @click="addFoodToCart(food)">添加</a>
                <a-list-item-meta :description="food.description">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div>{{food.price}}元</div>
              </a-list-item>
            </a-list>
          </a-tab-pane>
          <a-tab-pane tab="购物车" key="2">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="(food, index) in foodInCart" :key="food.id">
                <a-list-item-meta :description="food.description">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div style="margin-right: 20px">{{food.price}}元</div>
                <div>
                  <a slot="actions" @click="minus(food)" class="number-input">-</a>
                  <a slot="description" class="number-input">{{foodNumberList[index]}}份</a>
                  <a slot="actions" @click="plus(food)" class="number-input">+</a>
                </div>
              </a-list-item>
            </a-list>
            <div style="padding: 24px 0 0 24px;" v-if="foodNumberList.length > 0">
              <span style="font-size: 16px">总价：{{this.totalAmount}}元</span>
              <a-button style="float: right" type="primary" size="large">结算</a-button>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
export default {
  name: 'ShoppingCart',
  data () {
    return {
      foodList: [
        { id: 0, name: '蛋炒饭', price: 3, description: '我觉得可以' },
        { id: 1, name: '土豆', price: 76, description: '我觉得不行' }
      ],
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
    }
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
    minus (food) {
      const index = this.foodInCart.indexOf(food);
      if (this.foodNumberList[index] === 1) {
        this.foodInCart.splice(index, 1);
        this.foodNumberList.splice(index, 1);
      } else {
        const newNumber = this.foodNumberList[index] -= 1;
        this.foodNumberList.splice(index, 1, newNumber);
      }
    },
    plus (food) {
      const index = this.foodInCart.indexOf(food);
      const newNumber = this.foodNumberList[index] += 1;
      this.foodNumberList.splice(index, 1, newNumber);
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
