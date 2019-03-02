<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/avator.jpg" alt="" slot="cover"/>
        <a-card-meta :title="restaurantInfo.name">
          <p slot="description">编号：{{restaurantInfo.id}}</p>
          <p slot="description">电话：{{restaurantInfo.phone}}</p>
          <p slot="description">余额：{{restaurantInfo.amount}}元</p>
        </a-card-meta>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="地址列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="address in restaurantAddressList" :key="address.name">
                <a-list-item-meta :description="defaultInfo(address.default)">
                  <a slot="title" >{{address.name}}</a>
                </a-list-item-meta>
                <div style="margin-left: 10px" v-if="!address.default">
                  <a slot="actions" @click="setAddressAsDefault(address.name)" class="plain-text">设为默认</a>
                  <a slot="actions" @click="deleteAddress(address.name)" class="plain-text delete-button">删除</a>
                </div>
              </a-list-item>
            </a-list>
            <a-button style="float: right; margin: 20px;" type="primary" shape="circle" icon="plus" size="large" @click="addAddress"/>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>

    <a-modal title="添加地址" :visible="visible" @ok="handleOk" @cancel="handleCancel">
      <a-form :form="addressForm">
        <a-form-item label="名称">
          <a-select v-decorator="['addressName', {rules: [{required: true, message: '请输入地址'}]}]">
            <a-select-option v-for="address in addressList" :key="address">{{address}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'ShoppingCart',
  data () {
    return {
      restaurantInfo: {},
      restaurantAddressList: [],
      addressList: [],
      visible: false
    };
  },
  computed: {
    ...mapGetters(['userInfo', 'baseUrl'])
  },
  beforeCreate () {
    this.addressForm = this.$form.createForm(this);
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
          this.restaurantAddressList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    addAddress () {
      this.visible = true;
    },
    handleOk () {
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
              this.visible = false;
              this.restaurantAddressList = response.data.data;
              this.$message.success(response.data.msg);
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    handleCancel () {
      this.visible = false;
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
  .delete-button {
    color: red;
  }
  .delete-button:hover {
    opacity: 0.8;
  }
</style>
