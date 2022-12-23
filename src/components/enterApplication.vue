<template>
    <div class="demo-collapse">
        <el-form :model="enterForm"
                 :rules="rules"
                 label-position="right"
                 ref="enterForm"
                 label-width="110px"
                 size="default">
            <el-collapse v-model="activeNames"  @change="handleChange">
                <el-collapse-item title="基本信息" name="1">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="日期" prop="date">
                                <el-input v-model="enterForm.date" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="时间" prop="time">
                                <el-input v-model="enterForm.time" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="学号" prop="stuId">
                                <el-input v-model="enterForm.stuId" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="姓名" prop="stuname">
                                <el-input v-model="enterForm.stuname" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="班级" prop="class">
                                <el-input v-model="enterForm.class" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="院系" prop="dept">
                                <el-input v-model="enterForm.dept" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="辅导员" prop="fudaoyuan">
                                <el-input v-model="enterForm.fudaoyuan" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-collapse-item>
                <el-collapse-item title="行程信息" name="2">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="进校原因" prop="reason">
                                <el-input v-model="enterForm.reason" style="width: 200px"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-form-item label="预计进校日期" prop="expdate">
                            <input type="date" v-model="enterForm.expdate" style="width: 200px">
                        </el-form-item>
                    </el-row>
                </el-collapse-item>
                <el-collapse-item title="七日健康信息" name="3">
                    <el-table :data="sevenHDinfo" style="width: 85%" border>
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

                </el-collapse-item>
            </el-collapse>
            <div class="buttons-1">
                <button class="button" @click.prevent="submit">提交</button>
            </div>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "enterApplication",
        props:['allcity'],
        data(){
            return{
                activeNames:['1' ,'2'],
                enterForm:{
                    stuId:sessionStorage.getItem('token'),
                    date:'',
                    time:'',
                    stuname:sessionStorage.getItem('userName'),
                    class:sessionStorage.getItem('className'),
                    fudaoyuan:sessionStorage.getItem('adminName'),
                    fudaoyuanId:sessionStorage.getItem('adminId'),
                    dept:sessionStorage.getItem('deptName'),
                    deptId:sessionStorage.getItem('deptId'),
                    reason:'',
                    expdate:'',
                    bodyTemperature:'',
                    city:'',
                },
                city:this.allcity,
                realname: {
                    value:"label",
                    label:"label",
                    children:"children"
                },
                sevenHDinfo:[],
                rules:{
                    reason:[
                        { required: true, message: '请选择部门', trigger: 'change' }
                    ],
                    expdate:[
                        { required: true, message: '请选择采购员', trigger: 'change' }
                    ],
                }
            }
        },
        created() {
            let _this=this;
            var myDate = new Date();	//创建Date对象
            var Y = myDate.getFullYear();   //获取当前完整年份
            var M = myDate.getMonth() + 1;  //获取当前月份
            var D = myDate.getDate();   //获取当前日1-31
            var H = myDate.getHours();
            var Mf = myDate.getMinutes();
            var S = myDate.getSeconds();
            if(M < 10){
                M = '0' + M;
            }
            // 日不足10补0
            if(D < 10){
                D = '0' + D;
            }

            if(H < 10){
                H = '0' + H;
            }
            if(Mf < 10){
                Mf = '0' + Mf;
            }
            if(S < 10){
                S = '0' + S;
            }
            var nowdate = Y+'-'+M+'-'+D;
            var nowtime = H+':'+Mf+':'+S;
            this.enterForm.date = nowdate;
            this.enterForm.time = nowtime;
            this.getndaysDH();
        },
        methods: {
            getndaysDH(){
                this.$http.get('http://localhost:8006/healthdaily/nDayrecords',{
                    params: {
                            stuId:sessionStorage.getItem('token'),
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
            handleChange(val) {
                console.log(val);
            },
            submit(){
                let data={
                    stuId: this.enterForm.stuId,
                    adminId: this.enterForm.fudaoyuanId,
                    expComindate :this.enterForm.expdate,
                    cominReason: this.enterForm.reason
                }
                console.log('data',data)
                this.$http.post('http://localhost:8006/CA/fillin',data).then(res=>{
                    this.$message({
                        message: res.data.msg,
                        type: 'success'
                    });
                }).catch(err=>{
                    this.$message({
                        message: "提交失败",
                        type: 'danger'
                    });
                })

            },
        }
    }
</script>

<style scoped>
    @import "../../src/style/main.css";
    h3 {
        margin: 30px 0 0;
    }
    :deep .el-collapse-item__header {
        color:dodgerblue;
        font-size: 12pt;
    }
</style>
