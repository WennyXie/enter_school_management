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
                          max-height="500px"
                >
                    <el-table-column label="学号" prop="stuId"></el-table-column>
                    <el-table-column label="填写日期" prop="myDate"/>
                    <el-table-column label="预计返校时间" prop="expComindate" v-if="type === 0"/>
                    <el-table-column label="预计离校时间" prop="expLeavdate" v-if="type === 1"/>
                    <el-table-column label="预计返校时间" prop="expRetdate" v-if="type === 1"/>
                    <el-table-column label="城市" prop="destCity" v-if="type === 1"/>
                    <el-table-column label="区" prop="destDistrict" v-if="type === 1"/>
                    <el-table-column label="街道" prop="destStreet" v-if="type === 1"/>
                    <el-table-column label="处理状态" prop="appStatus">
                        <template #default = "scope">
                            <el-tag size="large" v-if = "scope.row.appStatus === 0" type="danger">
                                被拒绝</el-tag>
                            <el-tag size="large" v-else-if="scope.row.appStatus === 1" type="warning">
                                辅导员未处理</el-tag>
                            <el-tag size="large" v-else-if="scope.row.appStatus === 2" type="success">
                                辅导员通过</el-tag>
                            <el-tag size="large" v-else-if="scope.row.appStatus === 3" type="success">
                                已通过</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" >
                        <template #default="scope">
                            <button class="table_button edit" @click="check(scope.row,scope.$index)" v-if="scope.row.appStatus === 2">检查</button>
                            <button class="table_button edit" disabled v-if="scope.row.appStatus !== 2" >检查</button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>

    <el-dialog
            title="检查结果"
            v-model="dialogVisible"
            width="500px"
            :before-close="handleClose">
        <p>{{message}}</p>
        <el-table :data="sevenHDinfo" style="width: 100%" border v-if="type === 0" max-height="400px">
            <el-table-column prop="date" label="日期" />
            <el-table-column prop="bodyTemperature" label="体温"/>
            <el-table-column prop="city" label="城市" />
            <el-table-column prop="district" label="区" />
            <el-table-column prop="street" label="街道" />
            <el-table-column prop="hdStatus" label="状态">
                <template #default = "scope">
                    <el-tag size="large" v-if = "scope.row.hdStatus === 0" type="danger">
                        异常</el-tag>
                    <el-tag size="large" v-else-if="scope.row.hdStatus === 1" type="success">
                        正常</el-tag>
                </template>

            </el-table-column>
        </el-table>

        <el-row>
            <el-col span="5">
                <el-button @click="permit" size="default" type="primary">同意</el-button>
            </el-col>
            <el-col span="5">
                <el-button @click="reject" style="background-color: #E9EEF4" size="default">拒绝</el-button>
            </el-col>
        </el-row>
    </el-dialog>

    <el-dialog
            title="拒绝理由"
            v-model="dialogVisible2"
            width="500px"
            :before-close="handleClose2">
        <el-input v-model="message" style="width: 400px" placeholder="输入拒绝理由"></el-input>
        <el-row>
            <el-col span="5">
                <el-button @click="submit" size="default" type="primary">提交</el-button>
            </el-col>
        </el-row>
    </el-dialog>

</template>

