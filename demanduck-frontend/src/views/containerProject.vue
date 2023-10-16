<template>
  <el-container>
    <el-header>
      <projectTop/>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script setup>
import projectTop from "@/components/projectTop.vue";

</script>

<style scoped>

</style>