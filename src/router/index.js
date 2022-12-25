
import { createRouter, createWebHistory} from 'vue-router'
import inLogin from '../views/login'
import Home from '../views/Home.vue'
import UserInfo from "../views/student/userInfo";
import FormInfo from "../views/student/FormInfo";
import EnterAndLeave from "../views/student/enterAndleave";
import Querry from "../views/student/querry";
import Application from "../views/fudaoyuan/Application";
import ClassList from "../views/fudaoyuan/ClassList";
import OtherInfomation from "../views/fudaoyuan/OtherInfomation";
import DeptApplication from "@/views/deptAdmin/deptApplication";
import DeptClassList from "@/views/deptAdmin/deptClassList";
import CampusList from "@/views/superAdmin/campusList";
import DepartmantList from "@/views/superAdmin/departmantList";
import Superquerry from "@/views/superAdmin/superquerry";

const routes = [
    {
        path: "/login",
        name: "Login",
        component: inLogin,
        meta: { title: "Login" }
    },
     {
        path: "/",
        name: "Home",
        component: Home,
        meta: {
            title: "Home",
            //requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/userInfo",
        name: "userInfo",
        component: UserInfo,
        meta: {
            title: "userInfo",
            //requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/formInfo",
        name: "formInfo",
        component: FormInfo,
        meta: {
            title: "formInfo",
            //requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/enter-leave",
        name: "enter-leave",
        component: EnterAndLeave,
        meta: {
            title: "enter-leave",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/querry",
        name: "querry",
        component: Querry,
        meta: {
            title: "querry",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/classList",
        name: "classlist",
        component: ClassList,
        meta: {
            title: "classList",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/application",
        name: "application",
        component: Application,
        meta: {
            title: "application",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/otherInfomation",
        name: "otherInfo",
        component: OtherInfomation,
        meta: {
            title: "otherInfo",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/deptApplication",
        name: "deptApplication",
        component: DeptApplication,
        meta: {
            title: "deptApplication",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/deptClassList",
        name: "deptClassList",
        component: DeptClassList,
        meta: {
            title: "deptClassList",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/departmantList",
        name: "departmantList",
        component: DepartmantList,
        meta: {
            title: "departmantList",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/campusList",
        name: "campusList",
        component: CampusList,
        meta: {
            title: "campusList",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },
    {
        path: "/supperQuerry",
        name: "supperQuerry",
        component: Superquerry,
        meta: {
            title: "supperQuerry",
            requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
        }
    },


];


const router = createRouter({
    //mode:'hash',
    history: createWebHistory('/'),  // 设置为history模式，就是路径里面没有#,  createWebHashHistory 是默认的，括号里面的就是基础路径，可以理解为项目名称，就是请求路径的基础url
    routes
});


export default router;
