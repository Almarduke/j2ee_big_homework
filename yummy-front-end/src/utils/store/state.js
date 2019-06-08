const state = {
  userInfo: {
    isLogin: false,
    userId: '',
    userType: '',
    token: ''
  },
  restaurantInfo: {
    id: '',
    name: '',
    phone: '',
    address: '',
    amount: ''
  },
  baseUrl: 'http://localhost:8086',
  colorBoard: [
    '#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8',
    '#FF9F7F', '#8378EA', '#A92528', '#273A49',
    '#000080', '#008000', '#FFBF00', '#E60000',
    '#FFB366', '#9ACD32', '#5E86C1', '#FF1493',
    '#FFFF99', '#36BF36', '#FFA500', '#6633CC',
    '#00FFFF', '#50C878', '#ADFF2F', '#FFFF4D',
    '#D2B48C', '#FF4500', '#A9A9A9', '#33E6CC',
    '#0000CD', '#0000CD', '#FFE5B4', '#800000',
    '#4D80E6', '#99E64D', '#FFFF00', '#FFA500',
    '#E6E6FA', '#FF2400', '#8A2BE2', '#00CED1',
    '#FFD700', '#FFCC00', '#9370DB', '#800000',
    '#5AA1A9', '#E17B5A', '#8FD4B2', '#D5811E'
  ]
};
export default state;
