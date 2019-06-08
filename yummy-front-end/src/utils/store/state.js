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
    '#4D80E6', '#8378EA', '#A92528', '#273A49',
    '#000080', '#008000', '#FFBF00', '#E60000',
    '#0000CD', '#9ACD32', '#FF1493', '#5E86C1',
    '#33E6CC', '#E17B5A', '#DDAA00', '#D5811E',
    '#36BF36', '#6633CC', '#703000', '#5AA1A9',
    '#FB7293', '#FFDB5C', '#37A2DA', '#9FE6B8',
    '#002D5B', '#50C878', '#ADFF2F', '#FFFF4D',
    '#D2B48C', '#FF4500', '#8FD4B2', '#800000',
    '#FFE5B4', '#FFD700', '#A9A9A9', '#FFB366',
    '#99E64D', '#FFFF00', '#FF9F7F', '#FFA500',
    '#E6E6FA', '#FF2400', '#9370DB', '#8A2BE2',
    '#FFFF99', '#FFA500', '#FFCC00', '#00CED1'
  ]
};
export default state;
