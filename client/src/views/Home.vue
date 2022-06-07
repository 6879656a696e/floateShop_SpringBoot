<template>
<div style="min-height: 100vh">
  <v-carousel
    cycle
    min-height="500px"
    height="auto"
    background-size="cover"
    hide-delimiter-background
    show-arrows-on-hover
    class="mb-12"
  >
    <v-carousel-item
      v-for="(item,i) in items"
      :key="i"
      :src="item.src"
    >
    </v-carousel-item>
  </v-carousel>

  <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Dolor et veniam alias temporibus saepe excepturi non eum, asperiores reprehenderit incidunt corporis dolorem odit suscipit fuga corrupti ad similique qui harum?</p>

    <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Dolor et veniam alias temporibus saepe excepturi non eum, asperiores reprehenderit incidunt corporis dolorem odit suscipit fuga corrupti ad similique qui harum?</p>

 <h3 class="logo mt-12">Collection</h3>
  <v-row color="secondary">
    <v-col
      v-for="(product, i) in product" 
      :key="i"
      class="d-flex child-flex"
      cols="4"
    >
        <router-link 
        v-bind:to="{ path: `/productdetail/${ product.productCategory }/${ product.productKey }`, params: { index:`$( product.productKey )` },}">
      <v-img
        :src="'/upload/'+product.productPic"
        class="grey lighten-2"
      >
        <template v-slot:placeholder>
          <v-row
            class="fill-height ma-0"
            align="center"
            justify="center"
          >
            <v-progress-circular
              indeterminate
              color="grey lighten-5"
            ></v-progress-circular>
          </v-row>
        </template>
      </v-img>
    </router-link>
    </v-col>
  </v-row>

    <p class="mt-12">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Dolor et veniam alias temporibus saepe excepturi non eum, asperiores reprehenderit incidunt corporis dolorem odit suscipit fuga corrupti ad similique qui harum?</p>
</div>
</template>

<script>
  export default {
    data () {
      return {
        product: {
          productKey: null,
          productName: '',
          productPic: '',
          productPrice: null,
          productDesc: '',
          productCnt: null,
          itemtotalprice: 0,
          productCategory: "",
        },
        items: [
          {
            src: require('@/assets/imgs/slide1.jpg'),
          },
          {
            src: require('@/assets/imgs/slide2.jpg'),
          },
          {
            src: require('@/assets/imgs/slide3.jpg'),
          },
          {
            src: require('@/assets/imgs/slide4.jpg'),
          },
          {
            src: require('@/assets/imgs/slide5.jpg'),
          },
        ],
      }
    },
    created() {
      let that= this;
      this.$axios.get('api/productList')
          .then((res) => {
            console.log(res);
            that.product=res.data;
          })
          .catch(err => {
            console.log(err);
          });
    }
  }
</script>