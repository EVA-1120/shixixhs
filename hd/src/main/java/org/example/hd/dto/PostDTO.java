package org.example.hd.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private Long userId;
    private String content;
    private Integer mediaType;
    private String mediaUrl;
    private String tags;
    private String nickname;
    private String avatar;
    private String createTime;
}