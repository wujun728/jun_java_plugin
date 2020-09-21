/*   
 * Project: OSMP
 * FileName: JdbcDao.java
 * version: V1.0
 */
package com.osmp.jdbc.support;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.osmp.jdbc.define.Page;
import com.osmp.jdbc.define.ProcedureList;
import com.osmp.jdbc.define.ProcedureObject;
import com.osmp.jdbc.define.tool.JdbcFinderHolder;
import com.osmp.jdbc.define.tool.TimeRecord;

/**
 * 封装jdbc基本无事务操作
 * @author heyu
 *
 */
public class JdbcDao{
    
    private JdbcFinderManager _getJdbcFinderManager(){
        JdbcFinderManager jdbcFinder = JdbcFinderHolder.get();
        Assert.notNull(jdbcFinder,"没有找到JdbcFinderManager服务");
        return jdbcFinder;
    }
    
    private String _getSql(String sqlName,Map<String,Object> paramMap){
        String sql = _getJdbcFinderManager().getQueryStringByStatementKey(sqlName,paramMap);
        Assert.notNull(sql,"没有找到"+sqlName+"对应的sql语句");
        TimeRecord.start("执行sql语句:"+sqlName+",参数:"+paramMap);
        return sql;
    }
    
    private String _getSql(String sqlName){
        String sql = _getJdbcFinderManager().getQueryStringByStatementKey(sqlName);
        Assert.notNull(sql,"没有找到"+sqlName+"对应的sql语句");
        TimeRecord.start("执行sql语句:"+sqlName);
        return sql;
    }
    
