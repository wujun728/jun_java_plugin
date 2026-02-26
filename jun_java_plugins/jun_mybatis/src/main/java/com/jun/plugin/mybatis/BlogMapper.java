package com.jun.plugin.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Blog Mapper接口（测试类型转换器、对象foreach等）
 */
public interface BlogMapper {
    // ==== 基础CRUD ====
    @Insert("INSERT INTO blog (blog_title, content, publish_time, author_id) VALUES (#{blogTitle}, #{content}, #{publishTime}, #{authorId})")
    int insertBlog(Blog blog);

    @Select("SELECT id, blog_title, content, publish_time, author_id FROM blog WHERE id = #{id}")
    Blog findBlogById(Integer id);

    @Update("UPDATE blog SET blog_title = #{blogTitle}, content = #{content} WHERE id = #{id}")
    int updateBlog(Blog blog);

    @Delete("DELETE FROM blog WHERE id = #{id}")
    int deleteBlogById(Integer id);

    // ==== 类型转换器 ====
    // 自定义日期转换器（查询，将字符串日期转为Date）
    @Select("SELECT id, blog_title, content, publish_time, author_id FROM blog WHERE publish_time = #{publishTime}")
    Blog findBlogByPublishTime(@Param("publishTime") Date publishTime);

    // 新增带日期转换器
    @Insert("INSERT INTO blog (blog_title, content, publish_time, author_id) VALUES (#{blogTitle}, #{content}, #{publishTime}, #{authorId})")
    int insertBlogWithConverter(Blog blog);

    // ==== Map与foreach ====
    // 输入参数为Map，返回Blog列表
    @Select("<script>" +
            "SELECT id, blog_title, content, publish_time, author_id FROM blog WHERE author_id IN " +
            "<foreach collection='authorIds' item='aid' open='(' separator=',' close=')'>" +
            "#{aid}" +
            "</foreach>" +
            "</script>")
    List<Blog> findBlogByMap(@Param("paramMap") Map<String, Object> paramMap);

    // 迭代对象数组（批量查询博客）
    @Select("<script>" +
            "SELECT id, blog_title, content, publish_time, author_id FROM blog WHERE id IN " +
            "<foreach collection='blogArray' item='blog' open='(' separator=',' close=')'>" +
            "#{blog.id}" +
            "</foreach>" +
            "</script>")
    List<Blog> findBlogByObjectArray(@Param("blogArray") Blog[] blogArray);

    // 迭代对象集合（批量新增博客）
    @Insert("<script>" +
            "INSERT INTO blog (blog_title, content, publish_time, author_id) VALUES " +
            "<foreach collection='blogList' item='blog' separator=','>" +
            "(#{blog.blogTitle}, #{blog.content}, #{blog.publishTime}, #{blog.authorId})" +
            "</foreach>" +
            "</script>")
    int batchInsertBlog(@Param("blogList") List<Blog> blogList);
}
