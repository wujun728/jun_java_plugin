/*   
 * Project: OSMP
 * FileName: BaseTemplate.java
 * version: V1.0
 */
package com.osmp.jdbc.support.template;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.osmp.jdbc.define.Page;
import com.osmp.jdbc.define.ProcedureList;
import com.osmp.jdbc.define.ProcedureObject;
import com.osmp.jdbc.define.tool.ToolsRowMapper;
import com.osmp.jdbc.define.tool.TransactionWrapper;
import com.osmp.jdbc.support.JdbcTemplate;

public abstract class BaseTemplate implements JdbcTemplate {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadLocal<TransactionWrapper> tranLocal = new ThreadLocal<TransactionWrapper>();


    private NamedParameterJdbcTemplate ownJdbcTemplate;
    private DataSourceTransactionManager transactionManager;

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setOwnJdbcTemplate(NamedParameterJdbcTemplate ownJdbcTemplate){
        this.ownJdbcTemplate = ownJdbcTemplate;
    }

    @Override
    public void _begin(){
        TransactionWrapper tran = tranLocal.get();
        if(tran != null){
            tran.countUp();
            return;
        }
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        tranLocal.set(new TransactionWrapper(status));
    }

    @Override
    public void _commit(){
        TransactionWrapper tran = tranLocal.get();
        Assert.notNull(tran, "no transaction found.");
        if(tran.getTransactionCounts()>1){
            tran.countDown();
            return;
        }
        TransactionStatus status = tran.getStatus();
        if(tran.isRollback()){
            transactionManager.rollback(status);
        }else{
            transactionManager.commit(status);
        }
        tran = null;
        tranLocal.remove();
    }

