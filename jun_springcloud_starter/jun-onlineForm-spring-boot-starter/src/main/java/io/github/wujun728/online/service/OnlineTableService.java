/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.common.utils.PageResult
 *  net.maku.framework.mybatis.service.BaseService
 */
package io.github.wujun728.online.service;

import java.rmi.ServerException;
import java.util.List;

import io.github.wujun728.online.common.BaseService;
import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.query.OnlineTableQuery;
import io.github.wujun728.online.vo.OnlineTableVO;

public interface OnlineTableService
extends BaseService<OnlineTableEntity> {
    public void save(OnlineTableVO var1) throws ServerException;

    public void delete(List<String> var1);

    public PageResult<OnlineTableVO> page(OnlineTableQuery var1);

    public OnlineTableVO get(String var1);

    public void update(OnlineTableVO var1);
}

