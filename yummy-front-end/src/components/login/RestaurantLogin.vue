<template>
  <div>
    <a-form style="margin-top: 20px" :form="loginForm" @submit="handleLogin">
      <a-form-item v-bind="formItemLayout" label="识别号">
        <a-input v-decorator="['id', {rules: [{required: true, message: '请输入识别号'},
                 {validator: idValidator}]}]"/>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="密码">
        <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                 {validator: passwordValidator}]}]" type="password"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 5 }">
        <a-button class="submit-button" @click="showModal">注册</a-button>
        <a-button type="primary" class="submit-button" html-type="submit" >登陆</a-button>
      </a-form-item>
    </a-form>

    <a-modal title="餐厅注册" :visible="visible" @ok="handleOk"
             :confirmLoading="confirmLoading" @cancel="handleCancel">
      <a-form :form="registerForm">
        <a-form-item v-bind="formItemLayout" label="密码">
          <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                   {validator: passwordValidator}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="手机">
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机号'},
                 {validator: phoneValidator}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="餐厅名" >
          <a-input v-decorator="['name', {rules: [{required: true, message: '请输入餐厅名'}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="地址">
          <a-select v-decorator="['address', {rules: [{required: true, message: '请输入地址'}]}]">
            <a-select-option v-for="address in addressList" :key="address">
              {{address}}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import * as actionTypes from '@/utils/store/action-types';
import { mapGetters, mapActions } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';

export default {
  name: 'MemberLogin',
  data () {
    return {
      visible: false,
      addressList: [],
      confirmLoading: false,
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
    ...mapGetters(['restaurantInfo', 'baseUrl'])
  },
  beforeCreate () {
    this.loginForm = this.$form.createForm(this);
    this.registerForm = this.$form.createForm(this);
  },
  mounted () {
    this.$http({
      url: this.baseUrl + '/address/getAll',
      method: 'GET'
    }).then((response) => {
      this.addressList = response.data.data;
    });
  },
  methods: {
    handleLogin (e) {
      e.preventDefault();
      this.loginForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: this.baseUrl + '/restaurant/login',
            method: 'POST',
            params: {
              id: values.id,
              password: values.password
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.$message.success(response.data.msg);
              this[actionTypes.LOGIN]({
                isLogin: true,
                userId: values.id,
                userType: 'RESTAURANT',
                userLevel: 0,
                token: ''
              });
              this[actionTypes.SET_RESTAURANT_INFO](response.data.data);
              this.$router.push('/ManageFood');
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    idValidator (rule, value, callback) {
      let message = '识别码是7位字母和数字';
      (value && /[0-9a-zA-Z]{7}/) ? callback() : callback(message);
    },
    passwordValidator (rule, value, callback) {
      let message = '密码长度必须在6位以上';
      (value && value.toString().length >= 6) ? callback() : callback(message);
    },
    phoneValidator (rule, value, callback) {
      let message = '手机号必须是11位数字';
      (value && /\d{11}/.test(value.toString())) ? callback() : callback(message);
    },
    showModal () {
      this.visible = true;
    },
    handleOk () {
      this.registerForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: this.baseUrl + '/restaurant/signUp',
            method: 'POST',
            data: values
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.visible = false;
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
    },
    ...mapActions([actionTypes.LOGIN, actionTypes.SET_RESTAURANT_INFO])
  }
};
</script>

<style scoped>
  .submit-button {
    margin: 0 10px;
  }
</style>
