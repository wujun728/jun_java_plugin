/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.mybatis.service.BaseService
 */
package com.jun.plugin.online.service;

import java.util.List;

import com.jun.plugin.online.common.BaseService;
import com.jun.plugin.online.entity.OnlineTableColumnEntity;
import com.jun.plugin.online.vo.OnlineTableColumnVO;

public interface OnlineTableColumnService
extends BaseService<OnlineTableColumnEntity> {
    public List<OnlineTableColumnVO> getByTableId(String var1);

    public void saveBatch(String var1, List<OnlineTableColumnVO> var2);

    public void delete(String var1);
}

