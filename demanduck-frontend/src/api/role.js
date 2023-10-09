import request from '@/utils/request';

export function listCompanyRole(data) {
    return request({
        url: '/role/listCompanyRole',
        data
    });
}

export function deleteCompanyRole(data) {
    return request({
        url: '/role/deleteCompanyRole',
        data
    });
}

export function saveCompanyRole(data) {
    return request({
        url: '/role/saveCompanyRole',
        data
    });
}