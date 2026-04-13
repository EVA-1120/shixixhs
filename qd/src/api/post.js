// src/api/post.js
import request from './request'; // 统一使用 request（去掉重复的 api 导入）

// ---------------- 帖子相关 API ----------------
// 发布内容
export function createPost(data) {
    return request({
        url: '/post/create',
        method: 'post',
        data
    });
}

// 编辑内容
export function updatePost(data) {
    return request({
        url: '/post/update',
        method: 'post',
        data
    });
}

// 删除内容
export function deletePost(id) {
    return request({
        url: `/post/delete/${id}`,
        method: 'post'
    });
}

// 获取所有内容
export function fetchAllPosts() {
    return request({
        url: '/post/all',
        method: 'get'
    });
}

// 获取单个内容（帖子详情）
export function fetchPost(id) {
    return request({
        url: `/post/${id}`,
        method: 'get'
    });
}

// 获取我的内容
export function fetchMyPosts() {
    return request({
        url: '/post/my',
        method: 'get'
    });
}

// ---------------- 评论相关 API（修正路径，与后端完全匹配） ----------------
// 获取帖子的评论列表
export function getCommentsByPostId(postId) {
    return request({
        url: `/posts/${postId}/comments`, // 匹配后端 @GetMapping("/posts/{postId}/comments")
        method: 'get'
    });
}

// 创建评论或回复
export function createComment(postId, data) {
    // 修正：postId 放路径，data 放请求体（匹配后端 @PathVariable + @RequestBody）
    // data 格式: { content, parentId }（parentId 可选，回复时传）
    return request({
        url: `/posts/${postId}/comments`, // 匹配后端 @PostMapping("/posts/{postId}/comments")
        method: 'post',
        data
    });
}

// 删除评论
export function deleteComment(commentId) {
    return request({
        url: `/comments/${commentId}`, // 匹配后端 @DeleteMapping("/comments/{commentId}")
        method: 'delete'
    });
}