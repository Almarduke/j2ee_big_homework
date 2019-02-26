<template>
  <div>
    <a-form style="margin-top: 20px" :form="loginForm" @submit="handleLogin">
      <a-form-item label="邮箱" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['email', {rules: [{type: 'email', message: '邮箱不合法'},
                 {required: true, message: '请输入邮箱'}]}]"/>
      </a-form-item>
      <a-form-item label="密码" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                 {validator: passwordValidate}]}]" type="password"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 5 }">
        <a-button class="submit-button" @click="showModal">注册</a-button>
        <a-button type="primary" class="submit-button" html-type="submit" >登陆</a-button>
      </a-form-item>
    </a-form>

    <a-modal title="用户注册" :visible="visible" @ok="handleOk('registerForm')"
             :confirmLoading="confirmLoading" @cancel="handleCancel">
      <a-form :form="registerForm">
        <a-form-item v-bind="formItemLayout" label="邮箱">
          <a-input v-decorator="['email', {rules: [{type: 'email', message: '邮箱不合法'},
                   {required: true, message: '请输入邮箱'}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="密码">
          <a-input v-decorator="['password', {rules: [{required: true, message: '请输入密码'},
                   {validator: passwordValidate}]}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="验证码" extra="输入验证码进行验证">
          <a-row :gutter="8">
            <a-col :span="15">
              <a-input v-decorator="['captcha', {rules:
                       [{ required: true, message: '请输入验证码' }]}]"/>
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
          console.log('Received values of form: ', values);
        }
      });
    },
    passwordValidate (rule, value, callback) {
      let message = '密码长度必须在6位以上';
      if (value && value.toString().length < 6) {
        callback(message);
      } else {
        callback();
      }
    },
    showModal () {
      this.visible = true;
    },
    handleOk () {
      this.registerForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
          this.visible = false;
        } else {
          alert('错了');
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
