<template>
  <el-row>
    <el-col :span="8" :offset="8">
      <el-form :model="user" label-width="80px">
        <el-form-item label="用户 ID">
          <el-input v-model="user.userId"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="user.email"></el-input>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>

</template>

<script setup>
import {ref} from 'vue';
import store from "@/store";

const user = ref({
  userId: store.state.userId,
  username: store.state.username,
  email: store.state.email,
  mobile: store.state.mobile,
});
</script>

<style scoped>

</style>