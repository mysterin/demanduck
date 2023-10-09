<template>
  <el-row justify="center">
    <el-col :span="8" align="middle">
      <div class="login-form">
        <el-form :model="form" label-width="80px" label-position="top">
          <h3>欢迎使用</h3>
          <el-form-item prop="email">
            <el-input prefix-icon="User" v-model="form.email" placeholder="请填写邮箱" autocomplete="off"></el-input>
            <el-link :underline="false" type="info" href="/register">注册账号</el-link>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="Lock" type="password" v-model="form.password" placeholder="请填写密码"
                      autocomplete="off"></el-input>
            <el-link :underline="false" type="info" href="/resetPassword">忘记密码</el-link>
          </el-form-item>
          <el-button type="primary" @click="submit" class="login-button">登录</el-button>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import store from '@/store';
import {login} from '@/api/user';
import {useRouter, useRoute} from "vue-router";
import { ref } from 'vue';

const form = ref({
  email: '',
  password: ''
});

const router = useRouter();
const route = useRoute();
const redirect = route.query.redirect || '/company-list';
const submit = () => {
  login(form.value).then(res => {
    if (res) {
      store.commit('setUser', res.data);
      router.push({path: redirect});
    }
  });
}
</script>

<style scoped>
.login-form {
  margin-top: 200px;
  padding: 30px;
  border: 1px solid var(--el-border-color);
  border-radius: var(--el-border-radius-base);
  box-shadow: var(--el-box-shadow-light);
}

.login-button {
  width: 100%;
}
</style>