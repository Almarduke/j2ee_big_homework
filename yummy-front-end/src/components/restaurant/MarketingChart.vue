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
      let myChart = this.$echarts.init(this.$refs.ConsumptionChart);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const dayList = [
        '2019-06-03', '2019-06-04', '2019-06-05', '2019-06-06',
        '2019-06-07', '2019-06-08', '2019-06-09'
      ];

      return {
        xAxis: {
          name: '日期',
          data: dayList,
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
        yAxis: [
          {
            name: '收入',
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
          {
            name: '收入增长率（%）',
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
          }
        ],
        series: [
          {
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: 'line',
            smooth: true
          },
          [{
            data: [12, 13, -10, 11, 23, 13, -2],
            type: 'line',
            smooth: true
          }]
        ]
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
