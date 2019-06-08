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
      let myChart = this.$echarts.init(this.$refs.SatisfactionChart);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        [244, 12, 4.5, 'Australia'],
        [132, 32, 5, 'Canada'],
        [73, 80, 3, 'China'],
        [467, 74, 4.3, 'Cuba'],
        [359, 20, 3, 'Finland'],
        [294, 77, 2, 'France'],
        [314, 32, 4.2, 'Germany'],
        [266, 29, 3.4, 'Iceland'],
        [177, 33, 1.4, 'India'],
        [250, 42, 3.6, 'Japan'],
        [873, 67, 4.7, 'North Korea'],
        [532, 21, 4.4, 'South Korea'],
        [24, 23, 3.4, 'New Zealand'],
        [46, 53, 5, 'Norway'],
        [100, 58, 3, 'Poland'],
        [234, 69, 2.8, 'Russia'],
        [106, 67, 5, 'Turkey'],
        [93, 75, 2.3, 'United Kingdom'],
        [37, 75, 2, 'United States']
      ];

      return {
        xAxis: {
          name: '消费金额（元）',
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
          name: '配送时间（分钟）',
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
            return data[2] / 5 * 100;
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
