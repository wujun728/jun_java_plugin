package com.itmuch.mybatis.mapper;

import com.itmuch.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @author Wujun
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    Optional<User> selectById(Long id);
}
