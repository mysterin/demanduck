<template>
  <el-row class="company-list">
    <el-col :span="12" :offset="6" v-if="companyList.length <= 0">
      <el-empty description="暂无公司"/>
    </el-col>
    <el-col :span="12" v-if="companyList">
      <el-card class="company-box" v-for="company in companyList" :key="company.id"
               shadow="hover" @click="handleCompanyClick(company)">
        <img :src="company.logo"/>
        <div>
          <span>{{ company.name }}</span>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import {getCompanyList} from '@/api/company';

export default {
  name: "companyList",
  data() {
    return {
      companyList: []
    }
  },
  mounted() {
    getCompanyList().then(res => {
      if (res) {
        this.companyList = res.list;
      }
    });
  },
  methods: {
    handleCompanyClick(company) {
      this.$router.push({path: '/' + company.id + "/project-list"})
    }
  }
}
</script>

<style scoped>
.company-box {
  height: 150px;
  width: 150px;
  cursor: pointer;
}

.company-box img {
  width: 100%;
}
</style>