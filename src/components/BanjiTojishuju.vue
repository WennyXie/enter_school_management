<template>
    <p>班级人数:{{totalNumber}}</p>
    <el-table :data="permission" style="width: 100%" border>
        <el-table-column prop="campus" label="校区" />
        <el-table-column prop="number" label="有权限人数"/>
    </el-table>
</template>

<script>
    export default {
        name: "stuPermission",
        data(){
            return{
                totalNumber:'',
                permission:[]
            }
        },
        created() {
            this.$http.get('http://localhost:8006/perm/stu/getstatistic',{
                params:{
                    stuId:sessionStorage.getItem('token')
                }
            }).then(res=>{
                //this.permission =res.data.data[0];
                let data={
                    campus:'',
                    number:''
                }
                this.totalNumber = res.data.data[0];
                for(var i=0;i<res.data.data[1].length;i++){
                    data={};
                    if(i === 0){
                        data.campus = "邯郸校区"
                        data.number = res.data.data[1][i]
                    }
                    if(i === 1){
                        data.campus = "枫林校区"
                        data.number = res.data.data[1][i]
                    }
                    if(i === 2){
                        data.campus = "江湾校区"
                        data.number = res.data.data[1][i]
                    }
                    if(i === 3){
                        data.campus = "张江校区"
                        data.number = res.data.data[1][i]
                    }
                    this.permission.push(data);
                }
            })
        }
    }
</script>

<style scoped>

</style>
