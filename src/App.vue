<template>
    <div style="display:flex;">
        <Login @login="login" v-if="islogin"/>
        <Menu :type="role" v-if="isRouterAlive" />
        <router-view v-if="isRouterAlive" />
    </div>
</template>

<script>
    import Login from './views/login.vue'
    import Menu from  './views/Menu'
    export default {
        name: 'App',
        components: {
            Login,
            Menu
        },
        data() {
            return {
                islogin:true,
                navigationDisable: null,
                isRouterAlive: false,
                seeFooter: false,
                role:'',
            };
        },
        created() {
            if(sessionStorage.getItem('token') !== null){
                console.log('token',sessionStorage.getItem('token'));
                this.isRouterAlive=true ;
                this.islogin = false;
                this.role = sessionStorage.getItem('token')
            }
        },
        methods: {
            login(type){
                this.isRouterAlive = true;
                this.islogin = false;
                this.role=type;
                sessionStorage.setItem('role',this.role);
            }
        }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

</style>
