import request from '@/utils/request';

export function uploadFile(data) {
    return request({
        url: '/file/upload',
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data
    });
}