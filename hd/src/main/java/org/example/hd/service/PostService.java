package org.example.hd.service;

import org.example.hd.dto.PostCreateRequest;
import org.example.hd.dto.PostUpdateRequest;
import org.example.hd.dto.PostDTO;
import java.util.List;

public interface PostService {
    Long createPost(Long userId, PostCreateRequest req);
    void updatePost(Long userId, PostUpdateRequest req);
    void deletePost(Long userId, Long postId);
    PostDTO getPost(Long id);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByUserId(Long userId);
}