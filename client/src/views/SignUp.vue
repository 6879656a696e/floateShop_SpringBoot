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
              <span class="formTitle">이름</span>
              <v-text-field
                ref="name"
                v-model="name"
                :rules="[() => !!name || '성함은 필수입력 항목입니다.']"
                :error-messages="errorMessages"
                placeholder="홍길동"
                required
              ></v-text-field>
            </div>


          <div class="inputBoxWrap">
            <span class="formTitle">이메일</span>
            <v-text-field
                ref="email"
                v-model="email"
                :rules="[() => !!email || '이메일은 필수입력 항목입니다.', rules.emailCheck]"
                :error-messages="errorMessages"
                placeholder="abc@abc.com"
                required
            ></v-text-field>
            <v-btn @click="duplicateCheck">중복체크</v-btn>
          </div>


          <div class="inputBoxWrap">
            <span class="formTitle">비밀번호</span>
            <v-text-field
                v-model="password"
                name="password"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[() => !!password || '비밀번호는 필수입력 항목입니다.',rules.required, rules.min]"
                :type="show1 ? 'text' : 'password'"
                hint="비밀번호는 문자, 숫자, 특수문자 포함 최소 8자 이상입니다."
                counter
                @click:append="show1 = !show1"
            ></v-text-field>
          </div>


          <div class="inputBoxWrap">
            <span class="formTitle">비밀번호 확인</span>
            <v-text-field
                v-model="passwordCheck"
                name="passwordCheck"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.required, passwordMatch]"
                :type="show1 ? 'text' : 'password'"
                counter
                @click:append="show1 = !show1"
            ></v-text-field>
          </div>

          <div class="inputBoxWrap">
            <span class="formTitle">우편번호</span>
            <v-text-field
                ref="zip"
                v-model="zip"
                :rules="[() => !!zip || '우편번호는 필수입력 항목입니다.']"
                required
                placeholder="12345"
                @click="getPostcode"
            ></v-text-field>
            <v-btn @click="getPostcode">우편번호 찾기</v-btn>
          </div>

            <div class="inputBoxWrap">
              <span class="formTitle">주소</span>
          <v-text-field
            ref="address1"
            v-model="address1"
            :rules="[() => !!address1 || '정확한 시/군/구를 입력해주세요.', addressCheck]"
            required
          ></v-text-field>
            </div>

            <div class="inputBoxWrap">
              <span class="formTitle">상세주소</span>
          <v-text-field
            ref="address2"
            v-model="address2"
            :rules="[
              () => !!address2 || '배송시 받으실 주소는 필수 입력 항목입니다.',
              () => !!address2 && address2.length <= 5 || '최소 다섯 글자 이상 입력해주세요.',
              addressCheck
            ]"
            placeholder="OO빌딩 203호"
            counter="25"
            required
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
            회원가입 완료
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
      name: null,
      email: null,
      password: '',
      passwordCheck: '',
      address2: null,
      address1: null,
      zip: null,
      duplicheck: false,
      formHasErrors: false,
      rules: {
        required: value => !!value || '필수 항목입니다.',
        min: v => v.length >= 8 || 'Min 8 characters',
        emailCheck: v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '이메일 형식에 맞게 입력해주세요.',
      },
    }),

    computed: {
      passwordMatch() {
        return () => (this.password === this.passwordCheck) || '비밀번호가 일치하지 않습니다.'
      },
      form () {
        return {
          name: this.name,
          email: this.email,
          password: this.password,
          address1: this.address1,
          address2: this.address2,
          zip: this.zip,
        }
      },
    },

    watch: {
      name () {
        this.errorMessages = ''
      }
    },

    methods: {
      duplicateCheck(){
        let that=this;
        this.duplicheck=true;

        this.$axios.post('api/user/duplicateCheck', {
          email: that.email,
        })
            .then(res =>{
              alert(res.data);
            })
            .catch(err=>{
              console.log(err);
            })
      },
      resetForm () {
        this.errorMessages = []
        this.formHasErrors = false

        Object.keys(this.form).forEach(f => {
          this.$refs[f].reset()
        })
      },
      submit () {
        const validate = this.$refs.form.validate();
        let that=this;

        if(that.duplicheck==false){
          alert("아이디 중복체크를 해주세요.");
        }

        if (validate) {
          this.$axios.post('api/user/signup', {
            name: that.name,
            password: that.password,
            zipcode: that.zip,
            email: that.email,
            address1:that.address1,
            address2:that.address2,
          })
          .then(() =>{
            alert("회원가입 완료");
            this.$router.push('/login');
          })
          .catch(err=>{
            console.log(err);
          })
        }

      },
      getPostcode(){
        new window.daum.Postcode({
          oncomplete: (data) => {
            let roadAddr = data.roadAddress; // 도로명 주소 변수
            let extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
              extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
              extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
              extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            this.zip = data.zonecode;
            this. address1 = roadAddr+extraRoadAddr;
          }
        }).open();
      }
    },
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