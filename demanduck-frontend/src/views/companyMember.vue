<template>

  <el-row>
    <el-col :span="4" :offset="4">
      <div class="add-button">
        <el-button type="primary" @click="handleAddMemberDialog()">添加成员</el-button>
      </div>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="16" :offset="4">
      <el-table :data="memberList">
        <el-table-column prop="username" label="姓名"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="role" label="角色">
          <template #default="scope">
            {{ roleMap(scope.row.role) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleModifyMemberDialog(scope.row)">修改角色</el-button>
            <el-button type="danger" size="small" @click="handleDeleteMember(scope.row)">移除成员</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <el-dialog :title="memberDialogStatus === 'ADD' ? '添加成员' : '修改角色'" v-model="memberDialogVisible"
             :close-on-click-modal="false" :close-on-press-escape="false">
    <el-row>
      <el-col :span="20" :offset="2">
        <el-form ref="memberForm" :model="entity" label-width="80px" :rules="rules" width="50%">
          <el-form-item label="姓名" prop="username" v-if="memberDialogStatus === 'UPDATE'">
            <el-input v-model="entity.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="entity.email" :disabled="memberDialogStatus === 'UPDATE'"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="entity.role" placeholder="请选择角色">
              <el-option label="管理员" value="COMPANY_ADMIN"></el-option>
              <el-option label="成员" value="COMPANY_MEMBER"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span class="dialog-footer">
      <el-button @click="memberDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleSaveMember">确 定</el-button>
    </span>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script setup>
import {ref} from 'vue';
import {useRoute} from "vue-router";
import {listCompanyRole, deleteCompanyRole, saveCompanyRole} from "@/api/role";

const route = useRoute();
const companyId = ref(route.params.companyId);
const memberList = ref([]);
const entity = ref({});
const memberDialogVisible = ref(false);
const memberDialogStatus = ref('');
const memberForm = ref(null);

const roleMap = (role) => {
  let roleMap = {
    'COMPANY_ADMIN': '管理员',
    'COMPANY_MEMBER': '普通成员'
  }
  return roleMap[role];
}

const rules = ref({
  username: [
    {required: true, message: '请输入姓名', trigger: 'blur'}
  ],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'}
  ],
  role: [
    {required: true, message: '请选择角色', trigger: 'blur'}
  ]
});
const init = () => {
  listCompanyRole({companyId: companyId.value}).then(res => {
    if (res) {
      memberList.value = res.list;
    }
  });
}
init();

const handleAddMemberDialog = () => {
  memberDialogVisible.value = true;
  memberDialogStatus.value = 'ADD';
  entity.value = {};
}

const handleModifyMemberDialog = (row) => {
  memberDialogVisible.value = true;
  memberDialogStatus.value = 'UPDATE';
  entity.value = row;
}
const handleSaveMember = () => {
  memberForm.value.validate((valid) => {
    if (valid) {
      saveCompanyRole({
        companyId: companyId.value,
        email: entity.value.email,
        role: entity.value.role
      }).then(res => {
        if (res) {
          memberDialogVisible.value = false;
          init();
        }
      });
    } else {
      return false;
    }
  });
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
.add-button {
  display: flex;
}
</style>