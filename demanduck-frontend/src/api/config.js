import request from '@/utils/request';

export function getStsToken(data) {
    return request({
        url: '/config/getStsToken',
        method: 'post',
        data
    });
}
