<template>
  <v-card
    class="mx-auto my-12"
    width="100%"
    elevation="0"
    color="secondary"
  >
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-img
      height="auto"
      :src="require('@/assets/imgs/store.jpg')"
    ></v-img>

    <v-card-title class="logo justify-center" >floatedStore</v-card-title>

    <v-card-text>
        <div class="grey--text ms-4 text-right">
          서울특별시 송파구 석촌호수로 1231 
        </div>

      <div class="my-4 text-subtitle-1">
        나만의 향기를 직접 만들고 싶다면?
      </div>

      <div>
        <p>저희 매장을 방문하셔서 직접 향기를 만들어 보세요. 향에 대해 잘 몰라도 전문 조향사가 함께 당신의 향을 찾아 드립니다.</p>
        <p>자사 제품 시향은 별도 예약 없이 방문하시면 됩니다. : ) </p>
      </div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>

    <p class=" justify-center mt-4 text-center"> <v-icon>mdi-clock-time-eleven-outline</v-icon>원하시는 시간을 선택해주세요 <v-icon>mdi-clock-time-eleven-outline</v-icon></p>

    <v-card-text class="pl-2 pr-0">
      <v-chip-group
        v-model="selection"
        active-class="success white--text"
        column
        class=" justify-space-between"
      >
        <v-chip
         v-for="(time, i) in time" :key="i" v-bind:disabled="time.able == false"
        class=" justify-space-between">
          {{ time.actualTime }}
         </v-chip>
      </v-chip-group>
    </v-card-text>

    <v-card-actions>
      <v-btn
        color="primary"
        outlined
        text
        @click="sheet = !sheet"
        block
      >
        예약하기
      </v-btn>
    </v-card-actions>
    <v-bottom-sheet v-model="sheet" class=" rounded-b-0"  v-if="onchange" 
>
    <Check 
    :warningsigns="warningsign"
        v-model="sheet"
        :value="sheet"
        @newsheet="onchange"/>
    </v-bottom-sheet>
  </v-card>
</template>

<script>
import Check from '../components/Check.vue'
  export default {
  components: { Check },
    data: () => ({
      confirm: false,
      selection: 1,
        sheet: false,
      warningsign:"아직 개발이 되지 않은 부분입니다. : ( ",
      time: [
        {
          actualTime: "10:30",
          able: true,
        },{
          actualTime: "11:00",
          able: true,
        },{
          actualTime: "11:30",
          able: false,
        },{
          actualTime: "13:30",
          able: true,
        },{
          actualTime: "14:00",
          able: false,
        },{
          actualTime: "14:30",
          able: true,
        },{
          actualTime: "15:00",
          able: true,
        },{
          actualTime: "15:30",
          able: false,
        },{
          actualTime: "16:00",
          able: true,
        },{
          actualTime: "16:30",
          able: false,
        },{
          actualTime: "17:30",
          able: false,
        },{
          actualTime: "18:30",
          able: true,
        },{
          actualTime: "19:30",
          able: false,
        },
      ]
    }),

    methods: {
      onchange( val ) {
        this.sheet = val
      }
    },
  }
</script>

<style scoped>
.v-chip-group--column .v-slide-group__content {
  justify-content: space-between !important;
}
</style>