package org.example.hd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank(message = "内容不能为空")
    private String content;
    private Integer mediaType; // 0-无 1-图片 2-视频
    private String mediaUrl;
    private String tags;
}
