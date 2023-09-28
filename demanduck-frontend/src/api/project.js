import request from '@/utils/request';

export function getProjectList(data) {
    return request({
        url: '/project/list',
        method: 'post',
        data
    });
}

export function createProject(data) {
    return request({
        url: '/project/create',
        method: 'post',
        data
    });
}