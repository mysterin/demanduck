<template>
  <el-alert title="正在登出中……" type="info" :closable="false" />
</template>

<script setup>
import { logout } from "@/api/user";
import { useRouter } from "vue-router";
import store from "@/store";
import { ElMessage } from "element-plus";

const router = useRouter();

logout().then(res => {
  if (res) {
    store.commit('setUser', '');
    router.push({path: '/user/login'});
  } else {
    ElMessage({
      message: '退出失败',
      type: 'error'
    });
  }
})
</script>

<style scoped>

</style>