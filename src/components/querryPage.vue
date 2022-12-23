<template>
    <div>
    <el-row>
        <el-col :span="16"><div class="grid-content ep-bg-purple" />
            <div class="input">
            <div>前n个提交入校申请数最多的学生</div>
                <el-input v-model="nLA" style="width: 300px;margin: 0 0 0 10px"></el-input>
            </div>
        </el-col>
        <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
            <el-button style="max-height: 50px" type="primary" @click="getnmostLAstudents">查询</el-button>
        </el-col>
    </el-row>

    <el-row>
        <el-col :span="16"><div class="grid-content ep-bg-purple" />
            <div class="input">
                <div>前n个平均离校时间最长的学生</div>
                <el-input v-model="nOT" style="width: 300px;margin: 0 0 0 10px"></el-input>
            </div>
        </el-col>
        <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
            <el-button style="max-height: 50px" type="primary" @click="getnlongestOTstudents">查询</el-button>
        </el-col>
    </el-row>

    <el-row>
        <el-col :span="16"><div class="grid-content ep-bg-purple" />
            <div class="input">
                <div>过去n天未曾出校的学生</div>
                <el-input v-model="nDays" style="width: 300px;margin: 0 0 0 10px"></el-input>
            </div>
        </el-col>
        <el-col :span="8"><div class="grid-content ep-bg-purple-light" />
            <el-button style="max-height: 50px" type="primary" @click="getnDaysnotoutstudents">查询</el-button>
        </el-col>
    </el-row>
    </div>

    <el-dialog
            title="查询结果"
            v-model="dialogVisible"
            width="700px"
            :before-close="handleClose">
        <el-table :data="tabledata" style="width: 100%" border max-height="500px">
            <el-table-column prop="stuId" label="学号" />
            <el-table-column prop="stuName" label="姓名"/>
            <el-table-column prop="stuEmail" label="邮箱" />
            <el-table-column prop="stuPhnum" label="电话"></el-table-column>
            <el-table-column prop="stuIdnum" label="证件号"></el-table-column>
            <el-table-column prop="amount" label="提交数量" v-if="type === 0"/>
            <el-table-column prop="totalTime" label="离校时间" v-if="type === 1"/>
        </el-table>
    </el-dialog>


</template>

