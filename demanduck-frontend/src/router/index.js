import {createRouter, createWebHistory} from 'vue-router'
import store from '../store'

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login'),
        meta: {
            // 隐藏导航
            hideNav: true,
            // 不需要登录
            notAuth: true
        }
    }, {
        path: '/company-list',
        name: 'companyList',
        component: () => import('../views/company/list'),
        meta: {
            // 隐藏导航
            hideNav: true,
        }
    }, {
        path: '/company/:companyId',
        name: 'company',
        component: () => import('../views/company'),
    }, {
        path: '/project/:projectId',
        name: 'project',
        component: () => import('../views/project'),
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
        next('/login')
    } else {
        next()
    }
})

router.afterEach((to) => {
    // 获取路由上的 companyId
    const companyId = to.params.companyId
    if (companyId) {
        // 获取公司信息
        store.commit('setCompanyId', companyId)
    }
})

export default router
