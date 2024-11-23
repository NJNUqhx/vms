<template>
  <div>
    <h1 style="text-align: center; font-size: 25px;">{{ '欢迎你!' + user.no }}</h1>
    <el-descriptions title="个人中心" :column="1" size="medium" style="font-size: 15px; text-align: center" border>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        账号
      </template>
      {{ user.no }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        电话
      </template>
      18100000000
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-location-outline"></i>
        性别
      </template>
      <el-tag :type="user.sex === '0' ? 'primary' : 'danger'"
              disable-transitions>
        <i :class="user.sex === 0 ? 'el-icon-male' : 'el-icon-female'"></i>
        {{ user.sex === 0 ? '男' : '女'}}
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>

    <DateUtil></DateUtil>
  </div>

</template>

<script>

import DateUtil from "./DateUtil"

export default {
  name: "Home",
  components: {
    DateUtil
  },
  computed:{
    user() {
      // 从 sessionStorage 获取 'CurUser' 并返回，若没有则使用默认值
      const storedUser = sessionStorage.getItem('CurUser')
      if (storedUser) {
        return JSON.parse(storedUser)
      } else {
        // 默认用户数据
        return {
          no: '未知用户',
          sex: '0'  // 默认性别为男
        }
      }
    }
  }
}
</script>

<style scoped>

</style>