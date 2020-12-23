package com.thinkingcao.silence.ssm.mapper;

import com.thinkingcao.silence.ssm.entity.User;

import java.util.List;

public interface UserMapper {
    int insert(User u);

    int updateById(User u);

    int deleteById(Long id);

    User selectById(Long id);

    List<User> selectAll();
}