<template>
  <div class="mod-home">
    <h1 align='center'>欢迎来到教室资源管理系统!</h1>

   <div class="opt-container">
    <img :src="images[0]">
  </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        images: [
          'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zzedu.net.cn%2Fimages%2Fjysj%2F2015%2F07%2F09%2F674A38A356D60C1A29B43193C6136786.jpg&refer=http%3A%2F%2Fimg.zzedu.net.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1628063711&t=b0f7110fdaa71969afb1fabfd9cba000'
        ]
      }
    },
    created () {
      let form = JSON.parse(localStorage.getItem('userInfo'))
      console.log(form)
      // if (form.hospitalId || form.hospitalId + '' === '0') {
      //   this.$router.push('/keshi-keshi')
      // } else {
      //   this.$router.push('/caiwu-caiwu')
      // }
    },
    methods: {
      // 获取报表数据
      getData () {
        this.$http({
          url: this.$http.adornUrl('/sys/order/form'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList_yiyuan = data.data
          } else {
            this.dataList_yiyuan = []
          }
        })
      }
    }
  }
</script>

<style>
  .mod-home {
    line-height: 1.5;
  }
  .opt-container {
  display: flex;
  justify-content: center;
  align-items: center;  
}
</style>

