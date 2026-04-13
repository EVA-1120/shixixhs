// src/main/java/org/example/hd/dto/CommentDTO.java
package org.example.hd.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {
    // 从 Comment 实体类复制过来的字段
    private Long id;
    private Long parentId;
    private String content;
    private LocalDateTime createTime;

    // 额外添加用于前端展示的字段
    private Long userId;
    private String username;
    // private String avatar; // 如果有头像

    // 用于构建树形结构的子评论列表
    private List<CommentDTO> replies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDTO> replies) {
        this.replies = replies;
    }
}