package com.jun.plugin.springboot.tools.system.mapper;

import org.apache.ibatis.annotations.Select;

import com.jun.plugin.springboot.tools.common.mapper.BaseMapper;
import com.jun.plugin.springboot.tools.system.model.SysDictItem;

import java.util.List;

public interface SysDictItmeMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM SYS_DICT_ITEM WHERE DICT_ID = #{mainId}")
    public List<SysDictItem> selectItemsByMainId(String mainId);
}