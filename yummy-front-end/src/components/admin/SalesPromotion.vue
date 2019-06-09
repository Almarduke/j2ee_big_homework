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
        [37, 9, '北京'],
        [30, 8, '天津'],
        [34, 11, '上海'],
        [26, 16, '重庆'],
        [12, 8, '河北'],
        [11, -8, '辽宁'],
        [20, -4, '吉林'],
        [13, 2, '黑龙江'],
        [36, 7, '江苏'],
        [38, 14, '浙江'],
        [19, 4, '安徽'],
        [25, 7, '福建'],
        [23, 10, '江西'],
        [28, 12, '山东'],
        [29, 5, '河南'],
        [35, 9, '湖北'],
        [32, 8, '陕西'],
        [32, 16, '湖南'],
        [39, 10, '广东'],
        [21, 10, '海南'],
        [19, 12, '四川'],
        [16, 10, '贵州'],
        [22, 7, '云南'],
        [20, 8, '山西'],
        [10, 6, '甘肃'],
        [11, -1, '青海'],
        [15, 4, '台湾'],
        [9, 1, '内蒙古'],
        [14, 11, '广西'],
        [6, -6, '西藏'],
        [10, -3, '宁夏'],
        [8, -2, '新疆'],
        [17, 2, '香港'],
        [24, 0, '澳门']
      ];

      const data2 = [
        [37, 4, '北京'],
        [30, 6, '天津'],
        [34, 8, '上海'],
        [26, 7, '重庆'],
        [12, 4, '河北'],
        [11, -6, '辽宁'],
        [20, -2, '吉林'],
        [13, 3, '黑龙江'],
        [36, 8, '江苏'],
        [38, 10, '浙江'],
        [19, 7, '安徽'],
        [25, 5, '福建'],
        [23, 6, '江西'],
        [28, 10, '山东'],
        [29, 7, '河南'],
        [35, 5, '湖北'],
        [32, 5, '陕西'],
        [32, 12, '湖南'],
        [39, 8, '广东'],
        [21, 4, '海南'],
        [19, 7, '四川'],
        [16, 6, '贵州'],
        [22, 8, '云南'],
        [20, 6, '山西'],
        [10, 3, '甘肃'],
        [11, 0, '青海'],
        [15, 3, '台湾'],
        [9, -1, '内蒙古'],
        [14, 4, '广西'],
        [6, 1, '西藏'],
        [10, 0, '宁夏'],
        [8, -1, '新疆'],
        [17, 4, '香港'],
        [24, 2, '澳门']
      ];

      return {
        title: {
          left: 'center',
          text: '各省份促销激励情况'
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
            '订单增长率', '营业额增长率'
          ]
        },
        xAxis: {
          name: '促销金额',
          min: 0,
          max: 40,
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          axisLabel: {
            formatter: '{value} 万元'
          }
        },
        yAxis: {
          name: '增长率',
          min: -10,
          max: 20,
          axisLabel: {
            formatter: '{value} %'
          },
          splitLine: {
            lineStyle: {
              type: 'dotted'
            }
          }
        },
        series: [
          {
            data: data1,
            name: '订单增长率',
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
            data: data2,
            name: '营业额增长率',
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
                '促销金额：' + value[0] + '万元<br>' +
                obj.seriesName + '：' + value[1] + '%<br>';
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
