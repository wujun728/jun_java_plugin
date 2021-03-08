package com.roncoo.jui.common.mapper;

import com.roncoo.jui.common.entity.WebSite;
import com.roncoo.jui.common.entity.WebSiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WebSiteMapper {
    int countByExample(WebSiteExample example);

    int deleteByExample(WebSiteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WebSite record);

    int insertSelective(WebSite record);

    List<WebSite> selectByExample(WebSiteExample example);

    WebSite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WebSite record, @Param("example") WebSiteExample example);

    int updateByExample(@Param("record") WebSite record, @Param("example") WebSiteExample example);

    int updateByPrimaryKeySelective(WebSite record);

    int updateByPrimaryKey(WebSite record);
}