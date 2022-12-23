<template>
    <div class="container">
    <el-table class="wuliao_class" style="width:90%;margin: 20px 0 0 50px "
              :data="tabledata"
              center
              align="center" border
              max-height="500px">
        <el-table-column label="院系名" prop="deptName"></el-table-column>
        <el-table-column label="院系管理员" prop="adminName"/>
        <el-table-column label="管理员工号" prop="adminId" />
        <el-table-column label="院系班级数" prop="classAmount"/>
        <el-table-column label="院系学生数" prop="stuAmount" />
        <el-table-column label="获取学生列表" >
            <template #default="scope">
                <button class="table_button edit" @click="getstudent(scope.row,scope.$index)">获取</button>
            </template>
        </el-table-column>
    </el-table>
    </div>

    <el-dialog
            title="查询结果"
            v-model="dialogVisible"
            width="700px"
            :before-close="handleClose">
        <el-table :data="studentList" style="width: 100%" border max-height="500px">
            <el-table-column prop="stuId" label="学号" />
            <el-table-column prop="stuName" label="姓名"/>
            <el-table-column prop="adminName" label="辅导员"/>
            <el-table-column prop="stuClassName" label="班级"/>
            <el-table-column prop="stuEmail" label="邮箱" />
            <el-table-column prop="stuPhnum" label="电话"></el-table-column>
            <el-table-column prop="stuIdnum" label="证件号"></el-table-column>
        </el-table>
    </el-dialog>
</template>

<script>
    export default {
        name: "departmentList",
        data(){
            return{
                dialogVisible:false,
                tabledata:[],
                studentList:[],
            }
        },
        methods:{
            handleClose(){
               this.dialogVisible = false;
            },
            getstudent(row,index){
                this.$http.get('http://localhost:8006/UserInfo/byDepartment',{
                    params:{
                        deptId:parseInt(row.deptId)
                    }
                }).then(res=>{
                    this.studentList = res.data.data[0]
                    this.dialogVisible = true
                }).catch(err=>{
                    this.$message.error("获取数据失败")
                })
            }
        },
        created() {
            this.$http.get('http://localhost:8006/UserInfo/allDepartment').then(res=>{
                this.tabledata = res.data.data[0]
            }).catch(err=>{
                this.$message.error("获取数据失败")
            })
        }
    }
</script>

<style scoped>
    @import "../../style/main.css";

</style>
