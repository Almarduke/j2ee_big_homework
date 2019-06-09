<template>
  <div class="container">
    <div ref="DistrictSatisfaction" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'DistrictSatisfaction',
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
      let myChart = this.$echarts.init(this.$refs.DistrictSatisfaction);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        [98, 4.9, '北京'],
        [96, 4.2, '天津'],
        [95, 4.7, '上海'],
        [87, 4.9, '重庆'],
        [85, 3.7, '河北'],
        [92, 3.8, '辽宁'],
        [83, 4.1, '吉林'],
        [84, 4.3, '黑龙江'],
        [97, 4.6, '江苏'],
        [90, 4.4, '浙江'],
        [90, 4.1, '安徽'],
        [88, 3.8, '福建'],
        [83, 4.1, '江西'],
        [90, 4.8, '山东'],
        [82, 4.5, '河南'],
        [93, 4.0, '湖北'],
        [88, 4.5, '陕西'],
        [99, 3.8, '湖南'],
        [93, 4.4, '广东'],
        [83, 4.6, '海南'],
        [89, 4.0, '四川'],
        [87, 3.8, '贵州'],
        [92, 4.2, '云南'],
        [98, 4.4, '山西'],
        [86, 3.9, '甘肃'],
        [91, 4.5, '青海'],
        [88, 4.7, '台湾'],
        [85, 4.0, '内蒙古'],
        [97, 4.0, '广西'],
        [87, 4.3, '西藏'],
        [82, 3.8, '宁夏'],
        [90, 3.9, '新疆'],
        [91, 4.7, '香港'],
        [92, 4.9, '澳门']
      ];

      return {
        title: {
          left: 'center',
          text: '各省份服务情况',
          subtext: '基于客户评价和订单达成率'
        },
        toolbox: {
          right: 20,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        xAxis: {
          name: '订单达成率',
          min: 80,
          max: 100,
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
            formatter: '{value} %'
          }
        },
        yAxis: {
          name: '客户满意度',
          min: 3.6,
          max: 5,
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
            formatter: '{value} 星'
          },
          scale: true
        },
        series: [{
          name: '1990',
          data: data,
          type: 'scatter',
          symbolSize: 30,
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
                value[2] + '</div>' +
                '订单达成率：' + value[0] + '%<br>' +
                '用户满意度：' + value[1] + '星';
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
