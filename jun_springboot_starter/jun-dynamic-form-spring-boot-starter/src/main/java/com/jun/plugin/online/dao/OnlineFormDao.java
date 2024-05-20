/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.baomidou.mybatisplus.core.metadata.IPage
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Param
 */
package com.jun.plugin.online.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OnlineFormDao {
    public void delete(@Param(value="tableName") String var1, @Param(value="list") List<Long> var2);

    public Map<String, Object> getById(@Param(value="tableName") String var1, @Param(value="id") Long var2);

    public void save(@Param(value="tableName") String var1, @Param(value="columns") Map<String, Object> var2);

    public List<Map<String, Object>> getList(IPage<?> var1, @Param(value="tableName") String var2, @Param(value="params") Map<String, Object> var3);

    public void update(@Param(value="tableName") String var1, @Param(value="id") Long var2, @Param(value="columns") Map<String, Object> var3);
}

