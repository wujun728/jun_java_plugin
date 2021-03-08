package com.springboot.springbootmybatisannotation.mapper;


import com.springboot.springbootmybatisannotation.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "createDate", column = "create_date")
    })
    List<User> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "nickName", column = "nick_name")
    })
    User getUser(String id);

    @Insert("INSERT INTO user(id, nick_name, age, create_date) VALUES(#{id}, #{nickName}, #{age}, #{createDate})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select uuid() as id from dual")
    Long insertUser(User user);

    @Update("UPDATE user SET nick_name = #{nickName}, age = #{age} WHERE create_date = #{createDate}")
    Long updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    Long deleteUser(String id);
}
