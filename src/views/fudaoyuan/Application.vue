<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="申请列表" name="first">申请列表
                <adminApplication /></el-tab-pane>
            <el-tab-pane label="查询" name="second">查询
                <adminApplication /></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import adminApplication from "@/components/adminApplication";
    export default {
        name: "application",
        components:{
            adminApplication,
        },
        data(){
            return{
                activeName:'first'
            }
        }
    }
</script>

<style scoped>

</style>
