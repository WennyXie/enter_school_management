<template>
    <div class="container">
        <el-row>
            <el-col :span="4" >
                <el-table style="width:95%;"
                          :data="group"
                          center
                          align="left"
                          highlight-current-row
                          :row-class-name="groupRowClassName"
                          @current-change="handleCurrentChange"
                          border>
                    <el-table-column label="事物类型" prop="groupname" min-width="42%"></el-table-column>
                </el-table>

            </el-col>
            <el-col :span="20">
                <el-select v-model="querrystatus" placeholder="选择状态" clearable>
                    <template v-for="item in thestatus" :key="item.status">
                        <el-option :label="item.name" :value="item.status"></el-option>
                    </template>
                </el-select>
                <el-button @click="querry">查询</el-button>
                <el-table class="wuliao_class" style="width:90%;"
                          :data="application" center
                          align="left" border
                          :row-class-name="tableRowClassName"
                >
                    <el-table-column label="学号" prop="stuId"></el-table-column>
                    <el-table-column label="填写日期" prop="currentDate"/>
                    <el-table-column label="预计返校时间" prop="expComindate" v-if="type === 0"/>
                    <el-table-column label="预计离校时间" prop="expLeavdate" v-if="type === 1"/>
                    <el-table-column label="预计返校时间" prop="expRetdate" v-if="type === 1"/>
                    <el-table-column label="城市" prop="destCity" v-if="type === 1"/>
                    <el-table-column label="区" prop="destDistrict" v-if="type === 1"/>
                    <el-table-column label="街道" prop="destStreet" v-if="type === 1"/>
                    <el-table-column label="处理状态" prop="status">
                        <template #default = "scope">
                            <el-tag size="large" v-if = "scope.row.status === 0" type="danger">
                                被拒绝</el-tag>
                            <el-tag size="large" v-else-if="scope.row.status === 1" type="warning">
                                未处理</el-tag>
                            <el-tag size="large" v-else-if="scope.row.status === 2" type="success">
                                辅导员通过</el-tag>
                            <el-tag size="large" v-else-if="scope.row.status === 3" type="success">
                                已通过</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" >
                        <template #default="scope">
                            <button class="table_button edit" @click="check(scope.row,scope.$index)" v-if="scope.row.status === 0">查看</button>
                            <button class="table_button edit" disabled v-if="scope.row.status !== 0" >查看</button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>



</template>

<script>
    import { ElMessage, ElMessageBox,ElNotification } from 'element-plus'
    export default {
        name: "stuApplication",
        data(){
            return {
                group: [
                    {
                        groupname:'入校申请'
                    },
                    {
                        groupname:'离校申请'
                    }

                ],
                thestatus:[
                    {status:0 , name:"被拒绝"},
                    {status:1 , name:"未处理"},
                    {status:2 , name:"辅导员同意"},
                    {status:3 , name:"已通过"},
                ],
                querrystatus:'',
                type:0,
                application:[],
                enter:[],
                leave:[],
            }
        },
        methods:{
            tableRowClassName({row,rowIndex}) {
                row.index = rowIndex+1;
            },
            groupRowClassName({row,rowIndex}) {
            },
            handleCurrentChange(val) {
                if(val.groupname === '离校申请'){
                    this.type = 1
                    this.application = this.leave
                }else{
                    this.type = 0
                    this.application = this.enter
                }
                console.log(val);
            },
            check(row,index){
                ElNotification({
                    title: '原因',
                    message: this.application[index].rejectReason,
                    type: '',
                });
            },
            getenter(){
                this.$http.get('http://localhost:8006/CA/myCA',{
                    params:{
                        stuId:sessionStorage.getItem('token')
                    }
                }).then(res=>{
                    console.log('res',res.data.data);
                    this.enter =res.data.data[0]
                    this.application=this.enter
                }).catch(err=>{
                    this.$message({
                        message: "获取失败",
                        type: 'danger'
                    });
                })
                this.getleave()
            },
            getleave(){
                this.$http.get('http://localhost:8006/LA/myLA',{
                    params:{
                        stuId:sessionStorage.getItem('token')
                    }
                }).then(res=>{
                    console.log('res',res.data.data);
                    this.leave =res.data.data[0]
                }).catch(err=>{
                    this.$message({
                        message: "获取失败",
                        type: 'danger'
                    });
                })
            },
            querry(){
                let data={
                    stuId:sessionStorage.getItem('token'),
                    status:this.querrystatus
                }
                if(this.type === 0){ //入校
                    this.$http.post('http://localhost:8006/CA/stuCA',data).then(res=>{
                        this.application = res.data.data[0];
                    }).catch(err=>{
                        this.$message({
                            message: "查询失败",
                            type: 'danger'
                        });
                    })
                }else{
                    this.$http.post('http://localhost:8006/LA/stuLA',data).then(res=>{
                        this.application = res.data.data[0];
                    }).catch(err=>{
                        this.$message({
                            message: "查询失败",
                            type: 'danger'
                        });
                    })
                }

            }
        },
        created() {
            this.getenter();
        }
    }
</script>

<style scoped>

</style>
