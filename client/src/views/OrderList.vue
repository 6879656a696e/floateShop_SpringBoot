<template>
  <div style="min-height: 100vh">
        <v-card
       elevation = "0"
       color="secondary"
        max-width="100%"
        min-height="700px"
        height="100%"
        class="d-flex mx-auto flex-column" 
        v-if="order.length ===0"
      >
        <v-card-title class="success--text pt-12 flex-column">
          <div class="pt-12">
            <v-icon class="mx-auto" color="error" size="30vw">mdi-alert-decagram-outline</v-icon>
            </div><br>
           <div class="myfont error--text text-center">
             <p>주문 내역을 확인할 수 없습니다. <v-icon size="4vw" color="error">mdi-emoticon-cry</v-icon> </p>
           </div>
        </v-card-title>
       </v-card>

    <v-card
        elevation = "0"
        color="secondary"
        max-width="100%"
        min-height="700px"
        v-else>
      <v-list-item v-for="(order, idx) in order" :key="idx">
        <router-link style="width:100%;" v-bind:to="{path:`/orderdetail/${order.id}`, params:{orderId:`${order.id}`},}">
        <v-list-item-content>
          <v-list-item-title class="mb-2"># {{order.id}}</v-list-item-title>
          <v-list-item-subtitle>
            주문일자: {{  order.orderDateTime }}
          </v-list-item-subtitle>
          <v-list-item-subtitle>
            {{ String(order.totalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",")  }} 원
          </v-list-item-subtitle>
          <v-divider></v-divider>
        </v-list-item-content>
        </router-link>
      </v-list-item>
    </v-card>
  </div>
</template>

<script>
//import qs from 'qs'
  export default {
    components: {
    },
    data () {
      return {
        order: {
          delFee: 0,
          id: 0,
          isOrderCancel: 0,
          orderDateTime: "",
          totalPrice: 0,
          user:{
            id: null,
            email: null,
            name: null,
          }
        },
        show: true,
        sheet:false,
        warningsign: "주문 전체가 취소됩니다. 취소하시겠습니까?",
        userKey: 0,
      }
    },
    created() {
      let that=this;
      that.userKey=this.$store.state.userkey;
      this.loadOrder();
    },
    methods : {
      loadOrder(){
        let that=this;
        this.$axios.post('api/getOrder', null,{
          params:{
            id: that.userKey
          }
        })
            .then((res)=>{
              console.log(res.data);
              that.order=res.data;
            })
            .catch((err)=>{
              console.log(err);
            })
      },
    }
  }
</script>

<style scoped>
.myfontsize {
  font-size: 1.2rem !important;
  font-family: "Noto Sans KR";
}
</style>