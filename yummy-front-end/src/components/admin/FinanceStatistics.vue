<template>
  <div class="container">
    <div ref="FinanceStatistics" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'FinanceStatistics',
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
      let myChart = this.$echarts.init(this.$refs.FinanceStatistics);
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
            '日交易额', '平台收入', '净利润', '分成比例', '毛利率'
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
            name: '日交易额',
            type: 'bar',
            data: [192, 374, 579, 734, 683, 498, 514, 374, 579, 734, 683, 498],
            itemStyle: {
              color: '#FF7000'
            }
          },
          {
            name: '平台收入',
            type: 'bar',
            data: [374, 683, 498, 514, 702, 698, 768, 374, 683, 498, 514, 702],
            itemStyle: {
              color: '#FFCC00'
            }
          },
          {
            name: '净利润',
            type: 'bar',
            data: [192, 374, 579, 498, 514, 822, 768, 579, 498, 514, 822, 768],
            itemStyle: {
              color: '#9ACD32'
            }
          },
          {
            name: '分成比例',
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
            name: '毛利率',
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
