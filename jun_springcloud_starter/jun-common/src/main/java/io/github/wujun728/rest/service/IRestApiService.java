package io.github.wujun728.rest.service;

import io.github.wujun728.rest.entity.ApiSql;

import java.sql.SQLException;
import java.util.Map;

public interface IRestApiService {
    Object doSQLProcess(ApiSql apiSql, Map<String, Object> parameters) throws SQLException;
}
