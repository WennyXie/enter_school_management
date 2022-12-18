<template>
  <div>
    <template v-for="item in data">
      <el-menu-item v-if="!item.children" @click="clickMenu(item)" :key="item.index">
        <svg-icon icon-class="home" @click="clickHome" v-if="isCollapse"></svg-icon>
        <template #title>
          <div class="menuText">{{item.label}}</div>
        </template>
      </el-menu-item>
        <el-sub-menu :index="item.index" :key="item.index" v-if="item.children!== undefined">
            <template #title>
                <svg-icon :icon-class="item.icon" @click="clickHome" v-if="isCollapse"></svg-icon>
                <div v-if="!isCollapse" class="menuText">{{item.label}}</div>
            </template>
            <item :data="item.children" @toMenu="clickMenu"></item>
        </el-sub-menu>
    </template>
  </div>
</template>

<script>
export default {
  name: "Item",
  props:["data","isCollapse"],
  methods:{
    clickMenu(item) {
        console.log(item);
        this.$router.push({path:item.path,query:{label:item.label}});
    },
    clickHome() {
      this.$router.push('/');
    }
  },
  data() {
    return {
    }
  },
  created() {
    console.log(this.isCollapse);
    console.log('data',this.data)
  }
}
</script>