<script>
    export default {
        name: "qurryPage",
        props:['role'],
        data(){
            return{
                type:0,
                nLA:'',
                nOT:'',
                nDays:'',
                tabledata:'',
                dialogVisible:false
            }
        },
        methods:{
            handleClose(){
                this.dialogVisible =false
            },
            getnmostLAstudents(){
                this.type=0;
                console.log('role',this.role);
                if(parseInt(this.nLA) === 0 || this.nLA === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }
                else {
                    if (this.role === 'admin') {
                        this.$http.get('http://localhost:8006/CA/class/nmostSubmit', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nLA)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                amount: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.amount = res.data.data[0][i];
                                data.stuId = res.data.data[1][i].stuId;
                                data.stuName = res.data.data[1][i].stuName;
                                data.stuEmail = res.data.data[1][i].stuEmail;
                                data.stuPhnum = res.data.data[1][i].stuPhnum;
                                data.stuIdnum = res.data.data[1][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if (this.role === "dept") {
                        this.$http.get('http://localhost:8006/CA/dept/nmostSubmit', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nLA)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                amount: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.amount = res.data.data[0][i];
                                data.stuId = res.data.data[1][i].stuId;
                                data.stuName = res.data.data[1][i].stuName;
                                data.stuEmail = res.data.data[1][i].stuEmail;
                                data.stuPhnum = res.data.data[1][i].stuPhnum;
                                data.stuIdnum = res.data.data[1][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if(this.role === 'super'){
                        this.$http.get('http://localhost:8006/CA/school/nmostSubmit', {
                            params: {
                                n: parseInt(this.nLA)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                amount: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.amount = res.data.data[0][i];
                                data.stuId = res.data.data[1][i].stuId;
                                data.stuName = res.data.data[1][i].stuName;
                                data.stuEmail = res.data.data[1][i].stuEmail;
                                data.stuPhnum = res.data.data[1][i].stuPhnum;
                                data.stuIdnum = res.data.data[1][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                }
            },
            getnlongestOTstudents(){
                this.type = 1;
                if(parseInt(this.nOT) === 0 || this.nOT === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }
                else {
                    if (this.role === "admin") {
                        this.$http.get('http://localhost:8006/outside/class/longestOuttime', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nOT)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                totalTime: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].student.stuId;
                                data.stuName = res.data.data[0][i].student.stuName;
                                data.stuEmail = res.data.data[0][i].student.stuEmail;
                                data.stuPhnum = res.data.data[0][i].student.stuPhnum;
                                data.stuIdnum = res.data.data[0][i].student.stuIdnum;
                                data.totalTime = res.data.data[0][i].totalOT;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if (this.role === "dept") {
                        this.$http.get('http://localhost:8006/outside/dept/longestOuttime', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nOT)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                totalTime: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].student.stuId;
                                data.stuName = res.data.data[0][i].student.stuName;
                                data.stuEmail = res.data.data[0][i].student.stuEmail;
                                data.stuPhnum = res.data.data[0][i].student.stuPhnum;
                                data.stuIdnum = res.data.data[0][i].student.stuIdnum;
                                data.totalTime = res.data.data[0][i].totalOT;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if(this.role === 'super'){
                        this.$http.get('http://localhost:8006/outside/school/longestOuttime', {
                            params: {
                                n: parseInt(this.nOT)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                                totalTime: ''
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].student.stuId;
                                data.stuName = res.data.data[0][i].student.stuName;
                                data.stuEmail = res.data.data[0][i].student.stuEmail;
                                data.stuPhnum = res.data.data[0][i].student.stuPhnum;
                                data.stuIdnum = res.data.data[0][i].student.stuIdnum;
                                data.totalTime = res.data.data[0][i].totalOT;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                }

            },
            getnDaysnotoutstudents(){
                this.type = 2;
                if(parseInt(this.nDays) === 0 || this.nDays === ''){
                    this.$message({
                        message:"请输入合法数据",
                        type:'warning'
                    })
                }
                else {
                    if (this.role === 'admin') {
                        this.$http.get('http://localhost:8006/log/class/ndaysnotOut', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nDays)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].stuId;
                                data.stuName = res.data.data[0][i].stuName;
                                data.stuEmail = res.data.data[0][i].stuEmail;
                                data.stuPhnum = res.data.data[0][i].stuPhnum;
                                data.stuIdnum = res.data.data[0][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if(this.role === "dept"){
                        this.$http.get('http://localhost:8006/log/dept/ndaysnotOut', {
                            params: {
                                adminId: sessionStorage.getItem('token'),
                                n: parseInt(this.nDays)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].stuId;
                                data.stuName = res.data.data[0][i].stuName;
                                data.stuEmail = res.data.data[0][i].stuEmail;
                                data.stuPhnum = res.data.data[0][i].stuPhnum;
                                data.stuIdnum = res.data.data[0][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                    if(this.role === 'super'){
                        this.$http.get('http://localhost:8006/log/school/ndaysnotOut', {
                            params: {
                                n: parseInt(this.nDays)
                            }
                        }).then(res => {
                            let data = {
                                stuId: '',
                                stuName: '',
                                stuEmail: '',
                                stuPhnum: '',
                                stuIdnum: '',
                            }
                            var List = [];
                            for (var i = 0; i < res.data.data[0].length; i++) {
                                data = {};
                                data.stuId = res.data.data[0][i].stuId;
                                data.stuName = res.data.data[0][i].stuName;
                                data.stuEmail = res.data.data[0][i].stuEmail;
                                data.stuPhnum = res.data.data[0][i].stuPhnum;
                                data.stuIdnum = res.data.data[0][i].stuIdnum;
                                List.push(data)
                            }
                            this.tabledata = List
                            this.dialogVisible = true;
                        }).catch(
                        )
                    }
                }
            },
        }


    }
</script>

<style scoped>
    @import "../../src/style/main.css";
    .demo-tabs > .el-tabs__content {
        padding: 32px;
        color: #6b778c;
        font-size: 32px;
        font-weight: 600;
    }
    .el-tabs--right .el-tabs__content,
    .el-tabs--left .el-tabs__content {
        height: 100%;
    }

    .el-row {
        margin-bottom: 20px;
    }
    .el-row:last-child {
        margin-bottom: 0;
    }
    .el-col {
        border-radius: 4px;
    }

    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
</style>
