package io.github.wujun728.db.record;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import io.github.wujun728.db.record.dialect.*;
import io.github.wujun728.db.record.exception.ActiveRecordException;
import io.github.wujun728.db.record.exception.SqlException;
import io.github.wujun728.db.record.kit.TimeKit;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.SqlContext;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.sql.SqlXmlUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static io.github.wujun728.db.record.Db.main;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
public class DbPro{

    private DataSource dataSource = null;
    private JdbcTemplate jdbcTemplate = null;
    private Dialect dialect = new MysqlDialect();

    public volatile static ConcurrentHashMap<String, DbPro> cache = new ConcurrentHashMap<>(32, 0.25F);
    public volatile static ConcurrentHashMap<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap<>(32);
    public volatile static ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(32);

    //public static final int DB_BATCH_COUNT = 1024;
    static final Object[] NULL_PARA_ARRAY = new Object[0];
    private static final Map<String, String> TABLE_PK_MAP = new HashMap<>();

    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    int transactionLevel = Connection.TRANSACTION_REPEATABLE_READ;

    public DbPro() {
    }
    public DbPro(String dsName) {
        init(dsName);
    }
    /*public DbPro(String dsName,DataSource dataSource,JdbcTemplate jdbcTemplate,Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        this.dialect = dialect;
        init(dsName);
    }*/

    /*static DbPro use() {
        return use(main);
    }*/

    static DbPro init(String dsName) {
        DbPro result = DbPro.cache.get(dsName);
        if (result == null) {
            result = new DbPro();
            result.setJdbcTemplate(DbPro.jdbcTemplateMap.get(dsName));
            result.setDataSource(DbPro.dataSourceMap.get(dsName));
            result.setDialect(DbPro.getDialect(result.getDataSource()));
            DbPro.registerRecord(result.getDataSource());
            DbPro.cache.put(dsName, result);
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
        Connection connection  = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String dbName = connection.getMetaData().getDatabaseProductName();
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
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            //return queryInt(sql,sqlContext.getParams());
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }


    public List<Map<String, Object>> queryList(String sql, Object... params) {
        return jdbcTemplate.queryForList(sql,params);
        //return queryMaps(getConnection(),sql,params );
    }

    public int updateSql(String sql) {
        return jdbcTemplate.update(sql);
        //int flag =  update(getConnection(),sql);
        //return flag;
    }
    public int updateSql(String sql, Object... params) {
        return jdbcTemplate.update(sql,params);
        //return update(getConnection(),sql,params);
    }
    public String queryForString(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class, params);
            // return queryStr(sql,params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    public Date queryForDate(String sql, Object[] params) {
        try {
            return jdbcTemplate.queryForObject(sql, Date.class, params);
            //return queryDate(sql,params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }

    public Map<String, Object> queryMap(String sql, Object... idValues) {
        Map<String, Object> resultMap;
        try {
            resultMap = jdbcTemplate.queryForMap(sql, idValues);
            return resultMap;
            //return queryFirstMap(sql,idValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
        //return queryInt("SELECT last_insert_id() as id");
    }

    public Integer saveBean(Object bean) {
        return updateSql(SqlUtil.getInsert(bean));
    }

    public boolean insert(String sql, Object... params) {
        return updateSql(sql,params)>0;
    }




    // Update ***********************************************************************************************************
    // Update ***********************************************************************************************************

    public Integer updateBean(Object bean) {
        return updateSql(SqlUtil.getUpdate(bean));
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




    public Boolean deleteBySql(String sql, Object... paras) {
        int flag = updateSql(sql, paras);
        return flag > 0;
    }


    public Boolean deleteBySql(String sql) {
        return deleteBySql(sql, new Object[]{});
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
            //return queryInt(sql,params);
        } catch (Exception e) {
            throw new SqlException(e, sql);
        }
    }





    //************************************************************************************************************************************************
    //Record begin  **************************************************************************************************************************************
    //************************************************************************************************************************************************



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
        return SqlXmlUtil.executeSql(getConnection(), sqlXml, params, true);
    }

    public int updateSqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.update(getConnection(), sqlXml, params);
    }

    public List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
        return SqlXmlUtil.query(getConnection(), sqlXml, params);
    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


    protected <T> List<Map<String, Object>> queryMaps(String sql, Object... paras) {
        List result = new ArrayList();
        return jdbcTemplate.queryForList(sql,paras);
    }
    /*protected <T> List<T> queryMaps( Connection conn, String sql, Object... paras) {
        List result = new ArrayList();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            dialect.fillStatement(pst, paras);
            ResultSet rs = pst.executeQuery();
            int colAmount = rs.getMetaData().getColumnCount();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= colAmount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            if (colAmount > 1) {
                while (rs.next()) {
                    Map map = new HashMap();
                    columns.stream().forEach(t -> {
                        try {
                            Object value = rs.getObject(t);
                            map.put(t, value);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                    result.add(map);
                }
            }
            else if(colAmount == 1) {
                while (rs.next()) {
                    result.add(rs.getObject(1));
                }
            }
            DbKit.close(rs);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }
    }*/

    /*protected <T> List<T> query( Connection conn, String sql, Object... paras) throws SQLException {
        List result = new ArrayList();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            dialect.fillStatement(pst, paras);
            ResultSet rs = pst.executeQuery();
            int colAmount = rs.getMetaData().getColumnCount();
            if (colAmount > 1) {
                while (rs.next()) {
                    Object[] temp = new Object[colAmount];
                    for (int i=0; i<colAmount; i++) {
                        temp[i] = rs.getObject(i + 1);
                    }
                    result.add(temp);
                }
            }
            else if(colAmount == 1) {
                while (rs.next()) {
                    result.add(rs.getObject(1));
                }
            }
            DbKit.close(rs);
            return result;
        }
    }*/

    public <T> List<T> query(String sql, Object... paras) {
        //List list = jdbcTemplate.queryForList(sql, paras);
        return (List<T>) jdbcTemplate.query(sql, new RowMapper<Object[]>() {
            public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                int colAmount = rs.getMetaData().getColumnCount();
                if (colAmount > 1) {
                    Object[] temp = new Object[colAmount];
                    for (int i=0; i<colAmount; i++) {
                        temp[i] = rs.getObject(i + 1);
                    }
                    return temp;
                }else if(colAmount == 1) {
                    return new Object[]{rs.getObject(1)};
                }
                return null;
            }
        });
    }
    /*public <T> List<T> query(String sql, Object... paras) {
        Connection conn = null;
        try {
            conn = getConnection();
            return query(conn, sql, paras);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        } finally {
            close(conn);
        }
    }*/


