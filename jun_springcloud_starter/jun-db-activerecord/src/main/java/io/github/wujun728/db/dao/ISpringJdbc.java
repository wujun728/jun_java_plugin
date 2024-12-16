package io.github.wujun728.db.dao;

import java.util.List;
import java.util.Map;

import io.github.wujun728.db.Page;
import io.github.wujun728.db.bean.PageResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author 周宁
 */
public interface ISpringJdbc {
    JdbcTemplate getJdbcTemplate();

    NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    String insertForId(String sql) throws Exception;

    String insertForId(String sql, Object[] args) throws Exception;

    int batchInsert(String sql, List<Object[]> batchArgs) throws Exception;

    int batchInsert(String sql, List<Object[]> batchArgs, int[] types) throws Exception;

    int batchInsert(String sql, List<Object[]> batchArgs, int batchPageSize) throws Exception;

    int batchInsert(String sql, List<Object[]> batchArgs, int[] types, int batchPageSize) throws Exception;

    void batchUpdate(String sql, List<Object[]> batchArgs) throws Exception;

    void batchUpdate(String sql, List<Object[]> batchArgs, int batchPageSize) throws Exception;

    void batchUpdate(String sql, List<Object[]> batchArgs, int[] types) throws Exception;

    void batchUpdate(String sql, List<Object[]> batchArgs, int[] types, int batchPageSize) throws Exception;

    int update(String sql, Object[] args) throws Exception;

    <T> T query(String sql, ResultSetExtractor<T> rse) throws Exception;

    <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws Exception;

    <T> List<T> query(String sql, RowMapper<T> rowMapper) throws Exception;

    <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws Exception;

    <T> List<T> query(String sql, Class<T> elementType) throws Exception;

    <T> List<T> query(String sql, Object[] args, Class<T> elementType) throws Exception;

    <T> List<T> query(String sql, Map<String, Object> paramMap, Class<T> elementType) throws Exception;

    <T> T queryForObject(String sql, Class<T> requiredType) throws Exception;

    <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws Exception;

    List<Map<String, Object>> queryForList(String sql, Object[] args) throws Exception;

    <T> PageResult<T> queryForPageResult(Page page, String sql, Object[] args, Class<T> requiredType) throws Exception;

    <T> PageResult<T> queryForPageResult(Page page, String sql, Map<String, Object> paramMap, Class<T> requiredType) throws Exception;
}
