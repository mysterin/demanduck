import request from '@/utils/request';

export function getOssStsToken(data) {
    return request({
        url: '/config/getOssStsToken',
        method: 'post',
        data
    });
}
