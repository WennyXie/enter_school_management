<template>
    <div class="demo-collapse">
        <el-form :model="leaveForm"
                 :rules="rules"
                 label-position="right"
                 ref="leaverForm"
                 label-width="110px"
                 size="default">
            <el-collapse v-model="activeNames"  @change="handleChange">
                <el-collapse-item title="基本信息" name="1">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="日期" prop="date">
                                <el-input v-model="leaveForm.date" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="时间" prop="time">
                                <el-input v-model="leaveForm.time" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="学号" prop="stuId">
                                <el-input v-model="leaveForm.stuId" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="姓名" prop="stuname">
                                <el-input v-model="leaveForm.stuname" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="班级" prop="class">
                                <el-input v-model="leaveForm.class" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="院系" prop="dept">
                                <el-input v-model="leaveForm.dept" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="辅导员" prop="fudaoyuan">
                                <el-input v-model="leaveForm.fudaoyuan" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-collapse-item>
                <el-collapse-item title="行程信息" name="2">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="离校原因" prop="reason">
                                <el-input v-model="leaveForm.reason" style="width: 100%"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="目的地" prop="city">
                                <el-cascader v-model="leaveForm.city"
                                             :options="city"
                                             :props = "realname"
                                             clearable></el-cascader>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                        <el-form-item label="预计离校日期" prop="expleavdate">
                            <input type="date" v-model="leaveForm.expleavdate" style="width: 200px">
                        </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="预计进校日期" prop="expcomindate">
                                <input type="date" v-model="leaveForm.expcomindate" style="width: 200px">
                            </el-form-item>
                        </el-col>
                    </el-row>
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
                leaveForm:{
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
                    expcomindate:'',
                    expleavdate:'',
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
                    city:[
                        { required: true, message: '请选择目的地', trigger: 'change' }
                    ],
                    reason:[
                        { required: true, message: '请填写原因', trigger: 'change' }
                    ],
                    expleavdate:[
                        { required: true, message: '请选择离校时间', trigger: 'change' }
                    ],
                    expcomindate: [
                        { required: true, message: '请选择返校时间', trigger: 'change' }
                    ]
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
            this.leaveForm.date = nowdate;
            this.leaveForm.time = nowtime;
            //this.getndaysDH();
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
                console.log('city',this.leaveForm.city)
                let data={
                    stuId: this.leaveForm.stuId,
                    adminId: this.leaveForm.fudaoyuanId,
                    leavReason:this.leaveForm.reason,
                    destCity:this.leaveForm.city[1],
                    destDistrict:this.leaveForm.city[2],
                    destStreet:this.leaveForm.city[3],
                    expLeavdate:this.leaveForm.expleavdate,
                    expRetdate:this.leaveForm.expcomindate,
                }
                if(this.leaveForm.city[1] === '市辖区'){
                    data.destCity = this.leaveForm.city[0]
                    data.destDistrict = this.leaveForm.city[2];
                    data.destStreet = this.leaveForm.city[3];
                }
                console.log('data',data)
                this.$http.post('http://localhost:8006/LA/fillin',data).then(res=>{
                    this.$message({
                        message: res.data.msg,
                        type: 'success'
                    });
                }).catch(err=>{
                    this.$message({
                        message: "提交失败",
                        type: 'success'
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
