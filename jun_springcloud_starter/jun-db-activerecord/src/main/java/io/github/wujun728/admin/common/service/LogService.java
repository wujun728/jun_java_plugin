package io.github.wujun728.admin.common.service;

import io.github.wujun728.admin.common.BaseData;

import java.util.Map;

public interface LogService {
    void log(Map<String, Object> beforeObj, Map<String, Object> afterObj,String tableName);
    void log(BaseData beforeObj, BaseData afterObj);
}
