/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.maku.framework.common.utils.PageResult
 */
package com.jun.plugin.online.service;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.online.query.OnlineFormQuery;
import com.jun.plugin.online.vo.form.OnlineFormVO;

public interface OnlineFormService {
    public OnlineFormVO getJSON(String var1) throws ServerException;

    public void save(String var1, Map<String, String> var2) throws ServerException;

    public void delete(String var1, List<Long> var2) throws ServerException;

    public void update(String var1, Map<String, String> var2) throws ServerException;

    public Map<String, Object> get(String var1, Long var2) throws ServerException;

    public PageResult<Map<String, Object>> page(String var1, OnlineFormQuery var2) throws ServerException;
}

