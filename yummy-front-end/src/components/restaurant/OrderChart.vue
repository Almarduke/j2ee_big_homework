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
      let data = [
        { day: '2019-05-26', orderNum: 14, paidOrderNum: 12, finishedOrderNum: 11 },
        { day: '2019-05-27', orderNum: 18, paidOrderNum: 17, finishedOrderNum: 15 },
        { day: '2019-05-28', orderNum: 16, paidOrderNum: 14, finishedOrderNum: 13 },
        { day: '2019-05-29', orderNum: 22, paidOrderNum: 19, finishedOrderNum: 17 },
        { day: '2019-05-30', orderNum: 20, paidOrderNum: 19, finishedOrderNum: 18 },
        { day: '2019-05-31', orderNum: 25, paidOrderNum: 22, finishedOrderNum: 20 },
        { day: '2019-06-01', orderNum: 14, paidOrderNum: 12, finishedOrderNum: 11 },
        { day: '2019-06-02', orderNum: 22, paidOrderNum: 19, finishedOrderNum: 17 },
        { day: '2019-06-03', orderNum: 18, paidOrderNum: 16, finishedOrderNum: 16 },
        { day: '2019-06-04', orderNum: 25, paidOrderNum: 22, finishedOrderNum: 20 },
        { day: '2019-06-05', orderNum: 15, paidOrderNum: 14, finishedOrderNum: 13 },
        { day: '2019-06-06', orderNum: 17, paidOrderNum: 15, finishedOrderNum: 14 },
        { day: '2019-06-07', orderNum: 19, paidOrderNum: 18, finishedOrderNum: 15 },
        { day: '2019-06-08', orderNum: 24, paidOrderNum: 21, finishedOrderNum: 20 },
        { day: '2019-06-09', orderNum: 22, paidOrderNum: 20, finishedOrderNum: 18 }
      ];
      let myChart = this.$echarts.init(this.$refs.OrderChart);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      let dayList = data.map((record) => {
        return record.day;
      });
      let orderList = data.map((record) => {
        return record.orderNum;
      });
      let paidOrderList = data.map((record) => {
        return record.paidOrderNum;
      });
      let finishedOrderList = data.map((record) => {
        return record.finishedOrderNum;
      });
      let paymentRate = data.map((record) => {
        return (record.paidOrderNum / record.orderNum * 100).toFixed(1);
      });
      let finishRate = data.map((record) => {
        return (record.finishedOrderNum / record.paidOrderNum * 100).toFixed(1);
      });

      return {
        title: {
          left: 'center',
          text: '订单统计',
          subtext: '订单情况和转化率'
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
            '订单总数', '付款订单数', '完成订单数', '付款率', '成交率'
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
            name: '订单数量',
            min: 0,
            max: 30,
            position: 'left',
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '订单转化率',
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
            name: '订单总数',
            type: 'bar',
            data: orderList,
            itemStyle: {
              color: '#FF7000'
            }
          },
          {
            name: '付款订单数',
            type: 'bar',
            data: paidOrderList,
            itemStyle: {
              color: '#FFCC00'
            }
          },
          {
            name: '完成订单数',
            type: 'bar',
            data: finishedOrderList,
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
            smooth: true,
            yAxisIndex: 1,
            data: paymentRate
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
            smooth: true,
            yAxisIndex: 1,
            data: finishRate
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
