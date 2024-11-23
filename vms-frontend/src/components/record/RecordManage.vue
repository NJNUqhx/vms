<template>
  <div>
    <el-input v-model="name" placeholder="请输入物品名" style="margin-bottom: 5px; width: 200px;"
              suffix-icon="el-icon-search" @keyup.enter.native="loadPost">
    </el-input>

    <el-select v-model="storage" placeholder="请选择仓库" style="margin-left:5px;">
      <el-option
          v-for="item in storageData"
          :key="item.id"
          :label="item.name"
          :value="item.id">
      </el-option>
    </el-select>

    <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left:5px;">
      <el-option
          v-for="item in goodstypeData"
          :key="item.id"
          :label="item.name"
          :value="item.id">
      </el-option>
    </el-select>

    <el-button style="margin-left: 5px;" type="primary" @click="loadPost">查询</el-button>
    <el-button type="success" @click="resetParam">重置</el-button>
    <el-button style="margin-left: 5px;" type="danger" @click="add">新增</el-button>

    <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555555'} " border>
      <el-table-column label="ID" prop="id" width="40">
      </el-table-column>
      <el-table-column label="物品名" prop="name" width="180">
      </el-table-column>

      <el-table-column label="所属仓库" prop="storage" width="180" :formatter="formatStorage">
      </el-table-column>

      <el-table-column label="所属分类" prop="goodstype" width="180" :formatter="formatGoodstype">
      </el-table-column>

      <el-table-column label="数量" prop="count" width="180">
      </el-table-column>

      <el-table-column label="备注" prop="remark">
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

        <el-form-item label="物品名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="所属仓库" prop="storage">
          <el-col :span="20">
            <el-select v-model="form.storage" placeholder="请选择所属仓库" style="margin-left:5px;">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="所属分类" prop="goodstype">
          <el-col :span="20">
            <el-select v-model="form.goodstype" placeholder="请选择所属分类" style="margin-left:5px;">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form.count"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
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
  name: "RecordManage",
  data() {
    let checkCount = (rule, value, callback) => {
        if(value > 9999){
          callback(new Error("数量输入过大"));
        }else{
          callback();
        }
    };
    return {
      tableData: [],
      storageData: [],
      goodstypeData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      storage: "",
      goodstype: "",
      count: "",
      remark: "",
      centerDialogVisible: false,
      form: {
        id: "",
        name: "",
        storage: "",
        goodstype: "",
        count: "",
        remark: ""
      },
      rules: {
        name: [
          {required: true, message: "请输入物品名", trigger: "blur"}
        ],
        storage: [
          {required: true, message: "请输入所属仓库", trigger: "blur"}
        ],
        goodstype: [
          {required: true, message: "请输入所属分类", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "请输入备注", trigger: "blur"}
        ],
        count: [
          {required: true, message: "请输入数量", trigger: "blur"},
          {pattern: /^[1-9][0-9]{0,4}$/, message: '数量必须为正整数', trigger: "blur"},
          {validator: checkCount, trigger: "blur"}
        ]
      }
    }
  },
  methods: {
    edit(row) {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.form.id = row.id
        this.form.name = row.name
        this.form.storage = row.storage
        this.form.goodstype = row.goodstype
        this.form.count = row.count
        this.form.remark = row.remark
      })
    },
    remove(id) {
      this.$axios.get(this.$httpUrl + '/goods/remove?id='+id).then(res => res.data).then(res => {
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
    loadPost() {
      // 加载所有物品
      this.$axios.post(this.$httpUrl + '/goods/query', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          goodstype: this.goodstype + "",
          storage: this.storage + ""
        }
      }).then(res => res.data).then(res => {
        console.log(res.data);
        if (res.code === 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        } else {
          alert("获取数据失败")
        }
      })

      // 加载所有仓库
      this.$axios.get(this.$httpUrl + '/storage/list').then(res => res.data).then(res => {
        console.log(res.data);
        if (res.code === 200) {
          this.storageData = res.data;
          console.log("storageData")
          console.log(this.storageData)
        } else {
          alert("获取数据失败")
        }
      })

      // 加载所有分类
      this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => res.data).then(res => {
        console.log(res.data);
        if (res.code === 200) {
          this.goodstypeData = res.data;
          console.log("goodstypeData")
          console.log(this.goodstypeData)
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
      this.name = "";
      this.storage = ""
      this.goodstype = ""
      this.count = ""
      this.remark = "";
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm()
      })
    },

    doSave() {
      this.$axios.post(this.$httpUrl + '/goods/save', this.form).then(res => res.data).then(res => {
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
      this.$axios.post(this.$httpUrl + '/goods/update', this.form).then(res => res.data).then(res => {
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
    },
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id === row.storage
      })

      return temp && temp.name
    },
    formatGoodstype(row){
      let temp = this.goodstypeData.find(item=>{
        return item.id === row.goodstype
      })

      return temp && temp.name
    }
  },
  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>