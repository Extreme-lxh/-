<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="角色" size="mini" prop="roleIdList">
        <el-radio-group v-model="roleIdList">
          <el-radio v-for="role in roleList" :key="role.roleId" :label="role.roleId">{{ role.roleName }}</el-radio>
        </el-radio-group>
        <!-- <el-checkbox-group v-model="dataForm.roleIdList">
          <el-checkbox v-for="role in roleList" :key="role.roleId" :label="role.roleId">{{ role.roleName }}</el-checkbox>
        </el-checkbox-group> -->
      </el-form-item>
      <!-- <el-form-item v-if="panduan_juese=='科室'" label="*科室编号" prop="depId">
        <el-select style="width:100%" v-model="dataForm.depId" placeholder="科室编号">
          <el-option
            v-for="item in keshiList"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalId">
          </el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item v-if="panduan_juese=='医院'" label="*医院编号" prop="hospitalId">
        <el-select style="width:100%" v-model="dataForm.hospitalId" placeholder="医院编号">
          <el-option
            v-for="item in dataList"
            :key="item.hospitalId"
            :label="item.hospitalName"
            :value="item.hospitalId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="dataForm.username" placeholder="登录帐号"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" :class="{ 'is-required': !dataForm.id }">
        <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="comfirmPassword" :class="{ 'is-required': !dataForm.id }">
        <el-input v-model="dataForm.comfirmPassword" type="password" placeholder="确认密码"></el-input>
      </el-form-item>
      <!-- <el-form-item label="邮箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item> -->
      <el-form-item label="状态" size="mini" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">正常</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // import { isEmail, isMobile } from '@/utils/validate'
  export default {
    data () {
      var validatePassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          callback(new Error('密码不能为空'))
        } else {
          callback()
        }
      }
      var validateComfirmPassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          callback(new Error('确认密码不能为空'))
        } else if (this.dataForm.password !== value) {
          callback(new Error('确认密码与密码输入不一致'))
        } else {
          callback()
        }
      }
      // var validateEmail = (rule, value, callback) => {
      //   if (!isEmail(value)) {
      //     callback(new Error('邮箱格式错误'))
      //   } else {
      //     callback()
      //   }
      // }
      // var validateMobile = (rule, value, callback) => {
      //   if (!isMobile(value)) {
      //     callback(new Error('手机号格式错误'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        panduan_juese: '',
        visible: false,
        roleList: [],
        roleIdList: '2',
        dataList: [],
        keshiList: [],
        dataForm: {
          id: '',
          createTime: '',
          createUserId: '',
          email: '',
          password: '',
          comfirmPassword: '',
          salt: '',
          userId: '',
          mobile: '',
          roleIdList: [],
          username: '',
          depId: '', // 科室编号
          hospitalId: '', // 医院编号
          status: 1
        },
        dataRule: {
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          password: [
            { validator: validatePassword, trigger: 'blur' }
          ],
          comfirmPassword: [
            { validator: validateComfirmPassword, trigger: 'blur' }
          ]
          // email: [
          //   { required: true, message: '邮箱不能为空', trigger: 'blur' },
          //   { validator: validateEmail, trigger: 'blur' }
          // ],
          // mobile: [
          //   { required: true, message: '手机号不能为空', trigger: 'blur' },
          //   { validator: validateMobile, trigger: 'blur' }
          // ]
        }
      }
    },
    watch: {
      roleIdList () {
        for (let i = 0; i < this.roleList.length; i++) {
          if (this.roleIdList + '' === this.roleList[i].roleId + '') {
            this.panduan_juese = this.roleList[i].roleName
          }
        }
      }
    },
    methods: {
      init (id) {
        // this.dataForm.username = ''
        this.dataForm.id = id || 0
        this.$http({
          url: this.$http.adornUrl('/sys/role/select'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.roleList = data && data.code === 0 ? data.list : []
          if (this.roleList.length) {
            console.log(localStorage.getItem('roleIdList'))
            // if (localStorage.getItem('roleIdList') && localStorage.getItem('roleIdList') !== 0 + '') {
            //   this.roleIdList = localStorage.getItem('roleIdList') * 1
            // } else {
            //   this.roleIdList = this.roleList[0] * 1
            // }
            this.roleIdList = ''
          }
        }).then(() => {
          this.visible = true
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
          })
        }).then(() => {
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/sys/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.user
                this.dataForm.id = id
                this.dataForm.password = ''
                // this.dataForm.username = data.user.username
                // this.dataForm.salt = data.user.salt
                // this.dataForm.email = data.user.email
                // this.dataForm.mobile = data.user.mobile
                // this.dataForm.roleIdList = data.user.roleIdList
                // this.dataForm.status = data.user.status * 1
                // this.dataForm.stuClass = data.user.stuClass
                // this.dataForm.depId = data.user.depId
                // this.dataForm.hospitalId = data.user.hospitalId
              }
            })
          }
        })
      },
      getDataList () {
        let query = {}
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
        })
      },
      getKeshiList () {
        let query = {}
        this.$http({
          url: this.$http.adornUrl('/sys/hospital/list'),
          method: 'get',
          params: this.$http.adornParams(query)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.keshiList = data.data
          } else {
            this.keshiList = []
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.dataForm.roleIdList = []
        if (this.roleIdList === '') {
          this.$message.success('请选择角色')
          return
        }
        this.dataForm.roleIdList.push(this.roleIdList)
        localStorage.setItem('roleIdList', this.roleIdList)
        console.log(this.dataForm)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let query = {}
            for (let i in this.dataForm) {
              if (this.dataForm[i] || this.dataForm[i] + '' === '0') {
                query[i] = this.dataForm[i]
              }
            }
            this.$http({
              url: this.$http.adornUrl(`/sys/user/${!this.dataForm.id ? 'save' : 'update'}`),
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
