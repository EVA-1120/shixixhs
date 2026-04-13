package org.example.hd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateRequest {
    private Long id;
    @NotBlank(message = "内容不能为空")
    private String content;
    private Integer mediaType;
    private String mediaUrl;
    private String tags;
}