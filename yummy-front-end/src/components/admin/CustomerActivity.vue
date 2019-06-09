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
        { name: '北京', value: 45, frequency: 27 },
        { name: '天津', value: 41, frequency: 22 },
        { name: '上海', value: 48, frequency: 25 },
        { name: '重庆', value: 32, frequency: 16 },
        { name: '河北', value: 28, frequency: 15 },
        { name: '辽宁', value: 35, frequency: 12 },
        { name: '吉林', value: 33, frequency: 14 },
        { name: '黑龙江', value: 36, frequency: 11 },
        { name: '江苏', value: 47, frequency: 23 },
        { name: '浙江', value: 45, frequency: 28 },
        { name: '安徽', value: 33, frequency: 19 },
        { name: '福建', value: 37, frequency: 18 },
        { name: '江西', value: 29, frequency: 21 },
        { name: '山东', value: 39, frequency: 19 },
        { name: '河南', value: 23, frequency: 8 },
        { name: '湖北', value: 33, frequency: 15 },
        { name: '陕西', value: 27, frequency: 17 },
        { name: '湖南', value: 34, frequency: 13 },
        { name: '广东', value: 40, frequency: 27 },
        { name: '海南', value: 24, frequency: 17 },
        { name: '四川', value: 32, frequency: 19 },
        { name: '贵州', value: 22, frequency: 20 },
        { name: '云南', value: 27, frequency: 21 },
        { name: '山西', value: 30, frequency: 17 },
        { name: '甘肃', value: 21, frequency: 13 },
        { name: '青海', value: 19, frequency: 10 },
        { name: '台湾', value: 33, frequency: 24 },
        { name: '内蒙古', value: 28, frequency: 9 },
        { name: '广西', value: 30, frequency: 21 },
        { name: '西藏', value: 18, frequency: 4 },
        { name: '宁夏', value: 24, frequency: 6 },
        { name: '新疆', value: 17, frequency: 3 },
        { name: '香港', value: 49, frequency: 7 },
        { name: '澳门', value: 46, frequency: 8 }
      ];

      return {
        textStyle: {
          fontSize: 16
        },
        title: {
          text: '各地区消费者活跃度',
          subtext: '过去一周的平均消费金额',
          x: 'center',
          textStyle: {
            fontSize: 18
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
          max: 50,
          left: 'left',
          top: 'bottom',
          text: ['High', 'Low'],
          seriesIndex: [1],
          inRange: {
            color: [
              '#87CEFA', '#73E68C', '#FFFF4D', '#FFA500', '#FF4D00'
            ]
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
