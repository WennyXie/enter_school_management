<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="申请列表" name="first">申请列表
                <deptApplication /></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import deptApplication from "@/components/deptApplication";
    export default {
        name: "application",
        components:{
            deptApplication,
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
