<template>
  <v-list        
  nav
  dense>
  <v-list-item-group
  v-model="group"
  active-class="white--text success">
  
    <v-list-item-title class="logo mb-2"><router-link to="/">floated Shop</router-link></v-list-item-title>
    
    <v-list-item
    v-for="menu in menu" 
    :key="menu.title" 
    class="justify-center"
    @click="movePage( menu.target );">
      <span class="myfont">{{ menu.title }}</span>
    </v-list-item>
        
        <v-flex class="mt-5">
          <router-link to="/login"  v-if="$store.state.isLogin===false">
            <v-btn justify="between" color="transparent" elevation="0"  >
              <v-icon class="mr-2">mdi-account</v-icon>
              <span class="myfont">로그인</span>
            </v-btn>
          </router-link>
          <div v-else>
            <v-btn  justify="between" @click="movePage( menu2.target )" v-for="menu2 in menu2" :key="menu2.title" color="transparent" elevation="0"  >
                    <v-icon class="mr-2">{{ menu2.icon }}</v-icon>
                    <span class="myfont">{{ menu2.title }} </span>
            </v-btn>
            <v-btn color="transparent" elevation="0" @click="logout">
              <v-icon class="mr-2">mdi-logout</v-icon>
              <span class="myfont">로그아웃</span>
            </v-btn>
          </div>
        </v-flex>
        </v-list-item-group>
      </v-list>
      </template>

<script>
  export default {
    data: () => ({
            group: null,
            menu: [ {
              title: '커스텀 상담 예약',
              target: 'Reservation'
            }, {
              title: '디퓨저 & 향수',
              target: 'Products'
            },{
              title: 'QnA',
              target: 'Board'
            }
            ],
            menu2: [ 
                {
                    icon: 'mdi-account',
                    title: '마이페이지',
                    target: 'Mypage'
                }, {
                    icon: 'mdi-cart',
                    title: '장바구니',
                    target: 'Cart'
                }, {
                    icon: 'mdi-message-text',
                    title: '주문내역',
                    target: 'OrderDetail'
                }
            ],
        }),
        methods: {
          movePage(target){
            this.$router.push({ name: target }).catch( ()=>{});
          },
          logout(){
            this.$store.commit("logout");
            this.$router.push("/");
          },
        }
      }
      </script>
      