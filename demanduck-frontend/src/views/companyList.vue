<template>
  <el-row class="company-list">
    <el-col :span="12">
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
      this.companyList = res.list;
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