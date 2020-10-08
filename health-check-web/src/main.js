import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
import axios from 'axios';
import 'font-awesome/css/font-awesome.min.css'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

// * http request 拦截器
axios.interceptors.request.use(
  config => {
    // * 判断是否存在token，如果存在的话，则每个http header都加上token
    // * token会在登录之后存储在本地
    if (sessionStorage.token) {
      config.headers["Authorization"] = `${sessionStorage.token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  });

// * http response 拦截器
axios.interceptors.response.use(
  response => {
    let data = response.data;
    // * 正常返回数据
    if (data.code === 200) {
      // * 返回data
      return data
    }
    // * 跳转到login
    if (data.code === 403) {
      router.replace('/login')
    }
    return Promise.reject(data);
  },
  error => {
    return Promise.reject(error);
  });

Vue.prototype.$http = axios;

//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

