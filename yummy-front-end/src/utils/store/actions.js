import * as actionTypes from './action-types';
import * as mutationTypes from './mutation-types';

const actions = {
  [actionTypes.LOGIN] ({ commit }, data) {
    commit(mutationTypes.LOGIN, data);
  },
  [actionTypes.LOGOUT] ({ commit }) {
    commit(mutationTypes.LOGOUT);
  },
  [actionTypes.SET_RESTAURANT_INFO] ({ commit }, data) {
    commit(mutationTypes.SET_RESTAURANT_INFO, data);
  }
};
export default actions;
