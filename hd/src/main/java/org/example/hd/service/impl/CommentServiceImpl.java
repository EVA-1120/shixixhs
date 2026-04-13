package org.example.hd.service.impl;

import org.example.hd.dto.CommentCreateRequest;
import org.example.hd.dto.CommentDTO;
import org.example.hd.entity.Comment;
import org.example.hd.mapper.CommentMapper;
import org.example.hd.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentMapper.findByPostId(postId);
        Map<Long, CommentDTO> map = new HashMap<>();
        List<CommentDTO> rootComments = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDTO dto = new CommentDTO();
            BeanUtils.copyProperties(comment, dto);
            dto.setUsername("用户_" + comment.getUserId());
            dto.setReplies(new ArrayList<>());
            map.put(dto.getId(), dto);
        }

        for (CommentDTO dto : map.values()) {
            if (dto.getParentId() == null || dto.getParentId() == 0L) {
                rootComments.add(dto);
            } else {
                CommentDTO parent = map.get(dto.getParentId());
                if (parent != null) {
                    parent.getReplies().add(dto);
                } else {
                    rootComments.add(dto);
                }
            }
        }
        return rootComments;
    }

    @Override
    @Transactional
    public void createComment(Long postId, CommentCreateRequest request, Long userId) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(request.getContent());
        comment.setParentId(request.getParentId());
        comment.setCreateTime(LocalDateTime.now());

        commentMapper.insert(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("无权删除他人评论");
        }
        commentMapper.deleteById(commentId);
    }
}