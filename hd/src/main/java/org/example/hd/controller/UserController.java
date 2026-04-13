package org.example.hd.controller;

import org.example.hd.dto.*;
import org.example.hd.service.UserService;
import org.example.hd.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody @Validated UserRegisterRequest request) {
        return userService.register(request.getUsername(), request.getPassword(), request.getNickname());
    }

    // 登录
    @PostMapping("/login")
    public String login(@RequestBody @Validated UserLoginRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    // 获取当前用户信息（需前端带Authorization: Bearer token）
    @GetMapping("/me")
    public UserDTO getProfile(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth != null && auth.startsWith("Bearer ") ? auth.substring(7) : null;
        Long userId = JwtUtil.getUserId(token);
        return userService.getUserById(userId);
    }

    // 更新个人信息
    @PutMapping("/me")
    public UserDTO updateProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth != null && auth.startsWith("Bearer ") ? auth.substring(7) : null;
        Long userId = JwtUtil.getUserId(token);
        return userService.updateProfile(userId, userDTO);
    }


}