    /**
     * @see #query(String, Object...)
     * @param sql an SQL statement
     */
    public <T> List<T> query(String sql) {		// return  List<object[]> or List<object>
        return query(sql, NULL_PARA_ARRAY);
    }

    /**
     * Execute sql query and return the first result. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return Object[] if your sql has select more than one column,
     * 			and it return Object if your sql has select only one column.
     */
    public <T> T queryFirst(String sql, Object... paras) {
        List<T> result = query(sql, paras);
        return (result.size() > 0 ? result.get(0) : null);
    }
    public <T> T queryFirstMap(String sql, Object... paras) {
        List<T> result = (List<T>) queryMaps( sql, paras);
        return (result.size() > 0 ? result.get(0) : null);
    }

    /**
     * @see #queryFirst(String, Object...)
     * @param sql an SQL statement
     */
    public <T> T queryFirst(String sql) {
        // return queryFirst(sql, NULL_PARA_ARRAY);
        List<T> result = query(sql, NULL_PARA_ARRAY);
        return (result.size() > 0 ? result.get(0) : null);
    }
    public <T> T queryFirstMap(String sql) {
        List<T> result = (List<T>) queryMaps( sql, NULL_PARA_ARRAY);
        return (result.size() > 0 ? result.get(0) : null);
    }

    // 26 queryXxx method below -----------------------------------------------
    /**
     * Execute sql query just return one column.
     * @param <T> the type of the column that in your sql's select statement
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return <T> T
     */
    public <T> T queryColumn(String sql, Object... paras) {
        List<T> result = query(sql, paras);
        if (result.size() > 0) {
            T temp = result.get(0);
            if (temp instanceof Object[] && ((Object[]) temp).length>1){
                throw new SqlException("Only ONE COLUMN can be queried.");
            }else if (temp instanceof Object[] && ((Object[]) temp).length == 1){
                return (T) ((Object[]) temp)[0];
            }
            return temp;
        }
        return null;
    }

