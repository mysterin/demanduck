<template>
  <el-upload class="avatar-uploader" ref="upload" action="#" :before-upload="handleBeforeUpload"
             :http-request="handleHttpRequest" :on-exceed="handleFileExceed" :file-list="fileList" :accept="accept"
             :name="fileName" :limit="limit" :list-type="listType" :tips="tips" :disabled="disabled">
    <el-icon class="avatar-uploader-icon">
      <Plus/>
    </el-icon>
    <template #tip>
      <div class="el-upload__tip">
        {{ tips }}
      </div>
    </template>
    <template #file="{ file }">
      <div v-if="accept === 'image/*'">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <el-icon><zoom-in /></el-icon>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
            <el-icon>
              <Delete />
            </el-icon>
          </span>
        </span>
      </div>
      <div v-else>
        <div class="file-item" style="display: inline-flex; align-items: center;">
          <el-icon class="file-icon">
            <document />
          </el-icon>
          <span class="file-name">{{ file.name }}</span>
          <span v-if="!disabled" @click="handleRemove(file)">
            <el-icon>
              <delete />
            </el-icon>
          </span>
        </div>
      </div>
    </template>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img :src="dialogImageUrl" alt="Preview Image" style="width: 100%;"/>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineEmits} from "vue";
import {getOssStsToken} from "@/api/config";
import OSS from "ali-oss";
import {uuid} from "@/utils/random";
import {getFileSuffix, getFileNanme} from "@/utils/file";
import {uploadFile} from "@/api/file";
import {ElMessage} from "element-plus";
import { Delete, Plus, ZoomIn } from '@element-plus/icons-vue'

const upload = ref(null);
const emit = defineEmits(['update:url']);
const props = defineProps({
  mode: {
    type: String,
    default: 'demanduck'
  },
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
    default: 'text'
  },
  tips: {
    type: String,
    default: '只能上传 jpg/png 文件，且不超过 2MB'
  },
  disabled: {
    type: Boolean,
    default: false
  },
  prefix: {
    type: String,
    default: 'tmp/'
  },
  fileTypes: {
    type: Array,
    default: () => {
      return ['jpg', 'png', 'jpeg'];
    }
  },
  maxSize: {
    type: Number,
    default: 2048
  },
  scene: {
    type: String,
    default: 'default'
  }
});

const dialogImageUrl = ref('');
const dialogVisible = ref(false);
const fileList = ref([]);

let client;

(() => {
  console.log(props.url);
  if (props.url) {
    props.url.split(',').map(url => {
      fileList.value.push({
        url: url,
        name: getFileNanme(url)
      });
    });
  }

  if (props.mode === 'oss') {
    // 阿里云上传
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
  } else {
    // 默认上传
    client = {
      put: (objectName, file) => {
        const formData = new FormData();
        formData.append('objectName', objectName);
        formData.append('file', file);
        formData.append('scene', props.scene);
        return uploadFile(formData);
      }
    };
  }

})();

const handleBeforeUpload = function (file) {
  const type = getFileSuffix(file.name);
  if (!type || !props.fileTypes.includes(type.substring(1))) {
    ElMessage.error(`文件格式不支持，仅支持 ${props.fileTypes.join(',')} 格式`);
    return false;
  }
  if (file.size > props.maxSize * 1024) {
    ElMessage.error(`文件大小超过 ${props.maxSize}KB`);
    return false;
  }
  return true;
}

const handleHttpRequest = function (options) {
  const file = options.file;
  const type = getFileSuffix(file.name);
  const objectName = props.prefix + uuid() + type;
  client
      .put(objectName, file)
      .then((res) => {
        if (res && res.data && res.data.url) {
          let url = fileList.value.map(item => item.url).join(',');
          emit('update:url', url);
        }
      })
      .catch(err => {
        console.error(err);
        ElMessage.error('上传失败');
      });
}

const handleFileExceed = () => {
  ElMessage.warning(`当前限制选择 ${props.limit} 个文件`);
}

const handlePictureCardPreview = file => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true
}

const handleRemove = file => {
  upload.value.handleRemove(file);
  let url = fileList.value.map(item => item.url).join(',');
  emit('update:url', url);
}

</script>
<style scoped></style>