package com.jun.plugin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jun.plugin.entity.User;

@Repository("userDao")
@Mapper
public interface UserDao {
    User login(User user);
}
