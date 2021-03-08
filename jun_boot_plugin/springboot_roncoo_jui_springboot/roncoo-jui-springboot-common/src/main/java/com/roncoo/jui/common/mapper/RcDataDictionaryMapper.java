package com.roncoo.jui.common.mapper;

import com.roncoo.jui.common.entity.RcDataDictionary;
import com.roncoo.jui.common.entity.RcDataDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RcDataDictionaryMapper {
    int countByExample(RcDataDictionaryExample example);

    int deleteByExample(RcDataDictionaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RcDataDictionary record);

    int insertSelective(RcDataDictionary record);

    List<RcDataDictionary> selectByExample(RcDataDictionaryExample example);

    RcDataDictionary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RcDataDictionary record, @Param("example") RcDataDictionaryExample example);

    int updateByExample(@Param("record") RcDataDictionary record, @Param("example") RcDataDictionaryExample example);

    int updateByPrimaryKeySelective(RcDataDictionary record);

    int updateByPrimaryKey(RcDataDictionary record);
}