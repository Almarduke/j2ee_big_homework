<template>
  <div class="container">
    <div ref="DeliveryTime" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'DishSelling',
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
      let data = [
        { time: '早上', weekBeforeLast: 32, lastWeek: 30, thisWeek: 28 },
        { time: '中午', weekBeforeLast: 48, lastWeek: 40, thisWeek: 42 },
        { time: '下午', weekBeforeLast: 28, lastWeek: 30, thisWeek: 25 },
        { time: '傍晚', weekBeforeLast: 45, lastWeek: 43, thisWeek: 39 },
        { time: '夜晚', weekBeforeLast: 36, lastWeek: 35, thisWeek: 33 },
        { time: '凌晨', weekBeforeLast: 25, lastWeek: 28, thisWeek: 26 }
      ];
      let myChart = this.$echarts.init(this.$refs.DeliveryTime);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      let timeList = data.map((record) => {
        return record.time;
      });

      let recordTwoWeeksBefore = data.map((record) => {
        return record.weekBeforeLast;
      });
      let recordOfLastWeek = data.map((record) => {
        return record.lastWeek;
      });
      let recordOfThisWeek = data.map((record) => {
        return record.thisWeek;
      });
      return {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
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
          data: [
            '上上周数据', '上周数据', '本周数据'
          ]
        },
        xAxis: [
          {
            type: 'category',
            name: '时间点',
            axisTick: {
              alignWithLabel: true
            },
            data: timeList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '平均配送时长',
            min: 0,
            max: 60,
            position: 'left',
            axisLabel: {
              formatter: '{value} 分钟'
            }
          }
        ],
        series: [
          {
            name: '上上周数据',
            type: 'bar',
            data: recordTwoWeeksBefore,
            itemStyle: {
              color: '#5AA1A9'
            }
          },
          {
            name: '上周数据',
            type: 'bar',
            data: recordOfLastWeek,
            itemStyle: {
              color: '#273A49'
            }
          },
          {
            name: '本周数据',
            type: 'bar',
            data: recordOfThisWeek,
            itemStyle: {
              color: '#A92528'
            }
          }
        ]
      };
    }
  }
};
</script>

<style scoped>
  .bubble-chart {
    height: 700px;
    width: 1280px;
    display: inline-block;
  }
  .container {
    height: 700px;
    width: 100%;
    min-width: 1280px;
  }
</style>
