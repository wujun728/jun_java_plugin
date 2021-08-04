package com.jun.plugin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jun.plugin.model.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> findAll();
}
