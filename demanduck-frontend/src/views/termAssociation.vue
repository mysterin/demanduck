<template>
  <el-row>
    <el-col :span="8" :offset="2" style="margin-top: 30px">
      <nib-upload v-model:url="fileUrl" :file-types="['xlsx']" accept=".xlsx"
                  :limit="1" scene="termAssociation"
                  list-type="text" tips="只能上传 excel，第一列是词条，第二列是关联的词条">
      </nib-upload>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="8" :offset="2" style="margin-top: 30px">
      <el-input type="text" v-model="termKey" placeholder="请输入关键字" clearable @change="handleChangeTermKey"></el-input>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="8" :offset="2" style="margin-top: 30px">
      <el-input type="textarea" v-model="termResult" :rows="4" :autosize="termResultDefaultSize" style="width: 500px" readonly></el-input>
    </el-col>
  </el-row>
  <el-row>

  </el-row>
</template>

<script setup>
import {ref} from 'vue';
import NibUpload from "@/components/nibUpload.vue";
import {findTerms} from "@/api/termAssociation";

const fileUrl = ref(null);
const termKey = ref(null);
const termResult = ref(null);
const termResultDefaultSize = ref({
  minRows: 4
});

const handleChangeTermKey = () => {
  findTerms({key: termKey.value}).then(res => {
    if (!res || !res.list || res.list.length === 0) {
      termResult.value = '未找到相关术语';
      return;
    }
    // 数组转字符串，换行符分隔
    termResult.value = res.list.join('\n');
  });
};
</script>

<style scoped>

</style>