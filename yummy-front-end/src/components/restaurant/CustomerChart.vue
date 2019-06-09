<template>
  <div class="container">
    <div ref="SatisfactionChart" class="bubble-chart"></div>
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
      let myChart = this.$echarts.init(this.$refs.SatisfactionChart);
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        ['玄武区', 12, 13, '大杯可乐'],
        ['鼓楼区', 24, 64, '烤鱿鱼'],
        ['江宁区', 46, 28, '大碗宽面'],
        ['浦口区', 37, 70, '火锅'],
        ['栖霞区', 32, 62, '鱼香肉丝'],
        ['秦淮区', 19, 24, '炸薯条'],
        ['玄武区', 25, 47, '柠檬蛋糕'],
        ['栖霞区', 15, 32, '大碗宽面'],
        ['浦口区', 18, 80, '烤鱿鱼'],
        ['鼓楼区', 42, 120, '寿司'],
        ['栖霞区', 52, 150, '牛排'],
        ['秦淮区', 33, 40, '咖喱饭'],
        ['玄武区', 46, 110, '烤鱿鱼'],
        ['栖霞区', 63, 52, '开水白菜'],
        ['江宁区', 62, 20, '皮蛋瘦肉粥'],
        ['秦淮区', 69, 42, '开水白菜'],
        ['浦口区', 67, 180, '鱼翅羹'],
        ['鼓楼区', 75, 18, '皮蛋瘦肉粥'],
        ['江宁区', 25, 120, '火锅']
      ];

      return {
        opacity: 0.3,
        toolbox: {
          right: 20,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        xAxis: {
          name: '顾客所在区域',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          data: [
            '玄武区', '鼓楼区', '江宁区', '栖霞区',
            '秦淮区', '浦口区'
          ]
        },
        yAxis: {
          name: '顾客平均年龄',
          TextStyle: {
            color: '#fff',
            fontSize: 14
          }
        },
        series: [{
          data: data,
          type: 'scatter',
          symbolSize: (data) => {
            return Math.max(data[2] / 200 * 160, 60);
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
            formatter: '消费水平: {@[2]}元\n喜爱的食物: {@[3]}'
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
                '顾客区域：' + value[0] + '</div>' +
                '平均年龄：' + value[1] + '岁<br>' +
                '消费水平：' + value[2] + '元<br>' +
                '喜爱的食物：' + value[3];
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
