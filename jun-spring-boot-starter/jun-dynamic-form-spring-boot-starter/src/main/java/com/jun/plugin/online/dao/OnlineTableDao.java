/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.mybatis.dao.BaseDao
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Update
 */
package com.jun.plugin.online.dao;

import com.jun.plugin.online.common.BaseDao;
import com.jun.plugin.online.entity.OnlineTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OnlineTableDao
extends BaseDao<OnlineTableEntity> {
    @Update(value={"${sql}"})
    public void exeSQL(@Param(value="sql") String var1);
}

