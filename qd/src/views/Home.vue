<!-- eslint-disable-next-line vue/multi-word-component-names -->
<template>
  <div class="home">
    <el-card>
      <h2>推荐内容流</h2>
      <el-divider></el-divider>
      <div v-if="posts.length">
        <el-card v-for="post in posts" :key="post.id" class="post-card" @click.native="goDetail(post.id)" style="cursor:pointer;">
          <div class="post-header">
            <el-avatar :src="post.avatar" />
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
            <el-tag v-for="tag in (post.tags || '').split(',')" :key="tag" type="info">{{ tag }}</el-tag>
          </div>
        </el-card>
      </div>
      <div v-else>
        <el-empty description="暂无内容" />
      </div>
    </el-card>
  </div>
</template>

<script>
import { fetchAllPosts } from '../api/post'

export default {
  data() {
    return {
      posts: []
    }
  },
  async created() {
    this.posts = await fetchAllPosts()
  },
  methods: {
    goDetail(id) {
      this.$router.push(`/post/${id}`)
    }
  }
}
</script>

<style>
.home { max-width: 800px; margin: 40px auto; }
.post-card { margin-bottom: 28px; }
.post-header { display: flex; align-items: center; gap: 12px; }
.nickname { font-weight: bold; }
.time { margin-left: auto; color: #aaa; font-size: 12px; }
.post-content { margin: 12px 0; }
.media { max-width: 100%; border-radius: 6px; }
.tags { margin-top: 8px; }
</style>