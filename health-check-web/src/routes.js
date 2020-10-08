import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import User from './views/system/User.vue'
import Project from './views/system/Project.vue'
import Check from './views/system/Check.vue'
import Result from './views/system/Result.vue'
import Notice from './views/system/Notice.vue'
import Dashboard from './views/dashboard/dashboard.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/user', component: User, name: '用户管理' },
            { path: '/project', component: Project, name: '项目管理' },
            { path: '/check', component: Check, name: '检查项管理' },
            { path: '/result', component: Result, name: '检测结果' },
            { path: '/notice', component: Notice, name: '通知信息' },
         
        ]
    },
    {
        path: '/',
        component: Home,
        name: 'Dashboard',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/dashboard', component: Dashboard, name: 'Dashboard' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;