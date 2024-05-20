/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.common.utils.PageResult
 *  net.maku.framework.mybatis.service.BaseService
 */
package com.jun.plugin.online.service;

import java.rmi.ServerException;
import java.util.List;

import com.jun.plugin.online.common.BaseService;
import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.online.entity.OnlineTableEntity;
import com.jun.plugin.online.query.OnlineTableQuery;
import com.jun.plugin.online.vo.OnlineTableVO;

public interface OnlineTableService
extends BaseService<OnlineTableEntity> {
    public void save(OnlineTableVO var1) throws ServerException;

    public void delete(List<String> var1);

    public PageResult<OnlineTableVO> page(OnlineTableQuery var1);

    public OnlineTableVO get(String var1);

    public void update(OnlineTableVO var1);
}

