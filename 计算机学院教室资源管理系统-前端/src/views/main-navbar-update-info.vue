<template>
  <el-dialog
    title="个人信息"
    :visible.sync="visible"
    :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      

      <!-- 学生的信息 -->
      <el-form-item v-if="dataForm.stuId" label="学号:" prop="stuId">
        <span>{{ dataForm.stuId }}</span>
      </el-form-item>
      <el-form-item v-if="dataForm.stuId" label="班级:" prop="stuClass">
        <span v-if="dataForm.stuClass">{{ dataForm.stuClass }}</span>
        <span v-else>-</span>
      </el-form-item>

      <!-- 教师的信息 -->
      <!-- <el-form-item v-if="dataForm.teaId" label="班级" prop="password">
        <el-input disabled v-model="dataForm.stuClass"></el-input>
      </el-form-item> -->

      <!-- 通用的信息 -->
      <el-form-item label="用户名:" prop="username">
        <!-- <span>{{ userName }}</span> -->
        <el-input maxlength="11" show-word-limit v-model="dataForm.username" placeholder="请输入11位用户名"></el-input>
      </el-form-item>
      <!-- <el-form-item label="手机号:" prop="mobile">
        <el-input maxlength="11" show-word-limit v-model="dataForm.mobile" placeholder="请输入11位手机号"></el-input>
      </el-form-item>
      <el-form-item label="邮箱:" prop="email">
        <el-input v-model="dataForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item> -->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { clearLoginInfo } from '@/utils'
  export default {
    data () {
      // var validateConfirmPassword = (rule, value, callback) => {
      //   if (this.dataForm.newPassword !== value) {
      //     callback(new Error('确认密码与新密码不一致'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        panduan: 1, // 用户的身份：学生1，老师2，管理员3
        visible: false,
        dataForm: {
          createTime: '',
          createUserId: 0,
          email: '',
          mobile: '',
          password: '',
          roleIdList: [
            0
          ],
          salt: '',
          status: 0,
          stuClass: '',
          stuId: '',
          teaId: '',
          userId: 0,
          username: ''
        },
        dataRule: {
          // mobile: [
          //   { required: true, message: '手机号不能为空', trigger: 'blur' }
          // ],
          // email: [
          //   { required: true, message: '邮箱不能为空', trigger: 'blur' }
          // ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ]
          // password: [
          //   { required: true, message: '原密码不能为空', trigger: 'blur' }
          // ],
          // newPassword: [
          //   { required: true, message: '新密码不能为空', trigger: 'blur' }
          // ],
          // confirmPassword: [
          //   { required: true, message: '确认密码不能为空', trigger: 'blur' },
          //   { validator: validateConfirmPassword, trigger: 'blur' }
          // ]
        }
      }
    },
    computed: {
      userName: {
        get () { return this.$store.state.user.name }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      }
    },
    created () {
      console.log(JSON.parse(localStorage.getItem('userInfo')))
      this.dataForm = JSON.parse(localStorage.getItem('userInfo'))
      console.log(this.dataForm)
      // if (this.dataForm.stuId) {
      //   this.panduan = 1
      // }
      // if (this.dataForm.teaId) {
      //   this.panduan = 2
      // }
      // if (this.dataForm.userId) {
      //   this.panduan = 3
      // }
    },
    mounted () {
      console.log(this.$store.state.user)
    },
    methods: {
      // 初始化
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let query = {}
            // for (let i in this.dataForm) {
            //   query[i] = this.dataForm[i]
            // }
            query.username = this.dataForm.username
            query.userId = this.dataForm.userId
            // query.roleIdList = this.dataForm.roleIdList
            this.$http({
              url: this.$http.adornUrl('/sys/user/update'),
              method: 'post',
              data: this.$http.adornData(query)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$nextTick(() => {
                      this.mainTabs = []
                      clearLoginInfo()
                      this.$router.replace({ name: 'login' })
                    })
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

