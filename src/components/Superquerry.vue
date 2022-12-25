<template>
    <div>
        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>过去n天尚未批准的入校申请和出校申请</div>
                    <el-input v-model="nDays1" style="width: 300px;margin: 0 0 0 10px"></el-input>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getndaysLAandCA">查询</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>已经出校但尚未返回学校的学生</div>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getleaveButnotcom">查询</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>未提交出校申请但离校已超过24h的学生</div>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getleaveButnotsubmit">查询</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>已提交出校申请但未离校的学生</div>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getsubmitButnotleave">查询</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>连续n天提交的健康日报时间完全一致的学生</div>
                    <el-input v-model="nDays2" style="width: 300px;margin: 0 0 0 10px"></el-input>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getsameHD">查询</el-button>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="16"><div class="grid-content ep-bg-purple" />
                <div class="input">
                    <div>过去n天每个院系学生产生最多出入记录的校区</div>
                    <el-input v-model="nDays3" style="width: 300px;margin: 0 0 0 10px;height: 30px"></el-input>
                </div>
            </el-col>
            <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
                <el-button style="max-height: 50px" type="primary" @click="getmostoutCampus">查询</el-button>
            </el-col>
        </el-row>
    </div>

    <el-dialog
            title="查询结果"
            v-model="LAandCA"
            width="1000px"
            :before-close="handleClose">
        <div>
        <p>尚未批准的入校申请有{{enteramount}}</p>
        <p>尚未批准的离校申请有{{leaveamount}}</p>
        </div>
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
                <el-table class="wuliao_class" style="width:90%;"
                          :data="application" center
                          align="left" border
                          :row-class-name="tableRowClassName"
                          max-height="400px">
                    <el-table-column label="学号" prop="stuId"></el-table-column>
                    <el-table-column label="填写日期" prop="myDate"/>
                    <el-table-column label="预计返校时间" prop="expComindate" v-if="type === 0"/>
                    <el-table-column label="预计离校时间" prop="expLeavdate" v-if="type === 1"/>
                    <el-table-column label="预计返校时间" prop="expRetdate" v-if="type === 1"/>
                    <el-table-column label="城市" prop="destCity" v-if="type === 1"/>
                    <el-table-column label="区" prop="destDistrict" v-if="type === 1"/>
                    <el-table-column label="街道" prop="destStreet" v-if="type === 1"/>
                </el-table>
            </el-col>
        </el-row>
    </el-dialog>

    <el-dialog
            title="查询结果"
            v-model="forstudent"
            width="1000px"
            :before-close="handleClose2">
        <p>查询到的学生数量是{{studentamount}}</p>
        <el-table :data="studentList" style="width: 100%" border max-height="500px">
            <el-table-column prop="stuId" label="学号" />
            <el-table-column prop="stuName" label="姓名"/>
            <el-table-column prop="stuEmail" label="邮箱" />
            <el-table-column prop="stuPhnum" label="电话"></el-table-column>
            <el-table-column prop="stuIdnum" label="证件号"></el-table-column>
            <el-table-column prop="leaveTime" label="离校时间" v-if="type === 2"/>
        </el-table>
    </el-dialog>


    <el-dialog
            title="查询结果"
            v-model="fordeptandCampus"
            width="1000px"
            :before-close="handleClose3">
            <el-row>
                <el-col :span="4" >
                    <el-table style="width:95%;"
                              :data="departList"
                              center
                              align="left"
                              highlight-current-row
                              :row-class-name="groupRowClassName"
                              @current-change="handleCurrentChange2"
                              border>
                        <el-table-column label="院系" prop="deptName" min-width="42%"></el-table-column>
                    </el-table>

                </el-col>
                <el-col :span="20">
                    <el-table class="wuliao_class" style="width:90%;"
                              :data="campusList" center
                              align="left" border
                              :row-class-name="tableRowClassName"
                              max-height="400px">
                        <el-table-column label="校区名称" prop="campusName"></el-table-column>
                        <el-table-column label="状态" prop="campusStatus">
                            <template #default = "scope">
                                <el-tag size="large" v-if = "scope.row.campusStatus === 0" type="danger">
                                    封控中</el-tag>
                                <el-tag size="large" v-else-if="scope.row.campusStatus === 1" type="success">
                                    正常</el-tag>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
    </el-dialog>


</template>