<script>
    export default {
        name: "stuApplication",
        data(){
            return {
                message:'',
                dialogVisible:false,
                dialogVisible2:false,
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
                    {status:1 , name:"辅导员未处理"},
                    {status:2 , name:"辅导员同意"},
                    {status:3 , name:"已通过"},
                ],
                querrystatus:'',
                type:0,
                CAId:'',
                LAId:'',
                stuId:'',
                application:[],
                enter:[],
                leave:[],
                sevenHDinfo:[],
            }
        },
        methods:{
            handleClose(){
                this.dialogVisible = false
            },
            handleClose2(){
                this.dialogVisible2 = false
            },
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
            getndaysDH(){
                this.$http.get('http://localhost:8006/healthdaily/nDayrecords',{
                    params: {
                        stuId:this.stuId,
                        days:7
                    },
                }).then(res=>{
                    this.sevenHDinfo = res.data.data[0]
                    console.log('seven',this.sevenHDinfo)
                    console.log('enterres',res);
                }).catch(err=>{
                    console.log('err',err);
                })
            },
            check(row,index){
                if(this.type === 0){ //入校
                    this.stuId=row.stuId
                    this.CAId =row.cominAppId
                    this.getndaysDH()
                    this.$http.get('http://localhost:8006/CA/check',{
                        params:{
                            CAId:row.cominAppId,
                        }
                    }).then(res=>{
                        this.message = res.data.msg;
                        this.dialogVisible = true
                    })
                }else{
                    this.LAId = row.leavAppId
                    this.$http.get('http://localhost:8006/LA/check',{
                        params:{
                            LAId:row.leavAppId,
                        }
                    }).then(res=>{
                        this.message = res.data.msg;
                        this.dialogVisible = true
                    })
                }
            },
            permit(){
                if(this.type === 0){ //入校
                    this.$http.get('http://localhost:8006/CA/approve',{
                        params:{
                            CAId:this.CAId,
                            adminID:sessionStorage.getItem('token')
                        }
                    }).then(res=>{
                        this.$message({
                            message: "审批完成",
                            type: 'success'
                        });
                        this.dialogVisible=false;
                    }).catch(err=>{
                        this.$message({
                            message: "审批失败",
                            type: 'danger'
                        });
                    })

                }else{
                    this.$http.get('http://localhost:8006/LA/approve',{
                        params:{
                            LAId:this.LAId,
                            adminID:sessionStorage.getItem('token')
                        }
                    }).then(res=>{
                        this.$message({
                            message: "审批完成",
                            type: 'success'
                        });
                        this.dialogVisible=false;
                    }).catch(err=>{
                        this.$message({
                            message: "审批失败",
                            type: 'danger'
                        });
                    })

                }

            },
            reject(){
                this.message='';
                this.dialogVisible2=true;
                this.dialogVisible = false;
            },
            submit(){
                if(this.type === 0){ //入校
                    this.$http.get('http://localhost:8006/CA/reject',{
                        params:{
                            CAId:this.CAId,
                            rejectReason:this.message,
                            adminID:sessionStorage.getItem('token')
                        }
                    }).then(res=>{
                        this.$message({
                            message: "审批完成",
                            type: 'success'
                        });
                        this.dialogVisible2=false;
                    }).catch(err=>{
                        this.$message({
                            message: "审批失败",
                            type: 'danger'
                        });
                    })

                }else{
                    this.$http.get('http://localhost:8006/LA/reject',{
                        params:{
                            LAId:this.LAId,
                            rejectReason:this.message,
                            adminID:sessionStorage.getItem('token')
                        }
                    }).then(res=>{
                        this.$message({
                            message: "审批完成",
                            type: 'success'
                        });
                        this.dialogVisible2=false;
                    }).catch(err=>{
                        this.$message({
                            message: "审批失败",
                            type: 'danger'
                        });
                    })

                }
            },
            getenter(){
                let data={
                    id:sessionStorage.getItem('token'),
                    status:"4"
                }
                this.$http.post('http://localhost:8006/CA/DA/stuCA',data).then(res=>{
                    console.log('res',res.data.data);
                    this.enter =res.data.data[0]
                    this.application=this.enter
                }).catch(err=>{
                    console.log(err)
                    this.$message({
                        message: "获取失败",
                        type: 'danger'
                    });
                })
                this.getleave()
            },
            getleave(){
                let data={
                    id:sessionStorage.getItem('token'),
                    status:"4"
                }
                this.$http.post('http://localhost:8006/LA/DA/stuLA',data).then(res=>{
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
                    id:sessionStorage.getItem('token'),
                    status:String(this.querrystatus)
                }
                if(this.type === 0){ //入校
                    this.$http.post('http://localhost:8006/CA/DA/stuCA',data).then(res=>{
                        this.application = res.data.data[0];
                    }).catch(err=>{
                        this.$message({
                            message: "查询失败",
                            type: 'danger'
                        });
                    })
                }else{
                    this.$http.post('http://localhost:8006/LA/DA/stuLA',data).then(res=>{
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
    @import "../../src/style/main.css";
    .el-button--text {
        margin-right: 15px;
    }
    .el-select {
        width: 300px;
    }
    .el-input {
        width: 300px;
    }
    .dialog-footer button:first-child {
        margin-right: 10px;
    }
</style>
