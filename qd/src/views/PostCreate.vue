<template>
  <div class="post-create">
    <el-card>
      <h2>发布内容</h2>
      <el-form :model="form">
        <el-form-item label="正文">
          <el-input type="textarea" :rows="5" v-model="form.content" placeholder="说点什么吧..." />
        </el-form-item>
        <el-form-item label="图片/视频地址">
          <el-input v-model="form.mediaUrl" placeholder="图片或视频外链" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.mediaType" placeholder="类型">
            <el-option label="无" :value="0" />
            <el-option label="图片" :value="1" />
            <el-option label="视频" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="话题标签">
          <el-input v-model="form.tags" placeholder="多个标签用英文逗号隔开" />
        </el-form-item>
        <el-button type="primary" @click="submit">发布</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { createPost } from '../api/post'
import { Message } from 'element-ui'

export default {
  data() {
    return {
      form: {
        content: '',
        mediaType: 0,
        mediaUrl: '',
        tags: ''
      }
    }
  },
  methods: {
    async submit() {
      if (!this.form.content) {
        Message.warning('内容不能为空')
        return
      }
      await createPost(this.form)
      Message.success('发布成功')
      await this.$router.push('/')
    }
  }
}
</script>