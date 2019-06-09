<template>
  <div>
    <a-layout style="height: 700px; background-color: white">
      <a-layout-sider width="120" style="background: #fff">
        <a-menu mode="inline" :defaultSelectedKeys="['week']" style="height: 100%" @click="handleClick">
          <a-menu-item key="week" >1周内</a-menu-item>
          <a-menu-item key="month">1个月内</a-menu-item>
          <a-menu-item key="season">3个月内</a-menu-item>
        </a-menu>
      </a-layout-sider>

      <a-layout-content style="height: 750px;">
        <div ref="ConsumptionChart" class="bubble-chart"></div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
// import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'DishChart',
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
      let data = [];
      switch (key) {
        case 'week':
          data = [
            [35, 2, '安格斯厚牛堡'],
            [12, 2, '薯条'],
            [9, 2, '大杯可乐'],
            [30, 1, '黄焖鸡米饭'],
            [15, 1, '牛肉炒刀削'],
            [15, 1, '牛肉炒拉面'],
            [12, 3, '招牌燃面'],
            [7, 4, '大口饭团']
          ];
          break;
        case 'month':
          data = [
            [35, 4, '安格斯厚牛堡'],
            [12, 4, '薯条'],
            [9, 6, '大杯可乐'],
            [33, 2, '安格斯芝士堡'],
            [13, 2, '椒盐金翅'],
            [4, 1, '甜筒'],
            [30, 3, '黄焖排骨'],
            [30, 7, '黄焖鸡米饭'],
            [15, 5, '牛肉炒刀削'],
            [15, 3, '牛肉炒拉面'],
            [12, 3, '招牌燃面'],
            [11, 3, '蒜泥白肉'],
            [7, 10, '大口饭团'],
            [17, 8, '速食便当'],
            [40, 2, '北京烤鸭'],
            [28, 2, '炭烤八抓鱼'],
            [28, 4, '麻辣香锅'],
            [10, 10, '羊肉串'],
            [8, 9, '牛肉串'],
            [10, 8, '啤酒'],
            [20, 4, '烤鱿鱼'],
            [31, 5, '卤肉饭套餐']
          ];
          break;
        case 'season':
          data = [
            [35, 6, '安格斯厚牛堡'],
            [12, 6, '薯条'],
            [14, 2, '原味板烧鸡腿堡'],
            [9, 9, '大杯可乐'],
            [33, 4, '安格斯芝士堡'],
            [13, 4, '椒盐金翅'],
            [4, 4, '甜筒'],
            [30, 7, '黄焖排骨'],
            [30, 10, '黄焖鸡米饭'],
            [15, 5, '牛肉炒刀削'],
            [15, 3, '牛肉炒拉面'],
            [21, 3, '孜然盖浇面'],
            [12, 5, '招牌燃面'],
            [11, 3, '蒜泥白肉'],
            [16, 2, '肥肠抄手'],
            [7, 13, '大口饭团'],
            [17, 9, '速食便当'],
            [42, 4, '北京烤鸭'],
            [32, 8, '炭烤八抓鱼'],
            [28, 8, '麻辣香锅'],
            [10, 15, '羊肉串'],
            [8, 12, '牛肉串'],
            [10, 8, '啤酒'],
            [20, 5, '烤鱿鱼'],
            [31, 5, '卤肉饭套餐'],
            [48, 3, '皇堡套餐'],
            [38, 3, '烤牛肉'],
            [25, 2, '芝士年糕'],
            [20, 2, '猪脑'],
            [34, 3, '虾滑'],
            [30, 2, '五花肉']
          ];
      }
      let myChart = this.$echarts.init(this.$refs.ConsumptionChart);
      myChart.setOption(this.getOption(data));
    },
    getOption (data) {
      return {
        title: {
          left: 'center',
          text: '消费过的菜品信息',
          subtext: '基于本用户数据'
        },
        toolbox: {
          right: 60,
          feature: {
            saveAsImage: {
              show: true
            }
          }
        },
        xAxis: {
          name: '菜品价格',
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
            formatter: '{value}元'
          }
        },
        yAxis: {
          name: '消费次数',
          min: 0,
          minInterval: 1,
          TextStyle: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          scale: true,
          axisLabel: {
            formatter: '{value}次'
          }
        },
        series: [{
          data: data,
          type: 'scatter',
          symbolSize: 40,
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
                '消费金额：' + value[0] + '<br>' +
                '消费次数：' + value[1];
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
    width: 1200px;
    display: inline-block;
  }
</style>
