<template>
  <div>
    <el-input v-model="name" placeholder="请输入名字" style="margin-bottom: 5px; width: 200px;"
              suffix-icon="el-icon-search" @keyup.enter.native="loadPost">
    </el-input>
    <el-select v-model="sex" filterable placeholder="请选择" style="margin-left: 5px;width: 100px">
      <el-option
          v-for="item in sexs"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-button style="margin-left: 5px;" type="primary" @click="loadPost">查询</el-button>
    <el-button type="success" @click="resetParam">重置</el-button>
    <el-button style="margin-left: 5px;" type="danger" @click="add">新增</el-button>

    <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555555'} " border>
      <el-table-column label="ID" prop="id" width="40">
      </el-table-column>
      <el-table-column label="账号" prop="no" width="180">
      </el-table-column>
      <el-table-column label="姓名" prop="name" width="180">
      </el-table-column>
      <el-table-column label="年龄" prop="age" width="80">
      </el-table-column>
      <el-table-column label="性别" prop="sex" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success' "
              disable-transitions>{{ scope.row.sex === 0 ? '男' : '女' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="roleId" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success') "
              disable-transitions>
            {{ scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '用户') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="电话" prop="phone" width="180">
      </el-table-column>
      <el-table-column label="操作" prop="operate">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="edit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="remove(scope.row.id)">
            <el-button slot="reference" size="small" type="danger" style="margin-left: 5px;">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :page-sizes="[2, 5, 10]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>

    <el-dialog
        :visible.sync="centerDialogVisible"
        center
        title="提示"
        width="30%">

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="名字" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>


      </el-form>


      <span slot="footer" class="dialog-footer">
    <el-button type="info">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "AdminManage",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error('年龄过大'));
      } else {
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl + '/user/findByNo?no=' + this.form.no).then(res => res.data).then(res => {
        if (res.code !== 200) {
          callback();
        } else {
          callback(new Error('账号已经存在'))
        }
      })
    }
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: Array(20).fill(item),
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      sex: "",
      sexs: [
        {
          value: '0',
          label: '男'
        }, {
          value: '1',
          label: '女'
        }
      ],
      centerDialogVisible: false,
      form: {
        id: "",
        no: "",
        name: "",
        password: "",
        age: "",
        sex: "0",
        phone: "",
        roleId: "1"
      },
      rules: {
        no: [
          {required: true, message: "请输入账号", trigger: "blur"},
          {min: 3, max: 8, message: "长度在3到8个字符", trigger: "blur"},
          {validator: checkDuplicate, trigger: "blur"}
        ],
        name: [
          {required: true, message: "请输入名字", trigger: "blur"}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 3, max: 8, message: "长度在3到8个字符", trigger: "blur"}
        ],
        age: [
          {required: true, message: "请输入年龄", trigger: "blur"},
          {min: 1, max: 3, message: "长度在1到3个位", trigger: "blur"},
          {pattern: /^([1-9][0-9]*){1,3}/, message: "年龄必须为正整数", trigger: "blur"},
          {validator: checkAge, trigger: "blur"}
        ],
        phone: [
          {required: true, message: "手机号不能为空", trigger: "blur"},
          {pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ]
      }
    }
  },
  methods: {
    edit(row) {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.form.no = row.no
        this.form.id = row.id
        this.form.name = row.name
        this.form.age = row.age + ""
        this.form.sex = row.sex + ""
        this.form.password = ""
        this.form.phone = row.phone
        this.form.roleId = row.roleId
      })
    },
    remove(id) {
      this.$axios.get(this.$httpUrl + '/user/remove?id='+id).then(res => res.data).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.$message({
            message: "操作成功",
            type: "success"
          })
          this.loadPost()
        } else {
          this.$message({
            message: "操作失败",
            type: "error"
          })
        }
      })
    },
    loadGet() {
      this.$axios.get(this.$httpUrl + '/user/list').then(res => res.data).then(res => {
        console.log(res);
        this.tableData = res;
      })
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/user/query', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex,
          roleId: '1'
        }
      }).then(res => res.data).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        } else {
          alert("获取数据失败")
        }
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.pageNum = 1;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    resetParam() {
      this.sex = "";
      this.name = "";
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm()
      })
    },

    doSave() {
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.centerDialogVisible = false
          this.$message({
            message: "操作成功",
            type: "success"
          })
          this.loadPost()
        } else {
          this.$message({
            message: "操作失败",
            type: "error"
          })
        }
      })
    },
    doEdit() {
      this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.centerDialogVisible = false
          this.$message({
            message: "操作成功",
            type: "success"
          })
          this.loadPost()
        } else {
          this.$message({
            message: "操作失败",
            type: "error"
          })
        }
      })
    },

    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doEdit();
          } else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });


    },
    resetForm() {
      this.$refs.form.resetFields()
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>