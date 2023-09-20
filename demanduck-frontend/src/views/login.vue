<template>
  <el-row justify="center">
    <el-col :span="6" align="middle">
      <!-- element 表单登陆-->
      <el-form ref="form" :model="form" label-width="80px" class="login-form">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登陆</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
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
    login() {
      this.$router.push({path: '/dashboard'})
    }
  }
}
</script>

<style scoped>
.login-form {
  margin-top: 200px;
}
</style>