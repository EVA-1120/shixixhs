<template>
  <div class="comment-item">
    <div class="comment-main">
      <p><strong>{{ comment.username }}:</strong> {{ comment.content }}</p>
      <small>{{ new Date(comment.createTime).toLocaleString() }}</small>
      <a @click="showReplyInput = !showReplyInput">回复</a>
      <!-- 假设有删除权限才显示删除按钮 -->
      <a v-if="canDelete" @click="$emit('delete', comment.id)">删除</a>
    </div>

    <!-- 回复输入框 -->
    <div v-if="showReplyInput" class="reply-input">
      <textarea v-model="replyContent" placeholder="写下你的回复..."></textarea>
      <button @click="submitReply">提交回复</button>
    </div>

    <!-- 递归显示子评论 -->
    <div v-if="comment.replies && comment.replies.length > 0" class="replies">
      <comment-item
          v-for="reply in comment.replies"
          :key="reply.id"
          :comment="reply"
          @reply="handleNestedReply"
          @delete="$emit('delete', $event)"
      ></comment-item>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CommentItem',
  props: {
    comment: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      showReplyInput: false,
      replyContent: '',
    };
  },
  computed: {
    // 🌟 核心修改：直接返回 4，对应我们后端写死的测试账号 ga 的 ID
    // 这样只要是 ga 发的评论，就都会显示“删除”按钮
    currentUserId() {
      // 进阶玩法：如果你以后把用户信息存到了 localStorage 里，可以解开下面这行的注释
      // return JSON.parse(localStorage.getItem('userInfo'))?.id || 4;
      return 4;
    },
    canDelete() {
      // 判断这条评论的作者 ID，是不是等于当前登录用户的 ID
      return this.comment.userId === this.currentUserId;
    },
  },
  methods: {
    submitReply() {
      if (!this.replyContent.trim()) return;
      this.$emit('reply', {
        content: this.replyContent,
        parentId: this.comment.id,
      });
      this.replyContent = '';
      this.showReplyInput = false;
    },
    handleNestedReply(replyData) {
      this.$emit('reply', replyData);
    }
  },
};
</script>

<style scoped>
.comment-item {
  margin-left: 20px;
  padding: 10px;
  border-left: 2px solid #eee;
}

.replies {
  margin-top: 10px;
}

.reply-input {
  margin-top: 5px;
}

a {
  cursor: pointer;
  color: blue;
  margin-left: 10px;
}
</style>