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
  name: 'DishChart',
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
        [244, 12, 'Australia'],
        [132, 32, 'Canada'],
        [73, 80, 'China'],
        [467, 74, 'Cuba'],
        [359, 20, 'Finland'],
        [294, 77, 'France'],
        [314, 32, 'Germany'],
        [266, 29, 'Iceland'],
        [177, 33, 'India'],
        [250, 42, 'Japan'],
        [873, 67, 'North Korea'],
        [532, 21, 'South Korea'],
        [24, 23, 'New Zealand'],
        [46, 53, 'Norway'],
        [100, 58, 'Poland'],
        [234, 69, 'Russia'],
        [106, 67, 'Turkey'],
        [93, 75, 'United Kingdom'],
        [37, 75, 'United States']
      ];

      return {
        toolbox: {
          right: 60,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        xAxis: {
          name: '消费金额',
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
          symbolSize: 20,
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
            formatter: '{@[2]}'
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
                '菜品类型：' + value[2] + '</div>' +
                '消费金额：' + value[0] + '<br>' +
                '消费次数：' + value[1];
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
