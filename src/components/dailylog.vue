<template>
    <div class="demo-collapse">
        <el-form :model="dailyForm"
                 :rules="rules"
                 label-position="right"
                 ref="dailyForm"
                 label-width="110px"
                 size="default">
            <el-collapse v-model="activeNames"  @change="handleChange">
                <el-collapse-item title="基本信息" name="1">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="学号" prop="stuId">
                                <el-input v-model="dailyForm.stuId" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="姓名" prop="stuname">
                                <el-input v-model="dailyForm.stuname" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="班级" prop="class">
                                <el-input v-model="dailyForm.class" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="院系" prop="dept">
                                <el-input v-model="dailyForm.dept" style="width: 200px" disabled></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="日期" prop="date">
                                <el-input v-model="dailyForm.date" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                        <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                            <el-form-item label="时间" prop="time">
                                <el-input v-model="dailyForm.time" style="width: 200px" disabled></el-input>
                            </el-form-item></el-col>
                    </el-row>
                </el-collapse-item>
                <el-collapse-item title="健康信息" name="2">
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="今日体温" prop="bodyTemperature">
                                <el-input v-model="dailyForm.bodyTemperature" style="width: 200px"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12"><div class="grid-content ep-bg-purple" />
                            <el-form-item label="所在城市" prop="city">
                            <el-cascader v-model="dailyForm.city"
                                         :options="city"
                                         :props = "realname"
                                         clearable></el-cascader>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-collapse-item>
            </el-collapse>
            <div class="buttons-1">
                <button class="button" @click.prevent="submit">提交</button>
                <button class="button 2" @click.prevent="save">保存</button>
            </div>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "dailylog",
        props:['allcity'],
        data(){
            return{
                activeNames:['1' ,'2'],
                dailyForm:{
                    stuId:sessionStorage.getItem('token'),
                    date:'',
                    time:'',
                    stuname:sessionStorage.getItem('userName'),
                    class:sessionStorage.getItem('className'),
                    dept:sessionStorage.getItem('deptName'),
                    bodyTemperature:'',
                    city:'',
                },
                city:this.allcity,
                realname: {
                        value:"label",
                        label:"label",
                        children:"children"
                    },
                rules:{
                    city:[
                        { required: true, message: '请选择部门', trigger: 'change' }
                    ],
                    bodyTemperature:[
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
            this.dailyForm.date = nowdate;
            this.dailyForm.time = nowtime;
        },
        methods: {
            handleChange(val) {
                console.log(val);
            },
            submit(){
                console.log('city',this.dailyForm.city)
                let data={
                    stuId: this.dailyForm.stuId,
                    bodyTemperature: this.dailyForm.bodyTemperature,
                    city:this.dailyForm.city[1],
                    district:this.dailyForm.city[2],
                    street:this.dailyForm.city[3]
                }
                if(this.dailyForm.city[1] === '市辖区'){
                    data.city = this.dailyForm.city[0]
                    data.district = this.dailyForm.city[2];
                    data.street = this.dailyForm.city[3];
                }
                console.log('data',data)
                this.$http.post('http://localhost:8006/healthdaily/fillin',data).then(res=>{
                    console.log('res')
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
    h3 {
        margin: 30px 0 0;
    }
    :deep .el-collapse-item__header {
        color:dodgerblue;
        font-size: 12pt;
    }
</style>
