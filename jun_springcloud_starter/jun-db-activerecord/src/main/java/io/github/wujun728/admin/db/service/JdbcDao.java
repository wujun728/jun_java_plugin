package io.github.wujun728.admin.db.service;

import io.github.wujun728.admin.common.PageData;
import io.github.wujun728.admin.common.PageParam;
import io.github.wujun728.admin.common.Result;
import io.github.wujun728.admin.db.data.ColumnMeta;

import java.util.List;
import java.util.Map;

public interface JdbcDao {
    int update(String msg,String sql,Object ...args);
    int update(String sql,Object ...args);
    Long insert(String msg,String sql,Object ...args);
    Long insert(String sql,Object ...args);

    <T> Result<PageData<T>> query(PageParam pageParam, Class<T> clz, String sql, Object... values);
    Result<PageData<Map<String,Object>>> query(PageParam pageParam, String sql,Object... values);
    <T> List<T> find(String sql,Class<T> clz,Object ...args);
    <T> T findOne(String sql,Class<T> clz,Object ...args);
    List<Map<String,Object>> find(String sql, Object ...args);
    <T> List<T> find(Class<T> clz);
    Map<String,Object> findOne(String sql, Object ...args);
    <T> List<T> find(Class<T> clz,String[] fields,Object[] args);
    <T> T findOne(Class<T> clz,String[] fields,Object[] args);
    <T> List<T> find(Class<T> clz,String field,Object arg);
    <T> T findOne(Class<T> clz,String field,Object arg);
    <T> T getById(Class<T> clz,Long id);
    Map<String,Object> getById(String tableName,Long id);
    List<ColumnMeta> columnMeta(String sql);
    List<ColumnMeta> namedColumnMeta(String sql);
    <T> List<T> findForObject(String sql,Class<T> clz,Object ...args);
    <T> T findOneForObject(String sql,Class<T> clz,Object ...args);

    <T> List<T> find(String sql,Class<T> clz,Map<String,Object> params);
    <T> T findOne(String sql,Class<T> clz,Map<String,Object> params);
    List<Map<String,Object>> find(String sql,Map<String,Object> params);
    Map<String,Object> findOne(String sql,Map<String,Object> params);
    <T> T findOneForObject(String sql,Map<String,Object> params,Class<T> clz);
    <T> List<T> findForObject(String sql,Map<String,Object> params,Class<T> clz);
}
