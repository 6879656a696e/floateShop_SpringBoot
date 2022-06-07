<template>
  <div style="min-height: 100vh">
        <v-card
       elevation = "0"
       color="secondary"
        max-width="100%"
        min-height="700px"
        height="100%"
        class="d-flex mx-auto flex-column" 
        v-if="item === ''"
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
  
  <v-stepper
    v-model="e13"
    vertical
       elevation = "0"
       min-height="100%"
       color="secondary"
    v-show="item != ''"

  >

    <v-stepper-step
      step="1"
    >
      주문 내역
    </v-stepper-step>

    <v-stepper-content step="1">
     <template>
         <v-list-item v-for="(orderedList, idx) in item" :key="idx">

          <v-list-item-avatar width="10vw" height="10vw">
            <v-img :src="'/upload/'+orderedList.itemPic"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>
                {{ orderedList.itemName }}
            </v-list-item-title>
            <v-list-item-subtitle>
                {{ order.orderDateTime }}
            </v-list-item-subtitle>

            <v-list-item-subtitle>
                {{ String(orderedList.itemPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 <br>
                수량: {{ orderedList.itemCount }} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 총 금액: {{ String(orderedList.itemTotalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>
      </template>

      <v-card
    v-show="item != ''"
    class="ml-auto"
    width="100%"
    min-height="700px"
    height="100%"
    tile
    elevation="0"
  >
  <v-divider class="mb-5"></v-divider>
    <v-list-item>
      <v-list-item-content class="text-right">
        <p class="myfontsize">
          총 {{ orderedCnt }} 개 주문 합계 : {{ String(order.totalPrice-order.delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </p>
      </v-list-item-content>
    </v-list-item>

    <v-list-item two-line>
      <v-list-item-content class="text-right">
        <v-list-item-title>배송비 별도: {{ String(order.delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원</v-list-item-title>
        <v-list-item-subtitle>80,000원 이상 구매시 무료배송</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

  </v-card>


      <v-btn
      block
        color="error"
         class="mt-12"
        @click="sheet = !sheet"
      >

        배송비 포함 {{ String(order.totalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 주문취소하기
      </v-btn>
    </v-stepper-content>

    <v-stepper-step
    :rules="[() => false]"
      v-show="delall == true "
      color="error"
      step="2"
    >
      주문 취소 완료
      <small>결제 금액은 전액 본인 계좌로 입금됩니다.  </small>
    </v-stepper-step>

    <v-stepper-content step="2">
      <v-card
        class="mb-12"
        height="auto"
        elevation="0"
      >
      <v-list-item>
      <v-list-item-content class=" grey--text">
        <p >
          총 {{ orderedCnt }} 개 환불 금액 : {{ String(order.totalPrice-order.delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </p>
        <p class="myfontsize">
          배송비 포함 {{ String(order.totalPrice+order.delFee).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원 환불 완료
        </p>
      </v-list-item-content>
    </v-list-item>
      </v-card>
      
    </v-stepper-content>

    <v-stepper-step step="next"
    v-show="e13 != 2">
      배송 준비
    </v-stepper-step>
  </v-stepper>
  <v-bottom-sheet
   v-model="sheet"
   class=" rounded-b-0"  v-if="onchange"
>
  <Check 
  :warningsigns="warningsign" 
  :delbtn="delbtn"  
  :e13="e13"
  :delbtnshow="delbtnshow"
        @newsheet="onchange"
        @deleteall="deleteAll"
   />
  </v-bottom-sheet>
  </div>
</template>

<script>
import Check from '../components/Check.vue'
//import qs from 'qs'
  export default {
    components: {
      Check,
    },
    data () {
      return {
        order: {
          delFee: 0,
          id: 0,
          isOrderCancel: 0,
          orderDateTime: "",
          totalPrice: 0,
        },
        item:{
          id: 0,
          isItemCancel: 0,
          itemCount: 0,
          itemKey: 0,
          itemName: "",
          itemPic:"",
          itemPrice: 0,
          itemTotalPrice: 0,
          order: {
            delFee: 0,
            id: 0,
            isOrderCancel: 0,
            orderDateTime: "",
            totalPrice: 0,
          }
        },
        e13: 1,
        delbtn:"주문취소",
        delbtnshow:"false",
        delall: "false",
        deldone: "true",
        show: true,
        sheet:false,
        warningsign: "주문 전체가 취소됩니다. 취소하시겠습니까?",
        userKey: 0,
        orderedCnt: 0,
        orderId: this.$route.params.orderId,
      }
    },
    created() {
      let that=this;
      that.userKey=this.$store.state.userkey;
      this.loadOrderList();
      this.loadOrder();
    },
    methods : {
      loadOrder(){
        let that=this;
        this.$axios.get('http://localhost:8080/api/getThisOrder', {
          params:{
            orderId: that.orderId
          }
        })
            .then((res)=>{
              that.order=res.data;
            })
            .catch((err)=>{
              console.log(err);
            })
      },
      loadOrderList(){
        let that=this;
        this.$axios.get('http://localhost:8080/api/getOrderList', {
          params:{
            orderId: that.orderId
          }
        })
            .then((res)=>{
              that.item=res.data;
              for(let i=0; i<that.item.length; i++){
                that.orderedCnt+=that.item[i].itemCount;
              }
            })
            .catch((err)=>{
              console.log(err);
            })
      },
      onchange( val ) {
        this.sheet = val
      },
      deleteAll( val  ) {
        let that=this;
        this.delall = val;
        this.$axios.get('http://localhost:8080/api/cancelOrder', {
          params: {
            orderId: that.orderId
          }
        })
            .then((res) => {
              console.log(res);
              alert("주문을 취소했습니다.");
              that.item=res.data;
              that.order=res.data;
            })
            .catch((err) => {
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