<template>
  <el-container>
    <el-aside width="70px">
      <leftNav/>
    </el-aside>
    <el-main>
      <router-view :key="route.fullPath"></router-view>
    </el-main>
  </el-container>

</template>

<script setup>
import leftNav from '@/components/leftNav';
import {useRoute} from "vue-router";

const route = useRoute();
</script>

<style scoped>

</style>