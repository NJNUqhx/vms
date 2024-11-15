<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 100%;margin-left: -1px;">
      <Aside></Aside>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px;height: 100%;border-bottom: rgba(168,168,168,0.3) 1px solid;">
        <Header></Header>
      </el-header>

      <el-main style="height: 100%">
        <MainBody></MainBody>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import MainBody from "@/components/MainBody";
import Header from "@/components/Header";

export default {
  name: "Index",
  components:{
    Aside,
    MainBody,
    Header
  }

}
</script>

<style scoped>
  .el-header {
    /*background-color: #B3C0D1;*/
    color: #333;
    line-height: 60px;
  }


  .el-aside {
    color: #333;
  }
</style>