import axios from 'axios'
import store from "@/store";
import { ElMessage, ElMessageBox } from 'element-plus';

const instance = axios.create({
    baseURL: '/api',
    method: 'POST',
})

/**
 * 设置头部 token
 */
instance.interceptors.request.use(request => {
    request.headers.Token = store.state.token;
    return request;
}, error => {
    ElMessage.error('请求失败，请重试');
    return Promise.reject(error);
});

/**
 * 返回错误码处理
 */
instance.interceptors.response.use(response => {
    const res = response.data;
    const code = res.code;
    if (code === 1002 || code === 1008) {
        ElMessageBox.confirm('用户未登录, 请重新登录', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            store.commit('setUser', '');
            location.reload();
        })
        return;
    }
    if (code !== 0) {
        ElMessage.error(res.msg);
        return;
    }
    return res;
});

export default instance;