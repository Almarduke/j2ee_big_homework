<template>
  <div class="container">
    <div ref="DishSelling" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'DishSelling',
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
      let myChart = this.$echarts.init(this.$refs.DishSelling);
      myChart.setOption(this.getOption());
    },
    getOption () {
      return {
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
          data: [
            '上上周营业额', '上周营业额', '本周营业额', '营业额增长率'
          ]
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: [
              '烤鱿鱼', '大碗宽面', '开水白菜', '火锅',
              '寿司', '牛排', '皮蛋瘦肉粥'
            ]
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '营业额',
            min: 0,
            max: 1000,
            position: 'left',
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '营业额增长率',
            min: -100,
            max: 100,
            position: 'right',
            axisLabel: {
              formatter: '{value} %'
            },
            splitLine: {
              lineStyle: {
                type: 'dotted'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#888',
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '上上周营业额',
            type: 'bar',
            data: [192, 374, 579, 734, 683, 498, 514],
            itemStyle: {
              color: '#5AA1A9'
            }
          },
          {
            name: '上周营业额',
            type: 'bar',
            data: [374, 683, 498, 514, 702, 698, 768],
            itemStyle: {
              color: '#273A49'
            }
          },
          {
            name: '本周营业额',
            type: 'bar',
            data: [192, 374, 579, 498, 514, 822, 768],
            itemStyle: {
              color: '#A92528'
            }
          },
          {
            name: '营业额增长率',
            type: 'line',
            itemStyle: {
              color: '#FFCC00',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1,
            data: [-12, 40, 34, -10, -7, -8, 20, -4, 25, -9, 7, 9]
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
