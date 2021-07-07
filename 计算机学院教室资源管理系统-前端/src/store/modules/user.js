export default {
  namespaced: true,
  state: {
    id: 0,
    name: '',
    panduan: '学生' // 判断用户的身份权限
  },
  mutations: {
    updateId (state, id) {
      state.id = id
    },
    updateName (state, name) {
      state.name = name
    },
    updatePanduan (state, panduan) {
      state.panduan = panduan
    }
  }
}
