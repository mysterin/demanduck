<template>
  <el-container>
    <el-aside width="70px" v-if="!$route.meta.hideNav">
      <leftNav/>
    </el-aside>
    <el-container>
      <el-header>
        <projectTop/>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import nav from '@/components/nav'
import projectTop from "@/components/projectTop.vue";

export default {
  name: "projectView",
  components: {
    'leftNav': nav,
    'projectTop': projectTop
  }
}
</script>

<style scoped>

</style>