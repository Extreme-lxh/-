<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="实训活动名称" prop="practiceName">
      <el-input v-model="dataForm.practiceName" placeholder="实训活动名称"></el-input>
    </el-form-item>
    <el-form-item label="选报开始时间" prop="signupStartTime">
      <el-date-picker
        v-model="dataForm.signupStartTime"
        value-format="yyyy-MM-dd 00:00:00"
        type="date"
        placeholder="选报开始时间">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="选报结束时间" prop="signupEndTime">
      <el-date-picker
        v-model="dataForm.signupEndTime"
        value-format="yyyy-MM-dd 00:00:00"
        type="date"
        placeholder="选报结束时间">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="实训结束时间" prop="practiceEndTime">
      <el-date-picker
        v-model="dataForm.practiceEndTime"
        value-format="yyyy-MM-dd 00:00:00"
        type="date"
        placeholder="实训结束时间">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="平时成绩占比" prop="basePercent">
      <el-input v-model="dataForm.basePercent" placeholder="平时成绩占比"></el-input>
    </el-form-item>
    <el-form-item label="验收成绩占比" prop="checkPercent">
      <el-input v-model="dataForm.checkPercent" placeholder="验收成绩占比"></el-input>
    </el-form-item>
    <el-form-item label="中期检查占比" prop="middlePercent">
      <el-input v-model="dataForm.middlePercent" placeholder="中期检查成绩占比"></el-input>
    </el-form-item>
    <!-- <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item> -->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          practiceId: 0,
          practiceName: '',
          signupStartTime: '',
          signupEndTime: '',
          practiceEndTime: '',
          basePercent: '',
          checkPercent: '',
          middlePercent: ''
        },
        dataRule: {
          practiceName: [
            { required: true, message: '实训活动名称不能为空', trigger: 'blur' }
          ],
          signupStartTime: [
            { required: true, message: '选报开始时间不能为空', trigger: 'blur' }
          ],
          signupEndTime: [
            { required: true, message: '选报结束时间不能为空', trigger: 'blur' }
          ],
          practiceEndTime: [
            { required: true, message: '实训结束时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      date (date1, date2) {
        var oDate1 = new Date(date1)
        var oDate2 = new Date(date2)
        if (oDate1.getTime() > oDate2.getTime()) {
          return true
        } else {
          return false
        }
      },
      init (id) {
        this.dataForm.practiceId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.practiceId) {
            this.$http({
              url: this.$http.adornUrl(`/sys/practice/info/${this.dataForm.practiceId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                console.log(data)
                this.dataForm.practiceName = data.data.practiceName
                this.dataForm.signupStartTime = data.data.signupStartTime
                this.dataForm.signupEndTime = data.data.signupEndTime
                this.dataForm.practiceEndTime = data.data.practiceEndTime
                this.dataForm.basePercent = data.data.basePercent
                this.dataForm.checkPercent = data.data.checkPercent
                this.dataForm.middlePercent = data.data.middlePercent
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            if (this.date(this.dataForm.signupStartTime, this.dataForm.signupEndTime)) {
              this.$message.error('选报开始时间不能超过选报结束时间')
              return
            }
            if (this.date(this.dataForm.signupEndTime, this.dataForm.practiceEndTime)) {
              this.$message.error('选报结束时间不能超过实训结束时间')
              return
            }
            this.$http({
              url: this.$http.adornUrl(`/sys/practice/${!this.dataForm.practiceId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'practiceId': this.dataForm.practiceId || undefined,
                'practiceName': this.dataForm.practiceName,
                'signupStartTime': this.dataForm.signupStartTime,
                'signupEndTime': this.dataForm.signupEndTime,
                'practiceEndTime': this.dataForm.practiceEndTime,
                'basePercent': this.dataForm.basePercent * 1,
                'checkPercent': this.dataForm.checkPercent * 1,
                'middlePercent': this.dataForm.middlePercent * 1
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
<style>
.el-input {
    position: relative;
    font-size: 14px;
    display: inline-block;
    width: 75% !important;
}
.el-form-item__label {
    text-align: right;
    float: left;
    font-size: 14px;
    color: #606266;
    line-height: 40px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 25% !important;
}
</style>