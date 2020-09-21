/*   
 * Project: OSMP
 * FileName: JdbcTemplate.java
 * version: V1.0
 */
package com.osmp.jdbc.support;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.osmp.jdbc.define.Page;
import com.osmp.jdbc.define.ProcedureList;
import com.osmp.jdbc.define.ProcedureObject;

/**
 * 
 * Description:JdbcTemplate
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:18:48上午10:51:30
 */
public interface JdbcTemplate {
    static final String QUERY_SINGLE_TABLE_YES = "YES";
    static final String QUERY_SINGLE_TABLE_NO = "NO";
    static final String QUERY_DEFAULT_SINGLE_TABLE = QUERY_SINGLE_TABLE_NO;
    /**
     * 数据源开始事务
     * @param name 数据源名
     */
    public void _begin();
    /**
     * 数据源提交事务
     * @param name 数据源名
     */
    public void _commit();
    /**
     * 数据源回滚事务
     * @param name 数据源名
     */
    public void _rollback();
    
    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, Class<T> clz, int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, Map<String, Object> queryParam, Class<T> clz,
            int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, List<Object> queryParam, Class<T> clz,
            int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, String countSqlKey, Class<T> clz,
            int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, String countSqlKey, Map<String, Object> queryParam,
            Class<T> clz, int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> Page<T> queryForPage(String querySql, String countSqlKey, List<Object> queryParam,
            Class<T> clz, int pageNumber, int pageSize);

    @Transactional(readOnly = true)
    <T extends Object> List<T> queryForList(String querySql, Class<T> clz);

    @Transactional(readOnly = true)
    <T extends Object> List<T> queryForList(String querySql, Map<String, Object> queryParam, Class<T> clz);

    @Transactional(readOnly = true)
    <T extends Object> List<T> queryForList(String querySql, List<Object> queryParam, Class<T> clz);

    @Transactional(readOnly = true)
    <T extends Object> List<T> queryForList(String querySql, Class<T> clz, Object... queryParam);

    /**
     * 查询单行实体记录,如果记录数大于1抛出异常
     * 
     * @param <T>
     * @param querySql
     * @param rowMapper
     * @return
     */
    @Transactional(readOnly = true)
    <T extends Object> T queryForObject(String querySql, Class<T> clz);

    /**
     * 查询单行实体记录,如果记录数大于1抛出异常
     * 
     * @param <T>
     * @param querySql
     * @param rowMapper
     * @return
     */
    @Transactional(readOnly = true)
    <T extends Object> T queryForObject(String querySql, Map<String, Object> queryParam, Class<T> clz);

    /**
     * 查询单行实体记录,如果记录数大于1抛出异常
     * 
     * @param <T>
     * @param querySql
     * @param rowMapper
     * @return
     */
    @Transactional(readOnly = true)
    <T extends Object> T queryForObject(String querySql, List<Object> queryParam, Class<T> clz);

    /**
     * 查询单行实体记录,如果记录数大于1抛出异常
     * 
     * @param <T>
     * @param querySql
     * @param rowMapper
     * @param queryParam
     * @return
     */
    @Transactional(readOnly = true)
    <T extends Object> T queryForObject(String querySql,Class<T> clz, Object... queryParam);

    String queryForString(String querySql, Object... queryParam);

    String queryForString(Map<String, Object> queryParam,String querySql);

    Integer queryForInteger(String querySql, Object... queryParam);

    Integer queryForInteger(Map<String, Object> queryParam,String querySql);

    Long queryForLong(String querySql, Object... queryParam);

    Long queryForLong(Map<String, Object> queryParam, String querySql);

    Date queryForDate(String querySql, Object... queryParam);

    Date queryForDate(Map<String, Object> queryParam, String querySql);

    BigDecimal queryForBigDecimal(String querySql, Object... queryParam);

    BigDecimal queryForBigDecimal(Map<String, Object> queryParam, String querySql);

    @Transactional
    int update(Map<String, Object> paramMap, String sql) throws DataAccessException;

    @Transactional
    int update(String sql, Object... args) throws DataAccessException;

    @Transactional
    int update(String sql, Object[] args, int[] argTypes) throws DataAccessException;

    /**
     * 插入返回自动生成的主键
     * @param sql
     * @param args
     * @return
     */
    long insert(String sql,Object... args);
    /**
     * 插入返回自动生成的主键
     * @param sql
     * @param paramMap
     * @return
     */
    long insert(Map<String,Object> paramMap,String sql);
    
    @Transactional
    int[] batchUpdate(String sql, Map<String, Object>[] batchValues);
    
    /**
     * 执行带结果集以及返回参数的存储过程
     * @param procSql String 调用的存储过程
     * @param input List input 输入参数
     * @param output List 其中泛型为java.sql.Types output输出参数类型 
     * @param clz 返回结果集泛型
     * @return ProcedureList
     */
    <T> ProcedureList<T> executeProcedureForList(final String procSql,final List<Object> input,
            final List<Integer> output,final Class<T> clz);
    
    /**
     * 执行带结果集以及返回参数的存储过程
     * @param procSql String 调用的存储过程
     * @param input List input 输入参数
     * @param output List 其中泛型为java.sql.Types output输出参数类型 
     * @param clz 返回结果集泛型
     * @return ProcedureObject
     */
    <T> ProcedureObject<T> executeProcedureForObject(final String procSql,final List<Object> input,
            final List<Integer> output,final Class<T> clz);
}
