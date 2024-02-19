<script setup lang="ts">
import { ref, watch } from "vue";

import axios from "axios";
import router from "@/router";

const name = ref("")
const email = ref("")
const password1 = ref("")
const password2 = ref("")
const nickName = ref("")

const isPasswordMatch = ref(true)

watch([password1, password2], () => {
  isPasswordMatch.value = password1.value === password2.value;
})

const signup = function () {
  if(!isPasswordMatch.value) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  axios
      .post("/auth/signup", {
        name: name.value,
        email: email.value,
        password1: password1.value,
        password2: password2.value,
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
    <el-input class="input-field" type="password" v-model="password1" placeholder="비밀번호를 입력해주세요."/>
    <el-input class="input-field" type="password" v-model="password2" placeholder="비밀번호 확인을 해주세요."/>
    <p v-if="!isPasswordMatch">비밀번호가 일치하지 않습니다.</p>
    <el-input class="input-field" v-model="nickName" placeholder="닉네임을 입력해주세요."/>
  </div>

  <div class="signup-container">
    <el-button type="primary" @click="signup()">회원가입</el-button>
  </div>
</template>

<style scoped>
.input-field {
  margin-bottom: 15px;
  padding-right: 50%;
  text-align: center;
}
.signup-container {
  padding-left: 45%;
}
</style>