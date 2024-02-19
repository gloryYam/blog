<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";
import router from "@/router";

const email = ref("")
const password = ref("")

const login = function () {
  axios
      .post("/auth/login", {
        email: email.value,
        password: password.value,
      })
      .then(() => {
        router.replace({name: "home"});
      });
};
</script>

<template>
  <div>
    <el-input class="input-field" v-model="email" placeholder="아이디를 입력해주세요."/>
    <el-input class="input-field" v-model="password" placeholder="비밀번호를 입력해주세요."/>
  </div>

  <div class="login-container">
    <el-button type="primary" @click="login()">로그인</el-button>
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