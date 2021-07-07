<template>
  <div class="app-container">
    <div v-if="panduan">
      <div class="mg-b">
        <el-input
          v-model="condition.roomName"
          placeholder="教室名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="getDate"
        />
        <el-select v-model="condition.teachingBuildingName" clearable placeholder="请选择教学楼">
          <el-option
            v-for="item in tableData_jxl"
            :key="item.teachingBuildingId"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
        <el-button
          class="filter-item"
          type="primary"
          @click="getDate"
        >搜索</el-button>
        <el-button
          v-if="$store.state.user.panduan=='管理员'"
          class="filter-item"
          type="primary"
          @click="tianjiakecheng()"
        >添加教室</el-button>
        <el-button
          v-if="$store.state.user.panduan=='管理员'"
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
        <el-table-column prop="name" label="教学楼名称" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.teachingBuildingName">{{ scope.row.teachingBuildingName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="教室名称" width="120">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.roomName">{{ scope.row.roomName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="教室容量">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.capacity">{{ scope.row.capacity }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="创建日期" width="150">
          <template slot-scope="scope">
            <span class="shenglue">{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="操作" width="110">
          <template slot-scope="scope">
            <el-button type="text" @click="roomId=scope.row.roomId;getSheshi(scope.row)">教室设施</el-button>
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
        <el-select v-model="type" clearable placeholder="请选择种类">
          <el-option
            v-for="item in types_sheshi"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button
          class="filter-item"
          type="primary"
          @click="panduan=true;infos=[]"
        >返回</el-button>
        <el-button
          class="filter-item"
          type="primary"
          @click="sheshi.type='';sheshi.num='';sheshi.materielId='';dialog_sheshi=true;"
        >添加教室设施</el-button>
        <el-button
          class="filter-item fr"
          type="primary"
          :disabled="infos.length==0"
          title="请勾选要删除的数据"
          @click="shanchuSheshi()"
        >批量删除</el-button>
      </div>
      <el-table :data="tableData_sheshi" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"
        />
        <el-table-column type="index" label="id" width="50" />
        <el-table-column prop="name" label="设施种类">
          <template slot-scope="scope">
            <span class="shenglue" :title="qiehuan(scope.row.type, types_sheshi, 'value' ,'name')">{{ qiehuan(scope.row.type, types_sheshi, 'value' ,'name') }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="设施数量">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.num">{{ scope.row.num }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="创建日期">
          <template slot-scope="scope">
            <span class="shenglue">{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="操作" width="110">
          <template slot-scope="scope">
            <el-button type="text" @click="sheshi=scope.row;dialog_sheshi=true">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="教室详情" :close-on-click-modal="false" width="50%" :visible.sync="dialogNotice">
      <el-row>
        <el-col class="jvzhong" :span="24"><b>{{ form.title }}</b></el-col>
        <el-col class="date" :span="24">{{ form.createTime }}</el-col>
        <el-col class="dialogNotice" :span="24">
          <p>{{ form.content }}</p>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog title="添加教室" :close-on-click-modal="false" width="50%" :visible.sync="dialogTianjia">
      <el-table :data="infoList" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column prop="name" label="教学楼">
          <template slot-scope="scope">
            <el-select style="width:100%" v-model="scope.row.teachingBuildingName" placeholder="请选择教学楼">
              <el-option
                v-for="item in tableData_jxl"
                :key="item.teachingBuildingId"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="教室名称">
          <template slot-scope="scope">
            <el-input v-model="scope.row.roomName" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="教室容量">
          <template slot-scope="scope">
            <el-input v-model="scope.row.capacity" type="number" min="0" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="操作" width="120">
          <template slot-scope="scope">
            <!-- <span class="jiajian">+</span>
            <span class="jiajian">-</span> -->
            <el-button
              class="filter-item"
              size="mini"
              type="primary"
              @click="jia(scope)"
            >+</el-button>
            <el-button
              :disabled="infoList.length<2"
              class="filter-item"
              size="mini"
              type="danger"
              @click="jian(scope)"
            >-</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTianjia = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="教室设施" :close-on-click-modal="false" width="50%" :visible.sync="dialog_sheshi">
      <el-row class="el-row">
        <el-col :span="span1">
          <span class="must">设施种类：</span>
        </el-col>
        <el-col :span="span2">
          <el-select style="width:100%" :disabled="sheshi.materielId!=''" v-model="sheshi.type" placeholder="请选择种类">
            <el-option
              v-for="item in types_sheshi"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="span1">
          <span class="must">设施数量：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="sheshi.num" type="number" min="0" />
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog_sheshi = false">取 消</el-button>
        <el-button type="primary" @click="add_sheshi()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      panduan: true,
      type: '',
      roomId: '',
      span1: 6,
      span2: 18,
      dialog: false,
      dialogNotice: false,
      dialog_sheshi: false,
      dialogTianjia: false,
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
        teachingBuildingName: '',
        roomName: ''
      },
      info: {
        teachingBuildingName: '',
        roomName: '',
        capacity: ''
      },
      infoList: [{
        teachingBuildingName: '',
        roomName: '',
        capacity: ''
      }],
      sheshi: {
        createTime: '',
        materielId: '',
        num: '',
        roomId: '',
        type: ''
      },
      tableData: [],
      tableData_jxl: [],
      tableData_sheshi: [],
      form: {
        title: '',
        createTime: '',
        content: ''
      },
      types: [{
        name: '未占用',
        value: 0
      }, {
        name: '已占用',
        value: 1
      }],
      types_sheshi: [{
        name: '板凳',
        value: 1
      }, {
        name: '桌子',
        value: 2
      }, {
        name: '多媒体设备',
        value: 3
      }, {
        name: '风扇',
        value: 4
      }, {
        name: '空调',
        value: 5
      }],
      pageIndex: 1,
      pageSize: 10
    }
  },
  watch: {
    'type' () {
      this.getSheshi()
    },
    'condition.teachingBuildingName' () {
      this.getDate()
    }
  },
  mounted () {
    this.getDate()
    this.getJiaoxuelou()
  },
  methods: {
    jia (val) {
      this.infoList.push({
        teachingBuildingName: this.infoList[this.infoList.length - 1].teachingBuildingName,
        roomName: '',
        capacity: ''
      })
    },
    jian (val) {
      if (this.infoList.length > 1) {
        this.infoList.splice(val.$index, 1)
      }
    },
    tianjiakecheng () {
      this.infoList = [{
        teachingBuildingName: '',
        roomName: '',
        capacity: ''
      }]
      this.dialogTianjia = true
    },
    qiehuan (val, shuzu, ziduan1, ziduan2) {
      for (let i = 0; i < shuzu.length; i++) {
        if (shuzu[i][ziduan1] + '' === val + '') {
          return shuzu[i][ziduan2]
        }
      }
    },
    shanchu (val) {
      const noticeIds = []
      for (let i = 0; i < this.infos.length; i++) {
        noticeIds.push(this.infos[i].roomId)
      }
      this.$confirm('此操作将删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/room/delete'),
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
    shanchuSheshi (val) {
      const noticeIds = []
      for (let i = 0; i < this.infos.length; i++) {
        noticeIds.push(this.infos[i].materielId
        )
      }
      this.$confirm('此操作将删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/room/materiel/delete'),
          method: 'post',
          data: this.$http.adornStr(noticeIds)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.getSheshi()
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
      for (let i = 0; i < this.infoList.length; i++) {
        if (!this.infoList[i].teachingBuildingName) {
          this.$message.error('教学楼不能为空')
          return
        }
        if (!this.infoList[i].roomName) {
          this.$message.error('教室名称不能为空')
          return
        }
        if (this.infoList[i].capacity < 1) {
          this.$message.error('教室容量不能小于1')
          return
        }
      }
      // const query = {}
      // for (const i in this.info) {
      //   query[i] = this.info[i]
      // }
      const query1 = this.infoList
      this.$http({
        url: this.$http.adornUrl('/sys/room/save'),
        method: 'post',
        data: this.$http.adornStr(query1)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.dialogTianjia = false
          this.getDate()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    add_sheshi (val) {
      if (!this.sheshi.type) {
        this.$message.error('设施种类不能为空')
        return
      }
      if (this.sheshi.num < 1) {
        this.$message.error('设施数量不能小于1')
        return
      }
      const query = {}
      for (const i in this.sheshi) {
        query[i] = this.sheshi[i]
      }
      let url = '/sys/room/materiel/update'
      var query1
      if (!query.materielId) {
        url = '/sys/room/materiel/save'
        query.roomId = this.roomId
        query1 = [query]
      } else {
        query1 = {
          materielId: query.materielId,
          num: query.num
        }
      }
      this.$http({
        url: this.$http.adornUrl(url),
        method: 'post',
        data: this.$http.adornStr(query1)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.dialog_sheshi = false
          this.getSheshi()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    handleFilter () {
      this.$message({
        message: '搜索',
        type: 'success'
      })
    },
    // 查询数据接口
    getDate () {
      this.condition.page = this.listQuery.page
      this.condition.limit = this.listQuery.limit
      this.$http({
        url: this.$http.adornUrl('/sys/room/list'),
        method: 'get',
        params: this.$http.adornParams(this.condition)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.tableData = data.page.list
          this.total = data.page.totalCount
        } else {
          this.tableData = []
        }
      })
    },
    // 查询数据接口
    getJiaoxuelou () {
      let query = {
        name: ''
      }
      this.$http({
        url: this.$http.adornUrl('/sys/teachingBuilding/all'),
        method: 'get',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.tableData_jxl = data.list
        } else {
          this.tableData_jxl = []
        }
      })
    },
    // 查询数据接口
    getSheshi (val) {
      let query = {
        roomId: this.roomId,
        type: this.type
      }
      this.$http({
        url: this.$http.adornUrl('/sys/room/materiel/list'),
        method: 'post',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        this.panduan = false
        this.infos = []
        if (data && data.code === 0) {
          this.tableData_sheshi = data.list
        } else {
          this.tableData_sheshi = []
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
.el-col{
  margin: 5px 0px;
}
.mg-b{
  margin-bottom: 15px;
}
</style>
