import request from '@/utils/request';

export function getProjectList(data) {
    return request({
        url: '/project/list',
        method: 'post',
        data
    });
}
