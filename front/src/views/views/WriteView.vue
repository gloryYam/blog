<script setup lang="ts">
import {ref} from "vue"

import axios from "axios";
import {useRouter} from "vue-router"

const title = ref("")     // 동적 변수
const content = ref("")   // 동적 변수

const router = useRouter();

const write = function () {
  axios
      .post("/api/posts", {
        title: title.value,
        content: content.value
      })
      .then(() => {
        router.replace({name: "home"});
      });
};
</script>

<template>
  <!--글 작성 api -->
  <div>
    <el-input v-model="title" placeholder="제목을 입력하세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write()">작성완료</el-button>
    </div>
  </div>
</template>

<style>

</style>