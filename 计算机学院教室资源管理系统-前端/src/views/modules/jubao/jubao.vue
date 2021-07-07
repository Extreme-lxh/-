<template>
  <div class="app-container">
    <div v-if="$store.state.user.panduan=='管理员'">
      <div class="mg-b">
        <el-select style="width:200px" v-model="condition.roomId" clearable placeholder="请选择教室">
          <el-option
            v-for="item in tableData_jiaoshi"
            :key="item.roomId"
            :label="item.roomName"
            :value="item.roomId">
          </el-option>
        </el-select>
        <!-- <el-button
          class="filter-item fr"
          type="primary"
          :disabled="infos.length==0"
          title="请勾选要删除的数据"
          @click="shanchu()"
        >批量删除</el-button> -->
      </div>
      <el-table :data="dataList_all" border style="width: 100%" @selection-change="handleSelectionChange">
        <!-- <el-table-column
          type="selection"
          width="55"
        /> -->
        <el-table-column type="index" label="id" width="50" />
        <el-table-column prop="name" label="教学楼" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.teachingBuildingName">{{ scope.row.teachingBuildingName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="教室" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.roomName">{{ scope.row.roomName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="图片" width="141">
          <template slot-scope="scope">
            <img v-if="scope.row.reportImgs" :src="scope.row.reportImgs" width="120px" height="80px">
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="举报内容">
          <template slot-scope="scope">
            <span :title="scope.row.reportContent">{{ scope.row.reportContent }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="创建日期" width="150">
          <template slot-scope="scope">
            <span class="shenglue">{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
    <div v-else>
      <div class="mg-b">
        <el-button
          class="filter-item"
          type="primary"
          @click="jubao()"
        >举报</el-button>
      </div>
      <el-table :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <!-- <el-table-column
          type="selection"
          width="55"
        /> -->
        <el-table-column type="index" label="id" width="50" />
        <el-table-column prop="name" label="教学楼" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.teachingBuildingName">{{ scope.row.teachingBuildingName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="教室" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.roomName">{{ scope.row.roomName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="图片" width="141">
          <template slot-scope="scope">
            <img v-if="scope.row.reportImgs" :src="scope.row.reportImgs" width="120px" height="80px">
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="举报内容">
          <template slot-scope="scope">
            <span :title="scope.row.reportContent">{{ scope.row.reportContent }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="创建日期" width="150">
          <template slot-scope="scope">
            <span class="shenglue">{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="教室详情" width="50%" :visible.sync="dialogNotice">
      <el-row>
        <el-col class="jvzhong" :span="24"><b>{{ form.title }}</b></el-col>
        <el-col class="date" :span="24">{{ form.createTime }}</el-col>
        <el-col class="dialogNotice" :span="24">
          <p>{{ form.content }}</p>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog title="举报" width="50%" :visible.sync="dialog">
      <el-row class="el-row">
        <el-row class="el-row">
          <el-col :span="span1">
            <span class="must">选择教室：</span>
          </el-col>
          <el-col :span="span2">
            <el-select style="width:100%" v-model="info.reportRoomId" clearable placeholder="请选择教室">
              <el-option
                v-for="item in tableData_jiaoshi"
                :key="item.roomId"
                :label="item.roomName"
                :value="item.roomId">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row class="el-row">
          <el-col :span="span1">
            <span class="must">上传图片：</span>
          </el-col>
          <el-col :span="span2">
            <el-upload
              class="avatar-uploader"
              action="http://localhost:8001/classroom-api/sys/file/upload/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
              <img v-if="info.reportImgs" :src="info.reportImgs" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
        </el-row>
        <el-col :span="span1">
          <span class="must">举报内容：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.reportContent" rows="10" type="textarea" />
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormadd = false">取 消</el-button>
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
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      infos: [],
      condition: {
        page: '1',
        limit: '10',
        userName: '',
        roomId: ''
      },
      info: {
        reportContent: '',
        reportRoomId: '',
        reportImgs: '',
        createTime: '',
        reportId: '',
        reportUserId: '',
        reportUsername: '',
        roomName: '',
        teachingBuildingName: ''
      },
      tableData: [],
      tableData_jiaoshi: [],
      dataList_all: [],
      form: {
        title: '',
        createTime: '',
        content: ''
      },
      pageIndex: 1,
      pageSize: 10,
      total: 0
    }
  },
  watch: {
    'condition.roomId' () {
      this.getJubao()
    }
  },
  mounted () {
    this.getJiaoshi()
    if (JSON.parse(localStorage.getItem('userInfo')).roleIdList.length) {
      this.getDate()
    } else {
      this.getJubao()
    }
  },
  methods: {
    shanchu (val) {
      const noticeIds = []
      for (let i = 0; i < this.infos.length; i++) {
        noticeIds.push(this.infos[i].noticeId)
      }
      this.$confirm('此操作将删除该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/notice/delete'),
          method: 'post',
          data: this.$http.adornStr(noticeIds)
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
    shenpi (val, neirong) {
      let text = '拒绝'
      if (val) {
        text = '同意'
      }
      const noticeIds = []
      for (let i = 0; i < this.infos.length; i++) {
        noticeIds.push(this.infos[i].noticeId)
      }
      this.$confirm('此操作将' + text + '该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/notice/delete'),
          method: 'post',
          data: this.$http.adornStr(noticeIds)
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
      if (!this.info.reportRoomId) {
        this.$message.error('请选择教室')
        return
      }
      if (!this.info.reportImgs) {
        this.$message.error('请上传图片')
        return
      }
      if (!this.info.reportContent) {
        this.$message.error('举报内容不能为空')
        return
      }
      const query = {}
      for (const i in this.info) {
        if (this.info[i] !== '') {
          query[i] = this.info[i]
        }
      }
      this.$http({
        url: this.$http.adornUrl('/sys/report/save'),
        method: 'post',
        data: this.$http.adornStr(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('发布成功')
          this.dialog = false
          this.getDate()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    jubao () {
      this.dialog = true
    },
    handleFilter () {
      this.$message({
        message: '搜索',
        type: 'success'
      })
    },
    // 查询数据接口
    getDate () {
      this.$http({
        url: this.$http.adornUrl('/sys/report/log'),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.tableData = data.list
        } else {
          this.tableData = []
        }
      })
    },
    // 获取数据列表
    getJubao () {
      this.condition.page = this.pageIndex
      this.condition.limit = this.pageSize
      let query = {}
      for (let i in this.condition) {
        if (this.condition[i] !== '') {
          query[i] = this.condition[i]
        }
      }
      this.$http({
        url: this.$http.adornUrl('/sys/report/list'),
        method: 'get',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList_all = data.page.list
          this.total = data.page.totalCount
        } else {
          this.dataList_all = []
        }
      })
    },
    handleAvatarSuccess (res, file) {
      this.info.reportImgs = 'http://loclhost:8001/classroom-api/files/' + res.fileName
    },
    // 查询数据接口
    getJiaoshi () {
      let query = {
        page: 1,
        limit: 999999
      }
      this.$http({
        url: this.$http.adornUrl('/sys/room/list'),
        method: 'get',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          let table = data.page.list
          for (let i = 0; i < table.length; i++) {
            table[i].roomName = table[i].teachingBuildingName + '-' + table[i].roomName
          }
          this.tableData_jiaoshi = table
        } else {
          this.tableData_jiaoshi = []
        }
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getJubao()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getJubao()
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
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    border: 1px dashed #d9d9d9;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
