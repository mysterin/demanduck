<template>
  <div>
  </div>
  <div>
    <el-row>
      <el-col :span="16" :offset="4">
        <el-table :data="memberList">
          <el-table-column prop="username" label="姓名"></el-table-column>
          <el-table-column prop="email" label="邮箱"></el-table-column>
          <el-table-column prop="role" label="角色"></el-table-column>
          <el-table-column label="操作" width="240">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleModifyMember(scope.row)">修改角色</el-button>
              <el-button type="danger" size="small" @click="handleDeleteMember(scope.row)">移除成员</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from "vue-router";
import { listCompanyRole, deleteCompanyRole } from "@/api/role";

const route = useRoute();
const companyId = ref(route.params.companyId);
const memberList = ref([]);
// const entity = ref({});

const init = () => {
  listCompanyRole({companyId: companyId.value}).then(res => {
    if (res) {
      memberList.value = res.list;
    }
  });
}
init();


const handleModifyMember = (row) => {
  console.log(row);
  // saveCompanyRole()
}
const handleDeleteMember = (row) => {
  deleteCompanyRole({roleId: row.roleId}).then(res => {
    if (res) {
      init();
    }
  });
}

</script>

<style scoped>

</style>