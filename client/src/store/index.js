import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import axios from "axios";

Vue.use(Vuex);

export const store =  new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    id: null,
    username:null,
    role: null,
    isLogin: false,
    products:[
        // {
        //   id: 1,
        //   title: '디퓨저1',
        //   src: require('@/assets/imgs/diffuser1.jpg'),
        //   price: 39900,
        //   base: "Lavender",
        //   flex:4,
        //   quantity: 1,
        //   itemtotalprice: 0,
        //   alias: "diffuser",
        // },
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
          })
          .catch(err=>{
            console.log(err);
          })
    },
    addOrder({ state, commit }, product) {
      const productItem = state.products.find(item => item.id === product.id);
      const cartItem = state.cart.find(item => item.id === product.id);

      //장바구니에 같은 제품이 없을 때 장바구니에 추가
      if( !cartItem ) {
        //console.log("productItem", productItem.title );
        commit('addCart', productItem);

      } else {
        if( product.quantity > 0 ) {
          //장바구니에 같은 제품이 있을 때 장바구니의 제품 수량을 증가
          //console.log( "addcartQuan", cartItem.quantity );
          commit('incrementItemQuantity', cartItem);
        }

    commit( 'incrementItemTotalPrice', cartItem );
    //console.log(cartItem.itemtotalprice);
      }
  },
    //장바구니 수량 빼기
    subOrder({state, commit}, cart) {
      const cartItem = state.cart.find(item => item.id === cart.id);
      if( cartItem.quantity > 0 ) {
        commit('decrementItemQuantity', cartItem);
        commit('decrementItemTotalPrice', cartItem);
        //console.log( "subCartQuan", cartItem.quantity );
     }

     if( cartItem.quantity == 0 ) {
        commit('subCart', cartItem);
        //console.log( "cartItem: ", cartItem );
       //connsole.log( "findIndex",state.cart.findIndex( cart=> cart.id == cartItem.id ));
     }
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
    setRole(state, role){
      state.role=role;
    },
    logout(state) {
      state.isLogin = false;
      state.id = null;
      state.username=null;
      localStorage.removeItem("access_token")
    },
    addCart( state, product ){
      state.cart.push({
          id: product.id,
          title: product.title,
          src: product.src,
          price: product.price,
          itemtotalprice: product.price,
          divider: true,
          inset: true,
          quantity: 1,
        });

        //alert("장바구니에 상품을 담았습니다.");
        //console.log( "addCart", product );
      },
      subCart( state, cartItem ) {
       const deleteThis = state.cart.findIndex( cart=> cart.id == cartItem.id );
       state.cart.splice( deleteThis, 1 );
        //alert("장바구니에 상품을 제거했습니다.");
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

