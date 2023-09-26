import {createStore} from 'vuex'

export default createStore({
    state: {
        userId: localStorage.getItem('userId') || '',
        username: localStorage.getItem('username') || '',
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
            state.userId = user.id;
            localStorage.setItem('userId', user.id);
            state.username = user.username;
            localStorage.setItem('username', user.username);
            state.email = user.email;
            localStorage.setItem('email', user.email);
            state.token = user.token;
            localStorage.setItem('token', user.token);
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
