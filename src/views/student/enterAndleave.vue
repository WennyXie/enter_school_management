<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="进出校" name="first">进出校
                <el-select v-model="log.campusId" placeholder="选择校区" aria-label="校区">
                    <template v-for="item in campus" :key="item.campusId">
                        <el-option :label="item.campusName" :value="item.campusId"></el-option>
                    </template>
                </el-select>
                <div class="buttons-1">
                    <button class="button" @click.prevent="shuaka">刷卡</button>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>

</template>

<script>
    export default {
        name: "enterandleave",
        data(){
            return{
                activeName:'first',
                log:{
                    stuId:sessionStorage.getItem('token'),
                    campusId:''
                },
                campus:[
                    {campusId: 1, campusName:'邯郸校区'},
                    {campusId: 2, campusName:'枫林校区'},
                    {campusId: 3, campusName:'江湾校区'},
                    {campusId: 4, campusName:'张江校区'},
                ],
            }
        },
        methods:{
            shuaka(){
                console.log(this.log);
                this.$http.post('http://localhost:8006/log/slashcard',this.log).then(res=>{
                    this.$message({
                        message: res.data.msg,
                        type: 'success'
                    })
                })

            },

        },
        created() {
        }
    }
</script>

<style scoped>

</style>
