package org.example.hd.service;

import org.example.hd.dto.CommentDTO;
import org.example.hd.dto.CommentCreateRequest;
import java.util.List;

public interface CommentService {
    // 获取帖子的评论树
    List<CommentDTO> getCommentsByPostId(Long postId);

    // 创建评论
    void createComment(Long postId, CommentCreateRequest request, Long userId);

    // 删除评论
    void deleteComment(Long commentId, Long userId);
}