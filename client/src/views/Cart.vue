<template>
<div style="min-height: 100vh">
  <v-card class="mx-auto" min-height="400"  height="auto" elevation="0" color="secondary">
    <v-list three-line color="secondary">
       <v-card
       elevation = "0"
       color="secondary"
        max-width="100%"
        class="d-flex mx-auto flex-column" 
        v-if="item.length===0"
      >
        <v-card-title class="success--text pt-12 flex-column">
          <div class="pt-12">
            <v-icon class="mx-auto" color="error" size="30vw">mdi-cart-remove</v-icon>
            </div><br>
           <div class="error--text text-center myfont">
             <h5>장바구니가 비어있어요. : ( </h5>
           </div>
           <router-link to="/products" >
           <div block width="100%" class="mt-12 white--text text-center primary myfont">
             <v-icon size="24px" color="white" class="mx-3">mdi-cart-arrow-down</v-icon>
            장바구니에 담으러 가기<v-icon size="24px" class="mx-3" color="white">mdi-cart-arrow-down</v-icon>
           </div>
           </router-link>
        </v-card-title>
       </v-card>

       <template>
         <v-list-item  v-for="(cart, idx) in item" :key="idx">

           <v-btn color="transparent" depressed class="mybtn mr-5" @click="deleteItem( cart.product.productKey, $store.state.userkey)">&#215;</v-btn>
           <v-checkbox
               color="success"
               hide-details
               @click="calcPrice(cart.count, cart.count*cart.product.productPrice)"
               v-model="goOrderItems"
               :value="cart.id"
           ></v-checkbox>

          <v-list-item-avatar width="10vw" height="10vw">
            <v-img :src="'/upload/'+cart.product.productPic"></v-img>
          </v-list-item-avatar>

          <v-list-item-content class="ml-3">
            <v-list-item-title>
                {{ cart.product.productName }}
            </v-list-item-title>
            <v-list-item-subtitle>
                {{ String(cart.product.productPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 <br>
                수량: {{ cart.count }}, 총 금액: {{ String(cart.count*cart.product.productPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
            </v-list-item-subtitle>
          </v-list-item-content><br><br>
          <div>
          <v-btn outlined color="info"
          v-on:click="addCount( cart.product.productKey, $store.state.userkey ), setCartTotal(), sendCartTotal()" class="mr-1 mybtn">&#43;</v-btn>
          <v-btn outlined color="error" class="mybtn"
          v-on:click="subCount( cart.product.productKey, $store.state.userkey ), setCartTotal(), sendCartTotal()">&#45;</v-btn>
          </div>
        </v-list-item>
      </template>
    </v-list>
  </v-card>

<v-card
    v-if="item != ''"
    class="ml-auto"
    width="100%"
    tile
    color="secondary"
    elevation="0"
  >
  <v-divider class="mb-5"></v-divider>
    <v-list-item>
      <v-list-item-content class="text-right">
        <v-list-item-title class="responFont text-h5">
          총 {{ itemTotal }} 개 주문 합계 : {{ String(cartTotal).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-list-item two-line>
      <v-list-item-content class="text-right">
        <v-list-item-title>배송비 별도: {{ String(delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원</v-list-item-title>
        <v-list-item-subtitle>80,000원 이상 구매시 무료배송</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>


    <v-btn block class="mt-12" height="8vh" color="error" outlined
    @click="goOrder(delFee)" >
      배송비 포함 {{ String(cartTotal +  delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 주문하기
    </v-btn>

  </v-card>

  <div class="text-center">
    <v-bottom-sheet v-model="sheet" class=" rounded-b-0"
    v-if="onchange" 
      
>
      <Check 
      :warningsigns="warningsign" 
      :orderbtns="orderbtn"  
      :orderbtnshow="orderbtnshow"
        v-model="sheet"
        :value="sheet"
        @newsheet="onchange"
      />
    </v-bottom-sheet>
  </div>
</div>
</template>

<script>
import Check from '../components/Check.vue'
//import qs from 'qs'
  export default {
      components: {
        Check
      },
    data(){
      return {
        orderItems:[],
        orderItemsCount:[],
        goOrderItems: [],
        CartItemIdList:{},
        cartItemTotal: 0,
        total: null,
        item:{
          cart:{
            id: null,
            total: null,
          },
          count: null,
          product: {
            productKey: null,
            productName: '',
            productPic: '',
            productPrice: null,
            productDesc: '',
            productCnt: null,
            productCategory: "",
          }
        },
        itemTotal: 0,
        cartTotal: 0,
        delFee: 0,
        sheet: false,
        orderbtn:"주문내역 확인하기",
        orderbtnshow:"false",
        warningsign: "주문이 완료되었습니다. 주문해주셔서 감사합니다.",
        }
    },
    created() {
      this.loadItems();
    },
    watch:{
      cartItemTotal(){
        this.sendCartTotal()
      }
    },
    methods: {
      loadItems() {
        let that = this;
        this.$axios.get('api/getCartList', {
          params: {
            userKey: that.$store.state.userkey
          }
        })
            .then((res) => {
              that.item = res.data;
            })
            .catch(err => {
              console.log(err);
            })
      },
      addCount(productKey, userKey) {
        this.$axios.get('api/addCount', {
          params: {
            productKey: productKey,
            userKey: userKey,
          }
        })
            .then((res) => {
              console.log(res.data);
              this.setCartTotal();
              this.$router.go();
            })
            .catch(err => {
              console.log(err);
            });
      },
      setCartTotal(){
        let that=this;
        this.$axios.get('api/getCartTotal', {params:  {
            userKey: this.$store.state.userkey
          }})
            .then((res) => {
              that.cartItemTotal=res.data;
            })
            .catch(err => {
              console.log(err);
            });
      },
      subCount(productKey, userKey) {
        this.$axios.get('api/subCount', {
          params: {
            productKey: productKey,
            userKey: userKey,
          }
        })
            .then((res) => {
              console.log(res.data);
              this.setCartTotal();
              this.$router.go();
            })
            .catch(err => {
              console.log(err);
            });
      },
      deleteItem(productKey, userKey) {
        this.$axios.get('api/deleteCart', {
          params: {
            productKey: productKey,
            userKey: userKey,
          }
        })
            .then((res) => {
              console.log(res.data);
              this.setCartTotal();
              alert(res.data);
              this.$router.go();
            })
            .catch(err => {
              console.log(err);
            });
      },
      sendCartTotal(){
        let that=this;
        that.$emit('getCartTotal', that.cartItemTotal);
      },
      onchange(val) {
        this.sheet = val;
      },
      goOrder(delFee) {
        let that=this;
        this.$axios.get('api/goOrder', {
          params:{
            cartIdList: that.goOrderItems.join(","),
            userKey: this.$store.state.userkey,
            delFee: delFee,
          }
        })
        .then((res) => {
          console.log(res.data);
           alert("주문되었습니다. 감사합니다.");
           this.$router.push("/orderList");
        })
        .catch(err => {
          console.log(err);
        })
      },
      calcPrice(cnt, price) {
        let that = this;
        that.cartTotal=0;
        that.itemTotal=0;
        that.orderItemsCount.push(cnt);
        that.orderItems.push(price);
          for (var i = 0; i < that.goOrderItems.length; i++) {
            that.cartTotal += that.orderItems[i];
            that.itemTotal += that.orderItemsCount[i];
          }

        if (that.cartTotal < 80000) {
          that.delFee = 3000;
        } else {
          that.delFee = 0;
        }

      },
    }
  }
</script>

<style scoped>
.mybtn {
  min-width: auto !important;
  padding: 1vw 1vw !important;
}

</style>