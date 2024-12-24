package io.github.wujun728.admin.db.service.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.admin.common.PageData;
import io.github.wujun728.admin.common.PageParam;
import io.github.wujun728.admin.common.Result;
import io.github.wujun728.admin.common.annotations.OrderBy;
import io.github.wujun728.admin.db.config.DbConfig;
import io.github.wujun728.admin.db.data.ColumnMeta;
import io.github.wujun728.admin.db.service.JdbcDao;
import io.github.wujun728.admin.util.RowMapperUtil;
import io.github.wujun728.admin.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Service("jdbcDao")
//@ConditionalOnProperty(value="db.type",havingValue = "mysql")
@Slf4j
public class MysqlJdbcDaoImpl implements JdbcDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private DbConfig dbConfig;

    @Override
    public int update(String msg,String sql, Object... args) {
        log.info("{},{},{}",msg,sql,args);
        int update = jdbcTemplate.update(sql, args);
        return update;
    }

    @Override
    public int update(String sql, Object... args) {
        return this.update("执行sql ",sql,args);
    }

    @Override
    public Long insert(String msg,String sql, Object... args) {
        log.info("{},{},{}",msg,sql,args);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            for(Object arg:args){
                ps.setObject(i++,arg);
            }
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long insert(String sql, Object... args) {
        return this.insert("执行insert ",sql,args);
    }

    @Override
    public <T> Result<PageData<T>> query(PageParam pageParam, Class<T> clz, String sql, Object... args) {
        String countSql = StrUtil.format("select count(*) from ({}) t",sql);
        log.info("countSql:{},参数{}",countSql,args);
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, args);
        int start = (pageParam.getPage() - 1) * pageParam.getPerPage();
        if(start >= count || start < 0){
            start = 0;
        }
        String pageSql = StrUtil.format("{} limit {},{}",sql,start,pageParam.getPerPage());
        log.info("querySql:{},参数{}",pageSql,args);
        List<T> data = jdbcTemplate.query(pageSql, RowMapperUtil.newRowMapper(clz),args);
        PageData<T> pageData = new PageData();
        pageData.setItems(data);
        pageData.setTotal(count);
        return new Result<>(pageData);
    }
    @Override
    public Result<PageData<Map<String,Object>>> query(PageParam pageParam,String sql,Object... args) {
        String countSql = StrUtil.format("select count(*) from ({}) t",sql);
        log.info("查询 {},{}",sql,args);
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, args);
        int start = (pageParam.getPage() - 1) * pageParam.getPerPage();
        if(start >= count || start < 0){
            start = 0;
        }
        String pageSql = StrUtil.format("{} limit {},{}",sql,start,pageParam.getPerPage());

        List<Map<String,Object>> data = jdbcTemplate.query(pageSql, RowMapperUtil.newMapMapper(),args);
        PageData<Map<String,Object>> pageData = new PageData();
        pageData.setItems(data);
        pageData.setTotal(count);
        return new Result<>(pageData);
    }

    @Override
    public <T> List<T> find(String sql, Class<T> clz, Object... args) {
        log.info(StrUtil.format("查询 {},{}"),sql,args);
        return jdbcTemplate.query(sql,RowMapperUtil.newRowMapper(clz),args);
    }

    @Override
    public <T> T findOne(String sql, Class<T> clz, Object... args) {
        List<T> list = find(sql, clz, args);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Map<String, Object>> find(String sql, Object... args) {
        log.info(StrUtil.format("查询 {},{}"),sql,args);
        return jdbcTemplate.query(sql,RowMapperUtil.newMapMapper(),args);
    }
    @Override
    public <T> List<T> find(Class<T> clz) {
        return this.find(clz,new String[]{},new Object[]{});
    }
    @Override
    public Map<String, Object> findOne(String sql, Object... args) {
        List<Map<String, Object>> list = this.find(sql, args);
        return list.isEmpty() ? null :list.get(0);
    }

    @Override
    public Map<String, Object> getById(String tableName, Long id) {
        if(id == null){
            return null;
        }
        String sql = StrUtil.format("select * from {} where id = ? ",tableName);
        List<Map<String,Object>> list = this.find(sql, id);
        return list.isEmpty() ? null :list.get(0);
    }

    @Override
    public <T> T getById(Class<T> clz, Long id) {
        if(id==null){
            return null;
        }
        String sql = StrUtil.format("select * from {} where id = ? ",getTableName(clz));
        List<T> list = this.find(sql, clz, id);
        return list.isEmpty() ? null :list.get(0);
    }

    private String getTableName(Class clz){
        return StringUtil.toSqlColumn(clz.getSimpleName());
    }
    @Override
    public List<ColumnMeta> columnMeta(String sql) {
        return columnMeta(sql,jdbcTemplate);
    }

    @Override
    public List<ColumnMeta> namedColumnMeta(String sql) {
        return columnMeta(sql,namedParameterJdbcTemplate);
    }

    private List<ColumnMeta> columnMeta(String sql, Object template) {

        List<ColumnMeta> columnMetas = new ArrayList<>();

        List<String> tableColumns = new ArrayList<>();
        Map<String,ColumnMeta> metaMap = new HashMap<>();
        sql = sql + " limit 0 ";

        PreparedStatementCallback<Object> psc = (PreparedStatementCallback<Object>) ps -> {
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            ResultSetMetaData resultSetMetaData = ps.getMetaData();

//            int parameterCount = parameterMetaData.getParameterCount();
//            log.info("parameterCount,{}",parameterCount);
            //下面无法获取
//            for(int i=1;i<=parameterCount;i++){
//                String parameterClassName = parameterMetaData.getParameterClassName(i);
//                String typeName = parameterMetaData.getParameterTypeName(i);
//                log.info("参数类型,{},{}",typeName,parameterClassName);
//            }
            int columnCount = resultSetMetaData.getColumnCount();

            for (int i = 1; i <=columnCount; i++) {
                ColumnMeta columnMeta = new ColumnMeta();

                String columnLabel = resultSetMetaData.getColumnLabel(i);
                String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                String columnClassName = resultSetMetaData.getColumnClassName(i);
                String tableName = resultSetMetaData.getTableName(i);
                String columnName = resultSetMetaData.getColumnName(i);
                log.info("返回数据类型,{},{},{},{},{}",tableName,columnName,columnLabel,columnClassName,columnTypeName);

                columnMeta.setColumnLabel(columnLabel);
                columnMeta.setColumnType(columnTypeName);
                columnMeta.setColumnClassName(columnClassName);
                columnMeta.setTableName(tableName);
                columnMeta.setColumnName(columnName);
                columnMetas.add(columnMeta);
                if(StrUtil.isNotBlank(tableName) && StrUtil.isNotBlank(columnName)){
                    tableColumns.add(StrUtil.format("(c.table_name = '{}' and c.column_name = '{}')",tableName,columnName));

                    metaMap.put(StrUtil.format("{}-{}",tableName,columnName),columnMeta);
                }
            }
            return null;
        };
        if (template instanceof JdbcTemplate){
            ((JdbcTemplate)template).execute(sql,psc );
        }else if(template instanceof NamedParameterJdbcTemplate){
            ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
            List<String> names = ReflectUtil.invoke(parsedSql,"getParameterNames");
            Map<String,Object> params = new HashMap<>();
            for(String name:names){
                params.put(name,"");
            }
            ((NamedParameterJdbcTemplate)template).execute(sql,params,psc);
        }
        if(!tableColumns.isEmpty()){
            StringBuffer commentSql = new StringBuffer(StrUtil.format(
                    "select c.TABLE_NAME,c.COLUMN_NAME,c.COLUMN_COMMENT from {}.`COLUMNS` c where c.TABLE_SCHEMA = '{}' and ({})",
                    dbConfig.getManageSchema(),
                    dbConfig.getSchema(),
                    StringUtil.concatStr(tableColumns," or \n ")
            ));
            log.info(commentSql.toString());

            List<Map<String, Object>> maps = this.find(commentSql.toString());
            for(Map<String,Object> en:maps){
                String key = StrUtil.format("{}-{}",en.get("tableName"),en.get("columnName"));
                String columnComment = (String) en.get("columnComment");
                if(metaMap.containsKey(key)){
                    metaMap.get(key).setColumnComment(columnComment);
                }
            }
        }
        return columnMetas;
    }

    @Override
    public <T> List<T> find(Class<T> clz, String[] fields, Object[] args) {
        String sql = StrUtil.format("select * from {} where 1=1 {} ",
                getTableName(clz),
                StringUtil.concatStr(Arrays.asList(fields).stream().map(field->StrUtil.format(" and {} = ? ",StringUtil.toSqlColumn(field))).collect(Collectors.toList()), " ")
        );
        OrderBy orderBy = clz.getAnnotation(OrderBy.class);
        if(orderBy != null){
            sql += " order by "+orderBy.value();
        }
        return this.find(sql,clz,args);
    }

    @Override
    public <T> T findOne(Class<T> clz, String[] fields, Object[] args) {
        List<T> list = this.find(clz, fields, args);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T> List<T> find(Class<T> clz, String field, Object arg) {
        return this.find(clz,new String[]{field},new Object[]{arg});
    }

    @Override
    public <T> T findOne(Class<T> clz, String field, Object arg) {
        return this.findOne(clz,new String[]{field},new Object[]{arg});
    }

    @Override
    public <T> List<T> findForObject(String sql, Class<T> clz, Object... args) {
        return jdbcTemplate.queryForList(sql,clz,args);
    }

    @Override
    public <T> T findOneForObject(String sql, Class<T> clz, Object... args) {
        List<T> list = this.findForObject(sql, clz, args);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T> List<T> find(String sql, Class<T> clz, Map<String, Object> params) {
        return namedParameterJdbcTemplate.query(sql,params,RowMapperUtil.newRowMapper(clz));
    }

    @Override
    public <T> T findOne(String sql, Class<T> clz, Map<String, Object> params) {
        List<T> list = this.find(sql, clz, params);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Map<String, Object>> find(String sql, Map<String, Object> params) {
        log.info("sql:{},params:{}",sql,params);
        return namedParameterJdbcTemplate.query(sql,params,RowMapperUtil.newMapMapper());
    }

    @Override
    public Map<String, Object> findOne(String sql, Map<String, Object> params) {
        List<Map<String, Object>> list = this.find(sql, params);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T> T findOneForObject(String sql, Map<String, Object> params, Class<T> clz) {
        List<T> list = this.findForObject(sql, params, clz);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public <T> List<T> findForObject(String sql, Map<String, Object> params, Class<T> clz) {
        log.info("sql:{},params:{}",sql,params);
        return namedParameterJdbcTemplate.queryForList(sql,params,clz);
    }
}
