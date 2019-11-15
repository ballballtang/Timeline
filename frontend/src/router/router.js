
import Main from '../pages/mainPage';
import VueRouter from 'vue-router';
import Vue from 'vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: '主页',
    component: Main,
    meta: {}
  },
];

const router = new VueRouter({
  mode: 'history',
  routes: routes
});

export {routes};
export default router;
