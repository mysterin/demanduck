<template>
  <el-container>
    <el-aside width="70px">
      <leftNav/>
    </el-aside>
    <el-main><router-view></router-view></el-main>
  </el-container>

</template>

<script>
import nav from '@/components/nav'

export default {
  name: "projectView",
  components: {
    'leftNav': nav,
  }
}
</script>

<style scoped>

</style>