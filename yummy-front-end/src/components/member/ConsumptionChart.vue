<template>
  <div>
    <a-layout style="height: 700px; background-color: white">
      <a-layout-sider width="120" style="background: #fff">
        <a-menu mode="inline" :defaultSelectedKeys="['week']" style="height: 100%" @click="handleClick">
          <a-menu-item key="week" >1周内</a-menu-item>
          <a-menu-item key="month">1个月内</a-menu-item>
          <a-menu-item key="season">3个月内</a-menu-item>
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

      let data = [];
      switch (key) {
        case 'week':
          data = [
            [56, 2, '金拱门'],
            [30, 1, '黄焖鸡米饭'],
            [15, 2, '兰州拉面'],
            [12, 3, '四川燃面'],
            [18, 1, '鸡鸣汤包'],
            [14, 4, '罗森']
          ];
          break;
        case 'month':
          data = [
            [56, 6, '金拱门'],
            [30, 8, '黄焖鸡米饭'],
            [15, 7, '兰州拉面'],
            [12, 5, '四川燃面'],
            [18, 6, '鸡鸣汤包'],
            [14, 9, '罗森'],
            [80, 2, '北京烤鸭'],
            [28, 4, '麻辣香锅'],
            [72, 4, '张记圆'],
            [31, 5, '永和大王']
          ];
          break;
        case 'season':
          data = [
            [56, 10, '金拱门'],
            [30, 17, '黄焖鸡米饭'],
            [15, 20, '兰州拉面'],
            [12, 10, '四川燃面'],
            [18, 9, '鸡鸣汤包'],
            [14, 14, '罗森'],
            [80, 5, '北京烤鸭'],
            [28, 8, '麻辣香锅'],
            [70, 6, '张记圆'],
            [31, 9, '永和大王'],
            [48, 3, '汉堡王'],
            [66, 4, '新石器烤肉'],
            [85, 2, '海底捞']
          ];
      }
      const maxNum = Math.max(...data.map((record) => {
        return record[0] * record[1];
      }));

      let myChart = this.$echarts.init(this.$refs.ConsumptionChart);
      myChart.setOption(this.getOption(data, maxNum));
    },
    getOption (data, maxNum) {
      return {
        title: {
          left: 'center',
          text: '消费过的饭店信息',
          subtext: '基于本用户数据'
        },
        toolbox: {
          right: 60,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
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
          },
          axisLabel: {
            formatter: '{value}元'
          }
        },
        yAxis: {
          name: '消费次数',
          min: 0,
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          scale: true,
          axisLabel: {
            formatter: '{value}次'
          }
        },
        series: [{
          name: '1990',
          data: data,
          type: 'scatter',
          symbolSize: (data) => {
            return Math.max(data[0] * data[1] / maxNum * 160, 80);
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
          },
          label: {
            show: true,
            position: 'inside',
            formatter: (params) => {
              const data = params.data;
              return `${data[2]}:\n${Math.floor(data[0] * data[1])}元`;
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
                value[2] + '</div>' +
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
