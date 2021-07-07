<template>
  <div class="app-container">
    <el-tabs  v-model="panduan" type="border-card">
      <el-tab-pane name="1" label="课程信息">
        <div class="mg-b">
          <el-input
            v-model="condition.className"
            placeholder="班级名称"
            style="width: 200px"
            class="filter-item"
          />
          <el-input
            v-model="condition.courseName"
            placeholder="课程名称"
            style="width: 200px"
            class="filter-item"
          />
          <!-- <el-select v-model="condition.courseName" clearable placeholder="请选择课程">
            <el-option
              v-for="item in types_kecheng"
              :key="item.courseId"
              :label="item.courseName"
              :value="item.courseId">
            </el-option>
          </el-select> -->
          <el-button
            class="filter-item"
            type="primary"
            @click="getDate"
          >搜索</el-button>
          <el-button
            class="filter-item"
            type="primary"
            @click="tianjiakecheng()"
          >添加课程</el-button>
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
          <el-table-column prop="name" label="课程名称" width="120">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.courseName">{{ scope.row.courseName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="班级名称" width="120">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.courseClass">{{ scope.row.courseClass }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="开始日期">
            <template slot-scope="scope">
              <span class="shenglue">{{ scope.row.startDate }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="结束日期">
            <template slot-scope="scope">
              <span class="shenglue">{{ scope.row.finishDate }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="创建日期">
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
      </el-tab-pane>
      <el-tab-pane name="2" label="课程时间">
        <div class="mg-b">
          <el-input
            v-model="tiaojian.courseName"
            placeholder="课程名称"
            style="width: 200px"
            class="filter-item"
          />
          <!-- <el-input
            v-model="tiaojian.courseClass"
            placeholder="班级名称"
            style="width: 200px"
            class="filter-item"
          /> -->
          <!-- <el-select v-model="tiaojian.roomId" clearable placeholder="请选择教室">
            <el-option
              v-for="item in tableData_jiaoshi"
              :key="item.roomId"
              :label="item.roomName"
              :value="item.roomId">
            </el-option>
          </el-select> -->
          <el-select v-model="tiaojian.week" clearable placeholder="请选择周几">
            <el-option
              v-for="item in types_week"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button
            class="filter-item"
            type="primary"
            @click="getSheshi()"
          >搜索</el-button>
          <el-button
            class="filter-item"
            type="primary"
            @click="dialogSheshi()"
          >添加课程时间</el-button>
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
          <el-table-column prop="name" label="班级"
          :filters="filters"
          :filter-method="filterTag">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.courseClass">{{ scope.row.courseClass }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="课程">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.courseName">{{ scope.row.courseName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="上课地点"
          :filters="jiaoshis"
          :filter-method="filterJiaoshi">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.teachingBuildingName + scope.row.roomName">{{ scope.row.teachingBuildingName }}-{{ scope.row.roomName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="星期">
            <template slot-scope="scope">
              <span class="shenglue" :title="qiehuan(scope.row.week, types_week, 'value', 'name')">{{ qiehuan(scope.row.week, types_week, 'value', 'name') }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="上课时间">
            <template slot-scope="scope">
              <span class="shenglue" :title="getTime(scope.row.beginTime)">{{ getTime(scope.row.beginTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="下课时间">
            <template slot-scope="scope">
              <span class="shenglue" :title="getTime(scope.row.finishTime)">{{ getTime(scope.row.finishTime) }}</span>
            </template>
          </el-table-column>
      
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle1"
          @current-change="currentChangeHandle1"
          :current-page="pageIndex1"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize1"
          :total="total1"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="教室详情" width="50%" :visible.sync="dialogNotice">
      <el-row>
        <el-col class="jvzhong" :span="24"><b>{{ form.title }}</b></el-col>
        <el-col class="date" :span="24">{{ form.createTime }}</el-col>
        <el-col class="dialogNotice" :span="24">
          <p>{{ form.content }}</p>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog title="添加课程" :close-on-click-modal="false" width="70%" :visible.sync="dialogTianjia">
      <el-table :data="infoList" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column prop="name" label="课程名称">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.courseName" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="班级名称">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.courseClass" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="开始日期">
          <template slot-scope="scope">
            <el-date-picker
              style="width:100%"
              size="mini"
              v-model="scope.row.startDate"
              value-format="yyyy-MM-dd 00:00:00"
              type="date"
              placeholder="课程开始日期">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="结束日期">
          <template slot-scope="scope">
            <el-date-picker
              style="width:100%"
              size="mini"
              v-model="scope.row.finishDate"
              value-format="yyyy-MM-dd 00:00:00"
              type="date"
              placeholder="课程结束日期">
            </el-date-picker>
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
      <!-- <el-row class="el-row">
        <el-col :span="span1">
          <span class="must">课程名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.courseName" />
        </el-col>
        <el-col :span="span1">
          <span class="must">班级名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.courseClass" />
        </el-col>
        <el-col :span="span1">
          <span class="must">开始时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-date-picker
            style="width:100%"
            v-model="info.startDate"
            value-format="yyyy-MM-dd 00:00:00"
            type="date"
            placeholder="课程开始时间">
          </el-date-picker>
        </el-col>
        <el-col :span="span1">
          <span class="must">结束时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-date-picker
            style="width:100%"
            v-model="info.finishDate "
            value-format="yyyy-MM-dd 00:00:00"
            type="date"
            placeholder="课程结束时间">
          </el-date-picker>
        </el-col>
      </el-row> -->
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTianjia = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改课程" :close-on-click-modal="false" width="50%" :visible.sync="dialog">
      <el-row class="el-row">
        <el-col :span="span1">
          <span class="must">课程名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.courseName" />
        </el-col>
        <el-col :span="span1">
          <span class="must">班级名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-input v-model="info.courseClass" />
        </el-col>
        <el-col :span="span1">
          <span class="must">开始时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-date-picker
            style="width:100%"
            v-model="info.startDate"
            value-format="yyyy-MM-dd 00:00:00"
            type="date"
            placeholder="课程开始时间">
          </el-date-picker>
        </el-col>
        <el-col :span="span1">
          <span class="must">结束时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-date-picker
            style="width:100%"
            v-model="info.finishDate "
            value-format="yyyy-MM-dd 00:00:00"
            type="date"
            placeholder="课程结束时间">
          </el-date-picker>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加课程时间" :close-on-click-modal="false" width="70%" :visible.sync="dialogKecheng">
      <el-table :data="sheshiList" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column prop="name" label="选择天数">
          <template slot-scope="scope">
            <el-select style="width:100%" v-model="scope.row.week" placeholder="请选择天数">
              <el-option
                v-for="item in types_week"
                :key="item.value"
                :label="item.name"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="教室名称">
          <template slot-scope="scope">
            <el-select style="width:100%" v-model="scope.row.roomId" placeholder="请选择教室">
              <el-option
                v-for="item in tableData_jiaoshi"
                :key="item.roomId"
                :label="item.roomName"
                :value="item.roomId">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="课程名称">
          <template slot-scope="scope">
            <el-select style="width:100%" v-model="scope.row.courseId" placeholder="请选择课程">
              <el-option
                v-for="item in types_kecheng"
                :key="item.courseId"
                :label="item.courseName"
                :value="item.courseId">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="上课时间">
          <template slot-scope="scope">
            <el-time-picker
              style="width:100%"
              v-model="scope.row.beginTime"
              :picker-options="{
                selectableRange: '06:00:00 - 22:00:00'
              }"
              placeholder="上课时间">
            </el-time-picker>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="下课时间">
          <template slot-scope="scope">
            <el-time-picker
              style="width:100%"
              v-model="scope.row.finishTime"
              :picker-options="{
                selectableRange: '06:00:00 - 22:00:00'
              }"
              placeholder="下课时间">
            </el-time-picker>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="操作" width="120">
          <template slot-scope="scope">
            <el-button
              class="filter-item"
              size="mini"
              type="primary"
              @click="jia1(scope)"
            >+</el-button>
            <el-button
              :disabled="sheshiList.length<2"
              class="filter-item"
              size="mini"
              type="danger"
              @click="jian1(scope)"
            >-</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogKecheng = false">取 消</el-button>
        <el-button type="primary" @click="add_sheshi(true)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改课程时间" :close-on-click-modal="false" width="50%" :visible.sync="dialog_sheshi">
      <el-row class="el-row">
        <el-col :span="span1">
          <span class="must">选择天数：</span>
        </el-col>
        <el-col :span="span2">
          <el-select style="width:100%" v-model="sheshi.week" placeholder="请选择天数">
            <el-option
              v-for="item in types_week"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="span1">
          <span class="must">教室名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-select style="width:100%" v-model="sheshi.roomId" placeholder="请选择教室">
            <el-option
              v-for="item in tableData_jiaoshi"
              :key="item.roomId"
              :label="item.roomName"
              :value="item.roomId">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="span1">
          <span class="must">课程名称：</span>
        </el-col>
        <el-col :span="span2">
          <el-select style="width:100%" v-model="sheshi.courseId" placeholder="请选择课程">
            <el-option
              v-for="item in types_kecheng"
              :key="item.courseId"
              :label="item.courseName"
              :value="item.courseId">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="span1">
          <span class="must">上课时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-time-picker
            style="width:100%"
            value-format="hh:mm:ss"
            v-model="sheshi.beginTime"
            :picker-options="{
              selectableRange: '06:00:00 - 22:00:00'
            }"
            placeholder="上课时间">
          </el-time-picker>
        </el-col>
        <el-col :span="span1">
          <span class="must">下课时间：</span>
        </el-col>
        <el-col :span="span2">
          <el-time-picker
            style="width:100%"
            value-format="hh:mm:ss"
            v-model="sheshi.finishTime"
            :picker-options="{
              selectableRange: '06:00:00 - 22:00:00'
            }"
            placeholder="下课时间">
          </el-time-picker>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog_sheshi = false">取 消</el-button>
        <el-button type="primary" @click="add_sheshi(false)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      filters: [],
      panduan: '1',
      type: '',
      roomId: '',
      span1: 6,
      span2: 18,
      dialog: false,
      dialogTianjia: false,
      dialogNotice: false,
      dialog_sheshi: false,
      dialogKecheng: false,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      infos: [],
      condition: {
        page: '1',
        limit: '20',
        className: '',
        courseName: ''
      },
      tiaojian: {
        page: '1',
        limit: '20',
        courseClass: '',
        courseName: '',
        roomId: '',
        week: ''
      },
      info: {
        courseClass: '',
        courseId: '',
        courseName: '',
        createTime: '',
        startDate: '',
        finishDate: ''
      },
      infoList: [{
        courseClass: '',
        courseId: '',
        courseName: '',
        createTime: '',
        startDate: '',
        finishDate: ''
      }],
      sheshi: {
        week: '',
        courseId: '',
        courseTimeId: '',
        createTime: '',
        roomId: '',
        beginTime: '',
        finishTime: ''
      },
      sheshiList: [{
        week: '',
        courseId: '',
        courseTimeId: '',
        createTime: '',
        roomId: '',
        beginTime: '',
        finishTime: ''
      }],
      tableData: [],
      tableData_jxl: [],
      tableData_sheshi: [],
      tableData_jiaoshi: [],
      types_kecheng: [],
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
      types_week: [{
        name: '周一',
        value: '1'
      }, {
        name: '周二',
        value: '2'
      }, {
        name: '周三',
        value: '3'
      }, {
        name: '周四',
        value: '4'
      }, {
        name: '周五',
        value: '5'
      }, {
        name: '周六',
        value: '6'
      }, {
        name: '周日',
        value: '7'
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
      total: 0,
      pageSize: 10,
      pageIndex1: 1,
      total1: 0,
      pageSize1: 10,
      jiaoshis: []
    }
  },
  watch: {
    // 'type' () {
    //   this.getSheshi()
    // },
    // 'condition.state' () {
    //   this.getDate()
    // },
    // 'condition.teachingBuildingName' () {
    //   this.getDate()
    // },
    panduan () {
      this.dataListSelections = []
      if (this.panduan === '1') {
        this.getDate()
      } else {
        this.getSheshi()
      }
    }
  },
  mounted () {
    this.getDate()
    this.getJiaoshi()
    this.getKecheng()
    // this.getJiaoxuelou()
  },
  methods: {
    filterJiaoshi (value, row) {
      return row.teachingBuildingName + '-' + row.roomName === value
    },
    filterTag (value, row) {
      return row.courseClass + '' === value
    },
    jia (val) {
      this.infoList.push({
        courseClass: '',
        courseId: '',
        courseName: '',
        createTime: '',
        startDate: '',
        finishDate: ''
      })
    },
    jian (val) {
      if (this.infoList.length > 1) {
        this.infoList.splice(val.$index, 1)
      }
    },
    jia1 (val) {
      console.log(this.sheshiList)
      this.sheshiList.push({
        week: '',
        courseId: '',
        courseTimeId: '',
        createTime: '',
        roomId: '',
        beginTime: '',
        finishTime: ''
      })
    },
    jian1 (val) {
      if (this.sheshiList.length > 1) {
        this.sheshiList.splice(val.$index, 1)
      }
    },
    tianjiakecheng () {
      this.infoList = [{
        courseClass: '',
        courseId: '',
        courseName: '',
        createTime: '',
        startDate: '',
        finishDate: ''
      }]
      this.dialogTianjia = true
    },
    getTime (val) {
      let time = val
      let h = parseInt(time / 3600)
      let m = parseInt((time % 3600) / 60)
      let s = parseInt(time % 60)
      if (h < 10) {
        h = '0' + h
      }
      if (m < 10) {
        m = '0' + m
      }
      if (s < 10) {
        s = '0' + s
      }
      return h + ':' + m + ':' + s
    },
    date (date1, date2) {
      var oDate1 = new Date(date1)
      var oDate2 = new Date(date2)
      if (oDate1.getTime() > oDate2.getTime()) {
        return true
      } else {
        return false
      }
    },
    dialogSheshi () {
      this.sheshiList = [{
        week: '',
        courseId: '',
        courseTimeId: '',
        createTime: '',
        roomId: '',
        beginTime: '',
        finishTime: ''
      }]
      this.dialogKecheng = true
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
        noticeIds.push(this.infos[i].courseId)
      }
      this.$confirm('此操作将删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/course/delete'),
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
        noticeIds.push(this.infos[i].courseTimeId
        )
      }
      this.$confirm('此操作将删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/sys/course/time/delete'),
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
        if (!this.infoList[i].courseName) {
          this.$message.error('课程名称不能为空')
          return
        }
        if (!this.infoList[i].courseClass) {
          this.$message.error('班级名称不能为空')
          return
        }
        if (!this.infoList[i].startDate) {
          this.$message.error('开始日期不能为空')
          return
        }
        if (!this.infoList[i].finishDate) {
          this.$message.error('结束日期不能为空')
          return
        }
        if (this.date(this.infoList[i].startDate, this.infoList[i].finishDate)) {
          this.$message.error('开始日期不能超过结束日期')
          return
        }
      }
      // const query = {}
      // for (const i in this.info) {
      //   if (this.info[i] !== '') {
      //     query[i] = this.info[i]
      //   }
      // }
      const query1 = this.infoList
      this.$http({
        url: this.$http.adornUrl('/sys/course/save'),
        method: 'post',
        data: this.$http.adornStr(query1)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.dialogTianjia = false
          this.getDate()
          this.getKecheng()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    checkTime (i) {
      if (i < 10) {
        i = '0' + i
      }
      return i
    },
    add_sheshi () {
      let table = []
      var date1 = ''
      var date2 = ''
      var dateValue1 = ''
      var dateValue2 = ''
      for (let i = 0; i < this.sheshiList.length; i++) {
        if (!this.sheshiList[i].week) {
          this.$message.error('请选择周几')
          return
        }
        if (!this.sheshiList[i].roomId) {
          this.$message.error('请选择教室')
          return
        }
        if (!this.sheshiList[i].courseId) {
          this.$message.error('请选择课程')
          return
        }
        if (!this.sheshiList[i].beginTime) {
          this.$message.error('请选择上课时间')
          return
        }
        if (!this.sheshiList[i].finishTime) {
          this.$message.error('请选择下课时间')
          return
        }
        date1 = new Date(this.sheshiList[i].beginTime)
        dateValue1 = this.checkTime(date1.getHours()) + ':' + this.checkTime(date1.getMinutes()) + ':' + this.checkTime(date1.getSeconds())
        date2 = new Date(this.sheshiList[i].finishTime)
        dateValue2 = this.checkTime(date2.getHours()) + ':' + this.checkTime(date2.getMinutes()) + ':' + this.checkTime(date2.getSeconds())
        table.push(JSON.parse(JSON.stringify(this.sheshiList[i])))
        table[i].beginTime = (dateValue1.split(':')[0] * 3600 + dateValue1.split(':')[1] * 60 + dateValue1.split(':')[2] * 1) * 1
        table[i].finishTime = (dateValue2.split(':')[0] * 3600 + dateValue2.split(':')[1] * 60 + dateValue2.split(':')[2] * 1) * 1
        if (table[i].beginTime > table[i].finishTime) {
          this.$message.error('上课时间不能超过下课时间')
          return
        }
      }
      console.log(this.sheshiList)
      this.$http({
        url: this.$http.adornUrl('/sys/course/time/save'),
        method: 'post',
        data: this.$http.adornStr(table)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message.success('操作成功')
          this.dialogKecheng = false
          this.getSheshi()
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    update_sheshi () {
      if (!this.sheshi.week) {
        this.$message.error('请选择周几')
        return
      }
      if (!this.sheshi.roomId) {
        this.$message.error('请选择教室')
        return
      }
      if (!this.sheshi.courseId) {
        this.$message.error('请选择课程')
        return
      }
      if (!this.sheshi.beginTime) {
        this.$message.error('请选择上课时间')
        return
      }
      if (!this.sheshi.finishTime) {
        this.$message.error('请选择下课时间')
        return
      }
      const query = {}
      for (const i in this.sheshi) {
        if (this.sheshi[i]) {
          query[i] = this.sheshi[i]
        }
      }
      query.beginTime = (query.beginTime.split(':')[0] * 3600 + query.beginTime.split(':')[1] * 60 + query.beginTime.split(':')[2] * 1) * 1
      query.finishTime = (query.finishTime.split(':')[0] * 3600 + query.finishTime.split(':')[1] * 60 + query.finishTime.split(':')[2] * 1) * 1
      if (query.beginTime > query.finishTime) {
        this.$message.error('上课时间不能超过下课时间')
        return
      }
      var query1 = {
        materielId: query.materielId,
        num: query.num
      }
      this.$http({
        url: this.$http.adornUrl('/sys/room/time/update'),
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
      this.condition.page = this.pageIndex
      this.condition.limit = this.pageSize
      this.$http({
        url: this.$http.adornUrl('/sys/course/list'),
        method: 'get',
        params: this.$http.adornParams(this.condition)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.tableData = data.page.list
          this.total = data.page.totalCount
        } else {
          this.tableData = []
          this.total = 0
        }
      })
    },
    // 查询数据接口
    getKecheng () {
      this.$http({
        url: this.$http.adornUrl('/sys/course/all'),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          let table = data.list
          for (let i = 0; i < table.length; i++) {
            table[i].courseName = table[i].courseName + '-' + table[i].courseClass
          }
          this.types_kecheng = table
        } else {
          this.types_kecheng = []
        }
      })
    },
    // 查询数据接口
    getJiaoshi () {
      this.condition.page = 1
      this.condition.limit = 99999
      this.$http({
        url: this.$http.adornUrl('/sys/room/list'),
        method: 'get',
        params: this.$http.adornParams(this.condition)
      }).then(({data}) => {
        if (data && data.code === 0) {
          let table = data.page.list
          let table1 = []
          for (let i = 0; i < table.length; i++) {
            table[i].roomName = table[i].teachingBuildingName + '-' + table[i].roomName
            table1.push({
              text: table[i].roomName,
              value: table[i].roomName
            })
          }
          this.jiaoshis = table1
          this.tableData_jiaoshi = table
        } else {
          this.tableData_jiaoshi = []
          this.jiaoshis = []
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
      let query = {}
      for (let i in this.tiaojian) {
        if (this.tiaojian[i] !== '') {
          query[i] = this.tiaojian[i]
        }
      }
      query.page = this.pageIndex1
      query.limit = this.pageSize1
      this.$http({
        url: this.$http.adornUrl('/sys/course/time/list'),
        method: 'get',
        params: this.$http.adornParams(query)
      }).then(({data}) => {
        this.infos = []
        if (data && data.code === 0) {
          this.tableData_sheshi = data.page.list
          this.total1 = data.page.totalCount
          let table = []
          for (let i = 0; i < data.page.list.length; i++) {
            console.log(table.indexOf(data.page.list[i].courseClass))
            if (table.indexOf(data.page.list[i].courseClass) < 0) {
              table.push(data.page.list[i].courseClass)
            }
          }
          let table1 = []
          for (let j = 0; j < table.length; j++) {
            table1.push({
              text: table[j],
              value: table[j]
            })
          }
          this.filters = table1
          console.log(this.filters)
        } else {
          this.tableData_sheshi = []
          this.total1 = 0
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
    // 每页数
    sizeChangeHandle1 (val) {
      this.pageSize1 = val
      this.pageIndex1 = 1
      this.getSheshi()
    },
    // 当前页
    currentChangeHandle1 (val) {
      this.pageIndex1 = val
      this.getSheshi()
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
.jiajian{
  margin-left: 8px;
  background-color: aliceblue;
  padding: 5px 10px;
  font-size: 20px;
  font-weight: bold;
}
</style>
