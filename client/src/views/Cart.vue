<template>
<div>
  <v-card class="mx-auto" min-height="400"  height="auto" elevation="0" color="secondary">
    <v-list three-line color="secondary">
       <v-card
       elevation = "0"
       color="secondary"
        max-width="100%"
        class="d-flex mx-auto flex-column" 
        v-if="$store.getters.getcartProducts.length === 0"
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

       <template v-for="cart in cart">
         <v-list-item :key="cart.id">
          <v-list-item-avatar width="10vw" height="10vw">
            <v-img :src="cart.src"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>
                {{ cart.title }}
            </v-list-item-title>
            <v-list-item-subtitle>
                {{ String(cart.price).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 <br>
                수량: {{ $store.getters.getcartProductsthis( cart ).quantity }}, 총 금액: {{ String($store.getters.getcartProductsthis( cart ).itemtotalprice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
            </v-list-item-subtitle>
          </v-list-item-content><br><br>
          <div>
          <v-btn outlined color="info"
          v-on:click="addOrder( cart )" class="mr-1 mybtn">추가</v-btn>
          <v-btn outlined color="error" class="mybtn"
          v-on:click="subOrder( cart )">삭제</v-btn>
          </div>
        </v-list-item>

        <v-divider
          v-if="cart.divider"
          :key="cart.title"
          :inset="cart.inset"
        ></v-divider> 
      </template>
    </v-list>
  </v-card>

<v-card
    v-if="$store.getters.getcartProducts.length != 0"
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
          총 {{ $store.getters.cartTotalItem }} 개 주문 합계 : {{ String($store.getters.cartTotal).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-list-item two-line>
      <v-list-item-content class="text-right">
        <v-list-item-title>배송비 별도: {{ String($store.getters.deliveryFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원</v-list-item-title>
        <v-list-item-subtitle>80,000원 이상 구매시 무료배송</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>


    <v-btn block class="mt-12" height="8vh" color="error" outlined 
    v-on:click="addOrderedList( cart )" >
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
  export default {
      components: {
        Check
      },
    data(){
      return {
        cart: this.$store.state.cart,
        cartTotal: this.$store.getters.cartTotal,
        delFee: this.$store.getters.deliveryFee,
        sheet: false,
        orderbtn:"주문내역 확인하기",
        orderbtnshow:"false",
        warningsign: "주문이 완료되었습니다. 주문해주셔서 감사합니다.",
        }
    },
    methods: {
        addOrder( cart ){
            this.$store.dispatch( "addOrder", cart );
      },
        subOrder( cart ){
            this.$store.dispatch( "subOrder", cart );
      },
        addOrderedList( cart ){
            this.$store.dispatch( "addOrderedList", cart );
            this.sheet= true;
      },
        removeOrderedList( cart ){
            this.$store.dispatch( "removeOrderedList", cart );
      },
      onchange( val ) {
        this.sheet = val
      }
    }
  }
</script>

<style scoped>
.mybtn {
  min-width: auto !important;
  padding: 1vw 2vw !important;
}

</style>