<template>
  <el-row justify="center">
    <el-col :span="8" >
      <div class="register-form">
        <el-form :model="user" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="user.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSendValidCode" plain>获取验证码</el-button>
          </el-form-item>
          <el-form-item label="验证码">
            <el-input v-model="user.code"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRegister" plain>注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {sendValidCode, register} from "@/api/user";
import {ElMessage, ElMessageBox} from 'element-plus';

const user = ref({scene: 'REGISTER'});
const router = useRouter();

const handleSendValidCode = () => {
  sendValidCode(user.value).then(res => {
    if (res) {
      ElMessage.success('验证码已发送');
    }
  }).catch(err => {
    console.log(err);
    ElMessage.error('验证码发送失败');
  })
}

const handleRegister = () => {
  register(user.value).then(res => {
    if (res) {
      ElMessageBox.alert('注册成功，请登录', '提示', {
        confirmButtonText: '确定',
        callback: () => {
          router.push('/user/login');
        }
      });
    }
  }).catch(err => {
    console.log(err);
    ElMessage.error('注册失败');
  })
}
</script>

<style scoped>
.register-form {
  margin-top: 200px;
  padding: 30px;
  border: 1px solid var(--el-border-color);
  border-radius: var(--el-border-radius-base);
  box-shadow: var(--el-box-shadow-light);
}
</style>