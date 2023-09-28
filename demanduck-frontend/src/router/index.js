import {createRouter, createWebHistory} from 'vue-router'
import store from '../store'

const routes = [
    {
        path: '/user/login',
        name: 'userLogin',
        component: () => import('../views/userLogin'),
        meta: {
            // 不需要登录
            notAuth: true
        }
    }, {
        path: '/',
        name: 'containerMain',
        component: () => import('../views/containerMain'),
        redirect: '/company-list',
        children: [
            {
                path: '/:companyId/workspace',
                name: 'workspace',
                component: () => import('../views/workspace'),
            }, {
                path: '/company-list',
                name: 'companyList',
                component: () => import('../views/companyList'),
            }, {
                path: '/:companyId/project-list',
                name: 'projectList',
                component: () => import('../views/projectList'),
            }, {
                path: '/project/:projectId',
                name: 'project',
                component: () => import('../views/containerProject'),
                redirect: {name: 'demand'},
                children: [
                    {
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
                    },
                ]
            }, {
                path: '/user/detail',
                name: 'userDetail',
                component: () => import('../views/userDetail'),
            }
            ]
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
