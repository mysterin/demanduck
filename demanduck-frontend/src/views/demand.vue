<template>
  <el-row>
    <el-col class="create-demand">
      <el-button type="primary" @click="handleCreateDemand" plain>创建需求</el-button>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="24">
      <el-table :data="demandList">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="priority" label="优先级"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop="assignUser" label="处理人"></el-table-column>
        <el-table-column prop="startTime" label="预计开始"></el-table-column>
        <el-table-column prop="endTime" label="预计结束"></el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <div class="page-pagination">
    <div>
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script setup>
console.log('demand');
import { ref } from 'vue';
import store from '@/store';
import {listDemand} from "@/api/demand";
import { useRouter } from "vue-router";

const router = useRouter();
const projectId = store.state.projectId;
const demandList = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const initDemandList = () => {
  listDemand({
    projectId: projectId,
    pageNo: currentPage.value,
    pageSize: pageSize.value
  }).then(res => {
    demandList.value = res.list;
    total.value = res.total;
  })
}
initDemandList();

const handleCreateDemand = () => {
  router.push({
    path: '/project/' + projectId +'/demand-edit',
  });
}

const handleCurrentChange = (val) => {
  currentPage.value = val;
  initDemandList();
}

</script>

<style scoped>
.create-demand {
  text-align: left;
  margin-bottom: 20px;
}

.page-pagination {
  margin-top: 20px;
}
</style>