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
@SuppressWarnings({"rawtypes", "unchecked"})
public class DbPro implements IDbPro {

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

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
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
            result = jdbcTemplate.update(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
        return result;
    }

    private List<Map<String, Object>> queryList(SqlContext sqlContext) {
        String sql = null;
        try {
            sql = sqlContext.getSql();
            return jdbcTemplate.queryForList(sql, sqlContext.getParams());
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

    private int updateSqlContext(SqlContext sqlContext) {
        int result;
        String sql = null;
        try {
            sql = sqlContext.getSql();
            result = jdbcTemplate.update(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
        return result;
    }

    /********************************************************************************
     * 私有方法  66666666666666666666
     ********************************************************************************/


//	************************************************************************************************************************************************
//	Class 111111111111111111  begin   **************************************************************************************************************
//	************************************************************************************************************************************************
    @Override
    public <T> List<T> findBeanList(Class<T> clazz, String sql) {
        List<Record> lists = find(sql);
        List<T> datas = RecordUtil.recordToBeanList(lists, clazz);
        return datas;
    }

    @Override
    public <T> List findBeanList(Class clazz, String sql, Object... params) {
        List datas = queryList(sql, params);
        return RecordUtil.mapToBeans(datas, clazz);
    }
    @Override
    public <T> List findMapList(Class clazz, String sql, Object... params) {
        List datas = queryList(sql, params);
        return datas;
    }
    @Override
    public <T> List findRecordList(Class clazz, String sql, Object... params) {
        List datas = queryList(sql, params);
        return RecordUtil.mappingList(datas);
    }

    @Override
    public  <T> List  findBeanList(Class beanClass, Map<String, Object> params) {
        List<Map<String, Object>> datas =  queryList(SqlUtil.getSelect(beanClass, params));
        return RecordUtil.mapToBeans(datas, beanClass);
    }


    /*public List<Map<String, Object>> findBeanList(Class beanClass, String field, Object parmas) {
        return findBeanList(beanClass, new String[]{field}, new Object[]{parmas});
    }*/


    /*public List<Map<String, Object>> findBeanList(Class beanClass, String[] fields, Object[] parmas) {
        return findBeanList(SqlUtil.getByParams(beanClass, fields, parmas));
    }*/


	/*public Integer saveEntity(Object bean) {
		return updateSqlContext(SqlUtil.getInsert(bean));
	}
	public Integer updateEntity(Object bean) {
		return updateSqlContext(SqlUtil.getUpdate(bean));
	}
	public Integer deleteEntity(Object bean) {
		return updateSqlContext(SqlUtil.getDelete(bean));
	}*/

    @Override
    public Integer saveBeanBackPrimaryKey(Object bean) {
        saveBean(bean);
        return jdbcTemplate.queryForObject("SELECT last_insert_id() as id", Integer.class);
    }

    @Override
    public Integer saveBean(Object bean) {
        return updateSql(SqlUtil.getInsert(bean));
    }

    @Override
    public Integer updateBean(Object bean) {
        return updateSql(SqlUtil.getUpdate(bean));
    }

    @Override
    public Integer deleteBean(Object bean) {
        return updateSql(SqlUtil.getDelete(bean));
    }

    /*public Object findById(Class beanClass, Object[] id) {
        SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
        return findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
    }*/

    /*public Object findByParams(Class beanClass, String[] fields, Object[] parmas) {
        SqlContext sqlContext = SqlUtil.getByParams(beanClass, fields, parmas);
        Object obj = findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
        return obj;
    }*/


    /*public Object findById(Class beanClass, Object[] id) {
        SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
        return findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
    }*/
    @Override
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

    /*public Object findEntityByParams(Class beanClass, String[] fields, Object[] parmas) {
        SqlContext sqlContext = SqlUtil.getByParams(beanClass, fields, parmas);
        Object obj = findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
        return obj;
    }*/

    @Override
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


    @Override
    public Object  findBeanBySql(Class clazz, String sql, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz), params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> Page findBeanPages(Class beanClass, int page, int rows) {
        return findBeanPages(beanClass, page, rows, Maps.newHashMap());
    }

    @Override
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



    /*public <T> List findEntityAll(Class beanClass, String sql, Object[] params) {
        List<Map<String, Object>> list = queryList(SqlUtil.getSelect(beanClass));
        return RecordUtil.mapToBeans(list, beanClass);
    }*/
    @Override
    public List<Map<String, Object>> queryList(Class beanClass, Map<String, Object> params) {
        return queryList(SqlUtil.getSelect(beanClass, params));
    }


    @Override
    public List<Map<String, Object>> queryBeanFieldList(Class beanClass, String field, Object parmas) {
        return queryBeanFieldsList(beanClass, new String[]{field}, new Object[]{parmas});
    }


    @Override
    public List<Map<String, Object>> queryBeanFieldsList(Class beanClass, String[] fields, Object[] parmas) {
        return queryList(SqlUtil.getByParams(beanClass, fields, parmas));
    }


    @Override
    public Page queryBeanPage(Class beanClass, int page, int rows) {
        return queryBeanPage(beanClass, page, rows, Maps.newHashMap());
    }

    @Override
    public Page queryBeanPage(Class beanClass, int page, int rows, Map<String, Object> params) {
        SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
        Page pageVo = new Page();
        List<Map<String, Object>> listData = queryList(sqlContext);
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        pageVo.setTotalRow(queryInt(SqlUtil.getCount(sqlContext)));
        return pageVo;
    }


    //************************************************************************************************************************************************
    //Class 111111111111111111  end   ***************************************************************************************************************
    //************************************************************************************************************************************************


    /*************************************************************************************************************************************************
     SQL 111111111111111111  begin   ***************************************************************************************************************
     * 业务层 禁止写sql语句，以下方法仅供子类调用
     *************************************************************************************************************************************************/

    @Override
    public List<Map<String, Object>> queryList(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql, params);
    }

    @Override
    public List<Map<String, Object>> queryList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    /*public List<Map<String, Object>> queryMaps(String sql) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return results;
    }

    public List<Map<String, Object>> queryMaps(String sql, Object[] paras) {
        List results = jdbcTemplate.queryForList(sql, paras);
        return results;
    }*/

	/*public  List<Map<String, Object>> query(String sql, Object[] paras) {
		List results = jdbcTemplate.queryForList(sql, paras);
		return results;
	}

	public  List<Map<String, Object>> query(String sql) {
		List results = jdbcTemplate.queryForList(sql);
		return results;
	}*/

    @Override
    public int updateSql(String sql) {
        return jdbcTemplate.update(sql);
    }

    /*public List<Map<String, Object>> queryList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }*/

    /*public List<Map<String, Object>> queryList(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql, params);
    }*/

    @Override
    public String queryForString(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    @Override
    public Date queryForDate(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, Date.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }


    @Override
    public Page<Map> queryMapPages(String sql, int page, int rows, Object[] params) {
        Page pageVo = new Page();
        pageVo.setList(queryList(SqlUtil.getSelect(sql, page, rows), params));
        pageVo.setTotalRow(count(SqlUtil.getCount(sql), params));
        return pageVo;
    }

    @Override
    public int count(String sql, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    @Override
    public int queryForInt(String sql, Object[] params) {
        return count(sql, params);
    }


    @Override
    public Boolean deleteById(String tableName, Object... idValues) {
        String primaryKeyStr = getPkNames(tableName);
        if (primaryKeyStr.contains(",")) {
            return deleteByIds(tableName, primaryKeyStr, idValues);
        }
        String sql = " DELETE FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
        int flag = jdbcTemplate.update(sql, idValues);
        return flag > 0;
    }

    @Override
    public Boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");
        String sql = dialect.forDbDeleteById(tableName, pKeys);
        int flag = getJdbcTemplate().update(sql, idValues);
        //int flag = jdbcTemplate.update(sql, idValues);
        return flag > 0;
    }


    @Override
    public Boolean deleteBySql(String sql, Object... paras) {
        int flag = jdbcTemplate.update(sql, paras);
        return flag > 0;
    }

    @Override
    public Boolean deleteBySql(String sql) {
        return deleteBySql(sql, new Object[]{});
    }


    /*************************************************************************************************************************************************
     SQL 111111111111111111  end   ***************************************************************************************************************
     *************************************************************************************************************************************************/


    //************************************************************************************************************************************************
    //Record begin  **************************************************************************************************************************************
    //************************************************************************************************************************************************

    /**
     * 根据id查找
     *
     * @param tableName 表名
     * @param id        主键
     * @return Record
     */
    @Override
    public Record findById(String tableName, Object id) {
        return findRecordById(tableName,id);
    }
    @Override
    public Record findRecordById(String tableName, Object id) {
        String primaryKeyStr = getPkNames(tableName);
        if (primaryKeyStr.contains(",")) {
            return findByIds(tableName, primaryKeyStr, new Object[]{id});
        }
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
        Map<String, Object> resultMap = null;
        try {
            resultMap = jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapping(resultMap);
    }


    @Override
    public Record findByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        Map<String, Object> resultMap;
        try {
            resultMap = jdbcTemplate.queryForMap(sql, idValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapping(resultMap);
    }

    @Override
    public List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Map<String, Object>> resultMap;
        try {
            resultMap = jdbcTemplate.queryForList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mappingList(resultMap);
    }

    @Override
    public <T> List findByColumnValueBeans(Class clazz, String columnNames, Object... columnValues) {
        String tableName = SqlUtil.getTableName(clazz);
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Map<String, Object>> resultMap;
        try {
            resultMap = jdbcTemplate.queryForList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapToBeans(resultMap, clazz);
    }

    @Override
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
            resultMap = jdbcTemplate.queryForList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mapToBeans(resultMap, clazz);
    }


    /*public List<Record> findBySql(String sql) {
        return find(sql);
    }*/

    @Override
    public List<Record> find(String sql) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return RecordUtil.mappingList(results);
    }


    @Override
    public List<Record> find(String sql, Object... param) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, param);
        return RecordUtil.mappingList(results);
    }

    @Override
    public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
        return paginate(pageNumber, limit, select, from, Maps.newHashMap());
    }

    @Override
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


    /**
     * 保存record
     *
     * @param tableName 表名
     * @param record    record对象
     */
    @Override
    public boolean save(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        List<Object> paras = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        dialect.forDbSave(tableName, pKeys, record, sql, paras);
        int flag = 0;
        try {
            flag = jdbcTemplate.update(sql.toString(), paras.toArray());
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

    @Override
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
        int result = jdbcTemplate.update(sql.toString(), paras.toArray());
        if (result >= 1) {
            return true;
        }
        return false;
    }

    @Override
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
        int result = jdbcTemplate.update(deleteSql, ids);
        if (result >= 1) {
            return true;
        }
        return false;
    }


    //************************************************************************************************************************************************
    //Record end  **************************************************************************************************************************************
    //************************************************************************************************************************************************


    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************

    @Override
    public Object executeSqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.executeSql(dataSource.getConnection(), sqlXml, params, true);
    }
    @Override
    public int updateSqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.update(dataSource.getConnection(), sqlXml, params);
    }
    @Override
    public List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.query(dataSource.getConnection(), sqlXml, params);
    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


}



