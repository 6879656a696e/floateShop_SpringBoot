<template>
  <v-footer
    dark
    padless
  >
    <v-card
      flat
      tile
      class=" lighten-1 white--text text-center"
      width="100%"
    >
      <v-card-text>
        <v-btn
          v-for="icon in icons"
          :key="icon"
          class="mx-4 white--text"
          icon
        >
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
      </v-card-text>

        <p>
          이 사이트는 운영중인 사이트가 아닌 뷰 연습용 예제 사이트입니다.<br>
          모든 디자인은 6879656a696e에게 귀속됩니다. <br>This site is made by Vue.js and is an example site. 
        </p>
        <h5 class="mb-5">Copyright ©6879656a696e</h5>


      <v-divider></v-divider>

      <h5 class="white--text pt-5 pb-5">
        {{ new Date().getFullYear() }} — <strong>Vue & Vuetify & 6879656a696e</strong>
      </h5>
    </v-card>
  </v-footer>
</template>

<script>
  export default {
    data: () => ({
      icons: [
        'mdi-facebook',
        'mdi-twitter',
        'mdi-message',
        'mdi-instagram',
      ],
    }),
  }
</script>