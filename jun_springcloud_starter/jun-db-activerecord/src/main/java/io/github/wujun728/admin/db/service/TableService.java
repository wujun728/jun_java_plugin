package io.github.wujun728.admin.db.service;

import io.github.wujun728.admin.common.PageData;
import io.github.wujun728.admin.common.PageParam;
import io.github.wujun728.admin.common.Result;
import io.github.wujun728.admin.common.service.CacheService;
import io.github.wujun728.admin.db.data.TableInfo;

import java.util.Map;

public interface TableService extends CacheService<Result<TableInfo>> {
    Result<PageData<TableInfo>> queryTable(PageParam pageParam);
    Result<TableInfo> tableInfo(String tableName);
    Result<Void> updateTable(TableInfo tableInfo);

    Result dropTable(String tableName);

    Map<String,String> generateCode(String tableName);
}
