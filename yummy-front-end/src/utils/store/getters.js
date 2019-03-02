const getters = {
  userInfo (state) {
    let userInfo = state.userInfo;
    if (!userInfo.isLogin) {
      userInfo.isLogin = sessionStorage.getItem('isLogin');
      userInfo.userId = sessionStorage.getItem('userId');
      userInfo.token = sessionStorage.getItem('token');
      userInfo.userType = sessionStorage.getItem('userType');
      userInfo.userLevel = sessionStorage.getItem('userLevel');
    }
    return userInfo;
  },
  baseUrl (state) {
    return state.baseUrl;
  },
  restaurantInfo (state) {
    let restaurantInfo = state.restaurantInfo;
    if (restaurantInfo.id === '') {
      restaurantInfo.id = sessionStorage.getItem('restaurantId');
      restaurantInfo.name = sessionStorage.getItem('restaurantName');
      restaurantInfo.phone = sessionStorage.getItem('restaurantPhone');
      restaurantInfo.address = sessionStorage.getItem('restaurantAddress');
    }
    return restaurantInfo;
  }
};
export default getters;
