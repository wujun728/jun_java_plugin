/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.mybatis.service.BaseService
 */
package io.github.wujun728.online.service;

import java.util.List;

import io.github.wujun728.online.common.BaseService;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.vo.OnlineTableColumnVO;

public interface OnlineTableColumnService
extends BaseService<OnlineTableColumnEntity> {
    public List<OnlineTableColumnVO> getByTableId(String var1);

    public void saveBatch(String var1, List<OnlineTableColumnVO> var2);

    public void delete(String var1);
}

