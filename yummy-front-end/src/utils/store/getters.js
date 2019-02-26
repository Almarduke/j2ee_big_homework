const getters = {
  userInfo (state) {
    let userInfo = state.userInfo;
    if (!userInfo.isLogin) {
      userInfo.isLogin = sessionStorage.getItem('isLogin');
      userInfo.userId = sessionStorage.getItem('userId');
      userInfo.token = sessionStorage.getItem('token');
      userInfo.userType = sessionStorage.getItem('userType');
    }
    return userInfo;
  },
  baseUrl (state) {
    return state.url.baseUrl;
  }
};
export default getters;
