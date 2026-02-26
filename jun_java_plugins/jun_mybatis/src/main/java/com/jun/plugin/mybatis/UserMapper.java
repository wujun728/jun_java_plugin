package com.jun.plugin.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * User Mapper接口（包含基础CRUD、Map参数/返回值、foreach等）
 */
public interface UserMapper {
    // ==== 基础CRUD ====
    // 新增
    @Insert("INSERT INTO user (user_name, age, create_time) VALUES (#{userName}, #{age}, #{createTime})")
    int insertUser(User user);

    // 单个查询（根据ID）
    @Select("SELECT id, user_name, age, create_time FROM user WHERE id = #{id}")
    User findUserById(Integer id);

    // 批量查询（所有用户）
    @Select("SELECT id, user_name, age, create_time FROM user ORDER BY id DESC")
    List<User> findAllUser();

    // 修改（根据ID更新用户名和年龄）
    @Update("UPDATE user SET user_name = #{userName}, age = #{age} WHERE id = #{id}")
    int updateUser(User user);

    // 删除（根据ID）
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUserById(Integer id);

    // ==== Map相关 ====
    // 输入参数为Map（查询用户）
    @Select("SELECT id, user_name, age, create_time FROM user WHERE user_name = #{userName} AND age = #{age}")
    User findUserByMap(Map<String, Object> paramMap);

    // 返回值为HashMap（根据ID查询，键为数据库列名）
    @Select("SELECT id, user_name, age, create_time FROM user WHERE id = #{id}")
    Map<String, Object> findUserMapById(Integer id);

    // ==== foreach迭代 ====
    // foreach 迭代List（批量查询用户）
    @Select("<script>" +
            "SELECT id, user_name, age, create_time FROM user WHERE id IN " +
            "<foreach collection='idList' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<User> findUserListByIds(@Param("idList") List<Integer> idList);

    // foreach 迭代数组（批量删除用户）
    @Delete("<script>" +
            "DELETE FROM user WHERE id IN " +
            "<foreach collection='idArray' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteUserByArray(@Param("idArray") Integer[] idArray);

    // foreach 迭代对象数组（批量新增用户）
    @Insert("<script>" +
            "INSERT INTO user (user_name, age, create_time) VALUES " +
            "<foreach collection='userArray' item='user' separator=','>" +
            "(#{user.userName}, #{user.age}, #{user.createTime})" +
            "</foreach>" +
            "</script>")
    int batchInsertUser(@Param("userArray") User[] userArray);

    // foreach 迭代对象集合（批量查询博客关联用户，复用User示例）
    @Select("<script>" +
            "SELECT id, user_name, age, create_time FROM user WHERE author_id IN " +
            "<foreach collection='blogList' item='blog' open='(' separator=',' close=')'>" +
            "#{blog.authorId}" +
            "</foreach>" +
            "</script>")
    List<User> findUserByBlogList(@Param("blogList") List<Blog> blogList);
}