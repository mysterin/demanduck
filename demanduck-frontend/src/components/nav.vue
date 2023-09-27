<template>
  <el-menu class="nav-menu" router :collapse="isCollapsed" title="工作台">
    <el-menu-item :index="'/workspace/' + companyId">
      <el-icon>
        <Monitor/>
      </el-icon>
      <span>工作台</span>
    </el-menu-item>
    <el-sub-menu index="project" title="项目">
      <template #title>
        <el-icon>
          <Menu/>
        </el-icon>
        <span>项目</span>
      </template>
      <el-menu-item-group>
        <el-menu-item v-for="project in projectList" :key="project.id" :index="'/project/' + project.id">
          <span>{{ project.name }}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
    <el-sub-menu index="setting" title="设置">
      <template #title>
        <el-icon>
          <Setting/>
        </el-icon>
        <span>设置</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/company-list">
          <span>切换公司</span>
        </el-menu-item>
        <el-menu-item index="/user/detail">
          <span>个人信息</span>
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
      isCollapsed: true,
      companyId: store.state.companyId,
      projectList: []
    };
  },
  mounted() {
    if (this.companyId) {
      getProjectList({companyId: this.companyId}).then(res => {
        this.projectList = res.list;
        store.commit('setProjectList', res.list);
      });
    }
  }
}
</script>

<style scoped>
</style>