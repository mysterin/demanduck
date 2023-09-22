import request from '@/utils/request';

export function login(params) {
    return request({
        url: '/user/login',
        data: params
    });
}