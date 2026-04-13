// src/main/java/org/example/hd/mapper/CommentMapper.java
package org.example.hd.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.hd.entity.Comment;
import java.util.List;

@Mapper
public interface CommentMapper {
    // 根据帖子ID查询所有评论
    List<Comment> findByPostId(Long postId);

    // 插入新评论 (返回值为受影响行数，保持 int)
    int insert(Comment comment);

    // 根据ID查询评论（用于权限验证）
    Comment findById(Long id);

    // 删除评论 (返回值为受影响行数，保持 int)
    int deleteById(Long id);

    /**
     * 根据父评论ID查找所有直接子评论的ID
     * @param parentId 父评论ID
     * @return 子评论ID列表 (修改为 Long 类型)
     */
    List<Long> findChildrenIds(@Param("parentId") Long parentId);
}