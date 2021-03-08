package com.zh.springbootmybatis.dao;

import com.zh.springbootmybatis.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    Optional<User> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("SELECT * FROM user WHERE age = #{age}")
    List<User> listByAge(@Param("age") Integer age);

    void saveBatch(List<User> users);
}