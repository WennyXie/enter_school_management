<template>

    <div class="container">
        <el-table class="wuliao_class" style="width:90%;margin: 20px 0 0 50px "
                  :data="tabledata"
                  center
                  align="center" border
                  max-height="500px"
                  :key="campusStatus"
        >
            <el-table-column label="校区名" prop="campusName"></el-table-column>
            <el-table-column label="状态" prop="campusStatus">
                <template #default = "scope">
                    <el-tag size="large" v-if = "scope.row.campusStatus === 0" type="danger">
                        封控中</el-tag>
                    <el-tag size="large" v-else-if="scope.row.campusStatus === 1" type="success">
                        正常</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" >
                <template #default="scope">
                    <button class="table_button edit" @click="change(scope.row,scope.$index)" v-if="scope.row.campusStatus === 1">封控</button>
                    <button class="table_button edit" @click="change(scope.row,scope.$index)" v-if="scope.row.campusStatus === 0">解封</button>
                </template>
            </el-table-column>
        </el-table>
    </div>

</template>

<script>
    export default {
        name: "campusList",
        data(){
            return{
                tabledata:[],
            }
        },
        methods:{
            getAllcampus(){
                this.$http.get('http://localhost:8006/Campus/getallCampus').then(res=>{
                    this.tabledata=res.data.data[0]
                }).catch(err=>{
                    this.$message.error("获取数据失败")
                })
            },
            change(row,index){
                if(row.campusStatus === 1){
                    row.campusStatus = 0
                }else{
                    row.campusStatus = 1
                }
                this.$http.put("http://localhost:8006/Campus/changeStatus",row).then(res=>{
                    this.getAllcampus();
                }).catch(err=>{
                    this.$message.error("更新校区状态失败")
                })
            }
        },
        created() {
            this.getAllcampus()
        }
    }
</script>

<style scoped>

</style>
