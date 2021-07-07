<template>
  <div class="mod-config">
    <el-input style="width:150px" v-model="dataForm.hospitalName" placeholder="医院的名称" />
    <el-input style="width:150px" v-model="dataForm.province" placeholder="医院所在省" />
    <el-input style="width:150px" v-model="dataForm.city" placeholder="医院所在市" />
    <el-button style="margin-bottom:10px" type="primary" @click="getDataList()">搜索</el-button>
    <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia(true)">添加</el-button>
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
        label="医院名称">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.hospitalName">{{scope.row.hospitalName}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="answerContent"
        header-align="center"
        align="center"
        label="所在省"
        width="100">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.province">{{scope.row.province}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="teaName"
        header-align="center"
        align="center"
        label="所在市"
        width="100">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.city">{{scope.row.city}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="teaName"
        header-align="center"
        align="center"
        label="医院地址">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.address">{{scope.row.address}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="医院加入日期"
        width="180">
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
    <!-- <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination> -->
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
        dialog: false,
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
          hospitalName: '',
          province: '',
          city: ''
        },
        options: [{
          name: '新用户优惠卷',
          value: 0
        }, {
          name: '领取优惠卷',
          value: 1
        }],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated () {
      this.getDataList()
    },
    methods: {
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
            this.dataList = data.data
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
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