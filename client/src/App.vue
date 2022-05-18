<template>
   <v-app color="secondary">
    <v-app-bar app hide-on-scroll scroll-target=".scroller" id="header">
      <v-spacer></v-spacer>
      <v-toolbar-title class="logo">
      <router-link to="/">floated Shop</router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <template #extension>
      <v-app-bar-nav-icon 
          @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-spacer></v-spacer>
        <span white--text>{{ $route.name }}</span>
      <v-spacer></v-spacer>
      <div>
        <router-link to="/cart">
         <v-icon size="26px">
              mdi-cart
            </v-icon>
            <span class="cartQuan">{{ $store.getters.cartTotalItem }}</span>
          </router-link>
          </div>
      </template>
    </v-app-bar>
    <div class="scroller">
      <v-container fluid color="secondary">
        <router-view />
      </v-container>
        <Footer />
    </div>

   <v-navigation-drawer
      v-model="drawer"
      absolute
      bottom
      temporary
      width="100%"
      class="myNav"
      secondary
    >
      <Menu />
    </v-navigation-drawer>
  </v-app>
</template>

<script>
import Footer from './components/Footer.vue'
import Menu from './components/Menu.vue'

export default {
  name: "App",
  components: {
    'Footer' : Footer,
    'Menu' : Menu,
  },
  data: () => ({
      drawer: false,
      group: null,
      icons: [
        'mdi-account',
        'mdi-cart',
        'mdi-menu',
      ],
    }),

    watch: {
      group () {
        this.drawer = false
      },
    },
};
</script>

<style scoped>
@import './assets/main.css';
@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Qwitcher+Grypen:wght@400;700&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');

#header {
  background: transparent;
  backdrop-filter: blur(5px);
  box-shadow: none;
  border-bottom: 1px solid #ddd;
}

.scroller {
  background: #eff0ea;
  overflow: auto;
  position: absolute;
  padding-top: 112px;
  height: 100%;
  width: 100%;
}

.v-navigation-drawer__content,
.myNav {
  border-radius: 25px 25px 0 0;
  text-align: center;
  width: 100%;
  height: 40% !important;
  bottom: 0;
  top: auto !important;
}
.cartQuan{
  font-size: .6rem;
  text-align: center;
}
</style>
