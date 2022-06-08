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
        <span class="menuName">{{ $route.name }}</span>
      <v-spacer></v-spacer>
      <div>
        <span v-if="$store.state.isLogin">{{ $store.state.username }} 님, 반갑습니다.</span>
        <router-link to="/cart" v-if="$store.state.role==='ROLE_USER'">
         <v-icon size="26px">
              mdi-cart
            </v-icon>
            <span class="cartQuan">{{ cartItemTotal }}</span>
          </router-link>
          </div>
      </template>
    </v-app-bar>
    <div class="scroller">
      <v-container fluid color="secondary">
        <router-view @getCartTotal="getCartTotal" />
      </v-container>
        <Footer />
    </div>

     <template>
       <v-row justify="center">
         <v-dialog
             v-model="dialog"
             width="600px"
             height="70vh"
         >
           <template v-slot:activator="{ on, attrs }">

             <v-btn
                 class="mx-2"
                 fab
                 dark
                 large
                 color="pink"
                 style="position: fixed; bottom: 2rem; right: 2rem;"
                 @click="dialog = false"
                 v-bind="attrs"
                 v-on="on"
             >
               <v-icon dark>
                 mdi-chat
               </v-icon>
             </v-btn>
           </template>
           <v-card>
             <v-card-title>
               <span class="text-h5">무엇을 도와드릴까요?</span>
             </v-card-title>
             <v-col
                 cols="12"
                 sm="12"
                 md="12"
             >
             <v-chip
                 class="my-3 mx-5"
             >
               ??????
             </v-chip>
             </v-col>
             <v-col
                 cols="12"
                 sm="12"
                 md="12"
                 style="text-align: right"
             >
             <v-chip
                 class="my-3  my-5"
                 color="primary"
             >
               *******
             </v-chip>
             </v-col>
               <v-form>
                 <v-container>
                   <v-row>
                     <v-col cols="12">
                       <v-text-field
                           v-model="message"
                           append-outer-icon="mdi-send"
                           filled
                           rounded
                           clear-icon="mdi-close-circle"
                           clearable
                           label="Message"
                           type="text"
                       ></v-text-field>
                     </v-col>
                   </v-row>
                 </v-container>
               </v-form>

             <v-card-actions>
               <v-spacer></v-spacer>
               <v-btn
                   color="green darken-1"
                   text
                   @click="dialog = false"
               >
                 상담완료
               </v-btn>
             </v-card-actions>
           </v-card>
         </v-dialog>
       </v-row>
     </template>



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
      total: null,
      message: "",
      dialog: false,
      cartItemTotal: 0,
      icons: [
        'mdi-account',
        'mdi-cart',
        'mdi-menu',
      ],
    }),
  created() {
    this.setCartTotal();
  },
  watch:{
    group () {
      this.drawer = false
    },
    cartItemTotal(){
      this.sendCartTotal()
    }
  },
  methods: {
    getCartTotal(val){
      let that=this;
      that.cartItemTotal=val;
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
    },
};
</script>

<style scoped>
@import './assets/main.css';
@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;1,100;1,200;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Qwitcher+Grypen:wght@400;700&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');

.menuName {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

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
