/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.media.Schema
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotNull
 *  org.hibernate.validator.constraints.Range
 */
package com.jun.plugin.online.query;

import java.util.LinkedHashMap;

import lombok.Data;

@Data
public class OnlineFormQuery {
    private static /* synthetic */ int[] Zd;
    Integer limit;
    LinkedHashMap<String, Object> params;
    Integer page;

    public LinkedHashMap<String, Object> getParams() {
        OnlineFormQuery RAub = null;
        return RAub.params;
    }

    private static void bk() {
        Zd = new int[4];
        OnlineFormQuery.Zd[0] = " ".length();
        OnlineFormQuery.Zd[1] = (0x27 ^ 0x14 ^ (0xB8 ^ 0xAB)) & (90 + 24 - 48 + 103 ^ 116 + 123 - 154 + 52 ^ -" ".length());
        OnlineFormQuery.Zd[2] = 5 ^ 0x3E;
        OnlineFormQuery.Zd[3] = 117 + 119 - 82 + 79 ^ 65 + 66 - -2 + 61;
    }

    public Integer getLimit() {
        OnlineFormQuery VAub = null;
        return VAub.limit;
    }

    private static boolean fk(Object object) {
        return object != null;
    }

    public OnlineFormQuery() {
        OnlineFormQuery bbub;
    }


    protected boolean canEqual(Object oyTb) {
        return oyTb instanceof OnlineFormQuery;
    }

    private static boolean Ck(Object object, Object object2) {
        return object == object2;
    }

    static {
        OnlineFormQuery.bk();
    }

    private static boolean dk(int n) {
        return n == 0;
    }

    public String toString() {
        OnlineFormQuery sXTb = null;
        return "OnlineFormQuery(page=" + sXTb.getPage() + ", limit=" + sXTb.getLimit() + ", params=" + sXTb.getParams() + ")";
    }

    private static boolean Ek(Object object) {
        return object == null;
    }


}

