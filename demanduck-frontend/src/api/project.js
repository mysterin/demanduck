import request from '@/utils/request';

export function getProjectList(data) {
    return request({
        url: '/project/list',
        data
    });
}

export function createProject(data) {
    return request({
        url: '/project/create',
        data
    });
}

export function getProject(data) {
    return request({
        url: '/project/get',
        data
    });
}