    @Override
    public void _rollback(){
        TransactionWrapper tran = tranLocal.get();
        Assert.notNull(tran, "no transaction found.");
        if(tran.getTransactionCounts()>1){
            tran.countDown();
            tran.setRollback(true);
            return;
        }
        TransactionStatus status = tran.getStatus();
        transactionManager.rollback(status);
        tran = null;
        tranLocal.remove();
    }


    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, Class<T> clz, int pageNo, int pageSize) {
        return queryForPage(querySql, QUERY_DEFAULT_SINGLE_TABLE, clz, pageNo, pageSize);
    }

    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, Map<String, Object> queryParam,
            Class<T> clz, int pageNo, int pageSize) {
        return queryForPage(querySql, QUERY_DEFAULT_SINGLE_TABLE, queryParam, clz, pageNo, pageSize);
    }

    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, List<Object> queryParam, Class<T> clz,
            int pageNo, int pageSize) {
        return queryForPage(querySql, QUERY_DEFAULT_SINGLE_TABLE, queryParam, clz, pageNo, pageSize);
    }

    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, String countSql, Class<T> clz,
            int pageNo, int pageSize) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");

        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);


        int totalCount = this.queryCont(querySql, countSql);
        if (totalCount == 0) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        }

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String pageSql = buildQueryPageSql(querySql, queryParam, Page.getStartOfPage(pageNo, pageSize), pageSize);
        if (!StringUtils.hasText(pageSql)) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        } else {
            List<T> listData = this.ownJdbcTemplate.query(pageSql, queryParam, rowMapper);
            if (CollectionUtils.isEmpty(listData)) {
                return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
            } else {
                return new Page<T>(pageNo, totalCount, pageSize, listData);
            }
        }
    }

    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, String countSql, Map<String, Object> queryParam,
            Class<T> clz, int pageNo, int pageSize) {
        Assert.notNull("query sql must be not null", querySql);
        Assert.notNull(clz, "call-back bean must be not null");

        if (CollectionUtils.isEmpty(queryParam)) {
            return queryForPage(querySql, countSql, clz, pageNo, pageSize);
        }

        int totalCount = this.queryCont(querySql, countSql, queryParam);
        if (totalCount == 0) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        }

        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);

        String pageSql = buildQueryPageSql(querySql, queryParam, Page.getStartOfPage(pageNo, pageSize), pageSize);
        if (!StringUtils.hasText(pageSql)) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        } else {
            List<T> listData = this.ownJdbcTemplate.query(pageSql, queryParam, rowMapper);
            if (CollectionUtils.isEmpty(listData)) {
                return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
            } else {
                return new Page<T>(pageNo, totalCount, pageSize, listData);
            }
        }
    }

    @Override
    public <T extends Object> Page<T> queryForPage(String querySql, String countSql, List<Object> queryParam,
            Class<T> clz, int pageNo, int pageSize) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");

        if (CollectionUtils.isEmpty(queryParam)) {
            return queryForPage(querySql, countSql, clz, pageNo, pageSize);
        }

        int totalCount = this.queryCont(querySql, countSql, queryParam);
        if (totalCount == 0) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        }

        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
        String pageSql = buildQueryPageSql(querySql, queryParam, Page.getStartOfPage(pageNo, pageSize), pageSize);
        if (!StringUtils.hasText(pageSql)) {
            return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
        } else {
            List<T> listData = this.ownJdbcTemplate.getJdbcOperations()
                    .query(pageSql, rowMapper, queryParam.toArray());
            if (CollectionUtils.isEmpty(listData)) {
                return new Page<T>(Page.DEFAULT_PAGE_NUMBER, 0, pageSize, new ArrayList<T>());
            } else {
                return new Page<T>(pageNo, totalCount, pageSize, listData);
            }
        }
    }

    @Override
    public <T extends Object> List<T> queryForList(String querySql, Class<T> clz) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        logger.debug("\n" + querySql);
        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
        return this.ownJdbcTemplate.getJdbcOperations().query(querySql, rowMapper, new Object[] {});
    }

    @Override
    public <T extends Object> List<T> queryForList(String querySql, Map<String, Object> queryParam,
            Class<T> clz) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        if (CollectionUtils.isEmpty(queryParam)) {
            queryForList(querySql, clz);
        }
        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
        logger.debug("Query parameters : " + queryParam);
        return this.ownJdbcTemplate.query(querySql, queryParam, rowMapper);
    }

    @Override
    public <T extends Object> List<T> queryForList(String querySql, List<Object> queryParam, Class<T> clz) {
        return queryForList(querySql, clz, queryParam.toArray());
    }

    @Override
    public <T extends Object> List<T> queryForList(String querySql, Class<T> clz, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return queryForList(querySql, clz);
        } else {
            RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
            logger.debug("Query parameters : " + ArrayUtils.toString(queryParam));
            return this.ownJdbcTemplate.getJdbcOperations().query(querySql, rowMapper, queryParam);
        }
    }

    @Override
    public <T extends Object> T queryForObject(String querySql, Class<T> clz) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        logger.debug("\n" + querySql);
        List<T> listData = queryForList(querySql, clz);
        if(listData == null || listData.isEmpty()) {
            return null;
        }
        return listData.get(0);
    }

    @Override
    public <T extends Object> T queryForObject(String querySql, Map<String, Object> queryParam, Class<T> clz) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        if (CollectionUtils.isEmpty(queryParam)) {
            queryForObject(querySql, clz);
        }
        logger.debug("Query parameters : " + queryParam);
        List<T> listData = queryForList(querySql, queryParam, clz);
        if(listData == null || listData.isEmpty()) {
            return null;
        }
        return listData.get(0);
    }

    @Override
    public <T extends Object> T queryForObject(String querySql, List<Object> queryParam, Class<T> clz) {
        return queryForObject(querySql,clz, queryParam.toArray());
    }

    @Override
    public <T extends Object> T queryForObject(String querySql, Class<T> clz, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        Assert.notNull(clz, "call-back bean must be not null");
        if (ArrayUtils.isEmpty(queryParam)) {
            return queryForObject(querySql, clz);
        }
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        List<T> listData = queryForList(querySql, clz, queryParam);
        if(listData == null || listData.isEmpty()) {
            return null;
        }
        return listData.get(0);
    }

    @Override
    public String queryForString(String querySql, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, String.class);
        } else {
            return this.queryForObject(querySql, String.class, queryParam);
        }
    }

    @Override
    public String queryForString(Map<String, Object> queryParam, String querySql) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (CollectionUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, String.class);
        } else {
            return this.queryForObject(querySql, queryParam, String.class);
        }
    }

    @Override
    public Integer queryForInteger(String querySql, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return this.ownJdbcTemplate.getJdbcOperations().queryForInt(querySql);
        } else {
            return this.ownJdbcTemplate.getJdbcOperations().queryForInt(querySql, queryParam);
        }
    }

    @Override
    public Integer queryForInteger(Map<String, Object> queryParam, String querySql) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (CollectionUtils.isEmpty(queryParam)) {
            return this.ownJdbcTemplate.getJdbcOperations().queryForInt(querySql);
        } else {
            return this.ownJdbcTemplate.queryForInt(querySql, queryParam);
        }
    }

    @Override
    public Long queryForLong(String querySql, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return this.ownJdbcTemplate.getJdbcOperations().queryForLong(querySql);
        } else {
            return this.ownJdbcTemplate.getJdbcOperations().queryForLong(querySql, queryParam);
        }
    }

    @Override
    public Long queryForLong(Map<String, Object> queryParam, String querySql) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (CollectionUtils.isEmpty(queryParam)) {
            return this.ownJdbcTemplate.getJdbcOperations().queryForLong(querySql);
        } else {
            return this.ownJdbcTemplate.queryForLong(querySql, queryParam);
        }
    }

    @Override
    public Date queryForDate(String querySql, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, Date.class);
        } else {
            return this.queryForObject(querySql, Date.class, queryParam);
        }
    }

    @Override
    public Date queryForDate(Map<String, Object> queryParam, String querySql) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (CollectionUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, Date.class);
        } else {
            return this.queryForObject(querySql, queryParam, Date.class);
        }
    }

    @Override
    public BigDecimal queryForBigDecimal(String querySql, Object... queryParam) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (ArrayUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, BigDecimal.class);
        } else {
            return this.queryForObject(querySql, BigDecimal.class, queryParam);
        }
    }

    @Override
    public BigDecimal queryForBigDecimal(Map<String, Object> queryParam, String querySql) {
        Assert.notNull(querySql, "query sql must be not null");
        logger.debug("\n" + querySql);
        logger.debug("\n" + ArrayUtils.toString(queryParam));
        if (CollectionUtils.isEmpty(queryParam)) {
            return this.queryForObject(querySql, BigDecimal.class);
        } else {
            return this.queryForObject(querySql, queryParam, BigDecimal.class);
        }
    }

    @Override
    public int update(Map<String, Object> paramMap, String sql) throws DataAccessException {
        logger.debug("Update sql : \n" + sql);
        logger.debug("Paramters : \n" + paramMap);
        try {
            return this.ownJdbcTemplate.update(sql, paramMap);
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int update(String sql, Object... args) throws DataAccessException {
        logger.debug("Update sql : \n" + sql);
        logger.debug("Paramters : \n" + ArrayUtils.toString(args));
        try {
            return this.ownJdbcTemplate.getJdbcOperations().update(sql, args);
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        logger.debug("Update sql : \n" + sql);
        logger.debug("Paramters : \n" + ArrayUtils.toString(args));
        try {
            return this.ownJdbcTemplate.getJdbcOperations().update(sql, args, argTypes);
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public long insert(String sql,Object... args) {
        logger.debug("Insert sql : \n" + sql);
        logger.debug("Paramters : \n" + ArrayUtils.toString(args));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final String sqlStr = sql;
            final Object[] argsArr = args;
            this.ownJdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con)
                        throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
                    if(argsArr != null && argsArr.length > 0){
                        PreparedStatementSetter argSetter = new ArgumentPreparedStatementSetter(argsArr);
                        if(argSetter != null){
                            argSetter.setValues(ps);
                        }
                    }
                    return ps;
                }
            }, keyHolder);
            if(keyHolder.getKey() == null) {
                return -1;
            }
            return keyHolder.getKey().longValue();
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public long insert(Map<String,Object> paramMap, String sql) {
        logger.debug("Insert sql : \n" + sql);
        logger.debug("Paramters : \n" + paramMap);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.ownJdbcTemplate.getJdbcOperations().update(getPreparedStatementCreator(sql,paramMap), keyHolder);
            if(keyHolder.getKey() == null) {
                return -1;
            }
            return keyHolder.getKey().longValue();
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    protected PreparedStatementCreator getPreparedStatementCreator(String sql, Map<String,Object> paramMap) {
        MapSqlParameterSource paramSource =  new MapSqlParameterSource(paramMap);
        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
        List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
        pscf.setReturnGeneratedKeys(true);
        return pscf.newPreparedStatementCreator(params);
    }

    @Override
    public int[] batchUpdate(String sql, Map<String, Object>[] batchValues) {
        logger.debug("Update sql : \n" + sql);
        logger.debug("batchValues : \n" + batchValues);
        int[] result = null;
        try {
            result = this.ownJdbcTemplate.batchUpdate(sql, batchValues);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            Assert.isNull(ex, ex.getMessage());
        }
        return result;
    }

    protected String buildQueryCountSqlDefault(String querySql) {
        Assert.isTrue(StringUtils.hasText(querySql), "count query sql string must be not null or empty");

        String formatSql = querySql.replaceAll("\n", " ").replaceAll("\\s{2,}", " ");

        int orderindex = formatSql.lastIndexOf(" order by");
        int endIndex = formatSql.lastIndexOf(")");
        if(orderindex > 0){
            if(endIndex > orderindex){
                querySql = formatSql.substring(0, orderindex) + formatSql.substring(endIndex);
            }else{
                querySql = formatSql.substring(0, orderindex);
            }
        }

        StringBuilder stringBuilder = new StringBuilder("select count(1) from (");
        stringBuilder.append(querySql);
        stringBuilder.append(") temptable");
        return stringBuilder.toString();
    }

    protected String buildQueryCountSql(String querySql) {
        Assert.isTrue(StringUtils.hasText(querySql), "count query sql string must be not null or empty");
        String where = null;
        int start = querySql.toLowerCase().indexOf("from");
        if (start != -1) {
            where = querySql.substring(start);
        }
        StringBuilder stringBuilder = new StringBuilder("select count(1) ");
        stringBuilder.append(where);
        return stringBuilder.toString();
    }

    protected String buildQueryCountSql(String querySql, String countSql) {
        String queryCountSql = null;
        if (QUERY_SINGLE_TABLE_NO.equalsIgnoreCase(countSql)) {
            queryCountSql = buildQueryCountSqlDefault(querySql);
        } else if (QUERY_SINGLE_TABLE_YES.equalsIgnoreCase(countSql)) {
            queryCountSql = buildQueryCountSql(querySql);
        } else {
            queryCountSql = countSql;
        }
        logger.debug("CountSQL : \n" + queryCountSql);
        return queryCountSql;
    }

    protected abstract String buildQueryPageSql(String querySql, Map<String, Object> queryParam, int startIndex,
            int pageSize);
    protected abstract String buildQueryPageSql(String querySql, List<Object> queryParam, int startIndex, int pageSize);

    protected int queryCont(String querySql) {
        String queryContString = buildQueryCountSql(querySql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }
        Number number = this.ownJdbcTemplate.getJdbcOperations().queryForObject(queryContString, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    protected int queryCont(String querySql, String countSql) {
        String queryContString = buildQueryCountSql(querySql, countSql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }
        Number number = this.ownJdbcTemplate.getJdbcOperations().queryForObject(queryContString, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    protected int queryCont(String querySql, Map<String, Object> queryParam) {
        if (CollectionUtils.isEmpty(queryParam)) {
            return queryCont(querySql);
        }
        String queryContString = buildQueryCountSql(querySql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }

        Number number = this.ownJdbcTemplate.queryForObject(queryContString, queryParam, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    protected int queryCont(String querySql, String countSql, Map<String, Object> queryParam) {
        if (CollectionUtils.isEmpty(queryParam)) {
            return queryCont(querySql, countSql);
        }
        String queryContString = buildQueryCountSql(querySql, countSql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }
        Number number = this.ownJdbcTemplate.queryForObject(queryContString, queryParam, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    protected int queryCont(String querySql, List<Object> queryParam) {
        if (CollectionUtils.isEmpty(queryParam)) {
            return queryCont(querySql);
        }
        String queryContString = buildQueryCountSql(querySql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }
        Number number = this.ownJdbcTemplate.getJdbcOperations().queryForObject(queryContString, queryParam.toArray(),
                Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    protected int queryCont(String querySql, String countSql, List<Object> queryParam) {
        if (CollectionUtils.isEmpty(queryParam)) {
            return queryCont(querySql, countSql);
        }
        String queryContString = buildQueryCountSql(querySql, countSql);
        if (!StringUtils.hasText(queryContString)) {
            return 0;
        }
        Number number = this.ownJdbcTemplate.getJdbcOperations().queryForObject(queryContString, queryParam.toArray(),
                Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    @Override
    public <T> ProcedureList<T> executeProcedureForList(final String procSql,final List<Object> input,
            final List<Integer> output,final Class<T> clz){
        Assert.notNull(procSql, "procSql must be not null");

        final int inputSize = input == null ? 0 : input.size();
        final int outputSize = output == null ? 0 : output.size();

        final ProcedureList<T> ret = new ProcedureList<T>(); 

        this.ownJdbcTemplate.getJdbcOperations().execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con)
                    throws SQLException {
                CallableStatement cs = con.prepareCall(procSql);
                for(int i = 0 ; i < inputSize; i++){
                    cs.setObject(i+1, input.get(i));
                }
                for(int j = 0; j < outputSize; j++){
                    cs.registerOutParameter(inputSize + j + 1, output.get(j));
                }
                return cs;
            }
        }, new CallableStatementCallback<Boolean>() {
            @Override
            public Boolean doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {
                boolean tag = cs.execute();
                ResultSet rs = null;
                if(tag){
                    rs = cs.getResultSet();
                    if(clz != null && rs != null){
                        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
                        List<T> list = new ArrayList<T>();
                        while(rs.next()){
                            list.add(rowMapper.mapRow(rs, 0));
                        }
                        ret.setResult(list);
                    }
                }
                
                while(cs.getMoreResults() || cs.getUpdateCount() > -1){
                    rs = cs.getResultSet();
                    if(clz != null && rs != null){
                        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
                        List<T> list = new ArrayList<T>();
                        while(rs.next()){
                            list.add(rowMapper.mapRow(rs, 0));
                        }
                        ret.setResult(list);
                    }
                }
                
                Object[] outputArr = new Object[outputSize];
                for(int k = 0; k < outputSize; k++){
                    outputArr[k] = cs.getObject(inputSize + k + 1);
                }
                ret.setOutputs(outputArr);
                return true;
            }
        });

        return ret;
    }
    
    @Override
    public <T> ProcedureObject<T> executeProcedureForObject(final String procSql,final List<Object> input,
            final List<Integer> output,final Class<T> clz){
        Assert.notNull(procSql, "procSql must be not null");

        final int inputSize = input == null ? 0 : input.size();
        final int outputSize = output == null ? 0 : output.size();

        final ProcedureObject<T> ret = new ProcedureObject<T>();
        this.ownJdbcTemplate.getJdbcOperations().execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con)
                    throws SQLException {
                CallableStatement cs = con.prepareCall(procSql);
                for(int i = 0 ; i < inputSize; i++){
                    cs.setObject(i+1, input.get(i));
                }
                
                for(int j = 0; j < outputSize; j++){
                    cs.registerOutParameter(inputSize + j + 1, output.get(j));
                }
                return cs;
            }
        }, new CallableStatementCallback<Boolean>() {
            @Override
            public Boolean doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {
                boolean tag = cs.execute();
                ResultSet rs = null;
                if(tag){
                    rs = cs.getResultSet();
                    if(clz != null && rs != null){
                        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
                        if(rs.next()){
                            ret.setResult(rowMapper.mapRow(rs, 0));
                            while(rs.next()){}
                        }
                    }
                }
                
                while(cs.getMoreResults() || cs.getUpdateCount() > -1){
                    rs = cs.getResultSet();
                    if(clz != null && rs != null){
                        RowMapper<T> rowMapper = new ToolsRowMapper<T>(clz);
                        if(rs.next()){
                            ret.setResult(rowMapper.mapRow(rs, 0));
                            while(rs.next()){}
                        }
                    }
                }
                
                Object[] outputArr = new Object[outputSize];
                for(int k = 0; k < outputSize; k++){
                    outputArr[k] = cs.getString(inputSize + k + 1);
                }
                ret.setOutputs(outputArr);
                return true;
            }
        });
        return ret;
    }
    

}
