<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="事物进度查询" name="first">
                <stuApplication/>
            </el-tab-pane>
            <el-tab-pane label="入校权限查询" name="second">入校权限查询
                <stuPermission />
            </el-tab-pane>
            <el-tab-pane label="离校总时长" name="third">离校总时长查询
                <p>你当前一年内的离校时间为{{totalTime}}秒</p>
            </el-tab-pane>
            <el-tab-pane label="班级统计数据" name="fourth">班级统计数据

            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import stuApplication from "@/components/stuApplication";
    import stuPermission from "@/components/stuPermission";
    export default {
        name: "querry",
        components:{
            stuApplication,
            stuPermission
        },
        data(){
            return{
                activeName:'first',
                totalTime:'',
            }
        },
        methods:{
            handleClick(tab, event) {
                if(this.activeName === 'third'){
                    this.$http.get('http://localhost:8006/outside/getLastYearTotalOT',{
                        params:{
                            stuId:sessionStorage.getItem('token')
                        }
                    }).then(res=>{
                        this.totalTime = res.data.data[0]
                    })
                }
            },

        }
    }
</script>

<style scoped>

</style>
