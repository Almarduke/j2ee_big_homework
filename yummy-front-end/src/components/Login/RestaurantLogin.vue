<template>
  <div>
    <a-form style="margin-top: 20px" :form="loginForm" @submit="handleLogin">
      <a-form-item label="识别号" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['id', {rules: [{required: true, message: '请输入识别号'},
                 {validator: idValidator}]}]"/>
      </a-form-item>
      <a-form-item label="密码" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                 {validator: passwordValidator}]}]" type="password"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 5 }">
        <a-button class="submit-button" @click="showModal">注册</a-button>
        <a-button type="primary" class="submit-button" html-type="submit" >登陆</a-button>
      </a-form-item>
    </a-form>

    <a-modal title="餐厅注册" :visible="visible" @ok="handleOk('registerForm')"
             :confirmLoading="confirmLoading" @cancel="handleCancel">
      <a-form :form="registerForm">
        <a-form-item v-bind="formItemLayout" label="邮箱">
          <a-input v-decorator="['email', {rules: [{type: 'email', message: '邮箱不合法'},
                   {required: true, message: '请输入邮箱'}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="密码">
          <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                   {validator: passwordValidator}]}]"/>
        </a-form-item>
        <a-form-item label="手机" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入手机号'},
                 {validator: phoneValidator}]}]"/>
        </a-form-item>
        <a-form-item label="姓名" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
          <a-input v-decorator="['name', {rules: [{required: true, message: '请输入姓名'}]}]" type="password"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="验证码" extra="输入验证码进行验证">
          <a-row :gutter="8">
            <a-col :span="15">
              <a-input v-decorator="['captcha', {rules:
                       [{ required: true, message: '请输入验证码' }, {validator: captchaValidator}]}]"/>
            </a-col>
            <a-col :span="6">
              <a-button>获取验证码</a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'MemberLogin',
  data () {
    return {
      visible: false,
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
  beforeCreate () {
    this.loginForm = this.$form.createForm(this);
    this.registerForm = this.$form.createForm(this);
  },
  methods: {
    handleLogin (e) {
      e.preventDefault();
      this.loginForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$router.push('/');
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
    captchaValidator (rule, value, callback) {
      let message = '验证码必须是6位数字';
      (value && /\d{6}/.test(value.toString())) ? callback() : callback(message);
    },
    showModal () {
      this.visible = true;
    },
    handleOk () {
      this.registerForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
          this.visible = false;
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
  .submit-button {
    margin: 0 10px;
  }
</style>
