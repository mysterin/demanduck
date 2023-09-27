<template>
  <el-menu mode="horizontal" router="true">
    <el-menu-item :index="'/project/' + projectId + '/demand'">
      <span>需求</span>
    </el-menu-item>
    <el-menu-item :index="'/project/' + projectId + '/mission'">
      <span>任务</span>
    </el-menu-item>
    <el-menu-item :index="'/project/' + projectId + '/flaw'">
      <span>缺陷</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import store from '@/store';
export default {
  name: "projectTop",
  data() {
    return {
      companyId: store.state.companyId,
      projectId: store.state.projectId
    }
  }
}
</script>

<style scoped>

</style>