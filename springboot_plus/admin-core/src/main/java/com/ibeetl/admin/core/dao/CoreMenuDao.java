package com.ibeetl.admin.core.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreMenu;

import java.util.List;

@SqlResource("core.coreMenu")
public interface CoreMenuDao extends BaseMapper<CoreMenu> {

    public void queryByCondtion(PageQuery query);

    public void clearMenuFunction( List<Long> functionIds);


    public List<CoreMenu> allMenuWithURL();

}
