import { createRouter, createWebHistory } from "vue-router";
import MainPage from "./components/MainPage.vue"
import Login from "./components/Login.vue"
import Registration from "./components/Registration.vue"
import Navbar from "./components/Navbar.vue"
import Key from "./components/Key.vue"
import Keys from "./components/Keys.vue"
import Applications from "./components/Applications.vue"
import Intime from "./components/Intime.vue"
import Teachers from "./components/Teachers.vue"
import Workers from "./components/Workers.vue"
import UserRegistration from "./components/UserRegistration.vue"
import Profile from "./components/Profile.vue"
import AdminLogin from "./components/AdminLogin.vue"
import AdminList from "./components/AdminList.vue";

const routes = [
    {
        path: '/',
        name: 'MainPage',
        component: MainPage
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/registration',
        name: 'Registration',
        component: Registration
    },
    {
        path: '/keys',
        name: 'Keys',
        component: Keys
    },
    {
        path: '/applications',
        name: 'Applications',
        component: Applications
    },
    {
        path: '/teachers',
        name: 'Teachers',
        component: Teachers
    },
    {
        path: '/schedule/:id',
        name: 'Schedule',
        component: Intime
    },
    {
        path: '/workers',
        name: 'Workers',
        component: Workers
    },
    {
        path: '/reguser',
        name: 'Reguser',
        component: UserRegistration
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile
    },
    {
        path: '/adminLogin',
        name: 'AdminLogin',
        component: AdminLogin
    },
    {
        path: '/deans',
        name: 'AdminList',
        component: AdminList
    }
]

export default createRouter({
    history: createWebHistory(),
    routes
})
