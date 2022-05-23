<template>
  <v-row justify="center" class="mt-12 mb-12">
    <v-col
      cols="12"
      sm="10"
      md="8"
      lg="6"
    >
      <v-card id="signupForm" class="rounded-lg" elevation="0">
        <v-form ref="form" >
        <v-card-text>
            <div class="inputBoxWrap">
              <span class="formTitle">상품명</span>
              <v-text-field
                ref="pName"
                v-model="pName"
                :rules="[() => !!pName || '상품명은 필수입력 항목입니다.']"
                :error-messages="errorMessages"
                placeholder="상품명"
                required
              ></v-text-field>
            </div>

          <div class="inputBoxWrap">
            <span class="formTitle">상품번호</span>
            <v-text-field
                ref="pNum"
                v-model="pNum"
            ></v-text-field>
          </div>

          <div class="inputBoxWrap">
            <span class="formTitle">카테고리</span>
            <v-select
                ref="pCate"
                v-model="pCate"
                :items="items"
                label="카테고리 선택"
            ></v-select>
          </div>

          <div class="inputBoxWrap">
            <span class="formTitle">사진</span>
            <input
                type="file"
                @change="upload"
                name="pPic"
            >
          </div>


          <div class="inputBoxWrap">
            <span class="formTitle">금액</span>
            <v-text-field
                v-model="pPrice"
                name="pPrice"
            ></v-text-field>
          </div>

            <div class="inputBoxWrap">
              <span class="formTitle">상세설명</span>
          <v-text-field
            ref="pDesc"
            v-model="pDesc"
            required
          ></v-text-field>
            </div>


          <div class="inputBoxWrap">
            <span class="formTitle">판매가능 수량</span>
            <v-text-field
                v-model="pCnt"
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
          <v-slide-x-reverse-transition>
            <v-tooltip
              v-if="formHasErrors"
              left
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  class="my-0"
                  v-bind="attrs"
                  @click="resetForm"
                  v-on="on"
                >
                  <v-icon>mdi-refresh</v-icon>
                </v-btn>
              </template>
              <span>Refresh form</span>
            </v-tooltip>
          </v-slide-x-reverse-transition>
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
      pName: null,
      pCate: null,
      pNum: null,
      pPic: '',
      pPrice: '',
      pCnt: 0,
      pDesc: null,
      formHasErrors: false,
      rules: {
        required: value => !!value || '필수 항목입니다.',
        min: v => v.length >= 8 || 'Min 8 characters',
      },
    }),

    computed: {
      form () {
        return {
          pName: this.pName,
          pCate: this.pCate,
          pNum: this.pNum,
          pPic: this.pPic,
          pPrice: this.pPrice,
          pDesc: this.pDesc,
          pCnt: 0,
        }
      },
    },

    watch: {
      name () {
        this.errorMessages = ''
      }
    },

    methods: {
      upload(e){
        let image = e.target.files[0];
        this.pPic = image.name;
      },
      resetForm() {
        this.errorMessages = []
        this.formHasErrors = false

        Object.keys(this.form).forEach(f => {
          this.$refs[f].reset()
        })
      },
      submit() {
        const validate = this.$refs.form.validate();
        let that = this;

        if (validate) {
          this.$axios.post('api/admin/addProduct', {
            product_name: that.pName,
            product_category: that.pCate,
            product_num: that.pNum,
            product_pic: that.pPic,
            product_price: that.pPrice,
            product_desc: that.pDesc,
            product_cnt: that.pCnt,
          })
              .then((res) => {
                console.log(res);
                //alert("상품 등록 완료");
               // this.$router.push('/product');
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