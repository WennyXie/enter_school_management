<template>
    <div class="container">
        <h3>个人信息</h3>
        <div class="demo-collapse">
            <el-form :model="userForm"
                     :rules="rules"
                     label-position="right"
                     ref="userForm"
                     label-width="110px"
                     size="default">
                <el-collapse v-model="activeNames"  @change="handleChange">
                    <el-collapse-item title="基本信息" name="1">
                        <el-row>
                            <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                                <el-form-item label="学号" prop="stuId">
                                    <el-input v-model="userForm.stuId" style="width: 200px" disabled></el-input>
                                </el-form-item></el-col>
                            <el-col :span="12"><div class="grid-content ep-bg-purple-light" />
                                <el-form-item label="姓名" prop="stuName">
                                    <el-input v-model="userForm.stuName" style="width: 200px" disabled></el-input>
                                </el-form-item></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="院系" prop="stuDepartName">
                                    <el-input v-model="userForm.stuDepartName" style="width: 200px" disabled></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="班级" prop="stuClassName">
                                    <el-input v-model="userForm.stuClassName" style="width: 200px" disabled></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="辅导员" prop="adminName">
                                    <el-input v-model="userForm.adminName" style="width: 200px" disabled></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-collapse-item>
                    <el-collapse-item title="可维护信息信息" name="2">
                        <el-row>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="手机" prop="stuPhnum">
                                    <el-input v-model="userForm.stuPhnum" style="width: 200px"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="邮箱" prop="stuEmail">
                                    <el-input v-model="userForm.stuEmail" style="width: 200px"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="证件类型" prop="stuIdtype">
                                    <el-select v-model="userForm.stuIdtype" placeholder="选择证件类型">
                                        <template v-for="item in options" :key="item.value">
                                            <el-option :label="item.name" :value="item.value"></el-option>
                                        </template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12"><div class="grid-content ep-bg-purple" />
                                <el-form-item label="证件号码" prop="stuIdnum">
                                    <el-input v-model="userForm.stuIdnum" style="width: 200px"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-collapse-item>
                </el-collapse>
                <div class="buttons-1">
                    <button class="button" @click.prevent="editConfirm">提交</button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "userInfo",
        data(){
            return{
                activeNames:['1','2'],
                userForm:{
                    stuClassId : '',
                    stuClassName:'',
                    stuDepartId : '',
                    stuDepartName:'',
                    adminName:'',
                    adminId:'',
                    stuEmail : '',
                    stuId : '',
                    stuName : '',
                    stuIdnum : '',
                    stuPhnum : '',
                    stuIdtype : '',
                    stuSchoolId: '',
                    hdUpdated: '',
                },
                options:[
                    {name:"身份证",value:1},
                    {name:"台胞证",value: 0}
                ],
                edit_data:{},
            }

        },
        methods:{
            handleChange(val) {
                console.log(val);
            },
            editConfirm() {
                let _this = this;
                let update = {
                    stuClassId : _this.userForm.stuClassId,
                    stuDepartId : _this.userForm.stuDepartId,
                    stuEmail : _this.userForm.stuEmail,
                    stuId : _this.userForm.stuId,
                    stuName : _this.userForm.stuName,
                    stuIdnum : _this.userForm.stuIdnum,
                    stuPhnum : _this.userForm.stuPhnum,
                    stuIdtype : _this.userForm.stuIdtype,
                    stuSchoolId: _this.userForm.stuSchoolId,
                    hdUpdated: _this.userForm.hdUpdated
                }
                console.log(update);
                this.$http.put("http://localhost:8006/UserInfo/update",update)
                    .then((res) => {
                        this.$message({
                            message: '修改成功',
                            type: 'success',
                        });
                    })
                    .catch(err => {
                        console.log(err);
                    })
            },
        },
        created() {
            this.$http.get('http://localhost:8006/UserInfo/student',{
                params:{
                    stuId:sessionStorage.getItem('token')
                }
            }).then(res=>{
                this.userForm=res.data.data[0]
                sessionStorage.setItem('userName',this.userForm.stuName);
                sessionStorage.setItem('adminId',this.userForm.adminId);
                sessionStorage.setItem('adminName',this.userForm.adminName);
                sessionStorage.setItem('classId',this.userForm.stuClassId);
                sessionStorage.setItem('className',this.userForm.stuClassName);
                sessionStorage.setItem('deptId',this.userForm.stuDepartId);
                sessionStorage.setItem('deptName',this.userForm.stuDepartName);
                console.log('res',res);
            }).catch(err=>{
                this.$message({
                    message: '获取数据失败',
                    type: 'warning',
                });
            })
        }
    }
</script>

<style scoped>
@import "../../style/main.css";

</style>
