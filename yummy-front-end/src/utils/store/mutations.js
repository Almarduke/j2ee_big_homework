import * as types from './mutation-types';

const mutations = {
  [types.LOGIN] (state, userInfo) {
    let stateUserInfo = state.userInfo;
    sessionStorage.setItem('userId', userInfo.userId); // 添加到sessionStorage
    sessionStorage.setItem('token', userInfo.token); // 添加到sessionStorage
    sessionStorage.setItem('userType', userInfo.userType);
    sessionStorage.setItem('isLogin', true);
    stateUserInfo.userId = userInfo.userId; // 同步的改变store中的状态
    stateUserInfo.token = userInfo.token;
    stateUserInfo.userType = userInfo.userType;
    stateUserInfo.isLogin = true;
  },
  [types.LOGOUT] (state) { // 退出，删除状态
    let stateUserInfo = state.userInfo;
    sessionStorage.removeItem('userId'); // 移除sessionStorage
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userType');
    sessionStorage.removeItem('isLogin');
    stateUserInfo.userId = ''; // 同步的改变store中的状态
    stateUserInfo.token = '';
    stateUserInfo.userType = '';
    stateUserInfo.isLogin = false;
  },
  [types.SET_RESTAURANT_INFO] (state, restaurantInfo) {
    let stateRestaurantInfo = state.restaurantInfo;
    sessionStorage.setItem('restaurantId', restaurantInfo.id);
    sessionStorage.setItem('restaurantName', restaurantInfo.name);
    sessionStorage.setItem('restaurantPhone', restaurantInfo.phone);
    sessionStorage.setItem('restaurantAddress', restaurantInfo.address);
    stateRestaurantInfo.id = restaurantInfo.id;
    stateRestaurantInfo.name = restaurantInfo.name;
    stateRestaurantInfo.phone = restaurantInfo.phone;
    stateRestaurantInfo.address = restaurantInfo.address;
  }
};
export default mutations;
