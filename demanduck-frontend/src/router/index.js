import {createRouter, createWebHistory} from 'vue-router'
import store from '../store'

const routes = [
    {
        path: '/user/login',
        name: 'userLogin',
        component: () => import('../views/user/login'),
        meta: {
            // 不需要登录
            notAuth: true
        }
    }, {
        path: '/user/detail',
        name: 'userDetail',
        component: () => import('../views/user/detail'),
    }, {
        path: '/company/:companyId',
        name: 'company',
        component: () => import('../views/company'),
    }, {
        path: '/project/:projectId',
        name: 'project',
        component: () => import('../views/project'),
        children: [
            {
                path: 'list',
                name: 'projectList',
                component: () => import('../views/project/list'),
            }, {
                path: 'demand',
                name: 'demand',
                component: () => import('../views/demand'),
            }, {
                path: 'mission',
                name: 'mission',
                component: () => import('../views/mission'),
            }, {
                path: 'flaw',
                name: 'flaw',
                component: () => import('../views/flaw'),
            }
            ]
    }, {
        path: '/workspace/:companyId',
        name: 'workspace',
        component: () => import('../views/workspace'),
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (!to.meta.notAuth && !store.state.token) {
        next('/user/login')
    } else if (!to.meta.notAuth && !store.state.companyId) {
        next('/company-list')
    } else {
        next()
    }
})

router.afterEach((to) => {
    const companyId = to.params.companyId
    const projectId = to.params.projectId
    if (companyId) {
        // 获取公司信息
        store.commit('setCompanyId', companyId)
    }
    if (projectId) {
        // 获取项目信息
        store.commit('setProjectId', projectId)
    }
})

export default router
