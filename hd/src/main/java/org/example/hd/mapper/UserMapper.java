package org.example.hd.mapper;

import org.example.hd.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    // 通过id寻找用户
    User selectById(Long id);

    User selectByUsername(String username);

    int insertUser(User user);

    // 更改用户信息
    int updateUser(User user);

    int updateProfile(@Param("id") Long id,
                      @Param("nickname") String nickname,
                      @Param("avatar") String avatar,
                      @Param("gender") Integer gender,
                      @Param("bio") String bio,
                      @Param("email") String email);

    /**
     * 根据ID列表批量查询用户
     * @param ids 用户ID列表
     * @return 用户列表
     */
    List<User> findByIds(@Param("ids") List<Long> ids);
}