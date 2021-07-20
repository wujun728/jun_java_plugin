package com.jun.plugin.mybatis.mapper.simple;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jun.plugin.mybatis.mapper.BaseMapper;
import com.jun.plugin.mybatis.model.simple.News;


/** 
 * @Description 新闻mapper接口
 * @author Wujun
 * @date Mar 16, 2017 3:35:19 PM  
 */
@Mapper
public interface NewsMapper extends BaseMapper<String, News> {

    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    List<News> findNewsByPage(@Param("keywords") String keywords);

}
