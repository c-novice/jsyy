import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '../views/layout/Layout'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  {path: '/login', component: () => import('@/views/login/index'), hidden: true},
  {path: '/404', component: () => import('@/views/404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/userInfo/list',
    name: 'userInfo',
    meta: {title: '用户管理', icon: 'table'},
    alwaysShow: true,
    children: [
      {
        path: 'userInfo/list',
        name: '用户列表',
        component: () => import('@/views/user/userInfo/list'),
        meta: {title: '用户列表', icon: 'table'}
      },
      {
        path: 'userInfo/show/:id',
        name: '用户查看',
        component: () => import('@/views/user/userInfo/show'),
        meta: {title: '用户查看'},
        hidden: true
      },
      {
        path: 'userInfo/authList',
        name: '认证审批列表',
        component: () => import('@/views/user/userInfo/authList'),
        meta: {title: '认证审批列表', icon: 'table'}
      }
    ]
  },
  {
    path: '/building',
    component: Layout,
    redirect: '/building/buildingInfo/list',
    name: 'buildingInfo',
    meta: {title: '设施管理', icon: 'table'},
    alwaysShow: true,
    children: [
      {
        path: 'buildingInfo/list',
        name: '设施列表',
        component: () => import('@/views/building/buildingInfo/list'),
        meta: {title: '设施列表', icon: 'table'}
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
