package org.example.hd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private Long id;
    private Long userId;
    private String content;
    private Integer mediaType; // 0-无 1-图片 2-视频
    private String mediaUrl;
    private String tags;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
