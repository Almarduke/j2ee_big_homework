<template>
  <div class="container">
    <div id="FinanceStatistics" class="line-chart"></div>
  </div>
</template>

<script>
import { OK } from '@/utils/status/HttpStatus';
import { mapGetters } from 'vuex';

export default {
  name: 'FinanceStatistics',
  data () {
    return {
    };
  },
  computed: {
    ...mapGetters(['baseUrl'])
  },
  mounted () {
    this.setData();
  },
  methods: {
    setData () {
      let myChart = this.$echarts.init(document.getElementById('FinanceStatistics'));
      this.$http({
        url: `${this.baseUrl}/admin/getFinanceStatistics`,
        method: 'GET'
      }).then((response) => {
        const code = response.data.code;

        if (code === OK) {
          const data = response.data.data;
          const dateList = data.map((item) => { return item.name; });
          const valueList = data.map((item) => { return item.value; });

          const option = {
            tooltip: {
              trigger: 'axis',
              formatter: '{b} <br/>收入金额: {c}元'
            },
            xAxis: {
              type: 'category',
              data: dateList
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: valueList,
              type: 'line'
            }]
          };
          myChart.setOption(option);
        } else {
          this.$message.error(response.data.msg);
        }
      });
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
