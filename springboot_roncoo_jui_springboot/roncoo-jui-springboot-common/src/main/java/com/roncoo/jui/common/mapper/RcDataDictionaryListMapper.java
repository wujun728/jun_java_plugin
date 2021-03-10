package com.roncoo.jui.common.mapper;

import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RcDataDictionaryListMapper {
    int countByExample(RcDataDictionaryListExample example);

    int deleteByExample(RcDataDictionaryListExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RcDataDictionaryList record);

    int insertSelective(RcDataDictionaryList record);

    List<RcDataDictionaryList> selectByExample(RcDataDictionaryListExample example);

    RcDataDictionaryList selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RcDataDictionaryList record, @Param("example") RcDataDictionaryListExample example);

    int updateByExample(@Param("record") RcDataDictionaryList record, @Param("example") RcDataDictionaryListExample example);

    int updateByPrimaryKeySelective(RcDataDictionaryList record);

    int updateByPrimaryKey(RcDataDictionaryList record);
}