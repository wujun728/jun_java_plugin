package com.ibeetl.admin.console.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreFunction;

@SqlResource("console.function")
public interface FunctionConsoleDao extends BaseMapper<CoreFunction> {

    /**
     * 根据条件查询
     *
     * @param query
     */
    public void queryByCondtion(PageQuery query);
}
