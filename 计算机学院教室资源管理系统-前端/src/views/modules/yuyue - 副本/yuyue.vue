<template>
  <div class="padding:0">
    <div v-if="juese=='guanli'">
      <el-date-picker
        style="width:200px"
        v-model="condition.useDate"
        value-format="yyyy-MM-dd 00:00:00"
        type="date"
        placeholder="请选择日期">
      </el-date-picker>
      <!-- <el-input
        v-model="condition.applyUserName"
        placeholder="申请者姓名"
        style="width: 200px"
        class="filter-item"
      /> -->
      <el-select style="width:200px" v-model="condition.roomId" clearable placeholder="请选择教室">
        <el-option
          v-for="item in tableData_jiaoshi"
          :key="item.roomId"
          :label="item.roomName"
          :value="item.roomId">
        </el-option>
      </el-select>
      <el-select style="width:200px" v-model="condition.applyState" clearable placeholder="请选择状态">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.name"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-bottom:10px" type="primary" @click="getYuyueList()">搜索</el-button>
      <!-- <el-button :disabled="dataListSelections.length==0" title="将勾选的数据删除掉" style="margin-bottom:10px" type="primary" @click="deleteHandle()">批量删除</el-button> -->
      <el-table
        :data="dataList_all"
        border
        @selection-change="selectionChangeHandle"
        style="width: 100%;">
        <el-table-column
          prop="teaName"
          header-align="center"
          label="状态"
          width="70">
          <template slot-scope="scope">
            <span v-if="scope.row.applyState==0" class="shenglue" title="待处理">待处理</span>
            <span v-if="scope.row.applyState==1" class="shenglue" title="成功">成功</span>
            <span v-if="scope.row.applyState==2" class="shenglue" title="失败">失败</span>
            <span v-if="scope.row.applyState==3" class="shenglue" title="退回">退回</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="申请人姓名">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.applyUserName">{{scope.row.applyUserName}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="申请人职务">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.applyUserPost">{{scope.row.applyUserPost}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="手机号">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.applyUserMobile">{{scope.row.applyUserMobile}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="用途">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.useDo">{{scope.row.useDo}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="地点">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.teachingBuildingName+scope.row.roomName">{{scope.row.teachingBuildingName}}-{{scope.row.roomName}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="使用日期">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.useDate">{{scope.row.useDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="开始时间">
          <template slot-scope="scope">
            <span class="shenglue" :title="getTime(scope.row.useStartTime)">{{getTime(scope.row.useStartTime)}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="teaName"
          header-align="center"
          label="结束时间">
          <template slot-scope="scope">
            <span class="shenglue" :title="getTime(scope.row.useEndTime)">{{getTime(scope.row.useEndTime)}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          header-align="center"
          align="center"
          label="申请日期">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          header-align="center"
          align="center"
          label="回复">
          <template slot-scope="scope">
            <span class="shenglue" :title="scope.row.handleMsg" v-if="scope.row.handleMsg">{{scope.row.handleMsg}}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="110"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" v-if="scope.row.applyState==0" title="同意此次申请" size="small" @click="caozuo(scope.row, 1)">同意</el-button>
            <el-button type="text" v-if="scope.row.applyState==0" title="拒绝此次申请" size="small" @click="caozuo(scope.row, 2)">拒绝</el-button>
            <span v-if="scope.row.applyState==1">已同意</span>
            <span v-if="scope.row.applyState==2">已拒绝</span>
            <span v-if="scope.row.applyState==3">已退回</span>
            <!-- <el-button type="text" size="small" @click="panduan='1';depId=scope.row.depId">教室列表</el-button> -->
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
    <el-tabs v-else v-model="panduan" type="border-card">
      <el-tab-pane name="1" label="教室预约">
        <el-date-picker
          style="width:200px"
          v-model="dataForm.date"
          value-format="yyyy-MM-dd 00:00:00"
          type="date"
          placeholder="请选择日期">
        </el-date-picker>
        <el-input
          v-model="dataForm.teachingBuildingName"
          placeholder="教学楼名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="getDate"
        />
        <el-input
          v-model="dataForm.roomName"
          placeholder="教室名称"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="getDate"
        />
        <el-button style="margin-bottom:10px" type="primary" @click="getDataList()">搜索</el-button>
        <!-- <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia()">申请教室</el-button> -->
        <!-- <el-button :disabled="dataListSelections.length==0" title="将勾选的数据删除掉" style="margin-bottom:10px" type="primary" @click="deleteHandle()">批量删除</el-button> -->
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandle"
          style="width: 100%;">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50">
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="教学楼名称"
            width="120">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.teachingBuildingName">{{scope.row.teachingBuildingName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stuName"
            header-align="center"
            align="center"
            label="教室名称"
            width="90">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.roomName">{{scope.row.roomName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stuName"
            header-align="center"
            align="center"
            label="教室容量"
            width="90">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.capacity">{{scope.row.capacity}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="课程">
            <template slot-scope="scope">
              <span v-for="(item, index) in scope.row.courses" class="shenglue" :key="index" :title="item.courseName">{{item.courseName}}<br></span>
              <span :title="scope.row.courses.length" v-if="scope.row.courses.length==0">暂无</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="班级">
            <template slot-scope="scope">
              <span v-for="(item, index) in scope.row.courses" class="shenglue" :key="index" :title="item.courseClass">{{item.courseClass}}<br></span>
              <span :title="scope.row.courses.length" v-if="scope.row.courses.length==0">暂无</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="周节次"
            width="80">
            <template slot-scope="scope">
              <span v-for="(item, index) in scope.row.courses" class="shenglue" :key="index" :title="qiehuan(item.week, types_week, 'value', 'name')">{{qiehuan(item.week, types_week, 'value', 'name')}}<br></span>
              <span :title="scope.row.courses.length" v-if="scope.row.courses.length==0">暂无</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="上课时间">
            <template slot-scope="scope">
              <span v-for="(item, index) in scope.row.courses" class="shenglue" :key="index" :title="getTime(item.beginTime)">{{getTime(item.beginTime)}}<br></span>
              <span :title="scope.row.courses.length" v-if="scope.row.courses.length==0">暂无</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="下课时间">
            <template slot-scope="scope">
              <span v-for="(item, index) in scope.row.courses" class="shenglue" :key="index" :title="getTime(item.finishTime)">{{getTime(item.finishTime)}}<br></span>
              <span :title="scope.row.courses.length" v-if="scope.row.courses.length==0">暂无</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="添加日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column> -->
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="100"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjia(scope.row)">预约</el-button>
              <!-- <el-button type="text" size="small" @click="yuyue(scope.row)">退回</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane name="2" label="预约记录">
        <el-table
          :data="dataList_wode"
          border
          @selection-change="selectionChangeHandle"
          style="width: 100%;">
          <el-table-column
            prop="teaName"
            header-align="center"
            label="状态"
            width="70">
            <template slot-scope="scope">
              <span v-if="scope.row.applyState==0" class="shenglue" title="待处理">待处理</span>
              <span v-if="scope.row.applyState==1" class="shenglue" title="成功">成功</span>
              <span v-if="scope.row.applyState==2" class="shenglue" title="失败">失败</span>
              <span v-if="scope.row.applyState==3" class="shenglue" title="退回">退回</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="申请人姓名">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.applyUserName">{{scope.row.applyUserName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="申请人职务">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.applyUserPost">{{scope.row.applyUserPost}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="手机号">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.applyUserMobile">{{scope.row.applyUserMobile}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="用途">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.useDo">{{scope.row.useDo}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="地点">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.teachingBuildingName+scope.row.roomName">{{scope.row.teachingBuildingName}}-{{scope.row.roomName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="使用日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.useDate">{{scope.row.useDate}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="开始时间">
            <template slot-scope="scope">
              <span class="shenglue" :title="getTime(scope.row.useStartTime)">{{getTime(scope.row.useStartTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="结束时间">
            <template slot-scope="scope">
              <span class="shenglue" :title="getTime(scope.row.useEndTime)">{{getTime(scope.row.useEndTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="申请日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="回复">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.handleMsg" v-if="scope.row.handleMsg">{{scope.row.handleMsg}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="80"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" :disabled="scope.row.applyState!=1" title="只有申请成功后才可以退回" size="small" @click="tuihui(scope.row)">退回</el-button>
              <!-- <el-button type="text" size="small" @click="panduan='1';depId=scope.row.depId">教室列表</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <!-- <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination> -->
    <el-dialog title="教室" :visible.sync="dialog">
      <el-form :model="form">
        <el-form-item label="*教室名称：" label-width="140px">
          <el-input v-model="form.depName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*教室图片：" label-width="140px">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8001/classroom-api/sys/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog = false">取 消</el-button>
        <el-button type="primary" title="请输入内容" @click="tianjia()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="预约教室" :visible.sync="dialog_chuangwei">
      <el-form :model="form_yuyue">
        <!-- <el-form-item label="*教室状态：" label-width="140px">
          <el-select v-model="form_yuyue.bedState" style="width:100%" placeholder="请选择">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select style="width:100%" v-model="form_yuyue.roomId" placeholder="请选择教室">
            <el-option
              v-for="item in tableData_jiaoshi"
              :key="item.roomId"
              :label="item.roomName"
              :value="item.roomId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="*所属教室：" label-width="140px">
          <el-select v-model="form_yuyue.depId" style="width:100%" placeholder="请选择">
            <el-option
              v-for="item in dataList_keshi"
              :key="item.depId"
              :label="item.depName"
              :value="item.depId">
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="*选择教室：" label-width="140px">
          <el-select style="width:100%" v-model="form_yuyue.roomId" placeholder="请选择教室">
            <el-option
              v-for="item in tableData_jiaoshi"
              :key="item.roomId"
              :label="item.roomName"
              :value="item.roomId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="*用途：" label-width="140px">
          <el-input v-model="form_yuyue.useDo" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*申请人姓名：" label-width="140px">
          <el-input v-model="form_yuyue.applyUserName" autocomplete="off" placeholder="申请人姓名"></el-input>
        </el-form-item>
        <el-form-item label="*申请人职务：" label-width="140px">
          <el-input v-model="form_yuyue.applyUserPost" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*联系方式" label-width="140px">
          <el-input v-model="form_yuyue.applyUserMobile" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*人数" label-width="140px">
          <el-input v-model="form_yuyue.peopleNum" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*使用日期" label-width="140px">
          <el-date-picker
            style="width:100%"
            v-model="form_yuyue.useDate"
            value-format="yyyy-MM-dd 00:00:00"
            type="date"
            placeholder="使用日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="*开始时间：" label-width="140px">
          <el-time-picker
            style="width:100%"
            v-model="form_yuyue.useStartTime"
            :picker-options="{
              selectableRange: '06:00:00 - 22:00:00'
            }"
            placeholder="开始时间">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="*结束时间：" label-width="140px">
          <el-time-picker
            style="width:100%"
            v-model="form_yuyue.useEndTime"
            :picker-options="{
              selectableRange: '06:00:00 - 22:00:00'
            }"
            placeholder="使用结束时间">
          </el-time-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog_chuangwei = false">取 消</el-button>
        <el-button type="primary" title="请输入内容" @click="tianjia_chuangwei()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        juese: 'guanli',
        panduan: '1',
        imageUrl: '',
        count: 1,
        dialog: false,
        dialog_chuangwei: false,
        depId: '',
        hospitalId: '',
        form: {
          depName: '',
          createTime: '',
          hospitalId: '',
          depImg: '',
          depId: '',
          list: []
        },
        form_yuyue: {
          applyState: '',
          applyUserId: '',
          applyUserName: '',
          applyUserPost: '',
          createTime: '',
          handleMsg: '',
          handleTime: '',
          applyUserMobile: '',
          peopleNum: '',
          roomId: '',
          roomName: '',
          teachingBuildingName: '',
          useDate: '',
          useDo: '',
          useEndTime: '',
          useId: '',
          useStartTime: ''
        },
        dataForm: {
          teachingBuildingName: '',
          roomName: '',
          date: ''
        },
        types: [{
          name: '下线',
          value: 0
        }, {
          name: '空闲',
          value: 1
        }, {
          name: '使用中',
          value: 2
        }, {
          name: '维修中',
          value: 3
        }],
        options: [{
          name: '未处理',
          value: 0
        }, {
          name: '同意',
          value: 1
        }, {
          name: '拒绝',
          value: 2
        }, {
          name: '退回',
          value: 3
        }],
        condition: {
          page: '1',
          limit: '20',
          applyUserName: '',
          roomId: '',
          useDate: '',
          applyState: ''
        },
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
        tableData_jiaoshi: [],
        dataList: [],
        dataList_keshi: [],
        dataList_yiyuan: [],
        dataList_wode: [],
        dataList_all: [],
        pageIndex: 1,
        pageSize: 10,
        total: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated () {
      if (JSON.parse(localStorage.getItem('userInfo')).roleIdList.length) {
        this.juese = 'qita'
        this.getDataList()
      } else {
        this.juese = 'guanli'
        this.getYuyueList()
      }
      this.getJiaoshi()
      // this.getKeshiList()
      // this.hospitalId = JSON.parse(localStorage.getItem('userInfo')).hospitalId
    },
    watch: {
      panduan () {
        this.dataListSelections = []
        if (this.panduan === '1') {
          this.getDataList()
        } else {
          this.getYuyueMyList()
        }
      }
    },
    methods: {
      caozuo (val, state) {
        this.$prompt('请输入回复信息', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({ value }) => {
          let query = {
            useId: val.useId,
            handleMsg: value,
            state: state
          }
          this.$http({
            url: this.$http.adornUrl('/sys/room/use/handle'),
            method: 'post',
            data: this.$http.adornStr(query)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.getYuyueList()
                }
              })
              this.dialog = false
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
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
      qiehuan (val, shuzu, ziduan1, ziduan2) {
        for (let i = 0; i < shuzu.length; i++) {
          if (shuzu[i][ziduan1] + '' === val + '') {
            return shuzu[i][ziduan2]
          }
        }
      },
      yuyue (val) {
        let query = {}
        this.$confirm('此操作将进行预约, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/notice/delete'),
            method: 'post',
            data: this.$http.adornStr(query)
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
      tuihui (val) {
        let query = {}
        this.$confirm('此操作将进行退回, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          query.useId = val.useId
          this.$http({
            url: this.$http.adornUrl('/sys/room/use/back'),
            method: 'get',
            params: this.$http.adornParams(query)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.getYuyueMyList()
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
      dialog_tianjia (val) {
        this.dialog_chuangwei = true
        this.form_yuyue = {
          applyState: '',
          applyUserId: '',
          applyUserName: '',
          applyUserPost: '',
          createTime: '',
          handleMsg: '',
          handleTime: '',
          applyUserMobile: '',
          peopleNum: '',
          roomId: val.roomId,
          roomName: '',
          teachingBuildingName: '',
          useDate: '',
          useDo: '',
          useEndTime: '',
          useId: '',
          useStartTime: ''
        }
      },
      // 添加教室
      tianjia () {
        this.form.depImg = this.imageUrl
        if ((!this.form.depName || this.form.depName + '' === '') && this.form.depName + '' !== '0') {
          this.$message.success('请输入教室名称')
          return
        }
        if ((!this.form.depImg || this.form.depImg + '' === '') && this.form.depImg + '' !== '0') {
          this.$message.success('请上传教室图片')
          return
        }
        let query = {}
        for (let i in this.form) {
          if (this.form[i]) {
            query[i] = this.form[i]
          }
        }
        query.hospitalId = JSON.parse(localStorage.getItem('userInfo')).hospitalId
        this.$http({
          url: this.$http.adornUrl(`/sys/dep/${!this.form.depId ? 'save' : 'update'}`),
          method: 'post',
          data: this.$http.adornStr(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.getKeshiList()
              }
            })
            this.dialog = false
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
      // 添加教室
      tianjia_chuangwei () {
        var date1 = new Date(this.form_yuyue.useStartTime)
        const dateValue1 = this.checkTime(date1.getHours()) + ':' + this.checkTime(date1.getMinutes()) + ':' + this.checkTime(date1.getSeconds())
        var date2 = new Date(this.form_yuyue.useEndTime)
        const dateValue2 = this.checkTime(date2.getHours()) + ':' + this.checkTime(date2.getMinutes()) + ':' + this.checkTime(date2.getSeconds())
        if (!this.form_yuyue.roomId && this.form_yuyue.roomId + '' !== '0') {
          this.$message.success('请选择教室')
          return
        }
        if (!this.form_yuyue.useDo && this.form_yuyue.useDo + '' !== '0') {
          this.$message.success('请输入用途')
          return
        }
        if (!this.form_yuyue.applyUserName && this.form_yuyue.applyUserName + '' !== '0') {
          this.$message.success('请输入申请人姓名')
          return
        }
        if (!this.form_yuyue.applyUserPost && this.form_yuyue.applyUserPost + '' !== '0') {
          this.$message.success('请输入申请人职务')
          return
        }
        if (!this.form_yuyue.applyUserMobile && this.form_yuyue.applyUserMobile + '' !== '0') {
          this.$message.success('请输入联系方式')
          return
        }
        if (this.form_yuyue.peopleNum < 1) {
          this.$message.success('人数最少为1')
          return
        }
        if (!this.form_yuyue.useDate && this.form_yuyue.useDate + '' !== '0') {
          this.$message.success('请选择使用日期')
          return
        }
        if (!this.form_yuyue.useStartTime && this.form_yuyue.useStartTime + '' !== '0') {
          this.$message.success('请选择开始时间')
          return
        }
        if (!this.form_yuyue.useEndTime && this.form_yuyue.useEndTime + '' !== '0') {
          this.$message.success('请选择结束时间')
          return
        }
        if (this.form_yuyue.useStartTime > this.form_yuyue.useEndTime) {
          this.$message.success('使用开始时间不能超过结束时间')
          return
        }
        let query = {}
        for (let i in this.form_yuyue) {
          if (this.form_yuyue[i] || this.form_yuyue[i] + '' === '0') {
            query[i] = this.form_yuyue[i]
          }
        }
        query.useStartTime = (dateValue1.split(':')[0] * 3600 + dateValue1.split(':')[1] * 60 + dateValue1.split(':')[2] * 1) * 1
        query.useEndTime = (dateValue2.split(':')[0] * 3600 + dateValue2.split(':')[1] * 60 + dateValue2.split(':')[2] * 1) * 1
        this.$http({
          url: this.$http.adornUrl('/sys/room/use/save'),
          method: 'post',
          data: this.$http.adornStr(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.getDataList()
              }
            })
            this.dialog_chuangwei = false
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        let query = {}
        for (let i in this.dataForm) {
          query[i] = this.dataForm[i]
        }
        this.$http({
          url: this.$http.adornUrl('/sys/room/use/info'),
          method: 'post',
          data: this.$http.adornData(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            let table = []
            let num = 0
            for (let i = 0; i < data.list.length; i++) {
              for (let j = 0; j < data.list[i].courses.length; j++) {
                table.push(data.list[i].courses[j])
                table[num].roomId = data.list[i].roomId
                table[num].roomName = data.list[i].roomName
                table[num].teachingBuildingName = data.list[i].teachingBuildingName
                table[num].capacity = data.list[i].capacity
                table[num].createTime1 = data.list[i].createTime1
                num++
              }
              if (data.list[i].courses.length < 1) {
                table.push([])
                table[num].roomId = data.list[i].roomId
                table[num].roomName = data.list[i].roomName
                table[num].teachingBuildingName = data.list[i].teachingBuildingName
                table[num].capacity = data.list[i].capacity
                table[num].createTime1 = data.list[i].createTime1
                num++
              }
            }
            console.log(table)
            this.dataList = data.list
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
        })
      },
      // 获取数据列表
      getYuyueList () {
        this.condition.page = this.pageIndex
        this.condition.limit = this.pageSize
        let query = {}
        for (let i in this.condition) {
          if (this.condition[i] !== '') {
            query[i] = this.condition[i]
          }
        }
        this.$http({
          url: this.$http.adornUrl('/sys/room/use/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_all = data.page.list
          } else {
            this.dataList_all = []
          }
        })
      },
      // 获取数据列表
      getYuyueMyList () {
        this.$http({
          url: this.$http.adornUrl('/sys/room/use/list/my'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_wode = data.list
          } else {
            this.dataList_wode = []
          }
        })
      },
      // 获取教室数据列表
      getKeshiList () {
        // this.dataListLoading = true
        let query = {
          hospitalId: this.hospitalId
        }
        this.$http({
          url: this.$http.adornUrl('/sys/dep/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_keshi = data.list
            if (this.dataList_keshi.length && this.depId === '') {
              console.log(666666666666666)
              this.depId = this.dataList_keshi[0].depId
              this.getChuangweiList()
            }
          } else {
            this.dataList_keshi = []
          }
          // this.dataListLoading = false
        })
      },
      // 获取教室数据列表
      getChuangweiList () {
        // this.dataListLoading = true
        let query = {
          depId: this.depId
        }
        this.$http({
          url: this.$http.adornUrl('/sys/dep/bed/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
          } else {
            this.dataList = []
          }
          // this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getYuyueList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getYuyueList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.depId
        })
        this.$confirm(`确定进行删除操作?`, '删除提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/dep/bed/del'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getChuangweiList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      // 删除
      deleteKeshi (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.depId
        })
        this.$confirm(`确定进行删除操作?`, '删除提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/dep/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getKeshiList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
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
      handleAvatarSuccess (res, file) {
        this.imageUrl = 'http://localhost:8001/classroom-api/sys/images/' + res.name
      }
    }
  }
</script>
<style scoped>
.el-dialog {
    position: relative;
    margin: 0 auto 50px;
    background: #fff;
    border-radius: 2px;
    -webkit-box-shadow: 0 1px 3px rgba(0,0,0,.3);
    box-shadow: 0 1px 3px rgba(0,0,0,.3);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 80%!important;
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
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>