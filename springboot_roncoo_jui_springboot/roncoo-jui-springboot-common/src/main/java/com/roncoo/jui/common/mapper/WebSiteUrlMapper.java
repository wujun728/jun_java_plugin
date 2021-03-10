package com.roncoo.jui.common.mapper;

import com.roncoo.jui.common.entity.WebSiteUrl;
import com.roncoo.jui.common.entity.WebSiteUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WebSiteUrlMapper {
    int countByExample(WebSiteUrlExample example);

    int deleteByExample(WebSiteUrlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WebSiteUrl record);

    int insertSelective(WebSiteUrl record);

    List<WebSiteUrl> selectByExample(WebSiteUrlExample example);

    WebSiteUrl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WebSiteUrl record, @Param("example") WebSiteUrlExample example);

    int updateByExample(@Param("record") WebSiteUrl record, @Param("example") WebSiteUrlExample example);

    int updateByPrimaryKeySelective(WebSiteUrl record);

    int updateByPrimaryKey(WebSiteUrl record);
}