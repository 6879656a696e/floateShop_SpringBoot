<template>
  <v-row justify="center" class="mt-12 mb-12">
    <v-col
      cols="12"
      sm="10"
      md="8"
      lg="6"
    >
      <v-card id="signupForm" class="rounded-lg" elevation="0">
        <v-form ref="form" action="/post" method="post" enctype="multipart/form-data">
        <v-card-text>
            <div class="inputBoxWrap">
              <span class="formTitle">상품명</span>
              <v-text-field
                ref="pName"
                v-model="product.productName"
                :error-messages="errorMessages"
                placeholder="상품명"
                required
              ></v-text-field>
            </div>

          <div class="inputBoxWrap">
            <span class="formTitle">상품번호</span>
            <v-text-field
                ref="pNum"
                v-model="product.productNum"
            ></v-text-field>
          </div>

          <div class="inputBoxWrap">
            <span class="formTitle">카테고리</span>
            <v-select
                ref="pCate"
                v-model="product.productCategory"
                :items="items"
                label="카테고리 선택"
            ></v-select>
          </div>

          <div class="inputBoxWrap">
            <span class="formTitle">사진</span>
            <input
                type="file"
                name="file"
                @change="upload"
            >
          </div>


          <div class="inputBoxWrap">
            <span class="formTitle">금액</span>
            <v-text-field
                v-model="product.productPrice"
                name="pPrice"
            ></v-text-field>
          </div>

            <div class="inputBoxWrap">
              <span class="formTitle">상세설명</span>
          <v-text-field
            ref="pDesc"
            v-model="product.productDesc"
            required
          ></v-text-field>
            </div>


          <div class="inputBoxWrap">
            <span class="formTitle">판매가능 수량</span>
            <v-text-field
                v-model="product.productCnt"
                name="pCnt"
            ></v-text-field>
          </div>

        </v-card-text>
        </v-form>

        <v-card-actions  class="mt-12">
          <v-btn text outlined color="error">
            취소
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
          outlined
            color="success"
            text
          type="button"
            @click="submit"
          >
            상품 등록
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    data: () => ({
      errorMessages: '',
      items: ['Diffuser', 'Perfume'],
      image:null,
      pName: null,
      pCate: null,
      pNum: null,
      pPic: '',
      pPrice: 0,
      pCnt: 0,
      pDesc: null,
      formHasErrors: false,
      product: {
        productKey: null,
        productName: '',
        productPic: '',
        productNum:'',
        productPrice: 0,
        productDesc: '',
        productCnt: null,
        itemtotalprice: 0,
        productCategory: "",
      },
    }),

    computed: {
      form () {
        return {
          pName: this.productName,
          pCate: this.productCategory,
          pNum: this.productNum,
          pPic: this.productPic,
          pPrice: this.productPrice,
          pDesc: this.productDesc,
          pCnt: this.productCnt,
        }
      },
    },

    watch: {
      name () {
        this.errorMessages = ''
      }
    },
    mounted() {
      let that=this;
      this.$axios.get('http://localhost:8080/api/productDetail', {params: {id: this.$route.params.productId}})
          .then((res) => {
            console.log(res);
            that.product=res.data[0];
          })
          .catch(err => {
            console.log(err);
          })
    },
    methods: {
      upload(e){
        this.image = e.target.files[0];
      },
      submit() {
        const validate = this.$refs.form.validate();
        let that = this;
        let formData=new FormData();
        formData.append("productKey", this.$route.params.productId);
        formData.append("productName", that.pName);
        formData.append("productCategory", that.pCate);
        formData.append("productNum", that.pNum);
        formData.append("file", that.image);
        formData.append("productPrice", that.pPrice);
        formData.append("productDesc", that.pDesc);
        formData.append("productCnt", that.pCnt);

        if (validate) {
          this.$axios({
            method: "post",
            url: 'http://localhost:8080/api/admin/modiProduct',
            data: formData,
            headers: { "Content-Type": "multipart/form-data" }
          })
              .then((res) => {
                console.log(res);
                alert("상품 수정 완료");
               this.$router.push('/products');
              })
              .catch(err => {
                console.log(err);
              })
        }
      },
    }
  }
</script>
<style scoped>
formTitle {
        display: block;
        margin: 20px 0;
    }

    .inputBoxWrap {
        margin: 50px 0;
    }
</style>