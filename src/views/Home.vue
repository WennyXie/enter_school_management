<template>
    <UserInfo v-if="isStudent"/>
    <ClassList v-if="isFudaoyuan" />
    <DeptClassList v-if="isDeptAdmin"/>
    <div class="home-text" v-if="isSupper">Welcome!you are SupperAdmin</div>
</template>

<script>
    import SideItem from '../components/SideItem.vue'
    import UserInfo from "@/views/student/userInfo";
    import ClassList from "@/views/fudaoyuan/ClassList";
    import DeptClassList from "@/views/deptAdmin/deptClassList";

    export default {
        name: 'Home',
        data() {
            return {
                isCollapse: true,
                isStudent :false,
                isFudaoyuan:false,
                isDeptAdmin:false,
                isSupper:false,
            }
        },
        components: {
            SideItem,
            UserInfo,
            ClassList,
            DeptClassList
        },
        methods: {

        },
        created() {
            if(sessionStorage.getItem('role') === '0'){
                this.isStudent =true
            }
            if(sessionStorage.getItem('role') === '1'){
                this.isFudaoyuan =true
            }
            if(sessionStorage.getItem('role') === '2'){
                this.isDeptAdmin=true
            }
            if(sessionStorage.getItem('role') === '3'){
                this.isSupper = true
            }
        }
    }
</script>

<style>

</style>
