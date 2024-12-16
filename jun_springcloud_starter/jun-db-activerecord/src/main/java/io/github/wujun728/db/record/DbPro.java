package io.github.wujun728.db.record;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.google.common.collect.Maps;
import io.github.wujun728.db.record.dialect.*;
import io.github.wujun728.db.record.exception.SqlException;
import io.github.wujun728.db.record.dialect.Dialect;
import io.github.wujun728.db.record.dialect.MysqlDialect;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.SqlContext;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.sql.SqlXmlUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.wujun728.db.record.Db.main;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
public class DbPro{

    private DataSource dataSource = null;
    private JdbcTemplate jdbcTemplate = null;
    private Dialect dialect = new MysqlDialect();

    public static final Map<String, DbPro> cache = new HashMap<>(32, 0.25F);
    public static final Map<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap<>(24);
    public static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(24);

    private static Map<String, String> TABLE_PK_MAP = new HashMap<>();

    public DbPro() {
    }
    public DbPro(String configName) {
        use(configName);
    }

    static DbPro use() {
        return use(main);
    }

    static DbPro use(String configName) {
        DbPro result = DbPro.cache.get(configName);
        if (result == null) {
            result = new DbPro();
            result.setJdbcTemplate(DbPro.jdbcTemplateMap.get(configName));
            result.setDataSource(DbPro.dataSourceMap.get(configName));
            result.setDialect(DbPro.getDialect(result.getDataSource()));
            DbPro.registerRecord(result.getDataSource());
            DbPro.cache.put(configName, result);
        }
        return result;
    }



