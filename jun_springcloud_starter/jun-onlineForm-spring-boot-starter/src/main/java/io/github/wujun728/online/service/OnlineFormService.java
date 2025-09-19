package io.github.wujun728.online.service;

import java.util.List;
import java.util.Map;

import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.query.OnlineFormQuery;
import io.github.wujun728.online.vo.form.OnlineFormVO;
import net.maku.framework.common.exception.ServerException;

public interface OnlineFormService {
    public OnlineFormVO getJSON(String var1) throws ServerException;

    public void save(String var1, Map<String, String> var2) throws ServerException;

    public void delete(String var1, List<Long> var2) throws ServerException;

    public void update(String var1, Map<String, String> var2) throws ServerException;

    public Map<String, Object> get(String var1, Long var2) throws ServerException;

    public PageResult<Map<String, Object>> page(String var1, OnlineFormQuery var2) throws ServerException;
}

