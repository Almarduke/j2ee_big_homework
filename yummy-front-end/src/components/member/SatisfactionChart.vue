<template>
  <div class="container">
    <div ref="SatisfactionChart" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'SatisfactionChart',
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
      const data = [
        [56, 23, 4.7, '金拱门'],
        [30, 20, 4.5, '黄焖鸡米饭'],
        [15, 45, 4.3, '兰州拉面'],
        [12, 30, 3.9, '四川燃面'],
        [18, 24, 4.0, '鸡鸣汤包'],
        [14, 14, 4.1, '罗森'],
        [80, 45, 4.2, '北京烤鸭'],
        [28, 35, 3.7, '麻辣香锅'],
        [70, 32, 4.1, '张记圆'],
        [31, 30, 4.4, '永和大王'],
        [48, 35, 4.9, '汉堡王'],
        [66, 48, 4.6, '新石器烤肉'],
        [85, 53, 5.0, '海底捞']
      ];
      let myChart = this.$echarts.init(this.$refs.SatisfactionChart);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      return {
        toolbox: {
          right: 20,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        xAxis: {
          name: '消费水平',
          axisLabel: {
            formatter: '{value}元'
          },
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
          name: '配送时间',
          axisLabel: {
            formatter: '{value}分钟'
          },
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
            return (data[2] - 3) * 60;
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
            formatter: '{@[3]}: {@[2]}星'
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
                '消费总额：' + value[0] + '元<br>' +
                '配送时间：' + value[1] + '分钟<br>' +
                '用户满意度：' + value[2] + '星';
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
