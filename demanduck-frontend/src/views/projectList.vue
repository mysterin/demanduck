<template>
  <el-row class="project-list">
    <el-col :span="4" v-for="project in projectList" :key="project.id">
      <el-card class="project-box"
               shadow="hover" @click="handleProjectClick(project)">
        <img :src="project.logo"/>
        <div>
          <span>{{ project.name }}</span>
        </div>
      </el-card>
    </el-col>
    <el-col :span="4">
      <el-card class="project-box" shadow="hover" @click="handleCreateDialog">
        <div class="create-button">
          <el-icon size="32">
            <Plus/>
          </el-icon>
        </div>
      </el-card>
    </el-col>
  </el-row>

  <el-dialog v-model="createProjectDialog" title="创建项目" width="30%">
    <el-form ref="createProjectForm" :model="createProjectFormData" :rules="rules">
      <el-form-item label="项目logo" prop="logo">
        <nibUpload :url="createProjectFormData.logo" prefix="project/logo/"></nibUpload>
      </el-form-item>
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="createProjectFormData.name"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="createProjectDialog = false" plain>取 消</el-button>
        <el-button type="primary" @click="handleCreateProject" palin>确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from 'vue';
import store from '@/store';
import {getProjectList, createProject} from "@/api/project";
import {ElMessage} from "element-plus";
import { useRouter } from "vue-router";
import nibUpload from "@/components/nibUpload";

const router = useRouter();
const companyId = ref(store.state.companyId);
const projectList = ref([]);
const createProjectDialog = ref(false);
const createProjectFormData = ref({
  logo: undefined,
  name: undefined
});
const createProjectForm = ref(null);
const rules = ref({
  logo: [
    {required: true, message: '请输入项目logo', trigger: 'blur'},
  ],
  name: [
    {required: true, message: '请输入项目名称', trigger: 'blur'},
    {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
  ]
});

const init = () => {
  getProjectList({companyId: companyId.value}).then(res => {
    projectList.value = res.list;
    store.commit('setProjectList', res.list);
  });
}

init();

const handleProjectClick = (project) => {
  store.commit('setProjectId', project.id);
  router.push({path: '/project/' + project.id})
}

const handleCreateDialog = () => {
  createProjectFormData.value = {};
  createProjectDialog.value = true;
}

const handleCreateProject = () => {
  createProjectForm.value.validate((valid) => {
        if (valid) {
          createProjectFormData.value.companyId = companyId.value;
          createProject(createProjectFormData.value).then(res => {
            if (res) {
              ElMessage({
                message: '创建成功',
                type: 'success'
              });
              init();
            }
            createProjectDialog.value = false;
          })
        } else {
          return false;
        }
      }
  )
  ;
}
</script>

<style scoped>

.project-box {
  height: 120px;
  width: 120px;
  cursor: pointer;
}

.project-box img {
  width: 100%;
}

.create-button {
  padding-top: 22px;
}
</style>