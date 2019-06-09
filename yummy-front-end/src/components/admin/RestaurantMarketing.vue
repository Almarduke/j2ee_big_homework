<template>
  <div class="container">
    <div ref="RestaurantMarketing" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'RestaurantMarketing',
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
        { area: '华东', weekBeforeLast: 493, lastWeek: 502, thisWeek: 517 },
        { area: '华南', weekBeforeLast: 498, lastWeek: 492, thisWeek: 504 },
        { area: '华北', weekBeforeLast: 510, lastWeek: 502, thisWeek: 508 },
        { area: '中部', weekBeforeLast: 482, lastWeek: 477, thisWeek: 480 },
        { area: '西部', weekBeforeLast: 450, lastWeek: 452, thisWeek: 448 },
        { area: '东北', weekBeforeLast: 478, lastWeek: 484, thisWeek: 483 }
      ];
      let myChart = this.$echarts.init(this.$refs.RestaurantMarketing);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      let areaList = data.map((record) => {
        return record.area;
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
        title: {
          left: 'center',
          text: '各地区饭店销售总额'
        },
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
          left: '70%',
          data: [
            '上上周销售额', '上周销售额', '本周销售额'
          ]
        },
        xAxis: [
          {
            type: 'category',
            name: '地区',
            axisTick: {
              alignWithLabel: true
            },
            data: areaList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '销售总额',
            min: 400,
            position: 'left',
            axisLabel: {
              formatter: '{value}万元'
            }
          }
        ],
        series: [
          {
            name: '上上周销售额',
            type: 'bar',
            data: recordTwoWeeksBefore,
            itemStyle: {
              color: '#5AA1A9'
            }
          },
          {
            name: '上周销售额',
            type: 'bar',
            data: recordOfLastWeek,
            itemStyle: {
              color: '#273A49'
            }
          },
          {
            name: '本周销售额',
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
