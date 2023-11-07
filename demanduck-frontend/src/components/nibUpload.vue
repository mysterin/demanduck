<template>
  <el-upload class="avatar-uploader"></el-upload>
</template>

<script setup>
import { ref } from "vue";
import { getStsToken } from "@/api/config";
import { OSS } from "ali-oss";

const upload = function(file) {
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
    client
      .put("file", file)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  });
};
</script>
<style scoped>

</style>