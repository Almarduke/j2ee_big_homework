<template>
  <div class="container">
    <div ref="CustomerSatisfaction" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'CustomerSatisfaction',
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
      let myChart = this.$echarts.init(this.$refs.CustomerSatisfaction);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        [54, 34, 4.5],
        [78, 28, 5],
        [21, 48, 3.9],
        [32, 37, 4.3],
        [38, 52, 3.8],
        [46, 31, 3.7],
        [41, 43, 4.2],
        [62, 29, 4.0],
        [66, 33, 4.1],
        [13, 40, 3.6],
        [19, 30, 4.7],
        [58, 51, 4.4],
        [82, 57, 3.8],
        [89, 35, 5],
        [93, 45, 3.9],
        [98, 28, 4.8],
        [26, 41, 5]
      ];

      return {
        title: {
          left: 'center',
          text: '顾客满意度数据',
          subtext: '基于配送时间和消费金额'
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
          name: '配送时间',
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
            formatter: '顾客平均满意度:\n{@[2]}星'
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
                '消费者信息' + '</div>' +
                '消费水平：' + value[0] + '元<br>' +
                '配送时间：' + value[1] + '分钟<br>' +
                '平均满意度：' + value[2] + '星';
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
