<template>
  <div>
    <a-layout style="height: 700px; background-color: white">
      <a-layout-sider width="120" style="background: #fff">
        <a-menu mode="inline" :defaultSelectedKeys="['week']" style="height: 100%" @click="handleClick">
          <a-menu-item key="week" >1周内</a-menu-item>
          <a-menu-item key="month">1个月内</a-menu-item>
          <a-menu-item key="seaon">3个月内</a-menu-item>
        </a-menu>
      </a-layout-sider>

      <a-layout-content style="height: 750px;">
        <div ref="ConsumptionChart" class="bubble-chart"></div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'ConsumptionChart',
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
      let myChart = this.$echarts.init(this.$refs.ConsumptionChart);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        [28604, 77, 17096869, 'Australia'],
        [31163, 77.4, 27662440, 'Canada'],
        [1516, 68, 1154605773, 'China'],
        [13670, 74.7, 10582082, 'Cuba'],
        [28599, 75, 4986705, 'Finland'],
        [29476, 77.1, 56943299, 'France'],
        [31476, 75.4, 78958237, 'Germany'],
        [28666, 78.1, 254830, 'Iceland'],
        [1777, 57.7, 870601776, 'India'],
        [29550, 79.1, 122249285, 'Japan'],
        [2076, 67.9, 20194354, 'North Korea'],
        [12087, 72, 42972254, 'South Korea'],
        [24021, 75.4, 3397534, 'New Zealand'],
        [43296, 76.8, 4240375, 'Norway'],
        [10088, 70.8, 38195258, 'Poland'],
        [19349, 69.6, 147568552, 'Russia'],
        [10670, 67.3, 53994605, 'Turkey'],
        [26424, 75.7, 57110117, 'United Kingdom'],
        [37062, 75.4, 252847810, 'United States']
      ];

      return {
        xAxis: {
          name: '平均消费',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          }
        },
        yAxis: {
          name: '消费次数',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          scale: true
        },
        series: [{
          name: '1990',
          data: data,
          type: 'scatter',
          symbolSize: (data) => {
            return Math.max(data[0] * data[1] / 3333792 * 100, 10);
          },
          itemStyle: {
            normal: {
              color: (data) => {
                return this.colorBoard[data.dataIndex % this.colorBoard.length];
              },
              shadowBlur: 10,
              shadowColor: 'rgba(36, 36, 36, 0.5)',
              shadowOffsetY: 5
            }
          }
        }],
        tooltip: {
          padding: 10,
          backgroundColor: '#222',
          borderColor: '#777',
          borderWidth: 1,
          formatter: function (obj) {
            const value = obj.value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">' +
                value[3] + '</div>' +
                '平均消费额：' + value[0] + '<br>' +
                '消费次数：' + value[1] + '<br>' +
                '消费总额：' + value[0] * value[1];
          }
        }
      };
    }
  }
};
</script>

<style scoped>
  .bubble-chart {
    height: 700px;
    width: 1200px;
    display: inline-block;
  }
</style>
