<template>
  <div class="container">
    <div ref="CuctomerActivity" class="bubble-chart"></div>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'CuctomerActivity',
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
      let myChart = this.$echarts.init(this.$refs.CuctomerActivity);
      require('echarts/map/js/china.js');
      myChart.setOption(this.getOption());
    },
    getOption () {
      const data = [
        { name: '河北', frequency: 5, value: 24 },
        { name: '黑龙江', frequency: 3, value: 34 }
      ];

      return {
        textStyle: {
          fontSize: 20
        },
        title: {
          text: '各地区消费者活跃度',
          subtext: '过去一周的平均消费金额',
          x: 'center',
          textStyle: {
            fontSize: 20
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: (data) => {
            let info = data.data;
            if (info === null || info === undefined) {
              return '';
            };
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">' +
                info.name + '</div>' +
                '平均消费金额：' + info.value + '元<br>' +
                '平均消费次数：' + info.frequency + '次';
          }
        },
        visualMap: {
          min: 0,
          max: 150,
          left: 'left',
          top: 'bottom',
          text: ['High', 'Low'],
          seriesIndex: [1],
          inRange: {
            color: ['#e0ffff', '#73cefc', '#2ea8ff', '#006edd']
          },
          calculable: true
        },
        geo: {
          map: 'china',
          roam: true,
          label: {
            normal: {
              show: true,
              textStyle: {
                color: 'rgba(0,0,0,0.4)'
              }
            }
          },
          itemStyle: {
            normal: {
              borderColor: 'rgba(0, 0, 0, 0.2)'
            },
            emphasis: {
              areaColor: null,
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        series: [
          {
            type: 'scatter',
            coordinateSystem: 'geo'
          },
          {
            type: 'map',
            geoIndex: 0,
            label: {
              normal: {
                formatter: '{b}',
                position: 'right',
                show: false
              },
              emphasis: {
                show: true
              }
            },
            data: data
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
    width: 100%;
    display: inline-block;
  }
  .container {
    height: 700px;
    width: 100%;
    min-width: 1200px;
  }
</style>
