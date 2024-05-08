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
                path: '/:companyId/member',
                name: 'member',
                component: () => import('../views/companyMember.vue'),
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
                        path: 'demand-edit',
                        name: 'demandEdit',
                        // alias: 'demand-edit/:demandId',
                        component: () => import('../views/demandEdit'),
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
            }, {
                path: '/user/logout',
                name: 'userLogout',
                component: () => import('../views/userLogout'),
            }
        ]
    }, {
        path: '/resetPassword',
        name: 'resetPassword',
        component: () => import('../views/resetPassword'),
        meta: {
            notAuth: true
        }
    }, {
        path: '/register',
        name: 'register',
        component: () => import('../views/register'),
        meta: {
            notAuth: true
        }
    }, {
        path: '/term/association',
        name: 'termAssociation',
        component: () => import('../views/termAssociation'),
        meta: {
            // 不需要登录
            notAuth: true
        }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (!to.meta.notAuth && !store.state.token) {
        next('/user/login?redirect=' + to.path)
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
