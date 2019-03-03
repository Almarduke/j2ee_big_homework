<template>
  <div class="container">
    <div id="MemberStatistics" class="pie-chart"></div>
    <div id="RestaurantStatistics" class="pie-chart"></div>
  </div>
</template>

<script>
import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'UserStatistics',
  computed: {
    ...mapGetters(['baseUrl'])
  },
  mounted () {
    this.setMemberData();
    this.setRestaurantData();
  },
  methods: {
    setMemberData () {
      let myChart = this.$echarts.init(document.getElementById('MemberStatistics'));
      this.$http({
        url: `${this.baseUrl}/admin/getMemberStatistics`,
        method: 'GET'
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          myChart.setOption(this.getOption(response.data.data, true));
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    setRestaurantData () {
      let myChart = this.$echarts.init(document.getElementById('RestaurantStatistics'));
      this.$http({
        url: `${this.baseUrl}/admin/getRestaurantStatistics`,
        method: 'GET'
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          myChart.setOption(this.getOption(response.data.data, false));
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    getOption (data, isMember) {
      let title;
      let subTitle;
      let tooltipTitle;
      let position;
      let formatter;
      let color;
      let legendData = data.map((item) => {
        return item.name;
      });

      if (isMember) {
        title = '会员统计';
        subTitle = '各等级会员人数';
        tooltipTitle = '会员等级';
        position = 'left';
        formatter = '{a} <br/>{b}: {c}人';
        color = ['#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8', '#FF9F7F', '#8378EA'];
      } else {
        title = '餐厅统计';
        subTitle = '不同营业额的餐厅数';
        tooltipTitle = '餐厅营业额';
        position = 'right';
        formatter = '{a} <br/>{b}: {c}家';
        color = ['#A92528', '#273A49', '#5AA1A9', '#E17B5A', '#8FD4B2', '#D5811E'];
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
          formatter: formatter,
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
  .pie-chart {
    height: 600px;
    width: 600px;
    display: inline-block;
  }
  .container {
    height: 640px;
    width: 1200px;
    min-width: 1200px;
    padding: 20px 0;
  }
</style>
