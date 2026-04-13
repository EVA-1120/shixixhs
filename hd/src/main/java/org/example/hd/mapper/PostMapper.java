package org.example.hd.mapper;


import org.example.hd.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostMapper {
    int insertPost(Post post);
    int updatePost(Post post);
    int deletePost(Long id, Long userId);
    Post selectById(Long id);
    List<Post> selectByUserId(Long userId);
    List<Post> selectAllPosts();
}