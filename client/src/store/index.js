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
    products:[
          {
          productKey: 0,
          productName: "",
          productPic: "",
          productPrice: 0,
          productDesc: "",
          productCnt: 10,
          itemtotalprice: 0,
          productCategory: "",
        },
       ],
    cart: [],
    orderedList: [],
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
    addOrderedList({ state } ) {
      var orderedList = state.cart.filter( ()=> true );
      // console.log("cart", cart ); 실행하려면 상단 매개변수에 cart를 추가할 것
      // console.log("cart 길이", cart.length );
    //  console.log("cart배열 복사", orderedList);
      state.orderedList = orderedList;
      if( state.orderedList ) {
        var cartLength = state.cart.length;
        //console.log( cartLength );
        state.cart.splice( 0, cartLength );
      }
    },
    removeOrderedList({ state }) {
    var orderedLength = state.orderedList.length;
    //console.log( orderedLength );
    state.orderedList.splice( 0, orderedLength);

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
      incrementItemQuantity( state, cartItem ) {
        cartItem.quantity++;
      },
      decrementItemQuantity( state, cartItem ) {
        cartItem.quantity--;
      },
      //가격변경
        incrementItemTotalPrice(state, cartItem) {
            cartItem.itemtotalprice += cartItem.price;
        },
        //가격변경
        decrementItemTotalPrice(state, cartItem) {
            cartItem.itemtotalprice -= cartItem.price;
        },
  },
  getters: {
    isLogin(state){
      return state.token == null ? false : true;
    },
    getcartProducts(state) {
      return state.cart.map(cartItem => {
        const product = state.products.find(product => product.id === cartItem.productId);
        if(product === undefined) {
          return {
            quantity: 0,
            itemtotalprice: 0,
          }
        } else return {
          id: product.id,
          title: product.title,
          price: product.price,
          inventory: product.inventory,
          quantity: cartItem.quantity,
          itemtotalprice: cartItem.itemtotalprice,
          src: product.src,
          divider: true,
          inset: true,
        };
      });
    },
    getcartProductsthis:() => (cartItem) => {
      //const cartItem = state.cart.find(item => item.productId === productItem.id);
      //const product = state.products.find(product => product.id === productItem.id);
      //console.log("getter", state.cart );
      if(cartItem === undefined) {
        return {
          quantity: 0,
          itemtotalprice: 0,
        }
      }
      return {
        product : cartItem.title,
        quantity: cartItem.quantity,
        itemtotalprice: cartItem.itemtotalprice,
      };
    },
    cartTotal(state) {
      let total = 0;
      state.cart.forEach(cartItem => {
        total += cartItem.itemtotalprice;
      });
      return total;
    },
    cartTotalItem(state) {
      let total = 0;
      state.cart.forEach(cartItem => {
        total += cartItem.quantity;
      });
      return total;
    },
    deliveryFee(state) {
      let limit = 80000;
      let delFee = 0;
      let total = 0;
      state.cart.forEach(cartItem => {
      total += cartItem.itemtotalprice;
    });
    if( total >= limit ) {
      return delFee;
    } else if( total < limit ) {
      delFee = 3000;
      return delFee;
    }
  },
    getOrderedProducts(state) {
    return state.orderedList.map(cartItem => {
        const product = state.products.find(product => product.id === cartItem.productId);
        if(product === undefined) {
          return {
            quantity: 0,
            itemtotalprice: 0,
          }
        } else return {
          id: product.id,
          title: product.title,
          price: product.price,
          inventory: product.inventory,
          quantity: cartItem.quantity,
          itemtotalprice: cartItem.itemtotalprice,
          src: product.src,
          divider: true,
          inset: true,
        };
      });
    },
    getOrderedProductsthis:() => (cartItem) => {
      if(cartItem === undefined) {
        return {
          quantity: 0,
          itemtotalprice: 0,
        }
      }
      return {
        product : cartItem.title,
        quantity: cartItem.quantity,
        itemtotalprice: cartItem.itemtotalprice,
      };
    },
    orderTotal(state) {
      let total = 0;
      state.orderedList.forEach(cartItem => {
        total += cartItem.itemtotalprice;
      });
      return total;
    },
    orderTotalItem(state) {
      let total = 0;
      state.orderedList.forEach(cartItem => {
        total += cartItem.quantity;
      });
      return total;
    },
    orderdeliveryFee(state) {
      let limit = 80000;
      let delFee = 0;
      let total = 0;
      state.orderedList.forEach(cartItem => {
      total += cartItem.itemtotalprice;
    });
    if( total >= limit ) {
      return delFee;
    } else if( total < limit ) {
      delFee = 3000;
      return delFee;
    }
  },
  },
});