    private JdbcTemplate _getTemplate(String catalog){
        JdbcTemplate template = _getJdbcFinderManager().findJdbcTemplate(catalog);
        Assert.notNull(template,"没有找到"+catalog+"对应的数据源");
        return template;
    }
    
    
    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, Class<T> clz, 
            int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName), clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, Map<String, Object> queryParam, Class<T> clz,
            int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName,queryParam),queryParam, clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, List<Object> queryParam, Class<T> clz,
            int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName),queryParam, clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, String countSqlKey, Class<T> clz,
            int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName),countSqlKey, clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, String countSqlKey, Map<String, Object> queryParam,
            Class<T> clz, int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName,queryParam),countSqlKey,queryParam, clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> Page<T> queryForPage(String sqlName,String catalog, String countSqlKey, List<Object> queryParam,
            Class<T> clz, int pageNumber, int pageSize){
        
        Page<T> data = _getTemplate(catalog).queryForPage(_getSql(sqlName),countSqlKey,queryParam, clz,pageNumber,pageSize);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> List<T> queryForList(String sqlName,String catalog,Class<T> clz){
        
        List<T> data = _getTemplate(catalog).queryForList(_getSql(sqlName), clz);
        TimeRecord.over();
        return data;
    }
    
    public <T extends Object> List<T> queryForList(String sqlName,String catalog,Map<String, Object> queryParam,Class<T> clz){
        
        List<T> data = _getTemplate(catalog).queryForList(_getSql(sqlName,queryParam),queryParam, clz);
        TimeRecord.over();
        return data;
    }
    
    public <T extends Object> List<T> queryForList(String sqlName,String catalog,List<Object> queryParam,Class<T> clz){
        
        List<T> data = _getTemplate(catalog).queryForList(_getSql(sqlName),queryParam, clz);
        TimeRecord.over();
        return data;
    }
    
    public <T extends Object> List<T> queryForList(String sqlName,String catalog,Class<T> clz,Object... queryParam){
        
        List<T> data = _getTemplate(catalog).queryForList(_getSql(sqlName), clz, queryParam);
        TimeRecord.over();
        return data;
    }
    
    public <T extends Object> T queryForObject(String sqlName,String catalog, Class<T> clz){
        
        T data = _getTemplate(catalog).queryForObject(_getSql(sqlName), clz);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> T queryForObject(String sqlName,String catalog, Map<String, Object> queryParam, Class<T> clz){
        
        T data = _getTemplate(catalog).queryForObject(_getSql(sqlName,queryParam),queryParam, clz);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> T queryForObject(String sqlName,String catalog, List<Object> queryParam, Class<T> clz){
        
        T data = _getTemplate(catalog).queryForObject(_getSql(sqlName),queryParam, clz);
        TimeRecord.over();
        return data;
    }

    public <T extends Object> T queryForObject(String sqlName,String catalog,Class<T> clz, Object... queryParam){
        
        T data = _getTemplate(catalog).queryForObject(_getSql(sqlName) ,clz, queryParam);
        TimeRecord.over();
        return data;
    }

    public String queryForString(String sqlName,String catalog, Object... queryParam){
        return _getTemplate(catalog).queryForString(_getSql(sqlName) , queryParam);
    }

    public String queryForString(String sqlName,Map<String, Object> queryParam,String catalog){
        return _getTemplate(catalog).queryForString(queryParam, _getSql(sqlName,queryParam));
    }

    public Integer queryForInteger(String sqlName,String catalog, Object... queryParam){
        return _getTemplate(catalog).queryForInteger(_getSql(sqlName) , queryParam);
    }

    public Integer queryForInteger(String sqlName, Map<String, Object> queryParam, String catalog){
        return _getTemplate(catalog).queryForInteger(queryParam, _getSql(sqlName,queryParam));
    }

    public Long queryForLong(String sqlName,String catalog, Object... queryParam){
        return _getTemplate(catalog).queryForLong(_getSql(sqlName) , queryParam);
    }

    public Long queryForLong(String sqlName, Map<String, Object> queryParam, String catalog){
        return _getTemplate(catalog).queryForLong(queryParam, _getSql(sqlName,queryParam));
    }

    public Date queryForDate(String sqlName,String catalog, Object... queryParam){
        return _getTemplate(catalog).queryForDate(_getSql(sqlName) , queryParam);
    }

    public Date queryForDate(String sqlName, Map<String, Object> queryParam, String catalog){
        return _getTemplate(catalog).queryForDate(queryParam, _getSql(sqlName,queryParam));
    }

    public BigDecimal queryForBigDecimal(String sqlName,String catalog, Object... queryParam){
        return _getTemplate(catalog).queryForBigDecimal(_getSql(sqlName) , queryParam);
    }

    public BigDecimal queryForBigDecimal(String sqlName, Map<String, Object> queryParam, String catalog){
        return _getTemplate(catalog).queryForBigDecimal(queryParam, _getSql(sqlName,queryParam));
    }

    public int update(String sqlName, Map<String, Object> paramMap, String catalog) throws DataAccessException{
        return _getTemplate(catalog).update(paramMap, _getSql(sqlName,paramMap));
    }

    public int update(String sqlName, String catalog,Object... args) throws DataAccessException{
        return _getTemplate(catalog).update(_getSql(sqlName) , args);
    }

    public int update(String sqlName,String catalog, Object[] args, int[] argTypes) throws DataAccessException{
        return _getTemplate(catalog).update(_getSql(sqlName) , args , argTypes);
    }

    public int[] batchUpdate(String sqlName,String catalog, Map<String, Object>[] batchValues){
        return _getTemplate(catalog).batchUpdate(_getSql(sqlName) , batchValues);
    }
    
    public <T> ProcedureList<T> executeProcedureForList(String sqlName,String catalog,final List<Object> input,
            final List<Integer> output,final Class<T> clz){
        
        ProcedureList<T> data = _getTemplate(catalog).executeProcedureForList(_getSql(sqlName) , input,output,clz);
        TimeRecord.over();
        return data;
    }
    public <T> ProcedureObject<T> executeProcedureForObject(String sqlName,String catalog,final List<Object> input,
            final List<Integer> output,final Class<T> clz){
        
        ProcedureObject<T> data = _getTemplate(catalog).executeProcedureForObject(_getSql(sqlName) , input,output,clz);
        TimeRecord.over();
        return data;
    }
}
