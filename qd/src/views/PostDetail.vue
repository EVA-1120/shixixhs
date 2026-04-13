<template>
  <div class="post-detail">

    <div class="image-window" v-if="formattedImages.length > 0">
      <el-carousel trigger="click" height="400px" :autoplay="false">
        <el-carousel-item v-for="(img, index) in formattedImages" :key="index">
          <img :src="img" class="post-image" alt="帖子图片">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="post-content">
      <h1>{{ post.title }}</h1>
      <div v-html="post.content" class="rich-text"></div>
    </div>

    <hr>

    <comment-list :post-id="postId" />
  </div>
</template>

<script>
import { fetchPost } from '@/api/post';
import CommentList from '@/components/CommentList.vue';

export default {
  name: 'PostDetail',
  components: {
    CommentList
  },
  data() {
    return {
      post: {
        title: '正在加载标题...',
        content: '正在拼命加载内容...',
        mediaUrl: ''
      },
      postId: null
    };
  },
  computed: {
    // 临时方案：适配前端 public/post/ 目录
    formattedImages() {
      if (!this.post || !this.post.mediaUrl) return [];

      let imgArray = Array.isArray(this.post.mediaUrl)
          ? this.post.mediaUrl
          : String(this.post.mediaUrl).split(',');

      return imgArray.map(img => {
        let cleanImg = img.trim();
        if (!cleanImg) return '';
        if (cleanImg.startsWith('http')) return cleanImg;

        // 自动处理路径，确保指向 /post/ 文件夹
        if (cleanImg.startsWith('/post/') || cleanImg.startsWith('post/')) {
          return cleanImg.startsWith('/') ? cleanImg : `/${cleanImg}`;
        } else {
          return `/post/${cleanImg}`;
        }
      }).filter(img => img !== '');
    }
  },
  created() {
    this.postId = this.$route.params.id;
    this.fetchPostDetails();
  },
  methods: {
    async fetchPostDetails() {
      try {
        const response = await fetchPost(this.postId);
        this.post = response.data || response;
      } catch (error) {
        console.error('获取详情失败:', error);
      }
    }
  }
};
</script>

<style scoped>
.post-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

/* 轮播图窗口：保持 400px 高度，背景设为深色或浅灰，让留白不突兀 */
.image-window {
  width: 100%;
  max-width: 500px;
  margin: 0 auto 20px auto;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #2c3e50; /* ⭐ 改为深色背景，留白时更像相框 */
}

/* 图片显示模式修改 */
.post-image {
  width: 100%;
  height: 100%;
  /* ⭐ 核心修改：使用 contain 确保图片完整显示，短边贴合，长边留白 */
  object-fit: contain;
  display: block;
}

.post-content h1 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.rich-text {
  line-height: 1.6;
  color: #444;
  font-size: 16px;
}

hr {
  margin: 30px 0;
  border: 0;
  border-top: 1px solid #eee;
}
</style>