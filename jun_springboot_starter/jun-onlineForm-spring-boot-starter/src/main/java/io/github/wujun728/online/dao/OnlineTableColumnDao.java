/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.mybatis.dao.BaseDao
 *  org.apache.ibatis.annotations.Mapper
 */
package io.github.wujun728.online.dao;

import java.util.List;

import io.github.wujun728.online.common.BaseDao;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OnlineTableColumnDao
extends BaseDao<OnlineTableColumnEntity> {
    @Select(" select * from online_table_column where table_id = #{value} order by sort ")
    public List<OnlineTableColumnEntity> getByTableId(String var1);
}

