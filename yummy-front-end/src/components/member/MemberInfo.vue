<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/avator.jpg" alt="" slot="cover"/>
        <a-card-meta :title="memberInfo.email">
          <p slot="description">名称：{{memberInfo.name}}</p>
          <p slot="description">手机：{{memberInfo.phone}}</p>
          <p slot="description">等级：{{memberInfo.level}}</p>
          <p slot="description">余额：{{memberInfo.amount}}元</p>
        </a-card-meta>
        <div style="float: right;" >
          <a-button class="little-button" shape="circle" icon="edit" @click="updateInfoVisible=true"/>
          <a-button class="little-button" shape="circle" icon="dollar" @click="chargeMoneyVisible=true"/>
          <a-popconfirm title="注销此用户吗？你无法再登录" @confirm="deleteMember" okText="确认" cancelText="取消">
            <a-button class="little-button" shape="circle" icon="delete" type="danger"/>
          </a-popconfirm>
        </div>
      </a-card>
    </a-layout-sider>

    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="地址列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="address in memberAddressList" :key="address.name">
                <a-list-item-meta :description="defaultInfo(address.default)">
                  <a slot="title" >{{address.name}}</a>
                </a-list-item-meta>
                <div style="margin-left: 10px" v-if="!address.default">
                  <a slot="actions" @click="setAddressAsDefault(address.name)" class="plain-text">设为默认</a>
                  <a slot="actions" @click="deleteAddress(address.name)" class="plain-text delete-button">删除</a>
                </div>
              </a-list-item>
            </a-list>
            <a-button style="float: right; margin: 20px;" type="primary" shape="circle" icon="plus" size="large" @click="addAddressVisible=true"/>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>

    <a-modal title="编辑用户信息" :visible="updateInfoVisible" @ok="updateInfo" @cancel="updateInfoVisible=false">
      <a-form :form="memberInfoForm" v-if="updateInfoVisible">
        <a-form-item v-bind="formItemLayout" label="名称">
          <a-input v-decorator="['name', {rules: [{required: true, message: '请输入名称'}], initialValue: memberInfo.name}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="手机">
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机号'},
            {validator: phoneValidator}], initialValue: memberInfo.phone}]"/>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal title="用户充值" :visible="chargeMoneyVisible" @ok="chargeMoney" @cancel="chargeMoneyVisible=false">
      <a-form :form="chargeMoneyForm" v-if="chargeMoneyVisible">
        <a-form-item v-bind="formItemLayout" label="充值金额">
          <div>
            <a-input-number :min="1" v-decorator="['amount', {rules: [{ required: true, message: '请输入充值金额' }], initialValue: 1}]"/>
            <span style="margin-left: 10px">元</span>
          </div>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal title="添加地址" :visible="addAddressVisible" @ok="addAddress" @cancel="addAddressVisible=false">
      <a-form :form="addressForm" v-if="addAddressVisible">
        <a-form-item v-bind="formItemLayout" label="名称">
          <a-select v-decorator="['addressName', {rules: [{required: true, message: '请输入地址'}]}]">
            <a-select-option v-for="address in addressList" :key="address">{{address}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import * as actionTypes from '@/utils/store/action-types';
import { mapGetters, mapActions } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'MemberInfo',
  data () {
    return {
      memberInfo: {},
      memberAddressList: [],
      addressList: [],
      updateInfoVisible: false,
      chargeMoneyVisible: false,
      addAddressVisible: false,
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
    this.addressForm = this.$form.createForm(this);
    this.memberInfoForm = this.$form.createForm(this);
    this.chargeMoneyForm = this.$form.createForm(this);
  },
  mounted () {
    this.$http({
      url: `${this.baseUrl}/member/getMemberInfo/${this.userInfo.userId}`,
      method: 'GET'
    }).then((response) => {
      this.memberInfo = response.data.data;
      this.memberAddressList = response.data.data.addressList;
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
          this.memberAddressList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    deleteAddress (addressName) {
      this.$http({
        url: this.baseUrl + '/address/delete',
        method: 'POST',
        params: {
          userId: this.userInfo.userId,
          addressName: addressName
        }
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.memberAddressList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    addAddress () {
      this.addressForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: this.baseUrl + '/address/addUserAddress',
            method: 'POST',
            params: {
              userId: this.userInfo.userId,
              addressName: values.addressName
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.addAddressVisible = false;
              this.memberAddressList = response.data.data;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    updateInfo () {
      this.memberInfoForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/member/updateMemberInfo/${this.userInfo.userId}`,
            method: 'POST',
            params: {
              newName: values.name,
              newPhone: values.phone
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.updateInfoVisible = false;
              this.memberInfo = response.data.data;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    chargeMoney () {
      this.chargeMoneyForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/member/chargeMoney/${this.userInfo.userId}`,
            method: 'POST',
            params: {
              amount: values.amount
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.chargeMoneyVisible = false;
              this.memberInfo = response.data.data;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    deleteMember () {
      this.$http({
        url: `${this.baseUrl}/member/deleteMember/${this.userInfo.userId}`,
        method: 'POST'
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.$message.success(response.data.msg);
          this[actionTypes.LOGOUT]();
          this.$router.push('/login');
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    phoneValidator (rule, value, callback) {
      let message = '手机号必须是11位数字';
      (value && /\d{11}/.test(value.toString())) ? callback() : callback(message);
    },
    ...mapActions([actionTypes.LOGOUT])
  }
};
</script>

<style scoped>
  .plain-text {
    margin: 0 5px;
    width: 20px;
    font-size: 14px;
  }
  .delete-button {
    color: red;
  }
  .delete-button:hover {
    opacity: 0.8;
  }
  .little-button {
    margin: 0 5px;
  }
</style>
