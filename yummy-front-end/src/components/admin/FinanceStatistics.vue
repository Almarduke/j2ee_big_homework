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
      let data = [
        { day: '2019-05-26', deal: 14, income: 12, profit: 11 },
        { day: '2019-05-27', deal: 18, income: 17, profit: 15 },
        { day: '2019-05-28', deal: 16, income: 14, profit: 13 },
        { day: '2019-05-29', deal: 22, income: 19, profit: 17 },
        { day: '2019-05-30', deal: 20, income: 19, profit: 18 },
        { day: '2019-05-31', deal: 25, income: 22, profit: 20 },
        { day: '2019-06-01', deal: 14, income: 12, profit: 11 },
        { day: '2019-06-02', deal: 22, income: 19, profit: 17 },
        { day: '2019-06-03', deal: 18, income: 16, profit: 16 },
        { day: '2019-06-04', deal: 25, income: 22, profit: 20 },
        { day: '2019-06-05', deal: 15, income: 14, profit: 13 },
        { day: '2019-06-06', deal: 17, income: 15, profit: 14 },
        { day: '2019-06-07', deal: 19, income: 18, profit: 15 },
        { day: '2019-06-08', deal: 24, income: 21, profit: 20 },
        { day: '2019-06-09', deal: 22, income: 20, profit: 18 }
      ];
      let myChart = this.$echarts.init(this.$refs.FinanceStatistics);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      let dayList = data.map((record) => {
        return record.day;
      });
      let dealList = data.map((record) => {
        return record.deal;
      });
      let incomeList = data.map((record) => {
        return record.income;
      });
      let profitList = data.map((record) => {
        return record.profit;
      });
      let shareRate = data.map((record) => {
        return (record.income / record.deal * 100).toFixed(1);
      });
      let grossRate = data.map((record) => {
        return (record.profit / record.income * 100).toFixed(1);
      });
      return {
        title: {
          left: 'center',
          text: '平台每日收入情况'
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
          left: '60%',
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
            data: dayList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '营业额',
            min: 0,
            max: 30,
            position: 'left',
            axisLabel: {
              formatter: '{value} 元'
            }
          },
          {
            type: 'value',
            name: '转化率',
            min: 0,
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
            data: dealList,
            itemStyle: {
              color: '#B399FF'
            }
          },
          {
            name: '平台收入',
            type: 'bar',
            data: incomeList,
            itemStyle: {
              color: '#5686BF'
            }
          },
          {
            name: '净利润',
            type: 'bar',
            data: profitList,
            itemStyle: {
              color: '#FF8099'
            }
          },
          {
            name: '分成比例',
            type: 'line',
            itemStyle: {
              color: '#FFCC00',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1,
            data: shareRate
          },
          {
            name: '毛利率',
            type: 'line',
            itemStyle: {
              color: '#008000',
              opacity: 1
            },
            lineStyle: {
              width: 5
            },
            yAxisIndex: 1,
            data: grossRate
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
