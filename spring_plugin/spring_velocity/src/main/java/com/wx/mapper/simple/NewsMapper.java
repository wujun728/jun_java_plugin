package com.wx.mapper.simple;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wx.common.base.model.Page;
import com.wx.entity.simple.News;
import com.wx.mapper.BaseMapper;

/**
 * @author Vincent.wang
 *
 */
public interface NewsMapper extends BaseMapper<String, News> {

    List<News> findNewsByKeywords(@Param("keywords") String keywords);

    List<News> findNewsByPage(Page<News> page);

}
