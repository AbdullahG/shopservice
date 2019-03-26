import Categories from '../page/Categories.vue'
import Items from '../page/Items.vue'
import VueRouter from 'vue-router'

const routes = [
    {
        path: '/items/:categoryId',
        component: Items,
        meta: {
            name: 'items'
        }
    },
    {
        path: '/items',
        component: Items,
        meta: {
            name: 'items'
        }
    },
    {
        path: '/*',
        component: Categories,
        meta: {
            name: 'categories'
        }
    }
];

const router = new VueRouter({routes})

export {router}