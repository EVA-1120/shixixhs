<template>
  <div class="my-posts">
    <el-card>
      <h2>我的内容</h2>
      <el-button type="primary" @click="$router.push('/post/create')">发布新内容</el-button>
      <el-divider></el-divider>
      <div v-if="posts.length">
        <el-card v-for="post in posts" :key="post.id" class="post-card">
          <div class="post-header">
            <img :src="post.avatar" class="avatar" />
            <span class="nickname">{{ post.nickname }}</span>
            <span class="time">{{ post.createTime }}</span>
          </div>
          <div class="post-content">{{ post.content }}</div>
          <div v-if="post.mediaType === 1">
            <img :src="post.mediaUrl" class="media" />
          </div>
          <div v-if="post.mediaType === 2">
            <video :src="post.mediaUrl" controls class="media"></video>
          </div>
          <div class="tags">
            <el-tag v-for="tag in post.tags.split(',')" :key="tag" type="info">{{ tag }}</el-tag>
          </div>
          <div style="margin-top:10px;">
            <el-button size="mini" @click="edit(post)">编辑</el-button>
            <el-button size="mini" type="danger" @click="del(post.id)">删除</el-button>
          </div>
        </el-card>
      </div>
      <div v-else>暂无内容</div>
    </el-card>
    <el-dialog title="编辑内容" :visible.sync="editDialogVisible">
      <el-form :model="editForm">
        <el-form-item label="正文">
          <el-input type="textarea" :rows="4" v-model="editForm.content" />
        </el-form-item>
        <el-form-item label="图片/视频地址">
          <el-input v-model="editForm.mediaUrl" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="editForm.mediaType">
            <el-option label="无" :value="0" />
            <el-option label="图片" :value="1" />
            <el-option label="视频" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="话题标签">
          <el-input v-model="editForm.tags" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="editDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="doEdit">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchMyPosts, updatePost, deletePost } from '../api/post'
import { Message } from 'element-ui'

export default {
  data() {
    return {
      posts: [],
      editDialogVisible: false,
      editForm: {}
    }
  },
  async created() {
    this.loadPosts()
  },
  methods: {
    async loadPosts() {
      this.posts = await fetchMyPosts()
    },
    edit(post) {
      this.editForm = { ...post }
      this.editDialogVisible = true
    },
    async doEdit() {
      await updatePost(this.editForm)
      Message.success('修改成功')
      this.editDialogVisible = false
      this.loadPosts()
    },
    async del(id) {
      await deletePost(id)
      Message.success('已删除')
      this.loadPosts()
    }
  }
}
</script>