// src/main/java/org/example/hd/dto/CommentCreateRequest.java
package org.example.hd.dto;

public class CommentCreateRequest {
    // 移除 postId（在service层通过参数传入）
    private String content;
    private Long parentId;  // 修改为 Long 类型

    // 补全 getter 和 setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}