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
      <a-layout-content style="height: 800px; padding: 24px; background-color: white">
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="菜品列表" key="1">
            <a-list itemLayout="horizontal" :bordered="true">
              <a-list-item v-for="food in foodList" :key="food.id">
                <a-list-item-meta :description="food.type">
                  <a slot="title" >{{food.name}}</a>
                </a-list-item-meta>
                <div style="margin-right: 20px">{{food.price}}元</div>
                <div style="margin-right: 20px">{{food.number}}份</div>
                <div>
                  <a slot="actions" @click="editFood(food)" class="plain-text">编辑</a>
                  <a slot="actions" @click="deleteFood(food)" class="plain-text delete-button">删除</a>
                </div>
              </a-list-item>
            </a-list>
            <a-button style="float: right; margin: 20px;" type="primary" shape="circle" icon="plus" size="large" @click="addFood"/>
          </a-tab-pane>
          <a-tab-pane tab="饭店经营情况" key="2">
            <marketing-chart></marketing-chart>
          </a-tab-pane>
          <a-tab-pane tab="消费者特征" key="3">
            <customer-chart></customer-chart>
          </a-tab-pane>
          <a-tab-pane tab="消费者满意度" key="4">
            <customer-satisfaction></customer-satisfaction>
          </a-tab-pane>
          <a-tab-pane tab="菜品销售统计" key="5">
            <dish-selling></dish-selling>
          </a-tab-pane>
          <a-tab-pane tab="订单统计" key="6">
            <order-chart></order-chart>
          </a-tab-pane>
          <a-tab-pane tab="配送效率统计" key="7">
            <delivery-time></delivery-time>
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
            <a-input-number :min="0" :max="Number(restaurantInfo.amount)" v-decorator="['amount', {rules: [{ required: true, message: '请输入提现金额' }], initialValue: 0}]"/>
            <span style="margin-left: 10px">元</span>
          </div>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal :title="isEditingFood?'编辑菜品':'添加菜品'" :visible="visible" @ok="handleOk" @cancel="handleCancel">
      <a-form :form="foodInfoForm">
        <a-form-item v-bind="formItemLayout" label="名称">
          <a-input v-decorator="['name', {rules: [{ required: true, message: '请输入名称' }], initialValue: selectedFoodInfo.name}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="类型">
          <a-input v-decorator="['type', {rules: [{ required: true, message: '请输入类型' }], initialValue: selectedFoodInfo.type}]"/>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="价格">
          <a-input-number :min="1" v-decorator="['price', {rules: [{ required: true, message: '请输入价格' }], initialValue: selectedFoodInfo.price}]"/>
          <span style="margin-left: 10px">元</span>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="类型">
          <a-input-number :min="0" v-decorator="['number', {rules: [{ required: true, message: '请输入类型' }], initialValue: selectedFoodInfo.number}]"/>
          <span style="margin-left: 10px">份</span>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout>
</template>

<script>
import { mapGetters } from 'vuex';
import { OK } from '@/utils/status/HttpStatus';
import MarketingChart from '@/components/restaurant/MarketingChart';
import CustomerChart from '@/components/restaurant/CustomerChart';
import CustomerSatisfaction from '@/components/restaurant/CustomerSatisfaction';
import DishSelling from '@/components/restaurant/DishSelling';
import OrderChart from '@/components/restaurant/OrderChart';
import DeliveryTime from '@/components/restaurant/DeliveryTime';

export default {
  name: 'ManageFood',
  components: { CustomerChart, MarketingChart, CustomerSatisfaction, DishSelling, OrderChart, DeliveryTime },
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
      foodList: [],
      isEditingFood: false,
      updateInfoVisible: false,
      withdrawVisible: false,
      visible: false,
      selectedFoodInfo: {
        id: null,
        name: '',
        type: '',
        price: 0,
        number: 0
      }
    };
  },
  computed: {
    ...mapGetters(['restaurantInfo', 'baseUrl'])
  },
  beforeCreate () {
    this.restaurantInfoForm = this.$form.createForm(this);
    this.withdrawForm = this.$form.createForm(this);
    this.foodInfoForm = this.$form.createForm(this);
  },
  mounted () {
    this.$http({
      url: this.baseUrl + '/address/getAll',
      method: 'GET'
    }).then((response) => {
      this.addressList = response.data.data;
    });

    this.$http({
      url: `${this.baseUrl}/food/getAll/${this.restaurantInfo.id}`,
      method: 'GET'
    }).then((response) => {
      let object = response.data;
      this.foodList = object.data;
    });
  },
  methods: {
    editFood (food) {
      this.isEditingFood = true;
      this.visible = true;
      this.selectedFoodInfo = food;
    },
    deleteFood (food) {
      this.$http({
        url: `${this.baseUrl}/food/deleteFood`,
        method: 'POST',
        data: {
          id: food.id,
          restaurantId: this.restaurantInfo.id,
          name: food.name,
          type: food.type,
          price: food.price
        }
      }).then((response) => {
        if (response.data.code === OK) {
          this.foodList = response.data.data;
          this.$message.success(response.data.msg);
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    addFood () {
      this.isEditingFood = false;
      this.visible = true;
      this.selectedFoodInfo = { id: null, name: '', type: '', price: 0, number: 0 };
    },
    handleOk () {
      this.foodInfoForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          const url = `${this.baseUrl}/food/${this.isEditingFood ? 'editFood' : 'addFood'}`;
          this.$http({
            url: url,
            method: 'POST',
            data: {
              id: this.selectedFoodInfo.id,
              restaurantId: this.restaurantInfo.id,
              name: values.name,
              type: values.type,
              price: values.price,
              number: values.number
            }
          }).then((response) => {
            if (response.data.code === OK) {
              this.foodList = response.data.data;
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
    updateInfo () {
      this.restaurantInfoForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/restaurant/updateRestaurantInfo/${this.restaurantInfo.id}`,
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
      (value && /^\d{11}$/.test(value.toString())) ? callback() : callback(message);
    },
    withdrawMoney () {
      this.withdrawForm.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.$http({
            url: `${this.baseUrl}/restaurant/withdrawMoney/${this.restaurantInfo.id}`,
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
