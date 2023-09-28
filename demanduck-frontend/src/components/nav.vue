<template>
  <el-menu class="nav-menu" router :collapse="isCollapsed" title="工作台">
    <el-menu-item :index="'/' + companyId + '/workspace'">
      <el-icon>
        <Monitor/>
      </el-icon>
      <span>工作台</span>
    </el-menu-item>
    <el-sub-menu index="project">
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
    <el-sub-menu index="setting">
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
import store from '@/store';

export default {
  name: "navView",
  data() {
    return {
      isCollapsed: true,
    };
  },
  computed: {
    companyId() {
      return store.state.companyId;
    },
    projectList() {
      return store.state.projectList;
    }
  },
}
</script>

<style scoped>
</style>