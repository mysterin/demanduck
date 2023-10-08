import {createStore} from 'vuex'

export default createStore({
    state: {
        userId: localStorage.getItem('userId') || '',
        username: localStorage.getItem('username') || '',
        mobile: localStorage.getItem('mobile') || '',
        email: localStorage.getItem('email') || '',
        token: localStorage.getItem('token') || '',
        companyId: localStorage.getItem('companyId') || '',
        projectId: localStorage.getItem('projectId') || '',
        projectList: []
    },
    getters: {},
    mutations: {
        setUser(state, user) {
            user = user || {};
            state.userId = user.id || '';
            localStorage.setItem('userId', state.userId);
            state.username = user.username || '';
            localStorage.setItem('username', state.username);
            state.mobile = user.mobile || '';
            localStorage.setItem('mobile', state.mobile);
            state.email = user.email || '';
            localStorage.setItem('email', state.email);
            state.token = user.token || '';
            localStorage.setItem('token', state.token);
        },
        setCompanyId(state, companyId) {
            state.companyId = companyId;
            localStorage.setItem('companyId', companyId);
        },
        setProjectId(state, projectId) {
            state.projectId = projectId;
            localStorage.setItem('projectId', projectId);
        },
        setProjectList(state, projectList) {
            state.projectList = projectList;
        }
    },
    actions: {},
    modules: {}
})
