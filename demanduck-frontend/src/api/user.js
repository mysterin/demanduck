import request from '@/utils/request';

export function login(data) {
    return request({
        url: '/user/login',
        data
    });
}

export function logout() {
    return request({
        url: '/user/logout'
    });
}

export function sendValidCode(data) {
    return request({
        url: '/user/sendValidCode',
        data
    });
}

export function resetPassword(data) {
    return request({
        url: '/user/resetPassword',
        data
    });
}

export function register(data) {
    return request({
        url: '/user/register',
        data
    });
}