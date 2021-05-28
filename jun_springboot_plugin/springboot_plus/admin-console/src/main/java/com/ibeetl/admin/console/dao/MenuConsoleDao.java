package com.ibeetl.admin.console.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreMenu;

@SqlResource("console.menu")
public interface MenuConsoleDao extends BaseMapper<CoreMenu> {

    /**
     * 根据条件分页查询
     * @param query 查询条件
     */
    void queryByCondtion(PageQuery query);


}
