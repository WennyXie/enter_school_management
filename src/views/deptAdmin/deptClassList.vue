<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="班级列表" name="first">班级列表
                <ClassList /></el-tab-pane>
            <el-tab-pane label="查询" name="second">
                <querryPage :role="role"/>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import ClassList from "@/components/ClassList";
    import querryPage from "@/components/querryPage";
    export default {
        name: "deptClassList",
        components:{
            ClassList,
            querryPage
        },
        data(){
            return{
                role:"dept",
                activeName:'first'
            }
        }
    }
</script>

<style scoped>

</style>
