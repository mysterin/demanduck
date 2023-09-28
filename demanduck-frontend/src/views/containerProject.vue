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

<script>
import projectTop from "@/components/projectTop.vue";

export default {
  name: "projectContainer",
  components: {
    'projectTop': projectTop
  }
}
</script>

<style scoped>

</style>