<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="班级学生列表" name="first">学生列表
                <studentList /></el-tab-pane>
            <el-tab-pane label="健康日报查询" name="second">进校申请
            </el-tab-pane>
            <el-tab-pane label="入校权限查询" name="third">离校申请
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import studentList from "@/components/studentList";
    export default {
        name: "ClassList",
        components:{
            studentList,

        }
    }
</script>

<style scoped>

</style>
