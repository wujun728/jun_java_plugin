/*
 * Decompiled with CFR 0.152.
 */
package io.github.wujun728.online.vo.query;

import java.util.Map;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;

public abstract class AbstractQuery {
    public /* synthetic */ OnlineTableColumnEntity column;

    public abstract Map<String, Object> getQuery();

    public AbstractQuery() {
        AbstractQuery dNRb;
    }
}

