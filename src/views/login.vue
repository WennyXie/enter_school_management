<template>
    <div class="container">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
            <h2>登录</h2>
            <el-form-item label="用户名" prop="num">
                <el-input v-model="loginForm.num" type="text" placeholder="请输入学号/工号" style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item label="身份" prop="role">
                <el-select v-model="loginForm.role" placeholder="选择身份">
                    <template v-for="item in option" :key="item.type">
                        <el-option :label="item.typename" :value="item.type"></el-option>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit('loginForm')">登录</el-button>
                <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "inLogin",
        data() {
            return {
                loginForm: {
                    num: '',
                    password: '',
                    role: '',
                },
                option: [
                    {typename:'学生', type:0},
                    {typename:'辅导员', type:1},
                    {typename:'院系管理员', type:2},
                    {typename:'超级管理员', type:3},
                ],
                rules: {
                    num: [
                        {required: true, message: '请输入工号/学号', trigger: 'blur'},
                        //{ min: 8, max: 8, message: '长度为8', trigger: 'blur' }
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    role: [
                        {required: true, message: '请选择身份', trigger: 'change'}
                    ],
                },
            }
        },
        methods:{
            submit(){
                let _this = this;
                let data={
                    id:_this.loginForm.num,
                    userType:_this.loginForm.role
                }
                console.log('data',data)
                _this.$http.post('http://localhost:8006/login/',data).then(res=>{
                    console.log('login',res);
                    sessionStorage.setItem('token',data.id);
                    this.$emit('login',data.userType);
                }).catch(err=>{
                    console.log('login',err);


                })
            },
            resetForm(){

            },
        }
    }
</script>

<style scoped>
    @import "../style/main.css";
</style>
