import * as actionTypes from './action-types';
import * as mutationTypes from './mutation-types';

const actions = {
  [actionTypes.LOGIN] ({ commit }, data) {
    commit(mutationTypes.LOGIN, data);
  },
  [actionTypes.LOGOUT] ({ commit }) {
    commit(mutationTypes.LOGOUT);
  }
};
export default actions;
