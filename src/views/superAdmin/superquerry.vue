<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="查询1" name="first">
                <querryPage :role="role"/>
            </el-tab-pane>
            <el-tab-pane label="查询2" name="second">
                <Superquerry />
            </el-tab-pane>
        </el-tabs>
    </div>

</template>

<script>
    import querryPage from "@/components/querryPage";
    import Superquerry from "@/components/Superquerry";
    export default {
        name: "supperQuerry",
        components:{
            querryPage,
            Superquerry
        },
        data(){
            return{
                activeName:'first',
                role:'super'
            }
        }
    }
</script>

<style scoped>

</style>
