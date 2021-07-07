<template>
  <div class="mod-config">
    <el-button style="margin-bottom:10px" type="primary" @click="dialog_tianjia()">添加</el-button>
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
        prop="stuName"
        header-align="center"
        align="center"
        label="优惠券类型"
        width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponType==0">新用户优惠券</el-tag>
          <el-tag type="danger" v-if="scope.row.couponType==1">领取优惠券</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="teaName"
        header-align="center"
        label="优惠券介绍">
        <template slot-scope="scope">
          <span class="shenglue">用户消费满{{scope.row.couponFull}}元优惠{{scope.row.couponMinus}}元</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="answerContent"
        header-align="center"
        align="center"
        label="优惠券剩余数量"
        width="150">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.couponLeftNum">{{scope.row.couponLeftNum}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="teaName"
        header-align="center"
        align="center"
        label="优惠券总数量"
        width="150">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.couponCount">{{scope.row.couponCount}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="优惠券创建日期"
        width="180">
        <template slot-scope="scope">
          <span class="shenglue" :title="scope.row.createTime" v-if="scope.row.createTime">{{scope.row.createTime}}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <!-- <el-table-column
        v-if="$store.state.user.panduan != '学生'"
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <span v-if="scope.row.answerContent">已答复</span>
          <el-button v-else type="text" size="small" @click="dialog_dafu(scope.row.queId)">答复</el-button>
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
    <el-dialog title="回答问题" :visible.sync="dialog">
      <el-form :model="form">
        <el-form-item label="*优惠券类型：" label-width="140px">
          <el-select style="width:100%" v-model="form.couponType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="*优惠券满多少：" label-width="140px">
          <el-input v-model="form.couponFull" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*优惠券减多少：" label-width="140px">
          <el-input v-model="form.couponMinus" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*优惠券总数量：" label-width="140px">
          <el-input v-model="form.couponCount" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="*优惠券剩余数量：" label-width="140px">
          <el-input v-model="form.couponLeftNum" type="number" autocomplete="off"></el-input>
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
          couponCount: '',
          couponId: '',
          couponLeftNum: '',
          couponMinus: '',
          couponType: '',
          createTime: ''
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
      dialog_tianjia (val) {
        this.dialog = true
      },
      tianjia () {
        if ((!this.form.couponType || this.form.couponType + '' === '') && this.form.couponType + '' !== '0') {
          this.$message.success('请选择优惠券类型')
          return
        }
        if ((!this.form.couponType || this.form.couponFull + '' === '') && this.form.couponFull + '' !== '0') {
          this.$message.success('请输入优惠券满多少')
          return
        }
        if ((!this.form.couponMinus || this.form.couponMinus + '' === '') && this.form.couponMinus + '' !== '0') {
          this.$message.success('请输入优惠券减多少')
          return
        }
        if ((!this.form.couponCount || this.form.couponCount + '' === '') && this.form.couponCount + '' !== '0') {
          this.$message.success('请输入优惠券总数量')
          return
        }
        if ((!this.form.couponLeftNum || this.form.couponLeftNum + '' === '') && this.form.couponLeftNum + '' !== '0') {
          this.$message.success('请输入优惠券剩余数量')
          return
        }
        if (this.form.couponFull < this.form.couponMinus) {
          this.$message.success('优惠券的优惠额度不能超过商品价值')
          return
        }
        let query = {}
        for (let i in this.form) {
          query[i] = this.form[i]
        }
        this.$http({
          url: this.$http.adornUrl('/sys/coupon/save'),
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
        this.$http({
          url: this.$http.adornUrl('/sys/coupon/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
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
          return item.proId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/generator/proinfo/delete'),
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