package org.example.hd.service.impl;

import org.example.hd.dto.UserDTO;
import org.example.hd.entity.User;
import org.example.hd.mapper.UserMapper;
import org.example.hd.service.UserService;
import org.example.hd.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDTO register(String username, String password, String nickname) {
        if (userMapper.selectByUsername(username) != null){
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userMapper.insertUser(user);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;

    }

    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 签发JWT
        return JwtUtil.generateToken(user.getId(), user.getUsername());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO updateProfile(Long id, UserDTO userDTO) {
        User user = userMapper.selectById(id);
        if (user == null) throw new RuntimeException("用户不存在");
        user.setNickname(userDTO.getNickname());
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setBio(userDTO.getBio());
        userMapper.updateUser(user);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
