<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/rice.jpg" alt="" slot="cover"/>
        <a-card-meta :title="restaurantInfo.name">
          <p slot="description">编号：{{restaurantInfo.id}}</p>
          <p slot="description">电话：{{restaurantInfo.phone}}</p>
          <p slot="description">余额：{{restaurantInfo.amount}}元</p>
          <p slot="description">地址：{{restaurantInfo.address}}</p>
        </a-card-meta>
        <div style="float: right;">
          <a-button class="little-button" shape="circle" icon="edit" @click="updateInfoVisible=true"/>
          <a-button class="little-button" shape="circle" icon="dollar" @click="withdrawVisible=true"/>
        </div>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="地址列表" key="1">
            <!--<a-list itemLayout="horizontal" :bordered="true">-->
              <!--<a-list-item v-for="address in restaurantAddressList" :key="address.name">-->
                <!--<a-list-item-meta :description="defaultInfo(address.default)">-->
                  <!--<a slot="title" >{{address.name}}</a>-->
                <!--</a-list-item-meta>-->
                <!--<div style="margin-left: 10px" v-if="!address.default">-->
                  <!--<a slot="actions" @click="setAddressAsDefault(address.name)" class="plain-text">设为默认</a>-->
                  <!--<a slot="actions" @click="deleteAddress(address.name)" class="plain-text delete-button">删除</a>-->
                <!--</div>-->
              <!--</a-list-item>-->
            <!--</a-list>-->
            <!--<a-button style="float: right; margin: 20px;" type="primary" shape="circle" icon="plus" size="large" @click="addAddress"/>-->
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>

    <a-modal title="编辑餐厅信息" :visible="updateInfoVisible" @ok="updateInfo" @cancel="updateInfoVisible=false">
      <a-form :form="restaurantInfoForm" v-if="updateInfoVisible">
        <a-form-item v-bind="formItemLayout" label="名称">
          <a-input v-decorator="['name', {rules: [{required: true, message: '请输入名称'}], initialValue: restaurantInfo.name}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="手机">
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机号'},
            {validator: phoneValidator}], initialValue: restaurantInfo.phone}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="地址">
          <a-select v-decorator="['address', {rules: [{required: true, message: '请输入地址'}], initialValue: restaurantInfo.address}]">
            <a-select-option v-for="address in addressList" :key="address">
              {{address}}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal title="用户提现" :visible="withdrawVisible" @ok="withdrawMoney" @cancel="withdrawVisible=false">
      <a-form :form="withdrawForm" v-if="withdrawVisible">
        <a-form-item v-bind="formItemLayout" label="提现金额">
          <div>
            <a-input-number :min="0" :max="restaurantInfo.amount" v-decorator="['amount', {rules: [{ required: true, message: '请输入提现金额' }], initialValue: 0}]"/>
            <span style="margin-left: 10px">元</span>
          </div>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'RestaurantInfo',
  data () {
    return {
      restaurantInfo: {},
      restaurantAddressList: [],
      addressList: [],
      updateInfoVisible: false,
      withdrawVisible: false,
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
    this.restaurantInfoForm = this.$form.createForm(this);
    this.withdrawForm = this.$form.createForm(this);
  },
  mounted () {
    this.$http({
      url: `${this.baseUrl}/restaurant/getRestaurantInfo/${this.userInfo.userId}`,
      method: 'GET'
    }).then((response) => {
      this.restaurantInfo = response.data.data;
      this.restaurantAddressList = response.data.data.addressList;
    });
    this.$http({
      url: this.baseUrl + '/address/getAll',
      method: 'GET'
    }).then((response) => {
      this.addressList = response.data.data;
    });
  },
  methods: {
    defaultInfo (isDefault) {
      return isDefault ? '默认地址' : '其他地址';
    },
    setAddressAsDefault (addressName) {
      this.$http({
        url: this.baseUrl + '/address/setAsDefault',
        method: 'POST',
        params: {
          userId: this.userInfo.userId,
          addressName: addressName
        }
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.restaurantAddressList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    updateInfo () {
      this.restaurantInfoForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/restaurant/updateRestaurantInfo/${this.userInfo.userId}`,
            method: 'POST',
            params: {
              newName: values.name,
              newPhone: values.phone,
              newAddress: values.address
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.updateInfoVisible = false;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    phoneValidator (rule, value, callback) {
      let message = '手机号必须是11位数字';
      (value && /\d{11}/.test(value.toString())) ? callback() : callback(message);
    },
    withdrawMoney () {
      this.withdrawForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/restaurant/withdrawMoney/${this.userInfo.userId}`,
            method: 'POST',
            params: {
              amount: values.amount
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.withdrawVisible = false;
              this.restaurantInfo = response.data.data;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    }
  }
};
</script>

<style scoped>
  .little-button {
    margin: 0 5px;
  }
</style>
