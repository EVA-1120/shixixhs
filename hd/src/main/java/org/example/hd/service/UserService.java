package org.example.hd.service;

import org.example.hd.dto.UserDTO;
import org.example.hd.entity.User;

public interface UserService {
    UserDTO register(String username,String password,String nickname);


    String login(String username, String password);

    // 返回JWT
    UserDTO getUserById(Long id);


    UserDTO updateProfile(Long id, UserDTO userDTO);

    User getByUsername(String username);
}


