<template>
   <v-container fluid secondary>
        <v-row dense>
          <v-col
            v-for="product in product"
            :key="product.id"
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
        :src="product.src"
      >
        <v-expand-transition>
        <router-link 
        v-bind:to="{ path: `/productdetail/${ product.alias }/${ product.id }`, params: { index:`$( product.id )` },}">
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
          absolute
          color="primary"
          class="white--text justify-center"
          fab
          medium
          right
          top
          v-on:click="addOrder( product )"
        >
          <v-icon>mdi-cart</v-icon>
        </v-btn>
        <router-link 
        v-bind:to="{ path: `/productdetail/${ product.alias }/${ product.id }`, params: { index:`$( product.id )` },}">
        <div class="grey--text mb-2">
          {{ product.base }}
        </div>
        <div class="tgrey--text text-h6"
          >{{ product.title }}</div>
        <div class="font-weight-light mb-2">
          {{ String(product.price).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} 원
        </div>
        </router-link>
      </v-card-text>
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
        product: this.$store.state.products,
        sheet: false,
        warningsign: "장바구니에 추가되었습니다.",
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

