<template>
  <div class="app-container">
    <div class="mg-b">
      <el-input
        v-model="condition.name"
        placeholder="教学楼名称"
        style="width: 200px"
        class="filter-item"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="getDate"
      >搜索</el-button>
      <el-button
        class="filter-item"
        type="primary"
        @click="dialog=true;info.title='';info.content=''"
      >添加教学楼</el-button>
      <el-button
        class="filter-item fr"
        type="primary"
        :disabled="infos.length==0"
        title="请勾选要删除的数据"
        @click="shanchu()"
      >批量删除</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column type="index" label="id" width="50" />
      <el-table-column prop="name" label="教学楼名称">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.name">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="教室数量">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.roomCount">{{ scope.row.roomCount }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="num" label="添加日期">
        <template slot-scope="scope">
          <span class="shenglue">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="name" label="操作" width="60">
        <template slot-scope="scope">
          <el-button type="text" @click="push(true, scope.row)">详情</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <!-- <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination> -->
    <el-dialog title="添加教学楼" width="50%" :visible.sync="dialog">
      <el-row class="el-row">
        <el-col :span="span1">
          <span class="must">教学楼名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.name" />
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      span1: 6,
      span2: 18,
      dialog: false,
      dialogNotice: false,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      total: 0,
      infos: [],
      condition: {
        page: '1',
        limit: '20',
        name: '',
        title: ''
      },
      info: {
        title: '',
        content: ''
      },
      tableData: [],
      form: {
        title: '',
        createTime: '',
        content: ''
      },
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
    }
  },
  watch: {
    'condition.title' () {
      this.getDate()
    }
  },
  mounted () {
    this.getDate()
  },
  methods: {
    shanchu () {
      const ids = []
      for (let i = 0; i < this.infos.length; i++) {
        ids.push(this.infos[i].teachingBuildingId)
      }
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/teachingBuilding/delete'),
          method: 'post',
          data: this.$http.adornStr(ids)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.getDate()
              }
            })
            this.dialog = false
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch(() => {
        this.$message.info('取消操作')
      })
    },
    add () {
      if (!this.info.name) {
        this.$message.error('教学楼名称不能为空')
        return
      }
      const query = {}
      // for (const i in this.info) {
      //   query[i] = this.info[i]
      // }
      query.name = this.info.name
      this.$http({
        url: this.$http.adornUrl('/sys/teachingBuilding/save'),
        method: 'post',
        data: this.$http.adornStr(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('发布成功')
          this.dialog = false
          this.getDate()
          this.dialog = false
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    push (res, val) {
      for (const i in this.form) {
        this.form[i] = val[i]
      }
      this.dialogNotice = true
    },
    handleFilter () {
      this.$message({
        message: '搜索',
        type: 'success'
      })
    },
    // 查询数据接口
    getDate () {
      let query = {
        name: this.condition.name
      }
      this.$http({
        url: this.$http.adornUrl('/sys/teachingBuilding/all'),
        method: 'get',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.tableData = data.list
          this.getJiaoshi()
        } else {
          this.tableData = []
        }
      })
    },
    getJiaoshi () {
      this.condition.page = 1
      this.condition.limit = 99999
      this.$http({
        url: this.$http.adornUrl('/sys/room/list'),
        method: 'get',
        params: this.$http.adornParams(this.condition)
      }).then(({data}) => {
        if (data && data.code === 0) {
          for (let i = 0; i < this.tableData.length; i++) {
            for (let j = 0; j < data.page.list.length; j++) {
              if (data.page.list[j].teachingBuildingName === this.tableData[i].name) {
                this.tableData[i].roomCount++
              }
            }
          }
        }
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDate()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDate()
    },
    // 表格勾选的数据变化
    handleSelectionChange (res) {
      this.infos = res
    },
    // 删除数据
    deletes () {}
  }
}
</script>
<style>

.shenglue{
  text-overflow:ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
<style scoped>
.jvzhong{
  text-align: center;
}
.date{
  text-align: center;
  color: gray;
  font-size: small
}
.dialogNotice{
  min-height:300px
}
.mtb{
  margin: 10px 0;
}
span {
  float: left;
  margin-top: 6px
}
.el-row {
  width: 100%;
  margin: 5px 0px;
}
.mg-b{
  margin-bottom: 15px;
}
</style>
