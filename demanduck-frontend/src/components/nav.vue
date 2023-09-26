<template>
  <el-menu v-if="!$route.meta.hideNav" class="nav-menu" collapse="false">
    <el-menu-item index="1">
      <router-link :to="'/workspace/' + companyId">
        <el-icon>
          <Monitor/>
        </el-icon>
        <span>工作台</span>
      </router-link>
    </el-menu-item>
    <el-sub-menu index="2">
      <template #title>
        <el-icon>
          <Menu/>
        </el-icon>
        <span>项目</span>
      </template>
      <el-menu-item-group>
        <el-menu-item v-for="project in projectList" :key="project.id">
          <router-link :to="'/project/' + project.id">
            <span>{{ project.name }}</span>
          </router-link>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
  </el-menu>
</template>

<script>
import {getProjectList} from "@/api/project";
import store from '@/store';

export default {
  name: "navView",
  data() {
    return {
      companyId: store.state.companyId,
      projectList: []
    };
  },
  mounted() {
    getProjectList({companyId: this.companyId}).then(res => {
      this.projectList = res.list;
      store.commit('setProjectList', res.list);
    });
  }
}
</script>

<style scoped>
.nav-menu {
  width: 100px;
}
</style>