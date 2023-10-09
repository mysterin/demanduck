<template>
  <el-menu class="nav-menu" router :collapse="isCollapsed">
    <el-menu-item :index="'/' + companyId + '/workspace'" v-if="companyId" title="工作台">
      <el-icon>
        <Monitor/>
      </el-icon>
      <span>工作台</span>
    </el-menu-item>
    <el-sub-menu index="project" v-if="companyId" title="项目">
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
        <el-divider border-style="dashed"/>
        <el-menu-item index="/user/logout">
          <span>退出登录</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import store from '@/store';
import { ref, computed } from "vue";
import { getProjectList } from "@/api/project";

const isCollapsed = ref(true);
const companyId = computed(() => store.state.companyId);
const projectList = computed(() => store.state.projectList);

const init = function () {
  if (companyId.value && projectList.value.length <= 0) {
    getProjectList({companyId: companyId.value}).then(res => {
      store.commit('setProjectList', res.list);
    });
  }
}
init();
</script>

<style scoped>
.nav-menu {
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
}
</style>