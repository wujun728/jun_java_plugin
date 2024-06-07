/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.media.Schema
 *  net.maku.framework.common.query.Query
 */
package com.jun.plugin.online.query;

import com.jun.plugin.online.common.Query;
import lombok.Data;

@Data
public class OnlineTableQuery
extends Query {
    private /* synthetic */ String comments;
    private static /* synthetic */ int[] hd;
    private /* synthetic */ String name;

    protected boolean canEqual(Object mRVb) {
        return mRVb instanceof OnlineTableQuery;
    }


    private static boolean Lh(int n) {
        return n == 0;
    }

    public OnlineTableQuery() {
        OnlineTableQuery mTVb;
    }

    static {
        OnlineTableQuery.Jh();
    }

    private static void Jh() {
        hd = new int[4];
        OnlineTableQuery.hd[0] = " ".length();
        OnlineTableQuery.hd[1] = (0x4B ^ 0x16) & ~(0x72 ^ 0x2F);
        OnlineTableQuery.hd[2] = 0x72 ^ 0x6C ^ (0x29 ^ 0xC);
        OnlineTableQuery.hd[3] = 0x49 ^ 0x62;
    }

    private static boolean Nh(Object object) {
        return object != null;
    }

    public String getName() {
        OnlineTableQuery JTVb = null;
        return JTVb.name;
    }

    private static boolean mh(Object object) {
        return object == null;
    }

    private static boolean kh(Object object, Object object2) {
        return object == object2;
    }
}

