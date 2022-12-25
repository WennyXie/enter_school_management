<template>
    <div class="container">
        <el-table :data="tableData"
                  border
                  style="width: 90%"
                  :row-class-name="tableRowClassName2">
            <el-table-column type="expand" min-width="9%">
                <template #default="props">
                    <h3>班级成员</h3>
                    <el-table :data="props.row.stuList"
                              :row-class-name="tableRowClassName"
                              @row-click="handleTable"
                              border>
                        <el-table-column  min-width="8%" />
                        <el-table-column label="姓名" prop="stuName" min-width="11%" />
                        <el-table-column label="学号" prop="stuId" min-width="10%" />
                        <el-table-column label="健康日报填写情况" prop="hd_updated" min-width="20%" />
                        <el-table-column label="操作" min-width="50%">
                            <template #default="scope">
                                <button class="table_button edit"  @click="open(scope.row)">健康日报</button>
                                <button class="table_button delete" @click="querry2(scope.row,scope.$index)">入校权限</button>
                                <button class="table_button confirm" @click="querry4(scope.row,scope.$index)">离校申请</button>
                                <button class="table_button confirm" @click="querry5(scope.row,scope.$index)">入校申请</button>
                                <button class="table_button confirm" @click="querry3(scope.row,scope.$index)">离校时长</button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-table-column>>
            <el-table-column prop="className" label="班级" min-width="8%"/>
            <el-table-column prop="instructorName" label="辅导员名称" min-width="8%"/>
            <el-table-column prop="instructorId" label="辅导员工号" min-width="8%"/>
            <el-table-column prop="memberNum" label="人数" min-width="8%"/>
        </el-table>
    </div>

    <el-dialog
            title="查询天数"
            v-model="dialogVisible"
            width="500px"
            :before-close="handleClose">
        <el-input v-model="days" style="width: 400px" placeholder="输入查询天数"></el-input>
        <button @click="querry1">查询</button>
    </el-dialog>

    <el-dialog
            title="信息展示"
            v-model="dialogVisible2"
            width="90%"
            :before-close="handleClose2">
        <el-select v-model="querrystatus" placeholder="选择状态" clearable v-if="type===3 || type === 4">
            <template v-for="item in thestatus" :key="item.status">
                <el-option :label="item.name" :value="item.status"></el-option>
            </template>
        </el-select>
        <el-button @click="querry" v-if="type===3 || type === 4">查询</el-button>
        <el-table class="wuliao_class" style="width:90%;"
                  :data="querryInfo" center
                  align="left" border
                  :key="type"
                  :row-class-name="tableRowClassName">
            <el-table-column label="学号" prop="stuId" v-if="type === 3 || type === 4"></el-table-column>
            <el-table-column label="填写日期" prop="myDate" v-if="type === 3 || type === 4"/>
            <el-table-column label="预计返校时间" prop="expComindate" v-if="type === 4"/>
            <el-table-column label="预计离校时间" prop="expLeavdate" v-if="type === 3"/>
            <el-table-column label="预计返校时间" prop="expRetdate" v-if="type === 3"/>
            <el-table-column label="城市" prop="destCity" v-if="type === 3"/>
            <el-table-column label="区" prop="destDistrict" v-if="type === 3"/>
            <el-table-column label="街道" prop="destStreet" v-if="type === 3"/>
            <el-table-column label="处理状态" prop="appStatus" v-if="type === 3 || type === 4">
                <template #default = "scope">
                    <el-tag size="large" v-if = "scope.row.appStatus === 0" type="danger">
                        被拒绝</el-tag>
                    <el-tag size="large" v-else-if="scope.row.appStatus === 1" type="warning">
                        未处理</el-tag>
                    <el-tag size="large" v-else-if="scope.row.appStatus === 2" type="success">
                        辅导员通过</el-tag>
                    <el-tag size="large" v-else-if="scope.row.appStatus === 3" type="success">
                        已通过</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="totaltime" label="过去一年的离校总时长" v-if="type === 1">
            </el-table-column>
            <el-table-column prop="campus" label="校区" v-if="type === 2"/>
            <el-table-column prop="per_status" label="权限"  v-if="type === 2"/>
            <el-table-column prop="date" label="日期" v-if="type === 0"/>
            <el-table-column prop="bodyTemperature" label="体温" v-if="type === 0"/>
            <el-table-column prop="city" label="城市" v-if="type === 0"/>
            <el-table-column prop="district" label="区" v-if="type === 0"/>
            <el-table-column prop="street" label="街道" v-if="type === 0"/>
            <el-table-column prop="hdStatus" label="状态" v-if="type === 0">
                <template #default = "scope">
                    <el-tag size="large" v-if = "scope.row.hdStatus === 0" type="danger">
                        异常</el-tag>
                    <el-tag size="large" v-else-if="scope.row.hdStatus === 1" type="success">
                        正常</el-tag>
                </template>
            </el-table-column>
        </el-table>
    </el-dialog>
</template>

