import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"
import VueRouter from 'vue-router'
import {router} from './router/router.js'
import VueResource from 'vue-resource'



Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.use(VueResource)

Vue.http.options.root = 'http://localhost:8080'

new Vue({
    el: '#app',
    render: h => h(App),
    router: router
})