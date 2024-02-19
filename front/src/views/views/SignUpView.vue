<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import router from "@/router";

const name = ref("")
const email = ref("")
const password1 = ref("")
const password2 = ref("")
const nickName = ref("")

const signIn = function () {
  axios
      .post("/api/auth/signup", {
        name: name.value,
        email: email.value,
        password1: password.value,
        password2: rePassword.value,
        nickName: nickName.value
      })
      .then(() => {
        router.replace({name: "home"});
      });
};
</script>

<template>
  <div>
    <el-input class="input-field" v-model="name" placeholder="이름을 입력해주세요."/>
    <el-input class="input-field" v-model="email" placeholder="이메일을 입력해주세요."/>
    <el-input class="input-field" v-model="password1" placeholder="비밀번호를 입력해주세요."/>
    <el-input class="input-field" v-model="password2" placeholder="비밀번호를 입력해주세요."/>
    <el-input class="input-field" v-model="nickName" placeholder="비밀번호를 입력해주세요."/>
  </div>

  <div class="login-container">
    <el-button type="primary" @click="login()">회원가입</el-button>
  </div>
</template>

<style scoped>
.input-field {
  margin-bottom: 15px;
  padding-right: 50%;
  text-align: center;
}
.login-container {
  padding-left: 45%;
}
</style>