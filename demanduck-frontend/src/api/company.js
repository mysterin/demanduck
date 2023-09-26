import request from '@/utils/request';

export function getCompanyList(data) {
    return request({
        url: '/company/list',
        method: 'post',
        data
    });
}
