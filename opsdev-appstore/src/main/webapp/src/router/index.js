import Vue from 'vue'
import Router from 'vue-router'
import AppList from '@/components/AppList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'AppList',
      component: AppList
    }
  ]
})
