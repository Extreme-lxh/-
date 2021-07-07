<template>
  <div class="mod-config">
    <el-tabs type="border-card">
      <el-tab-pane label="订单列表">
        <el-input style="width:150px" v-model="dataForm.userName" placeholder="用户名" />
        <el-select style="width:150px" v-model="dataForm.hospitalId" clearable placeholder="请选择医院">
          <el-option
            v-for="item in dataList_yiyuan"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalId">
          </el-option>
        </el-select>
        <el-select style="width:150px" v-model="dataForm.orderState" clearable placeholder="订单状态">
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button style="margin-bottom:10px" type="primary" @click="getDataList()">搜索</el-button>
        <!-- <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia(true)">添加</el-button> -->
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
            align="center"
            label="医院名称">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.hospitalName">{{scope.row.hospitalName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            align="center"
            label="科室名称">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.depName">{{scope.row.depName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            align="center"
            label="订单状态">
            <template slot-scope="scope">
              <span class="shenglue" v-if="scope.row.state==1" title="进行中">进行中</span>
              <span class="shenglue" v-else title="已结束">已结束</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="开始时间"
            width="180">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.startTime" v-if="scope.row.startTime">{{scope.row.startTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="结束时间"
            width="180">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.finishTime" v-if="scope.row.finishTime">{{scope.row.finishTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            align="center"
            label="客户名">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.username">{{scope.row.username}}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjia(false, scope.row)">修改</el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="财务记录">
        <el-select v-model="form_caiwu.type" placeholder="请选择">
          <el-option
            v-for="item in options_cw"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button style="margin-bottom:10px" type="primary" @click="getCaiwuList()">搜索</el-button>
        <!-- <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia(true)">添加</el-button> -->
        <!-- <el-button :disabled="dataListSelections.length==0" title="将勾选的数据删除掉" style="margin-bottom:10px" type="primary" @click="deleteHandle()">批量删除</el-button> -->
        <el-table
          :data="dataList_caiwu"
          border
          v-loading="dataListLoading_caiwu"
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
            label="用户名">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.username">{{scope.row.username}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="answerContent"
            header-align="center"
            align="center"
            label="类型">
            <template slot-scope="scope">
              <span class="shenglue" :title="qiehuan(scope.row.type, options_cw, 'value' ,'name')">{{qiehuan(scope.row.type, options_cw, 'value' ,'name')}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="teaName"
            header-align="center"
            align="center"
            label="金额">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.money">{{scope.row.money}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            header-align="center"
            align="center"
            label="生成日期"
            width="180">
            <template slot-scope="scope">
              <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="dialog_tianjia(false, scope.row)">修改</el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle_caiwu"
          @current-change="currentChangeHandle_caiwu"
          :current-page="pageIndex_caiwu"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize_caiwu"
          :total="totalPage_caiwu"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </el-tab-pane>
    </el-tabs>
    
    <el-dialog title="医院" :visible.sync="dialog">
      <el-form :model="form">
        <!-- <el-form-item label="*优惠券类型：" label-width="140px">
          <el-select style="width:100%" v-model="form.couponType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="*医院名称：" label-width="140px">
          <el-input v-model="form.hospitalName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*医院所在省：" label-width="140px">
          <el-input v-model="form.province" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*医院所在市：" label-width="140px">
          <el-input v-model="form.city" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*医院地址：" label-width="140px">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog = false">取 消</el-button>
        <el-button type="primary" title="请输入内容" @click="tianjia()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        types: [{
          name: '已结束',
          value: 0
        }, {
          name: '进行中',
          value: 1
        }],
        options_cw: [{
          name: '余额消费',
          value: 0
        }, {
          name: '余额充值',
          value: 1
        }, {
          name: '押金退回',
          value: 2
        }, {
          name: '押金充值',
          value: 3
        }],
        dialog: false,
        form_dingdan: {
          curPage: '',
          limit: '',
          orderState: '',
          hospitalId: '',
          depId: '',
          bedId: '',
          userName: ''
        },
        form_caiwu: {
          curPage: '',
          limit: '',
          type: ''
        },
        form: {
          address: '',
          city: '',
          createTime: '',
          hospitalId: '',
          hospitalName: '',
          province: '',
          list: []
        },
        dataForm: {
          curPage: '',
          limit: '',
          orderState: '',
          hospitalId: '',
          depId: '',
          bedId: '',
          userName: ''
        },
        options: [{
          name: '新用户优惠卷',
          value: 0
        }, {
          name: '领取优惠卷',
          value: 1
        }],
        dataList: [],
        dataList_caiwu: [],
        dataList_yiyuan: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        pageIndex_caiwu: 1,
        pageSize_caiwu: 10,
        totalPage_caiwu: 0,
        dataListLoading: false,
        dataListLoading_caiwu: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated () {
      this.getDataList()
      this.getCaiwuList()
      this.getYiyuanList()
    },
    methods: {
      qiehuan (val, shuzu, ziduan1, ziduan2) {
        for (let i = 0; i < shuzu.length; i++) {
          if (shuzu[i][ziduan1] + '' === val + '') {
            return shuzu[i][ziduan2]
          }
        }
      },
      dialog_tianjia (val, res) {
        if (val) {
          this.dialog = true
          this.form = {
            address: '',
            city: '',
            createTime: '',
            hospitalId: '',
            hospitalName: '',
            province: '',
            list: []
          }
        } else {
          for (let i in this.form) {
            this.form[i] = res[i]
          }
          this.dialog = true
        }
      },
      tianjia () {
        if ((!this.form.hospitalName || this.form.hospitalName + '' === '') && this.form.hospitalName + '' !== '0') {
          this.$message.success('请输入医院名称')
          return
        }
        if ((!this.form.province || this.form.province + '' === '') && this.form.province + '' !== '0') {
          this.$message.success('请输入医院所在省')
          return
        }
        if ((!this.form.city || this.form.city + '' === '') && this.form.city + '' !== '0') {
          this.$message.success('请输入医院所在市')
          return
        }
        if ((!this.form.address || this.form.address + '' === '') && this.form.address + '' !== '0') {
          this.$message.success('请输入地址')
          return
        }
        let query = {}
        for (let i in this.form) {
          query[i] = this.form[i]
        }
        this.$http({
          url: this.$http.adornUrl(`/sys/hospital/${!query.hospitalId ? 'save' : 'update'}`),
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
            this.dialog = false
          } else {
            this.$message.error(data.msg)
          }
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
          } else {
            this.dataList_yiyuan = []
          }
        })
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        let query = {}
        for (let i in this.dataForm) {
          if (this.dataForm[i] || this.dataForm[i] + '' === '0') {
            query[i] = this.dataForm[i]
          }
        }
        query.curPage = this.pageIndex
        query.limit = this.pageSize
        this.$http({
          url: this.$http.adornUrl('/sys/order/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      getCaiwuList () {
        this.dataListLoading_caiwu = true
        let query = {}
        for (let i in this.form_caiwu) {
          if (this.form_caiwu[i] || this.form_caiwu[i] + '' === '0') {
            query[i] = this.form_caiwu[i]
          }
        }
        this.$http({
          url: this.$http.adornUrl('/sys/order/money/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_caiwu = data.data.list
            this.totalPage_caiwu = data.data.totalPage
          } else {
            this.dataList_caiwu = []
            this.totalPage_caiwu = 0
          }
          this.dataListLoading_caiwu = false
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
      // 每页数
      sizeChangeHandle_caiwu (val) {
        this.pageSize_caiwu = val
        this.pageIndex_caiwu = 1
        this.getCaiwuList()
      },
      // 当前页
      currentChangeHandle_caiwu (val) {
        this.pageIndex_caiwu = val
        this.getCaiwuList()
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
          return item.hospitalId
        })
        this.$confirm(`确定进行删除操作?`, '删除提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/hospital/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
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
</style>