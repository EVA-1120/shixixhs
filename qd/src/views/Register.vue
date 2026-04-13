<!-- eslint-disable-next-line vue/multi-word-component-names -->
<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2>注册</h2>
      <el-form :model="form" @submit.native.prevent="onRegister">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-button type="primary" @click="onRegister">注册</el-button>
        <el-link type="primary" @click="goLogin">已有账号？去登录</el-link>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import api from '../api/request'
import { Message } from 'element-ui'

export default {
  data() {
    return {
      form: {
        username: '',
        nickname: '',
        password: ''
      }
    }
  },
  methods: {
    async onRegister() {
      try {
        await api.post('/user/register', this.form)
        Message.success('注册成功，请登录')
        this.$router.push('/login')
      } catch (e) {
        // ⭐ 动态提取错误信息
        let errorMsg = '注册失败，请检查网络情况' // 保底提示

        if (e.response && e.response.data) {
          // 如果后端返回的是纯文本（比如 "用户名已存在"）
          if (typeof e.response.data === 'string') {
            errorMsg = e.response.data
          }
          // 如果后端返回的是 JSON 对象
          else if (e.response.data.message) {
            errorMsg = e.response.data.message
          } else if (e.response.data.error) {
            errorMsg = e.response.data.error
          }
        } else if (e.message) {
          errorMsg = e.message // 捕获网络断开等原生JS错误
        }

        Message.error(errorMsg)
      }
    },
    goLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style>
.register-container { display: flex; justify-content: center; align-items: center; height: 100vh; }
.register-card { width: 350px; }
</style>