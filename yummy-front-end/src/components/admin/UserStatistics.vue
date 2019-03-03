<template>
  <div class="container">
    <div id="MemberStatistics" class="pie-chart"></div>
    <div id="RestaurantStatistics" class="pie-chart"></div>
  </div>
</template>

<script>
export default {
  name: 'UserStatistics',
  data () {
    return {
    };
  },
  mounted () {
    this.setMemberData();
    this.setRestaurantData();
  },
  methods: {
    setMemberData () {
      let myChart = this.$echarts.init(document.getElementById('MemberStatistics'));

      let data = [
        { name: 'Lv0', value: 4345 },
        { name: 'Lv1', value: 3564 },
        { name: 'Lv2', value: 5947 },
        { name: 'Lv3', value: 3664 },
        { name: 'Lv4', value: 4137 },
        { name: 'Lv5', value: 2807 }
      ];
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(this.getOption(data, true));
    },
    setRestaurantData () {
      let myChart = this.$echarts.init(document.getElementById('RestaurantStatistics'));

      let data = [
        { name: 'Lv0', value: 4345 },
        { name: 'Lv1', value: 3564 },
        { name: 'Lv2', value: 5947 },
        { name: 'Lv3', value: 3664 },
        { name: 'Lv4', value: 4137 },
        { name: 'Lv5', value: 2807 }
      ];
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(this.getOption(data, false));
    },
    getOption (data, isMember) {
      let title;
      let subTitle;
      let tooltipTitle;
      let position;
      let color;
      let legendData = data.map((item) => {
        return item.name;
      });

      if (isMember) {
        title = '会员统计';
        subTitle = '各等级会员人数';
        tooltipTitle = '会员等级';
        position = 'left';
        color = ['#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8', '#FF9F7F', '#8378EA'];
      } else {
        title = '餐厅统计';
        subTitle = '各营业额餐厅数';
        tooltipTitle = '餐厅营业额';
        position = 'right';
        color = [
          '#A92528', '#273A49',
          '#5AA1A9', '#E17B5A',
          '#8FD4B2', '#D5811E'
        ];
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
          formatter: '{a} <br/>{b}: {c}人',
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
    height: 600px;
    width: 600px;
    display: inline-block;
  }
  .container {
    height: 640px;
    width: 1200px;
    min-width: 1200px;
    padding: 20px 0;
  }
</style>
