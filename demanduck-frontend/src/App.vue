<template>
  <el-row>
    <el-col :span="4">
      <nib-nav>
      </nib-nav>
    </el-col>
    <el-col :span="20">
      <router-view/>
    </el-col>
  </el-row>
</template>

<script>
import nav from './components/nav'

export default {
  name: 'app',
  components: {
    'nibNav': nav
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