    public <T> T queryColumn(String sql) {
        return (T)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public String queryStr(String sql, Object... paras) {
        Object s = queryColumn(sql, paras);
        return s != null ? s.toString() : null;
    }

    public String queryStr(String sql) {
        return queryStr(sql, NULL_PARA_ARRAY);
    }

    public Integer queryInt(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.intValue() : null;
    }

    public Integer queryInt(String sql) {
        return queryInt(sql, NULL_PARA_ARRAY);
    }

    public Long queryLong(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.longValue() : null;
    }

    public Long queryLong(String sql) {
        return queryLong(sql, NULL_PARA_ARRAY);
    }

    public Double queryDouble(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.doubleValue() : null;
    }

    public Double queryDouble(String sql) {
        return queryDouble(sql, NULL_PARA_ARRAY);
    }

    public Float queryFloat(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.floatValue() : null;
    }

    public Float queryFloat(String sql) {
        return queryFloat(sql, NULL_PARA_ARRAY);
    }

    public BigDecimal queryBigDecimal(String sql, Object... paras) {
        Object n = queryColumn(sql, paras);
        if (n instanceof BigDecimal) {
            return (BigDecimal)n;
        } else if (n != null) {
            return new BigDecimal(n.toString());
        } else {
            return null;
        }
    }

    public BigDecimal queryBigDecimal(String sql) {
        return queryBigDecimal(sql, NULL_PARA_ARRAY);
    }

    public BigInteger queryBigInteger(String sql, Object... paras) {
        Object n = queryColumn(sql, paras);
        if (n instanceof BigInteger) {
            return (BigInteger)n;
        } else if (n != null) {
            return new BigInteger(n.toString());
        } else {
            return null;
        }
    }

    public BigInteger queryBigInteger(String sql) {
        return queryBigInteger(sql, NULL_PARA_ARRAY);
    }

    public byte[] queryBytes(String sql, Object... paras) {
        return (byte[])queryColumn(sql, paras);
    }

    public byte[] queryBytes(String sql) {
        return (byte[])queryColumn(sql, NULL_PARA_ARRAY);
    }

    public java.util.Date queryDate(String sql, Object... paras) {
        Object d = queryColumn(sql, paras);

        if (d instanceof Temporal) {
            if (d instanceof LocalDateTime) {
                return TimeKit.toDate((LocalDateTime)d);
            }
            if (d instanceof LocalDate) {
                return TimeKit.toDate((LocalDate)d);
            }
            if (d instanceof LocalTime) {
                return TimeKit.toDate((LocalTime)d);
            }
        }

        return (java.util.Date)d;
    }

    public java.util.Date queryDate(String sql) {
        return queryDate(sql, NULL_PARA_ARRAY);
    }

    public LocalDateTime queryLocalDateTime(String sql, Object... paras) {
        Object d = queryColumn(sql, paras);

        if (d instanceof LocalDateTime) {
            return (LocalDateTime)d;
        }
        if (d instanceof LocalDate) {
            return ((LocalDate)d).atStartOfDay();
        }
        if (d instanceof LocalTime) {
            return LocalDateTime.of(LocalDate.now(), (LocalTime)d);
        }
        if (d instanceof java.util.Date) {
            return TimeKit.toLocalDateTime((java.util.Date)d);
        }

        return (LocalDateTime)d;
    }

    public LocalDateTime queryLocalDateTime(String sql) {
        return queryLocalDateTime(sql, NULL_PARA_ARRAY);
    }

    public java.sql.Time queryTime(String sql, Object... paras) {
        return (java.sql.Time)queryColumn(sql, paras);
    }

    public java.sql.Time queryTime(String sql) {
        return (java.sql.Time)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public java.sql.Timestamp queryTimestamp(String sql, Object... paras) {
        return (java.sql.Timestamp)queryColumn(sql, paras);
    }

    public java.sql.Timestamp queryTimestamp(String sql) {
        return (java.sql.Timestamp)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public Boolean queryBoolean(String sql, Object... paras) {
        return (Boolean)queryColumn(sql, paras);
    }

    public Boolean queryBoolean(String sql) {
        return (Boolean)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public Short queryShort(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.shortValue() : null;
    }

    public Short queryShort(String sql) {
        return queryShort(sql, NULL_PARA_ARRAY);
    }

    public Byte queryByte(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.byteValue() : null;
    }

    public Byte queryByte(String sql) {
        return queryByte(sql, NULL_PARA_ARRAY);
    }

    public Number queryNumber(String sql, Object... paras) {
        return (Number)queryColumn(sql, paras);
    }

    public Number queryNumber(String sql) {
        return (Number)queryColumn(sql, NULL_PARA_ARRAY);
    }
    // 26 queryXxx method under -----------------------------------------------


    public void setThreadLocalConnection(Connection connection) {
        threadLocal.set(connection);
    }

    public void removeThreadLocalConnection() {
        threadLocal.remove();
    }

    /**
     * Get Connection. Support transaction if Connection in ThreadLocal
     */
    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection getThreadLocalConnection() {
        try {
            Connection conn = threadLocal.get();
            if (conn != null)
                return conn;
            conn =  dataSource.getConnection();
            threadLocal.set(conn);
            return threadLocal.get();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Return true if current thread in transaction.
     */
    public boolean isInTransaction() {
        return threadLocal.get() != null;
    }


    public void close(Connection conn) {
        if (threadLocal.get() == null)		// in transaction if conn in threadlocal
            if (conn != null)
                try {conn.close();} catch (SQLException e) {throw new ActiveRecordException(e);}
    }


    /**
     * Execute sql update
     */
    protected int update( String sql, Object... paras) {
         return updateSql(sql,paras);
    }


    /**
     * @see #update(String, Object...)
     * @param sql an SQL statement
     */
    public int update(String sql) {
        return update(sql, NULL_PARA_ARRAY);
    }

    public List<Record> find(String sql, Object... paras)  {
        List<Map<String, Object>> results = queryList(sql,paras);
        return RecordUtil.mappingList(results);
    }

    /**
     */
    /*public List<Record> find(String sql, Object... paras) {
        Connection conn = null;
        try {
            conn = getConnection();
            return find( conn, sql, paras);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        } finally {
            close(conn);
        }
    }*/
    /*public List<Record> find(String sql, Object... param) {
        List<Map<String, Object>> results = queryList(sql, param);
        return RecordUtil.mappingList(results);
    }*/

    /**
     * @param sql the sql statement
     */
    public List<Record> find(String sql) {
        return find(sql, NULL_PARA_ARRAY);
    }
    /*public List<Record> find(String sql) {
        List<Map<String, Object>> results = queryList(sql);
        return RecordUtil.mappingList(results);
    }*/
    public List<Map> findMaps(String sql) {
        List<Record> lists =  find(sql, NULL_PARA_ARRAY);
        List<Map> datas = RecordUtil.recordToMaps(lists,true);
        return datas;
    }
    public <T> List<T> findObjs(Class<T> clazz,String sql) {
        List<Record> lists =  find(sql, NULL_PARA_ARRAY);
        List<T> datas = RecordUtil.recordToBeanList(lists,clazz);
        return datas;
    }

    public List<Record> findAll(String tableName) {
        String sql = dialect.forFindAll(tableName);
        return find(sql, NULL_PARA_ARRAY);
    }

    /**
     * Find first record. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the Record object
     */
    public Record findFirst(String sql, Object... paras) {
        List<Record> result = find(sql, paras);
        return result.size() > 0 ? result.get(0) : null;
    }

    /**
     * @see #findFirst(String, Object...)
     * @param sql an SQL statement
     */
    public Record findFirst(String sql) {
        return findFirst(sql, NULL_PARA_ARRAY);
    }

    /**
     * Find record by id with default primary key.
     * <pre>
     * Example:
     * Record user = Db.use().findById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     */
    public Record findById(String tableName, Object idValue) {
        return findByIds(tableName, dialect.getDefaultPrimaryKey(), idValue);
    }
   /* public Record findById(String tableName, Object id) {
        return findRecordById(tableName,id);
    }*/

    public Record findById(String tableName, String primaryKey, Object idValue) {
        return findByIds(tableName, primaryKey, idValue);
    }

    /**
     * Find record by ids.
     * <pre>
     * Example:
     * Record user = Db.use().findByIds("user", "user_id", 123);
     * Record userRole = Db.use().findByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     */
    public Record findByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Record> result = find(sql, idValues);
        if(CollectionUtil.isEmpty(result)){
            return null;
        }
        return result.size() > 0 ? result.get(0) : null;
    }

    /*public Record findByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        Map<String, Object> resultMap = queryMap(sql, idValues);
        if (resultMap == null) return null;
        return RecordUtil.mapping(resultMap);
    }*/

    /**
     * Delete record by id with default primary key.
     * <pre>
     * Example:
     * Db.use().deleteById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     * @return true if delete succeed otherwise false
     */
    public boolean deleteById(String tableName, Object idValue) {
        return deleteByIds(tableName, dialect.getDefaultPrimaryKey(), idValue);
    }
    public boolean deleteByPrimaryKey(String tableName, Object idValue) {
        String primaryKey = getPkNames(tableName);
        return deleteByIds(tableName,primaryKey , idValue);
    }

    public boolean deleteById(String tableName, String primaryKey, Object idValue) {
        return deleteByIds(tableName, primaryKey, idValue);
    }

    /**
     * Delete record by ids.
     * <pre>
     * Example:
     * Db.use().deleteByIds("user", "user_id", 15);
     * Db.use().deleteByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     * @return true if delete succeed otherwise false
     */
    public boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbDeleteById(tableName, pKeys);
        return update(sql, idValues) >= 1;
    }


    /**
     * Delete record.
     * <pre>
     * Example:
     * boolean succeed = Db.use().delete("user", "id", user);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record
     * @return true if delete succeed otherwise false
     */
    public boolean delete(String tableName, String primaryKey, Record record) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length <= 1) {
            Object t = record.get(primaryKey);	// 引入中间变量避免 JDK 8 传参有误
            return deleteByIds(tableName, primaryKey, t);
        }

        dialect.trimPrimaryKeys(pKeys);
        Object[] idValue = new Object[pKeys.length];
        for (int i=0; i<pKeys.length; i++) {
            idValue[i] = record.get(pKeys[i]);
            if (idValue[i] == null)
                throw new IllegalArgumentException("The value of primary key \"" + pKeys[i] + "\" can not be null in record object");
        }
        return deleteByIds(tableName, primaryKey, idValue);
    }

    /**
     * <pre>
     * Example:
     * boolean succeed = Db.use().delete("user", user);
     * </pre>
     * @see #delete(String, String, Record)
     */
    public boolean delete(String tableName, Record record) {
        //String defaultPrimaryKey = dialect.getDefaultPrimaryKey();
        String defaultPrimaryKey = getPkNames(tableName);
        Object t = record.get(defaultPrimaryKey);	// 引入中间变量避免 JDK 8 传参有误
        return deleteByIds(tableName, defaultPrimaryKey, t);
    }
    /*public boolean delete(String tableName, Record record) {
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
    }*/

    /**
     * Execute delete sql statement.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the row count for <code>DELETE</code> statements, or 0 for SQL statements
     *         that return nothing
     */
    public int delete(String sql, Object... paras) {
        return update(sql, paras);
    }

    /**
     * @see #delete(String, Object...)
     * @param sql an SQL statement
     */
    public int delete(String sql) {
        return update(sql);
    }

    /**
     * Paginate.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param select the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @param paras the parameters of sql
     * @return the Page object
     */
    /*public Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize, null, select, sqlExceptSelect, paras);
    }*/

    /**
     */
    public Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return doPaginate(pageNumber, pageSize, null, select, sqlExceptSelect, NULL_PARA_ARRAY);
    }

    public Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    protected Page<Record> doPaginate(int pageNumber, int pageSize, Boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        try {
            String totalRowSql = dialect.forPaginateTotalRow(select, sqlExceptSelect, null);
            StringBuilder findSql = new StringBuilder();
            findSql.append(select).append(' ').append(sqlExceptSelect);
            return doPaginateByFullSql( pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        }
    }

    protected Page<Record> doPaginateByFullSql(int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, StringBuilder findSql, Object... paras) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new SqlException("pageNumber and pageSize must more than 0");
        }
        /*if (dialect.isTakeOverDbPaginate()) {
            return dialect.takeOverDbPaginate(conn, pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
        }*/
        List result = query(  totalRowSql, paras);
        int size = result.size();
        if (isGroupBySql == null) {
            isGroupBySql = size > 1;
        }

        long totalRow;
        if (isGroupBySql) {
            totalRow = size;
        } else {
            totalRow = (size > 0) ? ((Number)result.get(0)).longValue() : 0;
        }
        if (totalRow == 0) {
            return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, 0, 0);
        }

