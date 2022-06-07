import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import axios from "axios";

Vue.use(Vuex);

export const store =  new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    id: null,
    userkey: null,
    username:null,
    role: null,
    isLogin: false,
  },
  actions: {
    LOGIN({commit}, data){
        commit("loginSuccess",  data.id);
      axios.post("/api/userInfo", {
        email: data.id,
      })
          .then(res => {
            console.log(res);
              commit("setUsername",  res.data.name);
              commit("setRole",  res.data.authorityDtoSet[0].authorityName);
              commit("setUserkey",  res.data.id);
          })
          .catch(err=>{
            console.log(err);
          })
    },
},
  mutations: {
    loginSuccess(state, id) {
      state.isLogin = true;
      state.id = id;
    },
    setUsername(state, username){
      state.username=username;
    },
    setUserkey(state, userkey){
      state.userkey=userkey;
    },
    setRole(state, role){
      state.role=role;
    },
    logout(state) {
      state.isLogin = false;
      state.id = null;
      state.username=null;
      localStorage.removeItem("access_token")
    },
  },
  getters: {
    isLogin(state){
      return state.token == null ? false : true;
    },
  },
});

