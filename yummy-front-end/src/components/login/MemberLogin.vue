<template>
  <div>
    <a-form style="margin-top: 20px" :form="loginForm" @submit="handleLogin">
      <a-form-item v-bind="formItemLayout" label="邮箱" >
        <a-input v-decorator="['email', {rules: [{type: 'email', message: '邮箱不合法'},
                 {required: true, message: '请输入邮箱'}]}]"/>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="密码" >
        <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                 {validator: passwordValidator}]}]" type="password"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 5 }">
        <a-button class="submit-button" @click="showModal">注册</a-button>
        <a-button type="primary" class="submit-button" html-type="submit" >登录</a-button>
      </a-form-item>
    </a-form>

    <a-modal title="用户注册" :visible="visible" @ok="handleOk" @cancel="handleCancel">
      <a-form :form="registerForm">
        <a-form-item v-bind="formItemLayout" label="邮箱">
          <a-input v-decorator="['email', {rules: [{type: 'email', message: '邮箱不合法'},
                   {required: true, message: '请输入邮箱'}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="密码">
          <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                   {validator: passwordValidator}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="手机" >
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机号'},
                 {validator: phoneValidator}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="姓名" >
          <a-input v-decorator="['name', {rules: [{required: true, message: '请输入姓名'}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="地址" >
          <a-select v-decorator="['address', {rules: [{required: true, message: '请输入地址'}]}]">
            <a-select-option v-for="address in addressList" :key="address">
              {{address}}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="验证码" extra="输入验证码进行验证">
          <a-row :gutter="8">
            <a-col :span="15">
              <a-input v-decorator="['checkCode', {rules:
                       [{ required: true, message: '请输入验证码' }, {validator: checkCodeValidator}]}]"/>
            </a-col>
            <a-col :span="6">
              <a-button @click="sendCheckCode">获取验证码</a-button>
            </a-col>
          </a-row>
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
            url: this.baseUrl + '/member/login',
            method: 'POST',
            params: {
              email: values.email,
              password: values.password
            }
          }).then((response) => {
            const code = response.data.code;
            if (code === OK) {
              this.$message.success(response.data.msg);
              this[actionTypes.LOGIN]({
                isLogin: true,
                userId: values.email,
                userType: response.data.data.type,
                userLevel: response.data.data.level,
                token: ''
              });
              this.$router.push('/MainPage');
            } else {
              this.$message.error(response.data.msg);
            }
          });
        }
      });
    },
    passwordValidator (rule, value, callback) {
      let message = '密码长度必须在6位以上';
      (value && value.toString().length >= 6) ? callback() : callback(message);
    },
    phoneValidator (rule, value, callback) {
      let message = '手机号必须是11位数字';
      (value && /^\d{11}$/.test(value.toString())) ? callback() : callback(message);
    },
    checkCodeValidator (rule, value, callback) {
      let message = '验证码必须是6位数字';
      (value && /^\d{6}$/.test(value.toString())) ? callback() : callback(message);
    },
    showModal () {
      this.visible = true;
    },
    sendCheckCode () {
      this.$http({
        url: this.baseUrl + `/member/sendCheckCode/${this.registerForm.getFieldValue('email')}`,
        method: 'POST'
      }).then((response) => {
        const code = response.data.code;
        if (code === OK) {
          this.$message.info(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    handleOk () {
      this.registerForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: this.baseUrl + '/member/signUp',
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
    ...mapActions([actionTypes.LOGIN])
  }
};
</script>

<style scoped>
.submit-button {
  margin: 0 10px;
}
</style>
