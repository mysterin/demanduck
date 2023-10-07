<template>
  <el-menu ref="projectTop" mode="horizontal" router :default-active="activeIndex">
    <div class="project-name" :title="project.name">
      <el-text size="large" tag="b" truncated>
        {{ project.name }}
      </el-text>
    </div>
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

<script setup>
import store from '@/store';
import { useRoute } from "vue-router";
import { getProject } from "@/api/project";
import { ref } from "vue";
import { onBeforeRouteUpdate } from "vue-router";

const route = useRoute();

// const companyId = ref(store.state.companyId);
const projectId = ref(store.state.projectId);
const activeIndex = ref(route.path);
const project = ref({});

const init = function() {
  if (projectId.value) {
    getProject({projectId: projectId.value}).then(res => {
      if (res) {
        project.value = res.data;
      }
    });
  }
}

init();

onBeforeRouteUpdate(to => {
  projectId.value = to.params.projectId;
  activeIndex.value = to.path;
  init();
});

</script>

<style scoped>
.project-name {
  display: flex;
  width: 250px;
  height: 50px;
  align-content: center;
}
</style>