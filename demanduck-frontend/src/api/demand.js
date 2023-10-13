import request from '@/utils/request';

export function listDemand(data) {
    return request({
        url: '/demand/list',
        data
    });
}

export function createDemand(data) {
    return request({
        url: '/demand/create',
        data
    });
}

export function updateDemand(data) {
    return request({
        url: '/demand/update',
        data
    });
}

export function getDemand(data) {
    return request({
        url: '/demand/get',
        data
    });
}