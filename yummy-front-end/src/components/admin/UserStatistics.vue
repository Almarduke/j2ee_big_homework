<template>
  <div class="container">
    <div ref="MemberStatistics" class="pie-chart"></div>
    <div ref="RestaurantStatistics" class="pie-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'UserStatistics',
  computed: {
    ...mapGetters(['baseUrl', 'colorBoard'])
  },
  mounted () {
    this.setMemberData();
    this.setRestaurantData();
  },
  methods: {
    setMemberData () {
      // let myChart = this.$echarts.init(this.$refs.MemberStatistics);
      // this.$http({
      //   url: `${this.baseUrl}/admin/getMemberStatistics`,
      //   method: 'GET'
      // }).then((response) => {
      //   const code = response.data.code;
      //   if (code === OK) {
      //     myChart.setOption(this.getOption(response.data.data, true));
      //   } else {
      //     this.$message.error(response.data.msg);
      //   }
      // });
      const myChart = this.$echarts.init(this.$refs.MemberStatistics);
      const data = [
        { name: '华东', value: 53827 },
        { name: '华南', value: 44231 },
        { name: '华北', value: 32970 },
        { name: '中部', value: 33684 },
        { name: '东北', value: 23923 },
        { name: '西部', value: 17929 }
      ];
      myChart.setOption(this.getOption(data, true));
    },
    setRestaurantData () {
      // let myChart = this.$echarts.init(this.$refs.RestaurantStatistics);
      // this.$http({
      //   url: `${this.baseUrl}/admin/getRestaurantStatistics`,
      //   method: 'GET'
      // }).then((response) => {
      //   const code = response.data.code;
      //   if (code === OK) {
      //     myChart.setOption(this.getOption(response.data.data, false));
      //   } else {
      //     this.$message.error(response.data.msg);
      //   }
      // });

      const myChart = this.$echarts.init(this.$refs.RestaurantStatistics);
      const data = [
        { name: '华东', value: 3827 },
        { name: '华南', value: 4231 },
        { name: '华北', value: 2970 },
        { name: '中部', value: 3681 },
        { name: '东北', value: 3925 },
        { name: '西部', value: 2928 }
      ];
      myChart.setOption(this.getOption(data, false));
    },
    getOption (data, isMember) {
      let title;
      let subTitle;
      let tooltipTitle;
      let position;
      let formatter;
      let color;
      let legendData = data.map((item) => {
        return item.name;
      });

      if (isMember) {
        title = '会员统计';
        subTitle = '各地区会员人数';
        tooltipTitle = '会员所在地区';
        position = 'left';
        formatter = '{a} <br/>{b}: {c}人';
        color = ['#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8', '#FF9F7F', '#8378EA'];
      } else {
        title = '餐厅统计';
        subTitle = '不同地区的餐厅数';
        tooltipTitle = '餐厅所在地区';
        position = 'right';
        formatter = '{a} <br/>{b}: {c}家';
        color = ['#A92528', '#273A49', '#5AA1A9', '#E17B5A', '#8FD4B2', '#D5811E'];
      }

      return {
        textStyle: {
          fontSize: 14
        },
        title: {
          text: title,
          subtext: subTitle,
          x: 'center',
          textStyle: {
            fontSize: 18
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: formatter,
          textStyle: {
            fontSize: 16
          }
        },
        legend: {
          orient: 'vertical',
          left: position,
          data: legendData
        },
        series: [
          {
            name: tooltipTitle,
            type: 'pie',
            radius: '60%',
            center: ['50%', '50%'],
            data: data
          }
        ],
        color: color
      };
    }
  }
};
</script>

<style scoped>
  .pie-chart {
    height: 750px;
    width: 600px;
    display: inline-block;
  }
  .container {
    height: 750px;
    width: 1200px;
    min-width: 1200px;
    padding: 20px 0;
  }
</style>
