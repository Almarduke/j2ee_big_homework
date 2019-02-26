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
  }
};
export default mutations;