    private static void registerRecord(DataSource dataSource) {
        List<String> tables = MetaUtil.getTables(dataSource);
        tables.forEach(tableName -> {
            Table table = MetaUtil.getTableMeta(dataSource, tableName);
            String pkStr = String.join(",", table.getPkNames());
            TABLE_PK_MAP.put(tableName, pkStr);// map不清，只做替换
        });
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    public DataSource getDataSource() {
        return this.dataSource;
    }

    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    public Dialect getDialect() {
        return dialect;
    }

    
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    private static Dialect getDialect(DataSource dataSource) {
        try {
            String dbName = dataSource.getConnection().getMetaData().getDatabaseProductName();
            if (dbName.equalsIgnoreCase("Oracle")) {
                return new OracleDialect();
            } else if (dbName.equalsIgnoreCase("mysql")) {
                return new MysqlDialect();
            } else if (dbName.equalsIgnoreCase("sqlite")) {
                return new Sqlite3Dialect();
            } else if (dbName.equalsIgnoreCase("postgresql")) {
                return new PostgreSqlDialect();
            } else {
                return new AnsiSqlDialect();
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败");
        }
    }


    /********************************************************************************
     * 私有方法   66666666666666666666
     ********************************************************************************/


    private int updateSql(SqlContext sqlContext) {
        int result;
        String sql = null;
        try {
            sql = sqlContext.getSql();
            result = updateSql(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
        return result;
    }

    private List<Map<String, Object>> queryList(SqlContext sqlContext) {
        String sql = null;
        try {
            sql = sqlContext.getSql();
            return queryList(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    private int queryInt(SqlContext sqlContext) {
        String sql = null;
        try {
            sql = sqlContext.getSql();
            return jdbcTemplate.queryForObject(sql, Integer.class, sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }


    public List<Map<String, Object>> queryList(String sql, Object... params) {
        return jdbcTemplate.queryForList(sql,params);
    }

    public int updateSql(String sql) {
        return jdbcTemplate.update(sql);
    }
    public int updateSql(String sql, Object... params) {
        return jdbcTemplate.update(sql,params);
    }
    public String queryForString(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    public Date queryForDate(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, Date.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    public Map<String, Object> queryMap(String sql, Object... idValues) {
        Map<String, Object> resultMap;
        try {
            resultMap = jdbcTemplate.queryForMap(sql, idValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return resultMap;
    }

    public static String getPkNames(String tableName) {
        if(CollectionUtil.isEmpty(DbPro.TABLE_PK_MAP)){
            DbPro.registerRecord(DbPro.dataSourceMap.get(main));
        }
        String primaryKey = DbPro.TABLE_PK_MAP.get(tableName);
        if(StrUtil.isEmpty(primaryKey)){
            throw new RuntimeException("当前操作的表没有主键或表不存在，tableName="+tableName);
        }
        return primaryKey;
    }

    /************************************************************************************************************************
     * 私有方法  66666666666666666666
     ************************************************************************************************************************/


    // Save ***********************************************************************************************************
    // Save ***********************************************************************************************************



    public Integer saveBeanBackPrimaryKey(Object bean) {
        saveBean(bean);
        return jdbcTemplate.queryForObject("SELECT last_insert_id() as id", Integer.class);
    }

    public Integer saveBean(Object bean) {
        return updateSql(SqlUtil.getInsert(bean));
    }

    public boolean insert(String sql, Object... params) {
        return updateSql(sql,params)>0;
    }
    public boolean save(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        List<Object> paras = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        dialect.forDbSave(tableName, pKeys, record, sql, paras);
        int flag = 0;
        try {
            flag = updateSql(sql.toString(), paras.toArray());
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("主键冲突，保存失败！");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }



    // Update ***********************************************************************************************************
    // Update ***********************************************************************************************************

    public Integer updateBean(Object bean) {
        return updateSql(SqlUtil.getUpdate(bean));
    }

    public boolean update(String sql, Object... params) {
        return updateSql(sql,params)>0;
    }
    public boolean update(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            ids[i] = record.get(pKeys[i].trim());    // .trim() is important!
            if (ids[i] == null)
                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
        }
        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<Object>();
        dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);
        if (paras.size() <= 1) {    // 参数个数为 1 的情况表明只有主键，也无需更新
            return false;
        }
        int result = updateSql(sql.toString(), paras.toArray());
        if (result >= 1) {
            return true;
        }
        return false;
    }


    // Delete ***********************************************************************************************************
    // Delete ***********************************************************************************************************

    public Integer deleteBean(Object bean) {
        return updateSql(SqlUtil.getDelete(bean));
    }


    public Boolean deleteById(String tableName, Object... idValues) {
        String primaryKeyStr = getPkNames(tableName);
        if (primaryKeyStr.contains(",")) {
            return deleteByIds(tableName, primaryKeyStr, idValues);
        }
        String sql = " DELETE FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
        int flag = updateSql(sql, idValues);
        return flag > 0;
    }


    public Boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");
        String sql = dialect.forDbDeleteById(tableName, pKeys);
        int flag = updateSql(sql, idValues);
        return flag > 0;
    }

    public Boolean deleteBySql(String sql, Object... paras) {
        int flag = updateSql(sql, paras);
        return flag > 0;
    }


    public Boolean deleteBySql(String sql) {
        return deleteBySql(sql, new Object[]{});
    }


    public boolean delete(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            ids[i] = record.get(pKeys[i].trim());    // .trim() is important!
            if (ids[i] == null)
                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
        }
        String deleteSql = dialect.forDbDeleteById(tableName, pKeys);
        int result = updateSql(deleteSql, ids);
        if (result >= 1) {
            return true;
        }
        return false;
    }


    // Query ***********************************************************************************************************
    // Query ***********************************************************************************************************



    public <T> List<T> findBeanList(Class<T> clazz, String sql) {
        List<Record> lists = find(sql);
        List<T> datas = RecordUtil.recordToBeanList(lists, clazz);
        return datas;
    }

    
    public <T> List findBeanList(Class clazz, String sql, Object... params) {
        List datas = queryList(sql, params);
        return RecordUtil.mapToBeans(datas, clazz);
    }
    
    public <T> List findMapList(Class clazz, String sql, Object... params) {
        return queryList(sql, params);
    }

    public  <T> List  findBeanList(Class beanClass, Map<String, Object> params) {
        List<Map<String, Object>> datas =  queryList(SqlUtil.getSelect(beanClass, params));
        return RecordUtil.mapToBeans(datas, beanClass);
    }


    
    public <T> List findRecordList(String sql, Object... params) {
        List datas = queryList(sql, params);
        return RecordUtil.mappingList(datas);
    }

    public <T> T findBeanById(Class<T> clazz, Object... idValue) {
        String tableName = SqlUtil.getTableName(clazz);
        String primaryKeyStr = getPkNames(tableName);
        Record record = findByIds(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        T datas = (T) RecordUtil.recordToBean(record, clazz);
        return datas;
    }

    
    public <T> T findBeanByIds(Class beanClass, String primaryKeys, Object... idValue) {
        String tableName = SqlUtil.getTableName(beanClass);
        String primaryKeyStr = StrUtil.join(",", primaryKeys);
        if (primaryKeys == null || StrUtil.isEmpty(primaryKeyStr)) {
            primaryKeyStr = getPkNames(tableName);
        }
        Record record = findByIds(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        T datas = (T) RecordUtil.recordToBean(record, beanClass);
        return datas;
    }


    public <T> Page findBeanPages(Class beanClass, int page, int rows) {
        return findBeanPages(beanClass, page, rows, Maps.newHashMap());
    }

    
    public <T> Page findBeanPages(Class beanClass, int page, int rows, Map<String, Object> params) {
        SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
        Page pageVo = new Page();
        List<Map<String, Object>> listData = queryList(sqlContext);
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        int totalRow = queryInt(SqlUtil.getCount(sqlContext));
        pageVo.setTotalRow(totalRow);
        pageVo.setPageSize(rows);
        pageVo.setPageNumber(page);
        if (totalRow % rows == 0) {
            pageVo.setTotalPage(totalRow / rows);
        } else {
            pageVo.setTotalPage(totalRow / rows + 1);
        }
        return pageVo;
    }

    
    public Page queryBeanPage(Class beanClass, int page, int rows) {
        return queryBeanPage(beanClass, page, rows, Maps.newHashMap());
    }

    
    public Page queryBeanPage(Class beanClass, int page, int rows, Map<String, Object> params) {
        SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
        Page pageVo = new Page();
        List<Map<String, Object>> listData = queryList(sqlContext);
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        pageVo.setTotalRow(queryInt(SqlUtil.getCount(sqlContext)));
        return pageVo;
    }

    
    public Page<Map> queryMapPages(String sql, int page, int rows, Object[] params) {
        Page pageVo = new Page();
        pageVo.setList(queryList(SqlUtil.getSelect(sql, page, rows), params));
        pageVo.setTotalRow(count(SqlUtil.getCount(sql), params));
        return pageVo;
    }

    
    public int count(String sql, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }





    //************************************************************************************************************************************************
    //Record begin  **************************************************************************************************************************************
    //************************************************************************************************************************************************

    public Record findById(String tableName, Object id) {
        return findRecordById(tableName,id);
    }
    
    public Record findRecordById(String tableName, Object id) {
        String primaryKeyStr = getPkNames(tableName);
        if (primaryKeyStr.contains(",")) {
            return findByIds(tableName, primaryKeyStr, new Object[]{id});
        }
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
        Map<String, Object> resultMap = null;
        try {
            resultMap = queryMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapping(resultMap);
    }


    
    public Record findByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        Map<String, Object> resultMap = queryMap(sql, idValues);
        if (resultMap == null) return null;
        return RecordUtil.mapping(resultMap);
    }


    public List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Map<String, Object>> resultMap;
        try {
            resultMap = queryList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mappingList(resultMap);
    }

    
    public <T> List findByColumnValueBeans(Class clazz, String columnNames, Object... columnValues) {
        String tableName = SqlUtil.getTableName(clazz);
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Map<String, Object>> resultMap;
        try {
            resultMap = queryList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapToBeans(resultMap, clazz);
    }

    
    public <T> List findByWhereSqlForBean(Class clazz, String whereSql, Object... columnValues) {
        String tableName = SqlUtil.getTableName(clazz);
        String[] pKeys = {};
        String sql = dialect.forDbFindById(tableName, pKeys);
        if (StrUtil.isEmpty(whereSql)) {
            sql = sql + " 1=1 ";
        } else {
            sql = sql + whereSql;
        }
        List<Map<String, Object>> resultMap;
        try {
            resultMap = queryList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapToBeans(resultMap, clazz);
    }


    
    public List<Record> find(String sql) {
        List<Map<String, Object>> results = queryList(sql);
        return RecordUtil.mappingList(results);
    }


    
    public List<Record> find(String sql, Object... param) {
        List<Map<String, Object>> results = queryList(sql, param);
        return RecordUtil.mappingList(results);
    }

    
    public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
        return paginate(pageNumber, limit, select, from, Maps.newHashMap());
    }

    
    public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from, Map<String, Object> params) {
        String sqlStr = select + " " + from;
        SqlContext sqlContext = SqlUtil.getSelect(new StringBuilder(sqlStr), params);
        Page pageVo = new Page();
        List<Map<String, Object>> results = queryList(sqlContext);
        List<Record> records = RecordUtil.mappingList(results);
        int totalpage = queryInt(SqlUtil.getCount(sqlContext));
        pageVo.setList(records);
        pageVo.setTotalRow(totalpage);
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(limit);
        pageVo.setTotalPage(totalpage / limit);
        return pageVo;
    }


    //************************************************************************************************************************************************
    //Record end  **************************************************************************************************************************************
    //************************************************************************************************************************************************


    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************

    
    public Object executeSqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.executeSql(dataSource.getConnection(), sqlXml, params, true);
    }
    
    public int updateSqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.update(dataSource.getConnection(), sqlXml, params);
    }
    
    public List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.query(dataSource.getConnection(), sqlXml, params);
    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


}



