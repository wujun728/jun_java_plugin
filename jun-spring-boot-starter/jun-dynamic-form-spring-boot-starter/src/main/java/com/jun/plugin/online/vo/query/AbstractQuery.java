/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.vo.query;

import java.util.Map;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public abstract class AbstractQuery {
    public /* synthetic */ OnlineTableColumnEntity column;

    public abstract Map<String, Object> getQuery();

    public AbstractQuery() {
        AbstractQuery dNRb;
    }
}

