import request from '@/utils/request';

export function findTerms(data) {
    return request({
        url: '/term/association/findTerms',
        data
    });
}