<script>
    export default {
        name: "classList",
        data(){
            return {
                dialogVisible2:false,
                dialogVisible:false,
                days:'',
                type:0,
                tableData:[],
                querrystatus:'',
                querryInfo:[],
                stuId:'',
                thestatus:[
                    {status:0 , name:"被拒绝"},
                    {status:1 , name:"未处理"},
                    {status:2 , name:"辅导员同意"},
                    {status:3 , name:"已通过"},
                ],
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
            handleClose(){
                this.dialogVisible = false;
            },
            handleClose2(){
                this.dialogVisible2 = false;
            },
            tableRowClassName({row,rowIndex}) {
                row.index = rowIndex+1;
            },
            tableRowClassName2({row,rowIndex}) {
                row.index = rowIndex+1;
            },
            querry(){
                let data={
                    id:this.stuId,
                    status:String(this.querrystatus)
                }
                console.log(data);
                if(this.type === 4){ //入校
                    this.$http.post('http://localhost:8006/CA/stuCA',data).then(res=>{
                        console.log(res.data)
                        this.querryInfo = res.data.data[0];
                    }).catch(err=>{
                        this.$message({
                            message: "查询失败",
                            type: 'danger'
                        });
                    })
                }else{
                    this.$http.post('http://localhost:8006/LA/stuLA',data).then(res=>{
                        this.querryInfo = res.data.data[0];
                    }).catch(err=>{
                        this.$message({
                            message: "查询失败",
                            type: 'danger'
                        });
                    })
                }

            },
            open(row){
                this.dialogVisible = true;
                this.stuId = row.stuId;
            },
            querry1(row, index){
                this.dialogVisible=false
                this.dialogVisible2 = true
                this.querryInfo=[];
                this.type = 0;
                this.$http.get('http://localhost:8006/healthdaily/nDayrecords',{
                    params: {
                        stuId:this.stuId,
                        days:this.days
                    },
                }).then(res=>{
                    this.querryInfo = res.data.data[0]
                    console.log('seven',this.sevenHDinfo)
                    console.log('enterres',res);
                }).catch(err=>{
                    console.log('err',err);
                })

            },
            querry2(row, index){
                this.querryInfo=[];
                this.dialogVisible2 = true
                this.type = 2;
                console.log(row)
                this.$http.get('http://localhost:8006/perm/stu/getperm',{
                    params:{
                        stuId:row.stuId
                    }
                }).then(res=>{
                    //this.permission =res.data.data[0];
                    let data={
                        campus:'',
                        per_status:''
                    }
                    for(var i=0;i<res.data.data[0].length;i++){
                        data={};
                        if(res.data.data[0][i].campusId === 1){
                            data.campus = "邯郸校区"
                        }
                        if(res.data.data[0][i].campusId === 2){
                            data.campus = "枫林校区"
                        }
                        if(res.data.data[0][i].campusId === 3){
                            data.campus = "江湾校区"
                        }
                        if(res.data.data[0][i].campusId === 4){
                            data.campus = "张江校区"
                        }
                        if(res.data.data[0][i].permitStatus === 0){
                            data.per_status = "无权限"
                        }
                        if(res.data.data[0][i].permitStatus === 1){
                            data.per_status = "有权限"
                        }
                        this.querryInfo.push(data);
                    }
                })
            },
            querry3(row, index){
                this.type =1;
                this.dialogVisible2 = true
                this.querryInfo=[];
                this.$http.get('http://localhost:8006/outside/getLastYearTotalOT',{
                    params:{
                        stuId:row.stuId
                    }
                }).then(res=>{
                    let data = {
                        totaltime:''
                    }
                    data.totaltime = res.data.data[0]
                    this.querryInfo.push(data)
                    console.log('total',this.totaltime)
                })
            },
            querry4(row, index){
                this.type =3;
                this.stuId=row.stuId
                this.dialogVisible2 = true
                this.querryInfo=[];
                this.$http.get('http://localhost:8006/LA/myLA',{
                    params:{
                        stuId:row.stuId
                    }
                }).then(res=>{
                    this.querryInfo = res.data.data[0]
                })
            },
            querry5(row, index){
                this.type =4;
                this.stuId=row.stuId
                this.dialogVisible2 = true
                this.querryInfo=[];
                this.$http.get('http://localhost:8006/CA/myCA',{
                    params:{
                        stuId:row.stuId
                    }
                }).then(res=>{
                    this.querryInfo = res.data.data[0]
                })
            },
            async getdata(){
                let _this = this;
                await _this.$http.get('http://localhost:8006/UserInfo/allClass',{
                    params:{
                        adminId:sessionStorage.getItem('token')
                    }
                }).then(res=>{
                    let data = {
                        className:'',
                        instructorName:'',
                        instructorId:'',
                        memberNum:'',
                        stuList:[]
                    };
                    var List=[];
                    var binaling=[];
                    let student={};
                    for(var i=0;i<res.data.data[0].length;i++){
                        data={};
                        data.className = res.data.data[0][i].className;
                        data.instructorName = res.data.data[1][i].adminName;
                        data.instructorId = res.data.data[1][i].adminId;
                        console.log(data)
                        data.memberNum = res.data.data[2][i];
                        List = res.data.data[3][i];
                        for(var j=0;j<List.length;j++){
                            student={};
                            student.stuName = List[j].stuName;
                            student.stuId = List[j].stuId;
                            if(List[j].hdUpdated === 1){
                                student.hd_updated = "今日已填写"
                            }else{
                                student.hd_updated = "今日未填写"
                            }
                            binaling.push(student);
                        }
                        data.stuList=binaling
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
