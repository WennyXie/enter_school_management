<template>
    <el-table :data="permission" style="width: 100%" border>
        <el-table-column prop="campus" label="校区" />
        <el-table-column prop="per_status" label="权限"/>
    </el-table>
</template>

<script>
    export default {
        name: "stuPermission",
        data(){
            return{
                permission:[]
            }
        },
        created() {
            this.$http.get('http://localhost:8006/perm/getperm',{
                params:{
                    stuId:sessionStorage.getItem('token')
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
                    this.permission.push(data);
                }
            })
        }
    }
</script>

<style scoped>

</style>
