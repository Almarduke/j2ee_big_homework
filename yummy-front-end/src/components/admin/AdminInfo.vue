<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/admin.png" alt="" slot="cover"/>
        <a-card-meta :title="adminInfo.name">
          <p slot="description">编号：{{adminInfo.email}}</p>
          <p slot="description">电话：{{adminInfo.phone}}</p>
        </a-card-meta>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="审批信息" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="restaurantInfoCheck in restaurantInfoCheckList" :key="restaurantInfoCheck.id">
                <a-list-item-meta :description="restaurantInfoCheck.id">
                  <a slot="title" >{{restaurantInfoCheck.name}}</a>
                </a-list-item-meta>
                <div style="margin-left: 10px">
                  <a slot="actions" class="plain-text" @click="showRestaurantInfoCheck(restaurantInfoCheck)">详情</a>
                  <a slot="actions" class="plain-texts" @click="approve(restaurantInfoCheck.id)">批准</a>
                </div>
              </a-list-item>
            </a-list>
          </a-tab-pane>
          <a-tab-pane tab="会员统计" key="2">
            <member-statistics></member-statistics>
          </a-tab-pane>
          <a-tab-pane tab="财务情况" key="3">

          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>

    <a-modal title="餐厅审核信息" :visible="visible" @ok="visible=false" @cancel="visible=false">
      <a-form :form="restaurantInfoCheckForm">
        <a-form-item v-bind="formItemLayout" label="编号">
          <span class="info">{{selectedRestaurantInfoCheck.id}}</span>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="名称" >
          <span class="info">{{selectedRestaurantInfoCheck.name}}</span>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="手机" >
          <span class="info">{{selectedRestaurantInfoCheck.phone}}</span>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="地址" >
          <span class="info">{{selectedRestaurantInfoCheck.address}}</span>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';
import MemberStatistics from '@/components/admin/UserStatistics';

export default {
  name: 'AdminInfo',
  components: { MemberStatistics },
  data () {
    return {
      adminInfo: {},
      restaurantInfoCheckList: [],
      visible: false,
      selectedRestaurantInfoCheck: {
        id: null,
        name: '',
        phone: '',
        address: ''
      },
      formItemLayout: {
        labelCol: {
          span: 5
        },
        wrapperCol: {
          span: 15
        }
      }
    };
  },
  computed: {
    ...mapGetters(['userInfo', 'baseUrl'])
  },
  beforeCreate () {
    this.restaurantInfoCheckForm = this.$form.createForm(this);
  },
  mounted () {
    this.$http({
      url: `${this.baseUrl}/member/getMemberInfo/${this.userInfo.userId}`,
      method: 'GET'
    }).then((response) => {
      this.adminInfo = response.data.data;
    });
    this.$http({
      url: this.baseUrl + '/admin/getRestaurantInfoCheckList',
      method: 'GET'
    }).then((response) => {
      this.restaurantInfoCheckList = response.data.data;
    });
  },
  methods: {
    showRestaurantInfoCheck (restaurantInfoCheck) {
      this.selectedRestaurantInfoCheck = restaurantInfoCheck;
      this.visible = true;
    },
    approve (restaurantId) {
      this.$http({
        url: `${this.baseUrl}/admin/approveRestaurantInfoCheck/${restaurantId}`,
        method: 'POST'
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.visible = false;
          this.restaurantInfoCheckList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    }
  }
};
</script>

<style scoped>
  .plain-text {
    margin: 0 5px;
    width: 20px;
    font-size: 14px;
  }
  .info {
    font-size: 14px;
    margin: 0 10px;
  }
</style>
