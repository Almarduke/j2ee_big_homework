<template>
  <div>
    <a-form style="margin-top: 20px" :form="form" @submit="handleSubmit">
      <a-form-item label="识别号" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['email', {rules: [{ required: true, message: 'Please input your email!' }]}]"/>
      </a-form-item>
      <a-form-item label="密码" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
        <a-input v-decorator="['note', {rules: [{ required: true, message: 'Please input your password!' }]}]"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 5 }">
        <a-button class="submit-button" @click="showModal">注册</a-button>
        <a-button type="primary" class="submit-button" html-type="submit" >登陆</a-button>
      </a-form-item>
    </a-form>

    <a-modal title="用户注册" :visible="visible" @ok="handleOk"
             :confirmLoading="confirmLoading" @cancel="handleCancel">
      <p>dasdsadsad</p>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'RestaurantLogin',
  data () {
    return {
      visible: false,
      confirmLoading: false
    };
  },
  beforeCreate () {
    this.form = this.$form.createForm(this);
  },
  methods: {
    handleSubmit (e) {
      console.log('我运行了');
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
        } else {
          console.log('error');
        }
      });
    },
    showModal () {
      this.visible = true;
    },
    handleOk () {
      this.ModalText = 'The modal will be closed after two seconds';
      this.confirmLoading = true;
      setTimeout(() => {
        this.visible = false;
        this.confirmLoading = false;
      }, 2000);
    },
    handleCancel () {
      console.log('Clicked cancel button');
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
