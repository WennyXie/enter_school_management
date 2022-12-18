<template>
    <UserInfo v-if="isStudent"/>
    <ClassList v-if="isFudaoyuan" />
</template>

<script>
    import SideItem from '../components/SideItem.vue'
    import UserInfo from "@/views/student/userInfo";
    import ClassList from "@/views/fudaoyuan/ClassList";

    export default {
        name: 'Home',
        data() {
            return {
                isCollapse: true,
                isStudent :false,
                isFudaoyuan:false,
            }
        },
        components: {
            SideItem,
            UserInfo,
            ClassList
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
        }
    }
</script>

<style>

</style>
