<template>
    <el-row class="tac">
    <el-col :span="4">
        <el-menu
        @open="handleOpen"
        @close="handleClose"
        background-color="rgba(176,196,222,0.6)"
        text-color="#395080"
        :collapse="isCollapse">
            <SideItem :data="studentmenu" :isCollapse="isCollapse"  v-if="type === 0"/>
            <SideItem :data="supermenu" :isCollapse="isCollapse"  v-if="type === 3"/>
            <SideItem :data="deptmenu" :isCollapse="isCollapse"  v-if="type === 2"/>
            <SideItem :data="fudaoyuanmenu" :isCollapse="isCollapse"  v-if="type === 1"/>
        </el-menu>
    </el-col>
    </el-row>
</template>

<script>
import SideItem from '../components/SideItem.vue'

export default {
    name: 'Menu',
    props:['type'],
  data() {
      return {
          studentmenu: [
              {index:"1",path:"/",name: "Home", label: "首页"},
              {index:"2",path:"/",name:"Home",label:"日常事物",icon:"wuliao",children:
                      [
                          {index:"2-1",path:"/formInfo",name:"formInfo",label:"健康日报"},
                          {index:"2-2",path:"/formInfo",name:"formInfo",label:"离校申请"},
                          {index:"2-3",path:"/formInfo",name:"formInfo",label:"进校申请"},
                      ]
              },
              {index:"3",path:"/",name:"Home",label:"进出校模拟",icon:"wuliao",children:
                      [
                          {index:"3-1",path:"/enter-leave",name:"enter-leave",label:"进出校模拟"},
                      ]
              },
              {index:"4",path:"/",name:"Home",label:"查询",icon:"wuliao",children:
                      [
                          {index:"4-1",path:"/querry",name:"querry",label:"事物进度查询"},
                          {index:"4-2",path:"/querry",name:"querry",label:"入校权限查询查询"},
                          {index:"4-3",path:"/querry",name:"querry",label:"一年内的离校总时长查询"},
                          {index:"4-4",path:"/querry",name:"querry",label:"班级统计数据"},
                      ]
              },
          ],
          deptmenu: [
              {index:"1",path:"/",name: "Home", label: "首页"},
              {index:"2",path:"/",name:"Home",label:"事物处理",icon:"qiye",children:[
                      {index:"2-1",path:"/deptApplication",name:"deptApplication",label:"离校申请"},
                      {index:"2-2",path:"/deptApplication",name:"deptApplication",label:"入校申请"},
                  ]
              },
          ],
          supermenu: [
              {index:"1",path:"/",name: "Home", label: "首页"},
              {index:"2",path:"/",name:"Home",label:"学校信息",icon:"qiye",children:[
                      {index:"2-1",path:"/departmantList",name:"departmantList",label:"院系列表"},
                  ]
              },
              {index:"3",path:"/",name:"Home",label:"学校信息",icon:"qiye",children:[
                      {index:"3-1",path:"/campusList",name:"campusList",label:"校区列表"},
                  ]
              },
              {index:"4",path:"/",name:"Home",label:"查询",icon:"qiye",children:[
                      {index:"4-1",path:"/supperQuerry",name:"supperQuerry",label:"查询"},
                  ]
              },
          ],
          fudaoyuanmenu: [
              {index:"1",path:"/",name: "Home", label: "首页"},
              {index:"2",path:"/",name:"Home",label:"事物处理",icon:"qiye",children:[
                      {index:"2-1",path:"/application",name:"application",label:"入校申请"},
                      {index:"2-1",path:"/application",name:"application",label:"离校申请"},
                  ]
              },
              {index:"3",path:"/",name:"Home",label:"其他数据",icon:"qiye",children:[
                      {index:"3-1",path:"/otherInfomation",name:"otherInfo",label:"其他信息"},
                  ]
              },
          ],
          isCollapse: true,
      }
  },
  components: {
    SideItem
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    getIsCollapse() {
      let currentPath = this.$router.currentRoute.value.fullPath;
      console.log(this.$router,currentPath);
      if(currentPath !== '/') {
        this.isCollapse = true;
      } else {
        this.isCollapse = false;
      }
      console.log(this.isCollapse);
    }
  },
  created() {
    // this.getIsCollapse();
  }
}
</script>

<style>

</style>
