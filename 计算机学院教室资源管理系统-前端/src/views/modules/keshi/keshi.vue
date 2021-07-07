<template>
  <div class="padding:0">
    <el-tabs  v-model="panduan" type="border-card">
      <el-tab-pane name="1" label="床位管理">
        <el-select style="width:150px" v-model="depId" placeholder="请添加科室">
          <el-option
            v-for="item in dataList_keshi"
            :key="item.depId"
            :label="item.depName"
            :value="item.depId">
          </el-option>
        </el-select>
        <el-button style="margin-bottom:10px" type="primary" @click="getChuangweiList()">搜索</el-button>
        <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia(true)">添加床位</el-button>
        <el-button :disabled="dataListSelections.length==0" title="将勾选的数据删除掉" style="margin-bottom:10px" type="primary" @click="deleteHandle()">批量删除</el-button>
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
            label="床位图片"
            width="120">
            <template slot-scope="scope">
              <img width="100%" :src="scope.row.bedImg">
            </template>
          </el-table-column>
          <el-table-column
            prop="stuName"
            header-align="center"
            align="center"
            label="床位状态"
            width="120">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.bedState==0">下线</el-tag>
              <el-tag v-if="scope.row.bedState==1">空闲</el-tag>
              <el-tag v-if="scope.row.bedState==2">使用中</el-tag>
              <el-tag v-if="scope.row.bedState==3">维修</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="床位价格">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.bedHourlyPrice">{{scope.row.bedHourlyPrice}}元/小时</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="添加日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjia(false, scope.row)">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane name="2" label="科室管理">
        <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjiakeshi(true)">添加科室</el-button>
        <el-button :disabled="dataListSelections.length==0" title="将勾选的数据删除掉" style="margin-bottom:10px" type="primary" @click="deleteKeshi()">批量删除</el-button>
        <el-table
          :data="dataList_keshi"
          border
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
            label="科室图片"
            width="120">
            <template slot-scope="scope">
              <img width="100%" :src="scope.row.depImg">
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="科室名称">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.depName">{{scope.row.depName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="添加日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjiakeshi(false, scope.row)">修改</el-button>
              <el-button type="text" size="small" @click="panduan='1';depId=scope.row.depId">床位列表</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <!-- <el-tab-pane name="3" label="维修">
        <el-table
          :data="dataList_weixiu"
          border
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
            label="科室图片"
            width="120">
            <template slot-scope="scope">
              <img width="100%" :src="scope.row.depImg">
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            label="科室名称">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.depName">{{scope.row.depName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="添加日期">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjiakeshi(false, scope.row)">修改</el-button>
              <el-button type="text" size="small" @click="panduan='1';depId=scope.row.depId">床位列表</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane> -->
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
    <el-dialog title="科室" :visible.sync="dialog">
      <el-form :model="form">
        <el-form-item label="*科室名称：" label-width="140px">
          <el-input v-model="form.depName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*科室图片：" label-width="140px">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8082/classroom-api/sys/file/upload"
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
    <el-dialog title="床位" :visible.sync="dialog_chuangwei">
      <el-form :model="form_chuangwei">
        <el-form-item label="*床位状态：" label-width="140px">
          <el-select v-model="form_chuangwei.bedState" style="width:100%" placeholder="请选择">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="*所属科室：" label-width="140px">
          <el-select v-model="form_chuangwei.depId" style="width:100%" placeholder="请选择">
            <el-option
              v-for="item in dataList_keshi"
              :key="item.depId"
              :label="item.depName"
              :value="item.depId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="!form_chuangwei.bedId" label="*床位数量：" label-width="140px">
          <el-input v-model="count" type="number" min="1" autocomplete="off" placeholder="添加床位数量，最少添加一张"></el-input>
        </el-form-item>
        <el-form-item label="*床位每小时价格：" label-width="140px">
          <el-input v-model="form_chuangwei.bedHourlyPrice" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*床位种类：" label-width="140px">
          <el-input v-model="form_chuangwei.bedType" autocomplete="off"></el-input>
        </el-form-item>
        <!-- <el-form-item label="*床位状态：" label-width="140px">
          <el-input v-model="form_chuangwei.bedState" autocomplete="off"></el-input>
        </el-form-item> -->
        <el-form-item label="*床位图片：" label-width="140px">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8082/classroom-api/sys/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
        form_chuangwei: {
          bedHourlyPrice: '',
          bedId: '',
          bedImg: '',
          bedState: 1,
          bedType: '',
          createTime: '',
          depId: '',
          hospitalId: ''
        },
        dataForm: {
          hospitalName: '',
          province: '',
          city: ''
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
          name: '新用户优惠卷',
          value: 0
        }, {
          name: '领取优惠卷',
          value: 1
        }],
        dataList: [],
        dataList_keshi: [],
        dataList_yiyuan: [],
        dataList_weixiu: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated () {
      this.depId = ''
      this.getDataList()
      this.getKeshiList()
      this.hospitalId = JSON.parse(localStorage.getItem('userInfo')).hospitalId
      // if (!this.hospitalId && this.hospitalId + '' !== '0') {
      //   this.getYiyuanList()
      // } else {
      //   this.getKeshiList()
      // }
      // this.getChuangweiList()
    },
    watch: {
      depId () {
        this.getChuangweiList()
      },
      panduan () {
        this.dataListSelections = []
        if (this.panduan === '3') {
          this.getWeixiuList()
        }
      }
    },
    methods: {
      qiehuan (val, shuzu, ziduan1, ziduan2) {
        for (let i = 0; i < shuzu.length; i++) {
          if (shuzu[i][ziduan1] + '' === val + '') {
            return shuzu[i][ziduan2]
          }
        }
      },
      dialog_tianjiakeshi (val, res) {
        if (val) {
          this.dialog = true
          this.form = {
            depName: '',
            createTime: '',
            hospitalId: '',
            depImg: '',
            depId: '',
            list: []
          }
          this.imageUrl = ''
        } else {
          for (let i in this.form) {
            if (res[i] || res[i] + '' === '0') {
              this.form[i] = res[i]
            }
          }
          this.imageUrl = res.depImg
          this.dialog = true
        }
      },
      dialog_tianjia (val, res) {
        if (val) {
          this.dialog_chuangwei = true
          this.form_chuangwei = {
            bedHourlyPrice: '',
            bedId: '',
            bedImg: '',
            bedState: 1,
            bedType: '',
            createTime: '',
            depId: '',
            hospitalId: ''
          }
          this.imageUrl = ''
          this.count = 1
        } else {
          for (let i in res) {
            if (res[i] || res[i] + '' === '0') {
              this.form_chuangwei[i] = res[i]
            }
          }
          this.imageUrl = res.bedImg
          this.count = 1
          this.dialog_chuangwei = true
        }
      },
      // 添加科室
      tianjia () {
        this.form.depImg = this.imageUrl
        if ((!this.form.depName || this.form.depName + '' === '') && this.form.depName + '' !== '0') {
          this.$message.success('请输入科室名称')
          return
        }
        if ((!this.form.depImg || this.form.depImg + '' === '') && this.form.depImg + '' !== '0') {
          this.$message.success('请上传科室图片')
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
      // 添加床位
      tianjia_chuangwei () {
        this.form_chuangwei.bedImg = this.imageUrl
        if (!this.form_chuangwei.depId && this.form_chuangwei.depId + '' !== '0') {
          this.$message.success('请选择科室')
          return
        }
        if (this.count < 1 && !this.form_chuangwei.bedId) {
          this.$message.success('床位数量最少位1')
          return
        }
        if (!this.form_chuangwei.bedHourlyPrice && this.form_chuangwei.bedHourlyPrice + '' !== '0') {
          this.$message.success('请输入床位价格')
          return
        }
        if (!this.form_chuangwei.bedType && this.form_chuangwei.bedType + '' !== '0') {
          this.$message.success('请输入床位种类')
          return
        }
        if (!this.form_chuangwei.bedImg && this.form_chuangwei.bedImg + '' !== '0') {
          this.$message.success('请上传床位图片')
          return
        }
        let query = {}
        for (let i in this.form_chuangwei) {
          if (this.form_chuangwei[i] || this.form_chuangwei[i] + '' === '0') {
            query[i] = this.form_chuangwei[i]
          }
        }
        query.hospitalId = JSON.parse(localStorage.getItem('userInfo')).hospitalId
        // query.depName = this.qiehuan(query.depId, this.dataList_keshi, 'depId', 'depName')
        // query.hospitalName = this.qiehuan(query.hospitalId, this.dataList_yiyuan, 'hospitalId', 'hospitalName')
        this.$http({
          url: this.$http.adornUrl(`/sys/dep/bed/${!this.form_chuangwei.bedId ? 'add/' + this.count : 'update'}`),
          method: 'post',
          data: this.$http.adornStr(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.getChuangweiList()
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
          if (this.dataForm[i]) {
            query[i] = this.dataForm[i]
          }
        }
        this.$http({
          url: this.$http.adornUrl('/sys/hospital/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
        })
      },
      // 获取数据列表
      getWeixiuList () {
        // let query = {}
        // for (let i in this.dataForm) {
        //   if (this.dataForm[i]) {
        //     query[i] = this.dataForm[i]
        //   }
        // }
        this.$http({
          url: this.$http.adornUrl('/sys/dep/bed/repaire/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_weixiu = data.list
          } else {
            this.dataList_weixiu = []
          }
        })
      },
      // 获取科室数据列表
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
      // 获取床位数据列表
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
            this.dataList = data.data
          } else {
            this.dataList = []
          }
          // this.dataListLoading = false
        })
      },
      // 获取医院数据列表
      getYiyuanList () {
        this.$http({
          url: this.$http.adornUrl('/sys/hospital/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_yiyuan = data.data
            if (this.dataList_yiyuan.length) {
              this.hospitalId = this.dataList_yiyuan[0].hospitalId
              this.getKeshiList()
            }
          } else {
            this.dataList_yiyuan = []
          }
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
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
      handleAvatarSuccess (res, file) {
        this.imageUrl = 'http://localhost:8082/classroom-api/sys/images/' + res.name
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