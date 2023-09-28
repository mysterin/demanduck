<template>
  <el-row justify="center">
    <el-col :span="6" align="middle">
      <!-- element 表单登录-->
      <el-form ref="form" :model="form" label-width="80px" class="login-form" label-position="top">
        <h3>欢迎使用</h3>
        <el-form-item prop="email">
          <el-input prefix-icon="User" v-model="form.email" placeholder="请填写邮箱" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="Lock" type="password" v-model="form.password" placeholder="请填写密码"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-button type="primary" @click="submit" class="login-button">登录</el-button>
      </el-form>
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
}

.login-button {
  width: 100%;
}
</style>