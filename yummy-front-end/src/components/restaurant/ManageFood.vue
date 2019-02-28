<template>
  <a-layout>
    <a-layout-sider width="240" style="margin-top: 20px; background-color: rgba(0,0,0,0)">
      <a-card hoverable style="width: 240px;">
        <img src="../../assets/rice.jpg" alt="" slot="cover"/>
        <a-card-meta :title="restaurantInfo.name">
          <p slot="description">{{restaurantInfo.phone}}</p>
          <p slot="description">{{restaurantInfo.address}}</p>
        </a-card-meta>
      </a-card>
    </a-layout-sider>
    <a-layout style="margin-top: 20px; padding: 0 24px 24px">
      <a-layout-content style="height: 700px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="菜品列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="food in foodList" :key="food.id">
                <a-list-item-meta :description="food.type">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div>{{food.price}}元</div>
                <div style="margin-left: 10px">
                  <a slot="actions" @click="editFood(food)" class="plain-text">编辑</a>
                  <a slot="actions" @click="deleteFood(food)" class="plain-text delete-button">删除</a>
                </div>
              </a-list-item>
            </a-list>
            <a-button style="float: right; margin: 20px;" type="primary" shape="circle" icon="plus" size="large" @click="addFood"/>
          </a-tab-pane>
        </a-tabs>
      </a-layout-content>
    </a-layout>

    <a-modal :title="isEditingFood?'编辑菜品':'添加菜品'" :visible="visible" @ok="handleOk" @cancel="handleCancel">
      <a-form :form="foodInfoForm">
        <a-form-item v-bind="formItemLayout" label="名称">
          <a-input v-decorator="['name', {rules: [{ required: true, message: '请输入名称' }], initialValue: selectedFoodInfo.name}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="类型">
          <a-input v-decorator="['type', {rules: [{ required: true, message: '请输入类型' }], initialValue: selectedFoodInfo.type}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="价格">
          <div>
            <a-input-number :min="1" v-decorator="['price', {rules: [{ required: true, message: '请输入价格' }], initialValue: selectedFoodInfo.price}]"/>
            <span style="margin-left: 10px">元</span>
          </div>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'ManageFood',
  data () {
    return {
      formItemLayout: {
        labelCol: {
          span: 8
        },
        wrapperCol: {
          span: 12
        }
      },
      foodList: [
        { id: 0, name: '蛋炒饭', price: 3, type: '我觉得可以' },
        { id: 1, name: '土豆', price: 76, type: '我觉得不行' }
      ],
      isEditingFood: false,
      visible: false,
      selectedFoodInfo: {
        id: 0,
        name: 'sadsdad',
        type: 'dsada',
        price: 233
      }
    };
  },
  computed: {
    ...mapGetters(['restaurantInfo', 'baseUrl'])
  },
  beforeCreate () {
    this.foodInfoForm = this.$form.createForm(this);
  },
  methods: {
    editFood (food) {
      this.isEditingFood = true;
      this.visible = true;
    },
    deleteFood (food) {
    },
    addFood () {
      this.isEditingFood = false;
      this.visible = true;
    },
    handleOk () {
      this.foodInfoForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          console.log('やったぜ');
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
