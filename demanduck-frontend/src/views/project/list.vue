<template>
  <el-row class="project-list">
    <el-col :span="12">
      <el-card class="project-box" v-for="project in projectList" :key="project.id" shadow="hover" @click="handleProjectClick(project)">
        <img :src="project.logo"/>
        <div>
          <span>{{ project.name }}</span>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import store from '@/store';
export default {
  name: "companyView",
  data() {
    return {
      companyId: store.state.companyId,
      projectList: store.state.projectList
    }
  },
  methods: {
    handleProjectClick(project) {
      this.$router.push({path: '/project/' + project.id})
    }
  }
}
</script>

<style scoped>

.project-box {
  height: 120px;
  width: 120px;
}

.project-box img {
  width: 100%;
}
</style>