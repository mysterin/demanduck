import axios from 'axios'
import store from "@/store";

const instance = axios.create({
    baseURL: '/api',
    method: 'POST',
})

instance.interceptors.request.use(request => {
    request.headers.Token = store.state.token;
    return request;
}, error => {
    console.log('error in request');
    return Promise.reject(error);
})

export default instance;