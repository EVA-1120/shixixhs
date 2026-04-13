package org.example.hd.entity;

import lombok.Data;

import java.util.Date;
@Data
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String email;
    private String phone;
    private Integer gender; // 0-未知 1-男 2-女
    private String bio;
    private String thirdPartyId;
    private Date createTime;
    private Date updateTime;
}
