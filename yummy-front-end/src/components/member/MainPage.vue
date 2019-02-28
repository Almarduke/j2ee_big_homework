<template>
  <div style="height: 800px; background-color: white">
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
export default {
  name: 'MainPage',
  data () {
    return {
      restaurantList: [
        { name: '', address: '', phone: '' }
      ]
    };
  },
  mounted () {
    this.restaurantList = [
      { name: '兰州拉面', address: '汉口路43号', phone: '13444322522' },
      { name: '东吴面馆', address: '汉口路48号', phone: '13476447522' },
      { name: '星巴克', address: '荔枝广场', phone: '134445432522' },
      { name: '汉堡王', address: '新街口', phone: '13476443532' },
      { name: '韩江道', address: '艾尚天地', phone: '13476347522' },
      { name: '肯德基', address: '广州路102号', phone: '1345232522' },
      { name: '麦当劳', address: '新街口', phone: '13476243532' },
      { name: '汉堡王2', address: '新街口', phone: '13476443532' },
      { name: '韩江道2', address: '艾尚天地', phone: '13476347522' }
    ];
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
