<template>
  <el-row justify="center">
    <el-col :span="6" align="middle">
      <!-- element 表单登录-->
      <el-form ref="form" :model="form" label-width="80px" class="login-form" label-position="top">
        <h3>欢迎使用 demanduck 需求鸭</h3>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-button type="primary" @click="submit" class="login-button">登录</el-button>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
import {login} from '@/api/login';
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