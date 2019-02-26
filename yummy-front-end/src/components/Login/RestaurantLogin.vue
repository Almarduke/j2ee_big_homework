<template>
  <a-form :form="form" @submit="handleSubmit" style="margin-top: 20px">
    <a-form-item label="识别号" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
      <a-input v-decorator="['note', {rules: [{ required: true, message: 'Please input your email!' }]}]"/>
    </a-form-item>
    <a-form-item label="密码" :label-col="{ span: 5 }" :wrapper-col="{ span: 15 }">
      <a-input v-decorator="['note', {rules: [{ required: true, message: 'Please input your password!' }]}]"/>
    </a-form-item>
    <a-form-item :wrapper-col="{ offset: 5 }">
      <a-button class="submit-button">注册</a-button>
      <a-button type="primary" class="submit-button">登陆</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
export default {
  name: 'RestaurantLogin'
};
</script>

<style scoped>
.submit-button {
  margin: 0 10px;
}
</style>
