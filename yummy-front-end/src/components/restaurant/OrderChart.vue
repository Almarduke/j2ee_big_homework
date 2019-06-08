<template>
  <div class="container">
    <div ref="OrderChart" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'OrderChart',
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
      let myChart = this.$echarts.init(this.$refs.OrderChart);
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
            '订单数', '付款订单数', '完成订单数', '付款率', '成交率'
          ]
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: [
              '2019-06-01', '2019-06-02', '2019-06-03', '2019-06-04',
              '2019-06-05', '2019-06-06', '2019-06-07', '2019-06-08',
              '2019-06-09', '2019-06-10', '2019-06-11', '2019-06-12'
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
            name: '订单总数',
            type: 'bar',
            data: [192, 374, 579, 734, 683, 498, 514, 374, 579, 734, 683, 498],
            itemStyle: {
              color: '#FF7000'
            }
          },
          {
            name: '付款订单数',
            type: 'bar',
            data: [374, 683, 498, 514, 702, 698, 768, 374, 683, 498, 514, 702],
            itemStyle: {
              color: '#FFCC00'
            }
          },
          {
            name: '完成订单数',
            type: 'bar',
            data: [192, 374, 579, 498, 514, 822, 768, 579, 498, 514, 822, 768],
            itemStyle: {
              color: '#9ACD32'
            }
          },
          {
            name: '付款率',
            type: 'line',
            itemStyle: {
              color: '#4D80E6',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1,
            data: [87, 79, 80, 72, 86, 75, 80, 80, 92, 95, 87, 93]
          },
          {
            name: '成交率',
            type: 'line',
            itemStyle: {
              color: '#0000CD',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1,
            data: [90, 88, 82, 84, 78, 81, 90, 85, 92, 87, 79, 83]
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