        int totalPage = (int) (totalRow / pageSize);
        if (totalRow % pageSize != 0) {
            totalPage++;
        }

        if (pageNumber > totalPage) {
            return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, totalPage, (int)totalRow);
        }

        // --------
        String sql = dialect.forPaginate(pageNumber, pageSize, findSql);
        List<Record> list = find(sql, paras);
        return new Page<Record>(list, pageNumber, pageSize, totalPage, (int)totalRow);
    }

    protected Page<Record> paginate(   int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras){
        String totalRowSql = dialect.forPaginateTotalRow(select, sqlExceptSelect, null);
        StringBuilder findSql = new StringBuilder();
        findSql.append(select).append(' ').append(sqlExceptSelect);
        return doPaginateByFullSql(  pageNumber, pageSize, null, totalRowSql, findSql, paras);
    }

    protected Page<Record> doPaginateByFullSql(int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        try {
            StringBuilder findSqlBuf = new StringBuilder().append(findSql);
            return doPaginateByFullSql(  pageNumber, pageSize, isGroupBySql, totalRowSql, findSqlBuf, paras);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        }
    }

    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, null, totalRowSql, findSql, paras);
    }

    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
    }

    /*protected boolean save( Connection conn, String tableName, String primaryKey, Record record) throws SQLException {
        String[] pKeys = primaryKey.split(",");
        List<Object> paras = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        dialect.forDbSave(tableName, pKeys, record, sql, paras);
        try (PreparedStatement pst =
                     dialect.isOracle() ?
                             conn.prepareStatement(sql.toString(), pKeys) :
                             conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            dialect.fillStatement(pst, paras);
            int result = pst.executeUpdate();
            dialect.getRecordGeneratedKey(pst, record, pKeys);
            //record.clearModifyFlag();
            return result >= 1;
        }
    }*/

    /**
     * Save record.
     * <pre>
     * Example:
     * Record userRole = new Record().set("user_id", 123).set("role_id", 456);
     * Db.use().save("user_role", "user_id, role_id", userRole);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record will be saved
     */
    /*public boolean save(String tableName, String primaryKey, Record record) {
        Connection conn = null;
        try {
            conn = getConnection();
            return save( conn, tableName, primaryKey, record);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        } finally {
            close(conn);
        }
    }*/
    public boolean save(String tableName,String primaryKey, Record record) {
        //String primaryKey = getPkNames(tableName);
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

    /**
     * @see #save(String, String, Record)
     */
    public boolean save(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return save(tableName,primaryKey/*dialect.getDefaultPrimaryKey()*/, record);
    }

    /*protected boolean update( Connection conn, String tableName, String primaryKey, Record record) throws SQLException {
        *//*if (record.modifyFlag == null || record.modifyFlag.isEmpty()) {
            return false;
        }*//*
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];

        for (int i=0; i<pKeys.length; i++) {
            ids[i] = record.get(pKeys[i].trim());	// .trim() is important!
            if (ids[i] == null)
                throw new SqlException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
        }

        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<Object>();
        dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);

        if (paras.size() <= 1) {	// 参数个数为 1 的情况表明只有主键，也无需更新
            return false;
        }

        int result = update( conn, sql.toString(), paras.toArray());
        if (result >= 1) {
            //record.clearModifyFlag();
            return true;
        }
        return false;
    }*/

    /**
     * Update Record.
     * <pre>
     * Example:
     * Db.use().update("user_role", "user_id, role_id", record);
     * </pre>
     * @param tableName the table name of the Record save to
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the Record object
     */
    public boolean update(String tableName,String primaryKey, Record record) {
        //String primaryKey = getPkNames(tableName);
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


    /**
     * Update record with default primary key.
     * <pre>
     * Example:
     * Db.use().update("user", record);
     * </pre>
     * @see #update(String, String, Record)
     */
    public boolean update(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return update(tableName, primaryKey/*dialect.getDefaultPrimaryKey()*/, record);
    }



    /**
     * Execute callback. It is useful when all the API can not satisfy your requirement.
     * @param callback the ICallback interface
     */
    protected Object execute( ICallback callback) {
        Connection conn = null;
        try {
            conn = getConnection();
            return callback.call(conn);
        } catch (Exception e) {
            throw new ActiveRecordException(e);
        } finally {
            close(conn);
        }
    }

    /**
     * Execute transaction.
     * @param transactionLevel the transaction level
     * @param atom the atom operation
     * @return true if transaction executing succeed otherwise false
     */
    protected boolean tx( int transactionLevel, IAtom atom) {
        Connection conn = getThreadLocalConnection();
        if (conn != null) {	// Nested transaction support
            try {
                if (conn.getTransactionIsolation() < transactionLevel)
                    conn.setTransactionIsolation(transactionLevel);
                boolean result = atom.run();
                if (result)
                    return true;
                throw new SqlException("Notice the outer transaction that the nested transaction return false");	// important:can not return false
            }
            catch (SQLException e) {
                throw new ActiveRecordException(e);
            }
        }

        Boolean autoCommit = null;
        try {
            conn = getConnection();
            autoCommit = conn.getAutoCommit();
            setThreadLocalConnection(conn);
            conn.setTransactionIsolation(transactionLevel);
            conn.setAutoCommit(false);
            boolean result = atom.run();
            if (result)
                conn.commit();
            else
                conn.rollback();
            return result;
        } catch (SqlException e) {
            if (conn != null) try {conn.rollback();} catch (Exception e1) {
                StaticLog.error(e1.getMessage(), e1);}
                //StaticLog.log(e.getMessage());
            return false;
        } catch (Throwable t) {
            if (conn != null) try {conn.rollback();} catch (Exception e1) {StaticLog.error(e1.getMessage(), e1);}
            throw t instanceof RuntimeException ? (RuntimeException)t : new ActiveRecordException(t);
        } finally {
            try {
                if (conn != null) {
                    if (autoCommit != null)
                        conn.setAutoCommit(autoCommit);
                    conn.close();
                }
            } catch (Throwable t) {
                StaticLog.error(t.getMessage(), t);	// can not throw exception here, otherwise the more important exception in previous catch block can not be thrown
            } finally {
                removeThreadLocalConnection();	// prevent memory leak
            }
        }
    }


    void setTransactionLevel(int transactionLevel) {
        int t = transactionLevel;
        if (t != 0 && t != 1  && t != 2  && t != 4  && t != 8) {
            throw new IllegalArgumentException("The transactionLevel only be 0, 1, 2, 4, 8");
        }
        this.transactionLevel = transactionLevel;
    }
    public int getTransactionLevel() {
        return transactionLevel;
    }

    /**
     * Execute transaction with default transaction level.
     * @see #tx(int, IAtom)
     */
    public boolean tx(IAtom atom) {
        return tx(getTransactionLevel(), atom);
    }


    /**
     * 主要用于嵌套事务场景
     *
     * 实例：https://jfinal.com/feedback/4008
     *
     * 默认情况下嵌套事务会被合并成为一个事务，那么内层与外层任何地方回滚事务
     * 所有嵌套层都将回滚事务，也就是说嵌套事务无法独立提交与回滚
     *
     * 使用 txInNewThread(...) 方法可以实现层之间的事务控制的独立性
     * 由于事务处理是将 Connection 绑定到线程上的，所以 txInNewThread(...)
     * 通过建立新线程来实现嵌套事务的独立控制
     */
    public Future<Boolean> txInNewThread(IAtom atom) {
        FutureTask<Boolean> task = new FutureTask<>(() -> tx(getTransactionLevel(), atom));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task;
    }

    public Future<Boolean> txInNewThread(int transactionLevel, IAtom atom) {
        FutureTask<Boolean> task = new FutureTask<>(() -> tx(transactionLevel, atom));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task;
    }

}



