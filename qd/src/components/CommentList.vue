<template>
  <div class="comment-list">
    <h3>评论区</h3>

    <!-- 顶级评论输入框 -->
    <div class="comment-form">
      <textarea
          v-model="newCommentContent"
          placeholder="发表你的看法..."
          rows="3"
      ></textarea>
      <button
          @click="submitNewComment"
          :disabled="isSubmitting || isServerDown"
      >
        {{ isSubmitting ? '提交中...' : '发表评论' }}
      </button>
    </div>

    <!-- 错误提示条 -->
    <div v-if="isServerDown" class="server-error">
      <div class="error-content">
        <span>⚠️ 评论服务暂时不可用，请稍后再试</span>
        <button @click="resetErrorState">重试</button>
      </div>
    </div>

    <!-- 评论列表 -->
    <div v-if="comments.length > 0" class="comments-container">
      <comment-item
          v-for="comment in comments"
          :key="comment.id"
          :comment="comment"
          @reply="handleReply"
          @delete="handleDelete"
          :disabled="isServerDown"
      ></comment-item>
    </div>

    <div v-else-if="isLoading" class="loading-indicator">
      加载评论中...
    </div>

    <p v-else class="empty-message">还没有评论，快来抢沙发吧！</p>
  </div>
</template>

<script>
import CommentItem from './CommentItem.vue';
import {
  getCommentsByPostId,
  createComment,
  deleteComment
} from '@/api/comment';

// 最大重试次数
const MAX_RETRIES = 3;
// 重试间隔时间（毫秒）
const RETRY_INTERVAL = 5000;

export default {
  name: 'CommentList',
  components: { CommentItem },
  props: {
    postId: {
      type: [Number, String],
      required: true,
    },
  },
  data() {
    return {
      comments: [],
      newCommentContent: '',
      isLoading: false,
      isSubmitting: false,
      errorCount: 0,
      isServerDown: false,
      retryTimer: null
    };
  },
  created() {
    this.fetchComments();
  },
  beforeDestroy() {
    // 清除所有定时器
    this.clearRetryTimer();
  },
  methods: {
    clearRetryTimer() {
      if (this.retryTimer) {
        clearTimeout(this.retryTimer);
        this.retryTimer = null;
      }
    },

    resetErrorState() {
      this.errorCount = 0;
      this.isServerDown = false;
      this.fetchComments();
    },

    async fetchComments() {
      // 清除之前的定时器
      this.clearRetryTimer();

      // 防止连续多次错误请求
      if (this.errorCount > MAX_RETRIES) {
        this.isServerDown = true;
        return;
      }

      this.isLoading = true;
      try {
        const response = await getCommentsByPostId(this.postId);

        // 重置错误状态
        this.errorCount = 0;
        this.isServerDown = false;

        // 处理不同的响应格式
        if (Array.isArray(response)) {
          this.comments = response;
        } else if (response && Array.isArray(response.data)) {
          this.comments = response.data;
        } else {
          throw new Error('无效的评论数据格式');
        }

      } catch (error) {
        console.error('获取评论失败', error);
        this.errorCount++;

        let errorMsg = '获取评论失败';
        if (error.response) {
          // 从响应中提取错误信息
          const serverMsg = error.response.data?.error || error.response.statusText;
          errorMsg += `: ${serverMsg} (状态码: ${error.response.status})`;
        } else if (error.message) {
          errorMsg += `: ${error.message}`;
        } else {
          errorMsg += '，请稍后重试';
        }

        this.$message.error(errorMsg);

        // 自动重试（如果错误次数不超过最大值）
        if (this.errorCount <= MAX_RETRIES) {
          this.retryTimer = setTimeout(() => {
            this.fetchComments();
          }, RETRY_INTERVAL);
        } else {
          this.isServerDown = true;
        }
      } finally {
        this.isLoading = false;
      }
    },

    async submitNewComment() {
      if (this.isServerDown) {
        this.$message.warning('评论服务暂时不可用');
        return;
      }

      const content = this.newCommentContent.trim();
      if (!content) {
        this.$message.warning('评论内容不能为空');
        return;
      }

      this.isSubmitting = true;
      try {
        await createComment(this.postId, content, null);
        this.$message.success('评论发表成功');
        this.newCommentContent = '';

        // 重新加载评论列表
        await this.fetchComments();

      } catch (error) {
        console.error('发表评论失败', error);

        let errorMsg = '发表评论失败';
        if (error.response) {
          // 处理不同的错误状态码
          if (error.response.status === 404) {
            errorMsg = '评论API地址错误，请联系管理员';
          } else if (error.response.status === 500) {
            errorMsg = '服务器内部错误，请稍后重试';
          } else {
            const serverMsg = error.response.data?.error || error.response.statusText;
            errorMsg += `: ${serverMsg}`;
          }
        } else if (error.message) {
          errorMsg += `: ${error.message}`;
        } else {
          errorMsg += '，请检查网络连接';
        }

        this.$message.error(errorMsg);
      } finally {
        this.isSubmitting = false;
      }
    },

    async handleReply(replyData) {
      if (this.isServerDown) {
        this.$message.warning('评论服务暂时不可用');
        return;
      }

      try {
        // 创建回复评论
        await createComment(
            this.postId,
            replyData.content,
            replyData.parentId
        );

        this.$message.success('回复发表成功');

        // 重新加载评论列表
        await this.fetchComments();

      } catch (error) {
        console.error('发表回复失败', error);

        let errorMsg = '发表回复失败';
        if (error.response) {
          const serverMsg = error.response.data?.error || error.response.statusText;
          errorMsg += `: ${serverMsg}`;
        } else if (error.message) {
          errorMsg += `: ${error.message}`;
        } else {
          errorMsg += '，请检查网络连接';
        }

        this.$message.error(errorMsg);
      }
    },

    async handleDelete(commentId) {
      if (this.isServerDown) {
        this.$message.warning('评论服务暂时不可用');
        return;
      }

      this.$confirm('确定要删除这条评论吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await deleteComment(commentId);
          this.$message.success('评论已删除');

          // 重新加载评论列表
          await this.fetchComments();

        } catch (error) {
          console.error('删除评论失败', error);
          let message = '删除失败，请稍后重试';

          if (error.response) {
            if (error.response.status === 403) {
              message = '没有权限删除此评论';
            } else if (error.response.status === 404) {
              message = '评论不存在或已被删除';
            }
          }

          this.$message.error(message);
        }
      }).catch(() => {
        // 用户取消删除
      });
    }
  },
};
</script>

<style scoped>
.comment-list {
  margin-top: 2rem;
  padding: 1rem;
  border-top: 1px solid #eee;
  position: relative;
}

.comment-form {
  margin-bottom: 2rem;
}

.comment-form textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: vertical;
  margin-bottom: 0.5rem;
}

.comment-form button {
  padding: 0.5rem 1rem;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-form button:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}

.comment-form button:hover:not(:disabled) {
  background-color: #66b1ff;
}

.comments-container {
  margin-top: 1rem;
}

.loading-indicator {
  text-align: center;
  padding: 1rem;
  color: #999;
}

.empty-message {
  text-align: center;
  padding: 2rem;
  color: #999;
}

.server-error {
  background-color: #fff6f6;
  border: 1px solid #ffd6d6;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 20px;
  color: #ff4d4d;
}

.error-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.error-content button {
  padding: 5px 10px;
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.error-content button:hover {
  background-color: #ff3333;
}
</style>