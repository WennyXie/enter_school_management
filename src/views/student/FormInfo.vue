<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick" :key="activeName">
            <el-tab-pane label="健康日报" name="first">健康日报
                <dailylog :allcity="allcity" /></el-tab-pane>
            <el-tab-pane label="进校申请" name="second">进校申请
                <enterApplication :allcity="allcity"/></el-tab-pane>
            <el-tab-pane label="离校申请" name="third">离校申请
                <leaveApplication :allcity="allcity"/> </el-tab-pane>
        </el-tabs>
    </div>

</template>

<script>
    import dailylog from "@/components/dailylog";
    import enterApplication from "@/components/enterApplication";
    import leaveApplication from "@/components/leaveApplication";
    import {citydata} from "@/city";
    export default {
        name: "PurchaseInfo",
        components: {
            dailylog,
            enterApplication,
            leaveApplication
        },
        data(){
            return {
                activeName: 'first',
                type:'2',
                allcity:citydata()
            }
        },
        methods: {
            handleClick(tab, event) {
                this.type='2';
                this.pcodid='';
                console.log(tab, event);
            },
            entry(pcodid){//入库
                this.activeName='fourth';
                this.type = '0';
                this.pcodid = pcodid;
                console.log('pcodid',this.pcodid)
                console.log('change()调用')
            },
            check(pcodid){//查看
                this.activeName='fourth';
                console.log('check()');
                this.pcodid = pcodid;
                this.type = '1';
                console.log('id:',this.pcodid);
            },
            update(){
                console.log('genxin')
                this.$router.push("/update")
                this.activeName = 'first';
            }
        },
        created() {

        }
    }
</script>

<style scoped>
    @import "../../../src/style/main.css";
</style>


