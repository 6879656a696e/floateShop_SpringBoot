<template>
   <v-container fluid secondary style="min-height: 100vh">
     <div class="d-flex justify-end" v-if="$store.state.role==='ROLE_ADMIN'">
       <router-link to="/addProduct"><v-btn color="primary">상품 등록하기</v-btn></router-link>
     </div>
        <v-row dense>
          <v-col
            v-for="product in product"
            :key="product.productKey"
            cols="12"
            lg="3"
            sm="4"
            md="3"
            class="d-flex justify-space-between"
          >

    <v-hover v-slot="{ hover }">
    <v-card
      class="mx-auto d-flex flex-column mt-10 mb-10"
      color="grey lighten-4"
      elevation="0"
      rounded-lg
      width="290"
    >
      <v-img
        :aspect-ratio="16/9"
        :src='"/upload/"+product.productPic'
      >
        <v-expand-transition>
        <router-link 
        v-bind:to="{ path: `/productdetail/${ product.productCategory }/${ product.productKey }`, params: { index:`$( product.productKey )` },}">
          <div
            v-if="hover"
            class="d-flex transition-fast-in-fast-out success darken-4 v-card--reveal text-h5 white--text"
            style="height: 100%;"
          >자세히 보기</div>
        </router-link>
        </v-expand-transition>
      </v-img>
      <v-card-text
        class="pt-6"
        style="position: relative;"
      >
        <v-btn
            v-if="$store.state.role==='ROLE_USER'"
          absolute
          color="primary"
          class="white--text justify-center"
          fab
          medium
          right
          top
          v-on:click="addOrder( product.productKey, $store.state.userkey ), sendCartTotal()"
        >
          <v-icon>mdi-cart</v-icon>
        </v-btn>
        <router-link 
        v-bind:to="{ path: `/productdetail/${ product.productCategory }/${ product.productKey }`, params: { productId:`$( product.productKey )` },}">
        <div class="grey--text mb-2">
          {{ product.productCategory }}
        </div>
        <div class="tgrey--text text-h6"
          >{{ product.productName }}</div>
        <div class="font-weight-light mb-2">
          {{ String(product.productPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </div>
        </router-link>
      </v-card-text>

      <div v-if="$store.state.role==='ROLE_ADMIN'" class="d-flex justify-end align-center my-4" >
        <router-link v-bind:to="{ path: `/modiProduct/${ product.productKey }`, params: { productId:`$( product.productKey )` },}">
        <v-btn depressed x-small color="#ea9d96" class="mx-1" >수정</v-btn></router-link>
        <v-btn  depressed x-small color="#8aaace" class="mx-1" @click="pDelete(product.productKey)">삭제</v-btn>
      </div>
    </v-card>
    </v-hover>
    </v-col>
        </v-row>

    <v-bottom-sheet v-model="sheet" class=" rounded-b-0" v-if="onchange" >
    <Check :warningsigns="warningsign" 
        v-model="sheet"
        :value="sheet"
        @newsheet="onchange"/>
    </v-bottom-sheet>
      </v-container>
</template>

<script>
    import Check from '../components/Check.vue'
  export default{
    components: {
          "Check" : Check
      },
    data(){
      return {
        cartItemTotal: 0,
        product: {
          productKey: null,
          productName: '',
          productPic: '',
          productPrice: null,
          productDesc: '',
          productCnt: null,
          itemtotalprice: 0,
          productCategory: "",
        },
        sheet: false,
        warningsign: "장바구니에 추가되었습니다.",
      }
    },
    watch:{
      cartItemTotal(){
        this.sendCartTotal()
      }
    },
    methods: {
      sendCartTotal(){
        let that=this;
        that.$emit('getCartTotal', that.cartItemTotal);
      },
      addOrder( productKey, userKey ){
        this.sheet = ! this.sheet;
        this.$axios.get('api/addCart', {params:  {productKey: productKey, userKey: userKey }})
            .then((res) => {
              console.log(res);
              this.setCartTotal();
              // alert(res.data);
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
      onchange( val ) {
        this.sheet = val;
      },
      pDelete(id){
        //let that=this;
        this.$axios.get('api/admin/deleteProduct', {params:  { id: id }})
            .then((res) => {
              console.log(res);
              alert(res.data);
              this.$router.go();
            })
            .catch(err => {
              console.log(err);
            })
      }
    },
    created() {
      let that= this;
      this.$axios.get('api/productList')
          .then((res) => {
            console.log(res);
            that.product=res.data;
          })
          .catch(err => {
            console.log(err);
          });
    }
  }
</script>

<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  position: absolute;
  width: 100%;
}
.myNav {
  border-radius: 25px 25px 0 0;
  text-align: center;
}
</style>

