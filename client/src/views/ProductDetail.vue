<template>
<div>
  <v-card
    class="mx-auto"
    width="100%"
    elevation="0"
      color="secondary" 
  >
    <v-img
      :src="product.src"
      min-height="200px"
    ></v-img>

    <v-card-title
        style="position: relative;">
      {{ product.title }}
      <v-btn
          absolute
          color="primary"
          class="white--text"
          fab
          medium
          right
          top
          v-on:click="addOrder( product )"
        >
          <v-icon>mdi-cart</v-icon>
        </v-btn>
    </v-card-title>

    <v-card-subtitle>
      {{ String(product.price).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
    </v-card-subtitle>
    <!-- 탭 시작 -->
  <v-card 
      color="secondary" 
    elevation="0">

    <v-tabs
      v-model="tab"
      background-color="transparent"
      color="primary" 
      grow
    >
      <v-tab
        v-for="tab in tabs"
        :key="tab.index"
      >
        {{ tab.index }}
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item
        v-for="tab in tabs"
        :key="tab.index"
      >
        <v-card
        color="secondary" 
          flat
        >
          <v-card-text >{{ tab.text }}</v-card-text>
          <Ratings v-if="tab.rating" />
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
<!-- 탭 끝  -->
  </v-card>
  <v-bottom-sheet v-model="sheet" class=" rounded-b-0" v-if="onchange" >
    <Check :warningsigns="warningsign" 
        v-model="sheet"
        :value="sheet"
        @newsheet="onchange"/>
    </v-bottom-sheet>
  </div>
</template>

<script>
import Check from '../components/Check.vue'
import Ratings from '../components/Ratings.vue'
  export default{
    components: {
          "Check" : Check,
          "Ratings": Ratings
      },
    data(){
        const menuId = this.$route.params.productId;
        const base = this.$store.state.products[menuId-1].base;
      return {
        product: this.$store.state.products[menuId-1],
        show: false,
        tab: null,
        sheet: false,
        warningsign: "장바구니에 추가되었습니다",
        tabs: [
          {
            index: '상세설명',
            text: '주요 성분: '+base,
        rating: false,
          },{
            index: '후기',
            text: '',
            rating: true,
          },
        ],
        }
    },
    methods: {
      addOrder( product ){
        this.$store.dispatch( "addOrder", product );
        this.sheet = ! this.sheet;
      },
      onchange( val ) {
        this.sheet = val
      },
    },
  }
</script>

<style>
/* Helper classes */
.basil--text {
  color: #356859 !important;
}
</style>