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
    <el-icon>
      <Plus/>
    </el-icon>
    <template #file="{ file }">
      <div>
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt=""/>
        <span class="el-upload-list__item-actions">
          <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in/></el-icon>
          </span>
          <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleRemove(file)"
          >
            <el-icon><Delete/></el-icon>
          </span>
        </span>
      </div>
    </template>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img :src="dialogImageUrl" alt="Preview Image" style="width: 100%;"/>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps} from "vue";
import {getOssStsToken} from "@/api/config";
import OSS from "ali-oss";
import {uuid} from "@/utils/random";
import {getFileSuffix} from "@/utils/file";

const upload = ref(null);
const props = defineProps({
  url: {
    type: String,
    default: ''
  },
  accept: {
    type: String,
    default: 'image/*'
  },
  fileName: {
    type: String,
    default: 'file'
  },
  limit: {
    type: Number,
    default: 1
  },
  listType: {
    type: String,
    default: 'picture-card'
  },
  tips: {
    type: String,
    default: '只能上传jpg/png文件，且不超过500KB'
  },
  disabled: {
    type: Boolean,
    default: false
  },
  fileList: {
    type: Array,
    default: () => {
      return [];
    }
  },
  ossPrefix: {
    type: String,
    default: 'tmp/'
  }
});

const dialogImageUrl = ref('');
const dialogVisible = ref(false);

let client;

(() => {
  getOssStsToken().then(res => {
    if (res && res.data) {
      client = new OSS({
        region: res.data.region,
        accessKeyId: res.data.accessKeyId,
        accessKeySecret: res.data.accessKeySecret,
        stsToken: res.data.stsToken,
        bucket: res.data.bucket,
        refreshSTSTokenInterval: 300000,
        refreshSTSToken: async () => {
          const res = await getOssStsToken();
          return {
            accessKeyId: res.data.accessKeyId,
            accessKeySecret: res.data.accessKeySecret,
            stsToken: res.data.securityToken,
          };
        }
      });
    }
  });
})();

const handleBefore = (file) => {
  console.log(file);
  return true;
}

const ossUpload = function (options) {
  const file = options.file;
  const type = getFileSuffix(file.name);
  const objectName = props.ossPrefix + uuid() + type;
  client
      .put(objectName, file)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
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

const handlePictureCardPreview = file => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true
}

const handleRemove = file => {
  console.log(file);
  upload.value.handleRemove(file);
}

</script>
<style scoped>

</style>