package com.jun.plugin.mapper.simple;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jun.plugin.common.base.model.Page;
import com.jun.plugin.entity.simple.News;
import com.jun.plugin.mapper.BaseMapper;

/**
 * @author Vincent.wang
 *
 */
public interface NewsMapper extends BaseMapper<String, News> {

    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    List<News> findNewsByPage(Page<News> page);

}
