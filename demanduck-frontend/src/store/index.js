import { createStore } from 'vuex'

export default createStore({
  state: {
      token: localStorage.getItem('token') || ''
  },
  getters: {
  },
  mutations: {
      setToken(state, token) {
          state.token = token;
          localStorage.setItem('token', token);
      }
  },
  actions: {
  },
  modules: {
  }
})
