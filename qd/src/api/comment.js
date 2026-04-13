import request from '@/utils/request'; // 引入你封装好的带 JWT token 的 axios 实例

// 获取帖子评论列表
export function getCommentsByPostId(postId) {
    return request({
        url: `/posts/${postId}/comments`,
        method: 'get'
    });
}

// 创建新评论或回复
export function createComment(postId, content, parentId) {
    return request({
        url: `/posts/${postId}/comments`,
        method: 'post',
        data: {
            content,
            parentId
        }
    });
}

// 删除评论
export function deleteComment(commentId) {
    return request({
        url: `/comments/${commentId}`,
        method: 'delete'
    });
}