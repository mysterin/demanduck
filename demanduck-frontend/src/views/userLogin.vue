<template>
  <el-row justify="center">
    <el-col :span="8" align="middle">
      <!-- element 表单登录-->
      <div class="login-form">
        <el-form ref="form" :model="form" label-width="80px" label-position="top">
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

<script>
import store from '@/store';
import {login} from '@/api/user';

export default {
  name: 'loginView',
  data() {
    return {
      form: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    submit() {
      login(this.form).then(res => {
        if (res) {
          store.commit('setUser', res.data)
          this.$router.push({path: '/company-list'})
        }
      });
    }
  }
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