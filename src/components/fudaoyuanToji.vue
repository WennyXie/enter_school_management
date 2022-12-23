<template>
    <div class="container">
        <el-table :data="tableData"
                  border
                  style="width: 90%"
                  :row-class-name="tableRowClassName2">
            <el-table-column type="expand" min-width="9%">
                <template #default="props">
                    <h3>权限情况</h3>
                    <el-table :data="props.row.permissionNum"
                              :row-class-name="tableRowClassName"
                              @row-click="handleTable">
                        <el-table-column  min-width="8%" />
                        <el-table-column label="校区" prop="campusName" min-width="11%" />
                        <el-table-column label="有权限人数" prop="permiNum" min-width="10%" />
                    </el-table>
                </template>
            </el-table-column>>
            <el-table-column prop="className" label="班级" min-width="8%"/>
            <el-table-column prop="instructorName" label="辅导员名称" min-width="8%"/>
            <el-table-column prop="instructorId" label="辅导员工号" min-width="8%"/>
            <el-table-column prop="memberNum" label="人数" min-width="8%"/>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: "fudaoyuanToji",
        data(){
            return {
                tableData:[],
            }
        },
        created() {
            this.getdata();
        },
        methods: {
            handleClick(row) {
                console.log(row);
            },
            handleTable(row,column,event) {
                console.log(row);
            },
            tableRowClassName({row,rowIndex}) {
                row.index = rowIndex+1;
            },
            tableRowClassName2({row,rowIndex}) {
                row.index = rowIndex+1;
            },
            async getdata(){
                let _this = this;
                await _this.$http.get('http://localhost:8006/perm/instr/getstatistic',{
                    params:{
                        adminId:sessionStorage.getItem('token')
                    }
                }).then(res=>{
                    let data = {
                        className:'',
                        instructorName:'',
                        instructorId:'',
                        memberNum:'',
                        permissionNum:[]
                    };
                    var permissionList=[];
                    var binaling=[];
                    let permission={};
                    for(var i=0;i<res.data.data[0].length;i++){
                        data={};
                        data.className = res.data.data[0][i].className;
                        data.instructorName = res.data.data[1][i].adminName;
                        data.instructorId = res.data.data[1][i].adminId;
                        console.log(data)
                        data.memberNum = res.data.data[2][i];
                        permissionList = res.data.data[3][i];
                        for(var j=0;j<permissionList.length;j++){
                            permission={};
                            if(j === 0 ){
                                permission.campusName = '邯郸校区';
                                permission.permiNum = permissionList[j];
                            }
                            if(j === 1 ){
                                permission.campusName = '枫林校区';
                                permission.permiNum = permissionList[j];
                            }
                            if(j === 2 ){
                                permission.campusName = '江湾校区';
                                permission.permiNum = permissionList[j];
                            }
                            if(j === 3 ){
                                permission.campusName = '张江校区';
                                permission.permiNum = permissionList[j];
                            }
                            binaling.push(permission);
                        }
                        data.permissionNum=binaling
                        binaling=[];
                        this.tableData.push(data);
                    }
                }).catch(err=>{
                    console.log(err);
                    _this.$message({
                        message: '获取数据失败',
                        type: 'warning',
                    });
                })

            }
        },
    }
</script>

<style scoped>
    h3{
        margin:2px 2px 2px 10px;
    }

</style>
