/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.mybatis.dao.BaseDao
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Update
 */
package io.github.wujun728.online.dao;

import io.github.wujun728.online.common.BaseDao;
import io.github.wujun728.online.entity.OnlineTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OnlineTableDao
extends BaseDao<OnlineTableEntity> {
    @Update(value={"${sql}"})
    public void exeSQL(@Param(value="sql") String var1);
}

