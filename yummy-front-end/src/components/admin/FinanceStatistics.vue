<template>
  <div class="container">
    <div id="FinanceStatistics" class="line-chart"></div>
  </div>
</template>

<script>
export default {
  name: 'FinanceStatistics',
  data () {
    return {
    };
  },
  mounted () {
    this.setData();
  },
  methods: {
    setData () {
      let myChart = this.$echarts.init(document.getElementById('FinanceStatistics'));
      let option = {
        xAxis: {
          type: 'category',
          data: [
            'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun',
            'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'
          ]
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [
            820, -932, 901, 934, -1290, 1330, 1320,
            820, -932, 901, -934, 1290, 1330, -1320
          ],
          type: 'line'
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    getOption (data, isMember) {
      let title;
      let subTitle;
      let tooltipTitle;
      let position;
      let color;
      let legendData = data.map((item) => {
        return item.name;
      });

      if (isMember) {
        title = '会员统计';
        subTitle = '各等级会员人数';
        tooltipTitle = '会员等级';
        position = 'left';
        color = ['#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8', '#FF9F7F', '#8378EA'];
      } else {
        title = '餐厅统计';
        subTitle = '各营业额餐厅数';
        tooltipTitle = '餐厅营业额';
        position = 'right';
        color = [
          '#A92528', '#273A49',
          '#5AA1A9', '#E17B5A',
          '#8FD4B2', '#D5811E'
        ];
      }

      return {
        textStyle: {
          fontSize: 14
        },
        title: {
          text: title,
          subtext: subTitle,
          x: 'center',
          textStyle: {
            fontSize: 18
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c}人',
          textStyle: {
            fontSize: 16
          }
        },
        legend: {
          orient: 'vertical',
          left: position,
          data: legendData
        },
        series: [
          {
            name: tooltipTitle,
            type: 'pie',
            radius: '60%',
            center: ['50%', '50%'],
            data: data
          }
        ],
        color: color
      };
    }
  }
};
</script>

<style scoped>
  .line-chart {
    height: 600px;
    width: 1200px;
    display: inline-block;
  }
  .container {
    height: 640px;
    width: 1200px;
    min-width: 1200px;
    padding: 20px 0;
  }
</style>
