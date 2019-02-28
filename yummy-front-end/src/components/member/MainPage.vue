<template>
  <div style="height: 700px; background-color: white">
    <a-card title="商家列表">
      <a-card-grid class="info-card" v-for="restaurantInfo in restaurantList"
                   :key="restaurantInfo.name" @click="selectRestaurant(restaurantInfo)">
        <div class="container">
          <a-row style="width: 240px">
            <a-col :span="8">
              <div class="img-style">
                <img src="../../assets/eleme.jpg" alt=""/>
              </div>
            </a-col>
            <a-col :span="16">
              <div class="text-style">
                <h3>{{restaurantInfo.name}}<br/></h3>
                <h4>{{restaurantInfo.address}}<br/></h4>
                <span>{{restaurantInfo.phone}}</span>
              </div>
            </a-col>
          </a-row>
        </div>
      </a-card-grid>
    </a-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'MainPage',
  data () {
    return {
      restaurantList: [
        { name: '', address: '', phone: '' }
      ]
    };
  },
  computed: {
    ...mapGetters(['userInfo', 'baseUrl'])
  },
  mounted () {
    this.$http({
      url: this.baseUrl + '/restaurant/getAll',
      method: 'GET'
    }).then((response) => {
      let object = response.data;
      this.restaurantList = object.data;
    });
  },
  methods: {
    selectRestaurant (key) {
      this.$router.push('/ShoppingCart');
    }
  }
};
</script>

<style scoped>
  .info-card {
    width: 25%;
    min-width: 240px;
  }
  .container {
    height: 80px;
    width: 240px;
    display: flex;
  }
  .img-style {
    width: 80px;
    height: 80px;
  }
  .text-style {
    padding-left: 20px;
    text-align: left;
  }
</style>
