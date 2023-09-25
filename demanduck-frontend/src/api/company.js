import request from '@/utils/request';

export function getCompanyList(params) {
    return request({
        url: '/company/list',
        method: 'post',
        params
    });
}
