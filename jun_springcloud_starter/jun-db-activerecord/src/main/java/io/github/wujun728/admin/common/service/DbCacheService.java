package io.github.wujun728.admin.common.service;

import java.util.Map;

public interface DbCacheService {
    Map<String,Object> getData(String tableName,String key);
}
