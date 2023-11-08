<template>
  <el-upload
      ref="upload"
      action="#"
      :before-upload="handleBefore"
      :http-request="ossUpload"
      :on-success="handleFileSuccess"
      :on-exceed="handleFileExceed"
      :on-remove="handleFileRemove"
      :file-list="fileList"
      :accept="accept"
      :name="fileName"
      :limit="limit"
      :list-type="listType"
      :tips="tips"
      :disabled="disabled"
  >
    <el-icon><Plus /></el-icon>
    <template #file="{ file }">
      <div>
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
        <span class="el-upload-list__item-actions">
          <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in /></el-icon>
          </span>
          <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleRemove(file)"
          >
            <el-icon><Delete /></el-icon>
          </span>
        </span>
      </div>
    </template>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";
import {getStsToken} from "@/api/config";
import {OSS} from "ali-oss";
import {randomString} from "@/utils/random";

const dialogImageUrl = ref('');
const dialogVisible = ref(false);
const disabled = ref(false);
const fileList = ref([]);
const accept = ref('image/*');
const fileName = ref('file');
const limit = ref(1);
const listType = ref('picture-card');
const tips = ref('只能上传jpg/png文件，且不超过500KB');


const handleBefore = (file) => {
  console.log(file);
  return true;
}

const ossUpload = function (options) {
  getStsToken().then(res => {
    const client = new OSS({
      region: res.data.region,
      accessKeyId: res.data.accessKeyId,
      accessKeySecret: res.data.accessKeySecret,
      stsToken: res.data.securityToken,
      bucket: res.data.bucket,
      refreshSTSTokenInterval: res.data.expiration,
      refreshSTSToken: async () => {
        const res = await getStsToken();
        return {
          accessKeyId: res.data.accessKeyId,
          accessKeySecret: res.data.accessKeySecret,
          stsToken: res.data.securityToken,
          refreshSTSTokenInterval: res.data.expiration
        };
      }
    });
    let file = options.file;
    let type = getExt(file.name);
    let objectName = randomString(10) + type;
    client
        .put(objectName, file)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
  });
}

const handleFileSuccess = (res, file, fileList) => {
  console.log(res);
  console.log(file);
  console.log(fileList);
}

const handleFileExceed = (files, fileList) => {
  console.log(files);
  console.log(fileList);
}

const handleFileRemove = (file, fileList) => {
  console.log(file);
  console.log(fileList);
}

// const handlePictureCardPreview = file => {
//   dialogImageUrl.value = file.url;
//   dialogVisible.value = true
// }

// const handleRemove = file => {
//   console.log(file);
// }

/**
 * 根据文件名字获取文件后缀，包含 .
 */
const getExt = (fileName) => {
  let ext = fileName.split('.');
  return '.' + ext[ext.length - 1];
}
</script>
<style scoped>

</style>