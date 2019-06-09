<template>
  <div class="container">
    <div ref="DishSelling" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'CustomerChart',
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
      const data1 = [
        [7, 20, '大杯可乐'],
        [64, 13, '烤鱿鱼'],
        [31, 18, '大碗宽面'],
        [70, 15, '火锅'],
        [62, 9, '鱼香肉丝'],
        [24, 12, '炸薯条'],
        [47, 4, '柠檬蛋糕'],
        [70, 17, '寿司'],
        [80, 5, '牛排'],
        [40, 8, '咖喱饭'],
        [56, 5, '开水白菜'],
        [18, 8, '皮蛋瘦肉粥']
      ];

      const data2 = [
        [6, 22, '大杯可乐'],
        [52, 16, '烤鱿鱼'],
        [30, 17, '大碗宽面'],
        [75, 14, '火锅'],
        [54, 12, '鱼香肉丝'],
        [20, 20, '炸薯条'],
        [48, 5, '柠檬蛋糕'],
        [75, 18, '寿司'],
        [75, 7, '牛排'],
        [42, 8, '咖喱饭'],
        [60, 4, '开水白菜'],
        [18, 5, '皮蛋瘦肉粥']
      ];

      const data3 = [
        [8, 22, '大杯可乐'],
        [63, 11, '烤鱿鱼'],
        [30, 15, '大碗宽面'],
        [80, 12, '火锅'],
        [60, 8, '鱼香肉丝'],
        [24, 18, '炸薯条'],
        [50, 3, '柠檬蛋糕'],
        [73, 20, '寿司'],
        [78, 6, '牛排'],
        [45, 9, '咖喱饭'],
        [50, 6, '开水白菜'],
        [20, 6, '皮蛋瘦肉粥']
      ];

      return {
        title: {
          left: 'center',
          text: '各菜品销售情况',
          subtext: '波士顿矩阵'
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
          left: '70%',
          data: [
            '上上周数据', '上周数据', '本周数据'
          ]
        },
        xAxis: {
          name: '销售金额',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          axisLabel: {
            formatter: '{value} 元'
          }
        },
        yAxis: {
          name: '销售数量',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: [
          {
            data: data1,
            name: '上上周数据',
            type: 'scatter',
            symbolSize: 30,
            itemStyle: {
              color: '#5AA1A9',
              shadowBlur: 10,
              shadowColor: 'rgba(36, 36, 36, 0.5)',
              shadowOffsetY: 5
            },
            label: {
              show: true,
              position: 'inside',
              formatter: '{@[2]}'
            }
          },
          {
            data: data2,
            name: '上周数据',
            type: 'scatter',
            symbolSize: 30,
            itemStyle: {
              color: '#273A49',
              shadowBlur: 10,
              shadowColor: 'rgba(36, 36, 36, 0.5)',
              shadowOffsetY: 5
            },
            label: {
              show: true,
              position: 'inside',
              formatter: '{@[2]}'
            }
          },
          {
            data: data3,
            name: '本周数据',
            type: 'scatter',
            symbolSize: 30,
            itemStyle: {
              color: '#A92528',
              shadowBlur: 10,
              shadowColor: 'rgba(36, 36, 36, 0.5)',
              shadowOffsetY: 5
            },
            label: {
              show: true,
              position: 'inside',
              formatter: '{@[2]}'
            }
          }
        ],
        tooltip: {
          padding: 10,
          backgroundColor: '#222',
          borderColor: '#777',
          borderWidth: 1,
          formatter: function (obj) {
            const value = obj.value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">' +
                value[2] + '</div>' +
                '食物价格：' + value[0] + '元<br>' +
                '销售数量：' + value[1] + '<br>';
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
    width: 100%;
    display: inline-block;
  }
  .container {
    height: 700px;
    width: 100%;
    min-width: 1200px;
  }
</style>
