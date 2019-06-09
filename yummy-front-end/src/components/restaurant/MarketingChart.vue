<template>
  <div class="container">
    <div ref="MarketingChart" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'MarketingChart',
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
      let data = [
        { day: '2019-05-26', value: 2030 }, { day: '2019-05-27', value: 2394 },
        { day: '2019-05-28', value: 2576 }, { day: '2019-05-29', value: 2431 },
        { day: '2019-05-30', value: 2295 }, { day: '2019-05-31', value: 2154 },
        { day: '2019-06-01', value: 2341 }, { day: '2019-06-02', value: 2598 },
        { day: '2019-06-03', value: 2713 }, { day: '2019-06-04', value: 2547 },
        { day: '2019-06-05', value: 2433 }, { day: '2019-06-06', value: 2350 },
        { day: '2019-06-07', value: 2589 }, { day: '2019-06-08', value: 2314 },
        { day: '2019-06-09', value: 2475 }
      ];
      let myChart = this.$echarts.init(this.$refs.MarketingChart);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      let dayList = data.map((record) => {
        return record.day;
      });
      let revenueList = data.map((record) => {
        return record.value;
      });
      let growthRateList = data.map((record, index, array) => {
        return index === 0 ? 0 : ((record.value / array[index - 1].value - 1) * 100).toFixed(1);
      });
      return {
        title: {
          left: 'center',
          text: '饭店每日经营情况',
          subtext: '基于营业额和增长率'
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
          data: [
            '当日营业额',
            '营业额增长率'
          ]
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: dayList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '当日营业额',
            min: 1500,
            position: 'left',
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '营业额增长率',
            min: -50,
            max: 50,
            position: 'right',
            axisLabel: {
              formatter: '{value} %'
            },
            splitLine: {
              lineStyle: {
                type: 'dotted'
              }
            }
          }
        ],
        series: [
          {
            name: '当日营业额(元)',
            type: 'bar',
            data: revenueList,
            barWidth: 40,
            itemStyle: {
              normal: {
                color: (data) => {
                  if (data.value <= 2100) {
                    return '#273A49';
                  } else if (data.value <= 2300) {
                    return '#5AA1A9';
                  } else if (data.value <= 2500) {
                    return '#AAAAAA';
                  } else if (data.value <= 2700) {
                    return '#C17B5A';
                  } else {
                    return '#A92528';
                  }
                },
                width: 20,
                opacity: 0.9
              }
            }
          },
          {
            name: '营业额增长率(%)',
            type: 'line',
            data: growthRateList,
            itemStyle: {
              color: '#FFCC00',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1
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
