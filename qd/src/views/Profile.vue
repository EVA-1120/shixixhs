<!-- eslint-disable-next-line vue/multi-word-component-names -->
<template>
  <div class="profile-container" v-if="user">
    <el-card>
      <h2>个人信息</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="form.avatar" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" placeholder="请选择">
            <el-option label="未知" :value="0" />
            <el-option label="男" :value="1" />
            <el-option label="女" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.bio" />
        </el-form-item>
        <el-button type="primary" @click="onUpdate">保存</el-button>
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
        avatar: '',
        email: '',
        gender: 0,
        bio: ''
      }
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    }
  },
  created() {
    if (this.user) {
      Object.assign(this.form, this.user)
    }
  },
  methods: {
    async onUpdate() {
      try {
        const data = await api.put('/user/me', this.form)
        this.$store.commit('setUser', data)
        Message.success('信息更新成功')
      } catch (e) {
        Message.error('更新信息失败')
      }
    }
  }
}
</script>

<style>
.profile-container { max-width: 500px; margin: 40px auto; }
</style>