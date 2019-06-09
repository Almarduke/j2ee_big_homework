<template>
  <div class="container">
    <div ref="RestaurantMarketing" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'RestaurantMarketing',
  computed: {
    ...mapGetters(['baseUrl', 'colorBoard'])
  },
  mounted () {
    this.setData('week');
  },
  methods: {
    handleClick (item) {
      this.setData(item.key);
    },
    setData (key) {
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
      let myChart = this.$echarts.init(this.$refs.RestaurantMarketing);
      myChart.setOption(this.getOption());
    },
    getOption () {
      return {
        title: {
          left: 'center',
          text: '各地区饭店销售总额'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        toolbox: {
          right: 20,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        legend: {
          left: '70%',
          data: [
            '上上周数据', '上周数据', '本周数据'
          ]
        },
        xAxis: [
          {
            type: 'category',
            name: '饭店所在区域',
            axisTick: {
              alignWithLabel: true
            },
            data: [
              '华东', '华南', '华北', '中部',
              '西部', '东北'
            ]
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '销售总额',
            min: 0,
            max: 1000,
            position: 'left',
            axisLabel: {
              formatter: '{value} 分钟'
            }
          }
        ],
        series: [
          {
            name: '上上周数据',
            type: 'bar',
            data: [192, 374, 579, 683, 498, 514],
            itemStyle: {
              color: '#5AA1A9'
            }
          },
          {
            name: '上周数据',
            type: 'bar',
            data: [374, 683, 498, 514, 698, 768],
            itemStyle: {
              color: '#273A49'
            }
          },
          {
            name: '本周数据',
            type: 'bar',
            data: [192, 374, 579, 498, 514, 822],
            itemStyle: {
              color: '#A92528'
            }
          }
        ]
      };
    }
  }
};
</script>

<style scoped>
  .bubble-chart {
    height: 700px;
    width: 1280px;
    display: inline-block;
  }
  .container {
    height: 700px;
    width: 100%;
    min-width: 1280px;
  }
</style>
