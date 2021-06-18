package com.zhaodui.springboot.system.mapper;

import com.zhaodui.springboot.common.mapper.BaseMapper;
import com.zhaodui.springboot.system.model.SysDictItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysDictItmeMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM SYS_DICT_ITEM WHERE DICT_ID = #{mainId}")
    public List<SysDictItem> selectItemsByMainId(String mainId);
}