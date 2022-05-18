<template>
  <div class="text-center" >
      <v-sheet
        class="text-center rounded-b-0 pt-5 pb-12"
        height="auto"
      >
        <div class="pt-5"><v-icon size="50px">mdi-cart-check</v-icon></div>
        <p class="pt-5">
          {{ warningsigns }}
        </p>

         <v-btn
          class="mt-6"
          text
          color="red"
          @click="onclickbtn"
        >
          닫기
        </v-btn>
        <router-link to="/orderdetail" 
          v-if="orderbtnshow">
        <v-btn
          class="mt-6"
          text
          color="red"
        >
          {{ orderbtns }}
        </v-btn>
        </router-link>

        <v-btn
          class="mt-6"
          text
          color="red"
          v-if="delbtnshow"
          @click="deleteConfirm"
        >
          {{ delbtn }}
        </v-btn>
      </v-sheet>
  </div>
</template>
<script>
export default {
    props: ['warningsigns', 'orderbtns', 'orderbtnshow', 'gopages', 'delbtn', 'delbtnshow', ],
  data: () => ({
    sheet: false,
    all: true,
  }),
  methods: {
        deleteConfirm(){
          this.$emit('deleteall', this.all );
          this.$emit('newsheet', this.sheet )
        },
        onclickbtn(){
          this.$emit('newsheet', this.sheet )
        }
  },
}
</script>
