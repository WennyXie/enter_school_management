<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="班级学生列表" name="first">学生列表
                <studentList /></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import studentList from "@/components/studentList";
    export default {
        name: "ClassList",
        components:{
            studentList,
        },
        data(){
            return{
                totalTime:'',
                activeName:'first'
            }
        },
        created() {

        }
    }
</script>

<style scoped>

</style>