<script>
    export default {
        name: "SuperQuerry",
        data(){
            return{
                LAandCA:false,
                forstudent:false,
                fordeptandCampus:false,
                nDays1:'',
                nDays2:'',
                nDays3:'',
                application:[],
                studentList:[],
                departList:[],
                campusList:[],
                leaveamount:'',
                enteramount:'',
                studentamount:'',
                leave:[],
                enter:[],
                type:0,
                group: [
                    {
                        groupname:'入校申请'
                    },
                    {
                        groupname:'离校申请'
                    }

                ],
                querrystatus:'',
            }

        },
        methods:{
            handleClose(){
                this.LAandCA=false
            },
            handleClose2(){
                this.forstudent=false
            },
            handleClose3(){
                this.fordeptandCampus=false
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
            handleCurrentChange2(val) {
                this.campusList = val.campusList;
                console.log(val);
            },
            getndaysLAandCA(){
                if(parseInt(this.nDays1) === 0 || this.nDays1 === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }
                else{
                    this.$http.get("http://localhost:8006/LA/lastndayUncheck",{
                        params:{
                            days:parseInt(this.nDays1)
                        }
                    }).then(res=>{
                        this.leave = res.data.data[1]
                        this.leaveamount = res.data.data[0]
                        this.getenter()
                    }).catch(err=>{
                        this.$message.error("查询失败")
                    })
                }
            },
            getenter(){
                this.$http.get("http://localhost:8006/CA/lastndayUncheck",{
                    params:{
                        days:parseInt(this.nDays1)
                    }
                }).then(res=>{
                    this.enter = res.data.data[1]
                    this.application=this.enter;
                    this.enteramount = res.data.data[0]
                    this.LAandCA = true
                }).catch(err=>{
                    this.$message.error("查询失败")
                })
            },
            getleaveButnotcom(){
                this.type =2;
                this.$http.get("http://localhost:8006/log/alloutschoolstudents").then(res=>{
                    let data = {
                        stuId: '',
                        stuName: '',
                        stuEmail: '',
                        stuPhnum: '',
                        stuIdnum: '',
                        leaveTime: ''

                    }
                    this.studentamount = res.data.data[0]
                    var List = [];
                    console.log(res.data.data[1])
                    for (var i = 0; i < res.data.data[1].length; i++) {
                        data = {};
                        data.stuId = res.data.data[1][i].student.stuId;
                        data.stuName = res.data.data[1][i].student.stuName;
                        data.stuEmail = res.data.data[1][i].student.stuEmail;
                        data.stuPhnum = res.data.data[1][i].student.stuPhnum;
                        data.stuIdnum = res.data.data[1][i].student.stuIdnum;
                        if(res.data.data[1][i].log == null){
                            data.leaveTime="该生尚未入校"
                        }else{
                            data.leaveTime = res.data.data[1][i].log.logDate+' ';
                            data.leaveTime += res.data.data[1][i].log.logTime;
                        }
                        List.push(data)
                    }
                    console.log("list",List)
                    this.studentList = List
                    this.forstudent = true;
                }).catch(err=>{
                    console.log(err)
                    this.$message.error("查询失败")
                })

            },
            getleaveButnotsubmit(){
                this.type = 0;
                this.$http.get("http://localhost:8006/log/outschoolover24h").then(res=>{
                    let data = {
                        stuId: '',
                        stuName: '',
                        stuEmail: '',
                        stuPhnum: '',
                        stuIdnum: '',
                        leaveTime: ''

                    }
                    this.studentamount = res.data.data[0]
                    var List = [];
                    console.log("res=====",res.data.data[1])
                    for (var i = 0; i < res.data.data[1].length; i++) {
                        data = {};
                        data.stuId = res.data.data[1][i].stuId;
                        data.stuName = res.data.data[1][i].stuName;
                        data.stuEmail = res.data.data[1][i].stuEmail;
                        data.stuPhnum = res.data.data[1][i].stuPhnum;
                        data.stuIdnum = res.data.data[1][i].stuIdnum;
                        List.push(data)
                    }
                    console.log("list",List)
                    this.studentList = List
                    this.forstudent = true;
                }).catch(err=>{
                    console.log(err)
                    this.$message.error("查询失败")
                })

            },
            getsubmitButnotleave(){
                this.type=0;
                this.$http.get("http://localhost:8006/LA/submitButnotout").then(res=>{
                    let data = {
                        stuId: '',
                        stuName: '',
                        stuEmail: '',
                        stuPhnum: '',
                        stuIdnum: '',
                        leaveTime: ''
                    }
                    this.studentamount = res.data.data[0]
                    var List = [];
                    console.log(res.data.data[1])
                    for (var i = 0; i < res.data.data[1].length; i++) {
                        data = {};
                        data.stuId = res.data.data[1][i].stuId;
                        data.stuName = res.data.data[1][i].stuName;
                        data.stuEmail = res.data.data[1][i].stuEmail;
                        data.stuPhnum = res.data.data[1][i].stuPhnum;
                        data.stuIdnum = res.data.data[1][i].stuIdnum;
                        List.push(data)
                    }
                    console.log("list",List)
                    this.studentList = List
                    this.forstudent = true;
                }).catch(err=>{
                    console.log(err)
                    this.$message.error("查询失败")
                })
            },
            getsameHD(){
                this.type=0;
                if(parseInt(this.nDays2) === 0 || this.nDays2 === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }
                else{
                    this.$http.get("http://localhost:8006/healthdaily/SA/nDaysame",{
                        params:{
                            days:parseInt(this.nDays2)
                        }
                    }).then(res=>{
                        let data = {
                            stuId: '',
                            stuName: '',
                            stuEmail: '',
                            stuPhnum: '',
                            stuIdnum: '',
                            leaveTime: ''
                        }
                        this.studentamount = res.data.data[0]
                        var List = [];
                        console.log(res.data.data[1])
                        for (var i = 0; i < res.data.data[1].length; i++) {
                            data = {};
                            data.stuId = res.data.data[1][i].stuId;
                            data.stuName = res.data.data[1][i].stuName;
                            data.stuEmail = res.data.data[1][i].stuEmail;
                            data.stuPhnum = res.data.data[1][i].stuPhnum;
                            data.stuIdnum = res.data.data[1][i].stuIdnum;
                            List.push(data)
                        }
                        console.log("list",List)
                        this.studentList = List
                        this.forstudent = true;
                    }).catch(err=>{
                        console.log(err)
                        this.$message.error("查询失败")
                    })
                }
            },
            getmostoutCampus(){
                this.campusList=[]
                this.departList=[]
                if(parseInt(this.nDays3) === 0 || this.nDays3 === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }else{
                    this.$http.get("http://localhost:8006/log/mostoutCampus",{
                        params:{
                            n:parseInt(this.nDays3)
                        }
                    }).then(res=>{
                        console.log('res------------',res.data.data[0])
                        this.departList = res.data.data[0];
                        this.campusList = res.data.data[0][0].campusList
                        this.fordeptandCampus=true
                    }).catch(err=>{
                        this.$message.error("查询失败")
                    })
                }

            }
        }
    }
</script>

<style scoped>

</style>
