import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css';
import "./style/main.css";
import SvgIcon from '@/icons/SvgIcon';
import './icons';
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'



const app = createApp(App)
app.use(router)
    .use(ElementPlus)
    .use(VueAxios, axios)
    .component('svg-icon', SvgIcon).mount('#app');





