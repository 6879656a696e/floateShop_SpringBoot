<template>
  <div class="login">
    <div id="loginWrap">

        <h2>다시 만나게 되어 반갑습니다. 👋🏼👋🏼</h2>
        <v-form action=""  ref="form">
            <div class="inputBoxWrap">
                <span class="formTitle">아이디</span>
                <v-text-field class="inputBox" v-model="id" label="아이디를 입력해주세요." hide-details="auto" :rules="[rules2.required]" ref="id"></v-text-field>
            </div>

            <div class="inputBoxWrap">
                <span class="formTitle">비밀번호</span>
                <v-text-field 
                v-model="pw" 
                ref="pw"
                class="inputBox input-group--focused" 
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.required]"
                :type="show ? 'text' : 'password'"
                name="input-10-2"
                label="비밀번호를 입력해주세요."
                @click:append="show = !show"></v-text-field>
            </div>

            <v-btn block outlined color="primary" 
            @click="checkForm">
                로그인
            </v-btn>
<v-list-item class="mt-10" >아직 가입을 안하셨다면</v-list-item>
            <router-link to="/signup"><v-btn block outlined  color="error" >
                회원가입
            </v-btn>
            </router-link>
        </v-form>
    </div>
  </div>
</template>

<script>
  export default {
      
    data () {
      return {
        show: false,
        id: null,
        pw: null,
        formHasErrors: false,
        errorMessages: '',
        rules: {
          required: value => !!value || '필수 입력 항목입니다.',
          // min: value => value.length >= 8 || '최소 8자리 이상 입력해주세요.',
        },
        rules2: {
        required: value => !!value || '필수 입력 항목입니다.',
        // min: value => (value && value.length >= 3) || '최소 세 글자 이상 입력해주세요.',
      },
      }
    },
     computed: {
      form () {
        return {
          id: this.id,
          pw: this.pw,
        }
      },
    },

       watch: {
      name () {
        this.errorMessages = ''
      },
    },
    methods: {
      checkForm() {
        const validate = this.$refs.form.validate();
        let that = this;
        if (validate) {
          this.$axios.post('api/login', {
            password: that.pw,
            email: that.id,
          })
              .then(res => {
                let token=res.data.token;
                localStorage.setItem("access_token", token);
                const data={
                  id: that.id,
                }
                this.$store.dispatch("LOGIN", data);
                // alert("로그인 성공");
                this.$router.push('/');
              })
              .catch(err => {
                console.log(err);
                alert("아이디나 비밀번호를 확인해주세요. : (");
              })
        }
      }
    }
  }
</script>


<style scoped>
    .login {
        max-width: 450px;
        margin: 50px auto;
    }
    #loginWrap {
        overflow: hidden;
        background: #fafafa;
        border-radius: 15px;
        height: auto;
        padding: 25px 25px 70px;
    }

    #loginWrap h2 {
        margin-top: 50px;
        text-align: center;
    }

    .formTitle {
        display: block;
        margin: 20px 0;
    }

    .inputBoxWrap {
        margin: 50px 0;
    }
</style>