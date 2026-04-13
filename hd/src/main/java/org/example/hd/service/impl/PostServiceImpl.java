package org.example.hd.service.impl;

import org.example.hd.dto.PostCreateRequest;
import org.example.hd.dto.PostDTO;
import org.example.hd.dto.PostUpdateRequest;
import org.example.hd.entity.Post;
import org.example.hd.entity.User;
import org.example.hd.mapper.PostMapper;
import org.example.hd.mapper.UserMapper;
import org.example.hd.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Long createPost(Long userId, PostCreateRequest req) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(req.getContent());
        post.setMediaType(req.getMediaType());
        post.setMediaUrl(req.getMediaUrl());
        post.setTags(req.getTags());
        postMapper.insertPost(post);
        return post.getId();
    }

    @Override
    public void updatePost(Long userId, PostUpdateRequest req) {
        Post post = new Post();
        post.setId(req.getId());
        post.setUserId(userId);
        post.setContent(req.getContent());
        post.setMediaType(req.getMediaType());
        post.setMediaUrl(req.getMediaUrl());
        post.setTags(req.getTags());
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        postMapper.deletePost(postId, userId);
    }

    @Override
    public PostDTO getPost(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) return null;
        return convert(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postMapper.selectAllPosts();
        List<PostDTO> dtos = new ArrayList<>();
        for (Post p : posts) dtos.add(convert(p));
        return dtos;
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postMapper.selectByUserId(userId);
        List<PostDTO> dtos = new ArrayList<>();
        for (Post p : posts) dtos.add(convert(p));
        return dtos;
    }

    private PostDTO convert(Post p) {
        PostDTO dto = new PostDTO();
        BeanUtils.copyProperties(p, dto);
        User u = userMapper.selectById(p.getUserId());
        if (u != null) {
            dto.setNickname(u.getNickname());
            dto.setAvatar(u.getAvatar());
        }
        if (p.getCreateTime() != null) dto.setCreateTime(sdf.format(p.getCreateTime()));
        return dto;
    }
}
