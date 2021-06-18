package com.roncoo.jui.common.mapper;

import com.roncoo.jui.common.entity.RcReport;
import com.roncoo.jui.common.entity.RcReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RcReportMapper {
    int countByExample(RcReportExample example);

    int deleteByExample(RcReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RcReport record);

    int insertSelective(RcReport record);

    List<RcReport> selectByExample(RcReportExample example);

    RcReport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RcReport record, @Param("example") RcReportExample example);

    int updateByExample(@Param("record") RcReport record, @Param("example") RcReportExample example);

    int updateByPrimaryKeySelective(RcReport record);

    int updateByPrimaryKey(RcReport record);
}