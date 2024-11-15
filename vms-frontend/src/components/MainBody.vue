<template>
  <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555555'} " border>
    <el-table-column prop="id" label="ID" width="40">
    </el-table-column>
    <el-table-column prop="no" label="账号" width="180">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="180">
    </el-table-column>
    <el-table-column prop="age" label="年龄" width="80">
    </el-table-column>
    <el-table-column prop="sex" label="性别" width="80">
      <template slot-scope="scope">
        <el-tag
          :type="scope.row.sex === 1 ? 'primary' : 'success' "
          disable-transitions>{{scope.row.sex === 1 ? '男' : '女'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="roleId" label="角色" width="120">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success') "
            disable-transitions>{{scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户')}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="电话" width="180">
    </el-table-column>
    <el-table-column prop="operate" label="操作">
      <el-button size="small" type="success">编辑</el-button>
      <el-button size="small" type="danger">删除</el-button>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "MainBody",
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: Array(20).fill(item),
    }
  },
  methods: {
    loadGet(){
      this.$axios.get(this.$httpUrl + '/user/list').then(res=>res.data).then(res=>{
        console.log(res);
        this.tableData = res;
      })
    },
    loadPost(){
      this.$axios.post(this.$httpUrl + '/user/listP', {}).then(res=>res.data).then(res=>{
        console.log(res);
        if(res.code === 200){
          this.tableData = res.data;
        }else{
          alert("获取数据失败")
        }
      })
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>