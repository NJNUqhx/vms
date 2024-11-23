<template>
  <div style="display: flex; line-height: 60px;">
    <div style="margin-top:8px;">
      <i :class="icon" style="font-size: 20px; cursor: pointer" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 35px;">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <el-dropdown>
      <span>{{ user.name }}</span>
      <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>

</template>

<script>
export default {
  name: "Header",
  data(){
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props:{
    icon: String
  },
  methods:{
    toUser(){
      console.log("toUser")
      this.$router.push("/Home")
    },
    logOut(){
      console.log("logOut");
      this.$confirm('确定退出登录么？', '提示', {
        confirmButtonText: '确认',  //确认按钮的文字显示
        type: 'warning',
        center: true, //文字居中显示
      })
          .then(() => {
            this.$message({
              type: "success",
              message: "退出登录成功"
            })
            this.$router.push("/")
            sessionStorage.clear()
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消退出登录"
            })
          });


    },
    collapse(){
      this.$emit("doCollapse");
    }
  }
}
</script>

<style scoped>

</style>