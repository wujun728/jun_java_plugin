
package io.github.wujun728.db.record;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.bean.IAtom;
import io.github.wujun728.db.record.bean.ICallback;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.rest.entity.ApiSql;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static io.github.wujun728.db.record.DbPro.dataSourceMap;
import static io.github.wujun728.db.record.DbPro.jdbcTemplateMap;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
@Component
public class Db<T> {

    private static DbPro MAIN = null;

    public final static String main = "main";

    @PostConstruct
    public void init(){
        try {
            DataSource dataSource = SpringUtil.getBean(DataSource.class);
            if (dataSource != null) {
                Db.init(Db.main, dataSource);
                DataSourcePool.add(main, dataSource);
                StaticLog.info("main数据源，当前main默认注入容器DataSource数据源。");
                StaticLog.info("jdbcTemplateMap.main，当前main数据源默认注入JdbcTemplate。");
            }
        } catch (Exception e) {
            StaticLog.error("warning : ExceptionInInitializerError 当前非Spring容器运行，请手动初始化Db.init的数据源。" + e.getMessage());
        }

    }

    public static DbPro use() {
        MAIN.setDataSource(dataSourceMap.get(main));
        MAIN.setJdbcTemplate(jdbcTemplateMap.get(main));
        return MAIN;
    }

    public static DbPro use(String dsName) {
        DbPro result = DbPro.cache.get(dsName);
        if (result == null || dataSourceMap.get(dsName) == null || jdbcTemplateMap.get(dsName) == null) {
            System.err.println("error : 当前Db.use(" + dsName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的dsName数据源。");
            throw new RuntimeException("error : 当前Db.use(" + dsName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的dsName数据源。");
        }
        result.setDataSource(dataSourceMap.get(dsName));
        result.setJdbcTemplate(jdbcTemplateMap.get(dsName));
        return result;
    }

    public static void init(DataSource dataSource) {
        init(main,dataSource);
    }
    public static void init(String dsName, DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplateMap.put(dsName, jdbcTemplate);
        dataSourceMap.put(dsName, dataSource);
        DbPro.init(dsName);
        MAIN = DbPro.init(main);
    }

    /**
     * main方法，测试使用
     * @param args
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource dataSource = DataSourcePool.init("main", url, username, password);
        //
        Db.init(Db.main, dataSource);
        //查询数据并返回单条Record
        Record record = Db.use().findById("biz_mail", 1);
        StaticLog.info(JSONUtil.toJsonPrettyStr(record));
        List data1 = Db.use(main).find(SqlUtil.getSelect(ApiSql.class).getSql());
        List data12 = Db.find(SqlUtil.getSelect(ApiSql.class).getSql());

        //查询数据并转为Bean清单
        //List<ApiSql> data2 = Db.queryForBeanList(" SELECT * FROM api_sql ", ApiSql.class, null);
        //Page<Record> data3 = Db.paginate(1, 10, " SELECT * ", " FROM api_sql ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(data1));
        StaticLog.info(JSONUtil.toJsonPrettyStr(data12));
    }


//	************************************************************************************************************************************************
//	  111111111111111111  begin   **************************************************************************************************************
//	************************************************************************************************************************************************


    public static JdbcTemplate getJdbcTemplate() {
        return MAIN.getJdbcTemplate();
    }

    public static  List<Map<String, Object>> queryList(String sql, Object... params) {
        return MAIN.queryList( sql,params );
    }

    public static  String queryForString(String sql, Object[] params) {
        return MAIN.queryForString( sql,params );
    }

    public static  Date queryForDate(String sql, Object[] params) {
        return MAIN.queryForDate( sql,params );
    }

    public static  Map<String, Object> queryMap(String sql, Object... idValues) {
        return MAIN.queryMap( sql,idValues );
    }


    /************************************************************************************************************************
     * 私有方法  66666666666666666666
     ************************************************************************************************************************/


    // Save ***********************************************************************************************************
    // Save ***********************************************************************************************************



    public static  Integer saveBeanBackPrimaryKey(Object bean) {
        return MAIN.saveBeanBackPrimaryKey( bean );
    }

    public static  Integer saveBean(Object bean) {
        return MAIN.saveBean( bean );
    }

    public static  boolean insert(String sql, Object... params) {
        return MAIN.insert( sql,params );
    }


    // Update ***********************************************************************************************************
    // Update ***********************************************************************************************************

    public static  Integer updateBean(Object bean) {
        return MAIN.updateBean( bean );
    }


    // Delete ***********************************************************************************************************
    // Delete ***********************************************************************************************************

    public static  Integer deleteBean(Object bean) {
        return MAIN.deleteBean( bean );
    }


    public static  Boolean deleteById(String tableName, Object... idValues) {
        return MAIN.deleteById( tableName,idValues );
    }


    public static  Boolean deleteBySql(String sql, Object... paras) {
        return MAIN.deleteBySql( sql,paras );
    }


    public static  Boolean deleteBySql(String sql) {
        return MAIN.deleteBySql( sql );
    }


    // Query ***********************************************************************************************************
    // Query ***********************************************************************************************************

    public  static <T> List<T> findBeanList(Class<T> clazz, String sql) {
        return MAIN.findBeanList( clazz,sql );
    }


    public static  <T> List findBeanList(Class clazz, String sql, Object... params) {
        return MAIN.findBeanList( clazz,sql,params );
    }

    public static  <T> List findMapList(Class clazz, String sql, Object... params) {
        return MAIN.findMapList( clazz,sql,params );
    }

    public static   <T> List  findBeanList(Class beanClass, Map<String, Object> params) {
        return MAIN.findBeanList( beanClass,params );
    }

    public static  <T> List findRecordList(String sql, Object... params) {
        return MAIN.findRecordList( sql,params );
    }

    public static  <T> T findBeanById(Class<T> clazz, Object... idValue) {
        return MAIN.findBeanById( clazz,idValue );
    }


    public static  <T> T findBeanByIds(Class beanClass, String primaryKeys, Object... idValue) {
        return MAIN.findBeanByIds( beanClass, primaryKeys,idValue );
    }


    public static  <T> Page findBeanPages(Class beanClass, int page, int rows) {
        return MAIN.findBeanPages( beanClass, page,rows );
    }

    public static <T> Page findBeanPages(Class beanClass, int page, int rows, Map<String, Object> params) {
        return MAIN.findBeanPages( beanClass, page,rows,params );
    }


    public static  Page queryBeanPage(Class beanClass, int page, int rows) {
        return MAIN.queryBeanPage( beanClass, page,rows );
    }

    public static  Page queryBeanPage(Class beanClass, int page, int rows, Map<String, Object> params) {
        return MAIN.queryBeanPage( beanClass, page,rows,params );
    }


    public static  Page<Map> queryMapPages(String sql, int page, int rows, Object[] params) {
        return MAIN.queryMapPages( sql, page,rows,params );
    }

    public static  int count(String sql, Object... params) {
        return MAIN.count( sql, params );
    }

    //************************************************************************************************************************************************
    //Record begin  **************************************************************************************************************************************
    //************************************************************************************************************************************************

    public static  Record findRecordById(String tableName, Object id) {
        return MAIN.findRecordById( tableName, id );
    }

    public static  List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        return MAIN.findByColumnValueRecords( tableName, columnNames, columnValues);
    }

    public static <T> List findByColumnValueBeans(Class clazz, String columnNames, Object... columnValues) {
        return MAIN.findByColumnValueBeans( clazz, columnNames, columnValues);
    }

    public static <T> List findByWhereSqlForBean(Class clazz, String whereSql, Object... columnValues) {
        return MAIN.findByWhereSqlForBean( clazz, whereSql, columnValues);
    }


    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
        return MAIN.paginate( pageNumber, limit, select, from);
    }

    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from, Map<String, Object> params) {
        return MAIN.paginate( pageNumber, limit, select, from, params);
    }

    //************************************************************************************************************************************************
    //Record end  **************************************************************************************************************************************
    //************************************************************************************************************************************************

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************

    public static Object executeSqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.executeSqlXml( sqlXml, params);
    }

    public static  int updateSqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.updateSqlXml( sqlXml, params);
    }

    public static  List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.querySqlXml( sqlXml, params);
    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


    //	************************************************************************************************************************************************
//	  111111111111111111  begin   **************************************************************************************************************
//	************************************************************************************************************************************************

    public static <T> List<T> query(String sql, Object... paras) {
        return MAIN.query(sql, paras);
    }

    /**
     * @see #query(String, Object...)
     * @param sql an SQL statement
     */
    public static <T> List<T> query(String sql) {
        return MAIN.query(sql);
    }

    /**
     * Execute sql query and return the first result. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return Object[] if your sql has select more than one column,
     * 			and it return Object if your sql has select only one column.
     */
    public static <T> T queryFirst(String sql, Object... paras) {
        return MAIN.queryFirst(sql, paras);
    }

    /**
     * @see #queryFirst(String, Object...)
     * @param sql an SQL statement
     */
    public static <T> T queryFirst(String sql) {
        return MAIN.queryFirst(sql);
    }

    // 26 queryXxx method below -----------------------------------------------
    /**
     * Execute sql query just return one column.
     * @param <T> the type of the column that in your sql's select statement
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return <T> T
     */
    public static <T> T queryColumn(String sql, Object... paras) {
        return MAIN.queryColumn(sql, paras);
    }

    public static <T> T queryColumn(String sql) {
        return MAIN.queryColumn(sql);
    }

    public static String queryStr(String sql, Object... paras) {
        return MAIN.queryStr(sql, paras);
    }

    public static String queryStr(String sql) {
        return MAIN.queryStr(sql);
    }

    public static Integer queryInt(String sql, Object... paras) {
        return MAIN.queryInt(sql, paras);
    }

    public static Integer queryInt(String sql) {
        return MAIN.queryInt(sql);
    }

    public static Long queryLong(String sql, Object... paras) {
        return MAIN.queryLong(sql, paras);
    }

    public static Long queryLong(String sql) {
        return MAIN.queryLong(sql);
    }

    public static Double queryDouble(String sql, Object... paras) {
        return MAIN.queryDouble(sql, paras);
    }

    public static Double queryDouble(String sql) {
        return MAIN.queryDouble(sql);
    }

    public static Float queryFloat(String sql, Object... paras) {
        return MAIN.queryFloat(sql, paras);
    }

    public static Float queryFloat(String sql) {
        return MAIN.queryFloat(sql);
    }

    public static BigDecimal queryBigDecimal(String sql, Object... paras) {
        return MAIN.queryBigDecimal(sql, paras);
    }

    public static BigDecimal queryBigDecimal(String sql) {
        return MAIN.queryBigDecimal(sql);
    }

    public static BigInteger queryBigInteger(String sql, Object... paras) {
        return MAIN.queryBigInteger(sql, paras);
    }

    public static BigInteger queryBigInteger(String sql) {
        return MAIN.queryBigInteger(sql);
    }

    public static byte[] queryBytes(String sql, Object... paras) {
        return MAIN.queryBytes(sql, paras);
    }

    public static byte[] queryBytes(String sql) {
        return MAIN.queryBytes(sql);
    }

    public static java.util.Date queryDate(String sql, Object... paras) {
        return MAIN.queryDate(sql, paras);
    }

    public static java.util.Date queryDate(String sql) {
        return MAIN.queryDate(sql);
    }

    public static LocalDateTime queryLocalDateTime(String sql, Object... paras) {
        return MAIN.queryLocalDateTime(sql, paras);
    }

    public static LocalDateTime queryLocalDateTime(String sql) {
        return MAIN.queryLocalDateTime(sql);
    }

    public static java.sql.Time queryTime(String sql, Object... paras) {
        return MAIN.queryTime(sql, paras);
    }

    public static java.sql.Time queryTime(String sql) {
        return MAIN.queryTime(sql);
    }

    public static java.sql.Timestamp queryTimestamp(String sql, Object... paras) {
        return MAIN.queryTimestamp(sql, paras);
    }

    public static java.sql.Timestamp queryTimestamp(String sql) {
        return MAIN.queryTimestamp(sql);
    }

    public static Boolean queryBoolean(String sql, Object... paras) {
        return MAIN.queryBoolean(sql, paras);
    }

    public static Boolean queryBoolean(String sql) {
        return MAIN.queryBoolean(sql);
    }

    public static Short queryShort(String sql, Object... paras) {
        return MAIN.queryShort(sql, paras);
    }

    public static Short queryShort(String sql) {
        return MAIN.queryShort(sql);
    }

    public static Byte queryByte(String sql, Object... paras) {
        return MAIN.queryByte(sql, paras);
    }

    public static Byte queryByte(String sql) {
        return MAIN.queryByte(sql);
    }

    public static Number queryNumber(String sql, Object... paras) {
        return MAIN.queryNumber(sql, paras);
    }

    public static Number queryNumber(String sql) {
        return MAIN.queryNumber(sql);
    }
    // 26 queryXxx method under -----------------------------------------------

    /**
     * Execute sql update
     */
    /*static int update(Connection conn, String sql, Object... paras) throws SQLException {
        return MAIN.update(conn, sql, paras);
    }*/

    /**
     * Execute update, insert or delete sql statement.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>,
     *         or <code>DELETE</code> statements, or 0 for SQL statements
     *         that return nothing
     */
    public static int update(String sql, Object... paras) {
        return MAIN.update(sql, paras);
    }

    /**
     * @see #update(String, Object...)
     * @param sql an SQL statement
     */
    public static int update(String sql) {
        return MAIN.update(sql);
    }

    /*static List<Record> find( String sql, Object... paras) throws SQLException {
        return MAIN.find( sql, paras);
    }*/

    /**
     */
    public static List<Record> find(String sql, Object... paras) {
        return MAIN.find(sql, paras);
    }

    /**
     * @param sql the sql statement
     */
    public static List<Record> find(String sql) {
        return MAIN.find(sql);
    }

    public static List<Record> findAll(String tableName) {
        return MAIN.findAll(tableName);
    }

    /**
     * Find first record. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the Record object
     */
    public static Record findFirst(String sql, Object... paras) {
        return MAIN.findFirst(sql, paras);
    }

    /**
     * @see #findFirst(String, Object...)
     * @param sql an SQL statement
     */
    public static Record findFirst(String sql) {
        return MAIN.findFirst(sql);
    }

    /**
     * Find record by id with default primary key.
     * <pre>
     * Example:
     * Record user = Db.findById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     */
    public static Record findById(String tableName, Object idValue) {
        return MAIN.findById(tableName, idValue);
    }

    public static Record findById(String tableName, String primaryKey, Object idValue) {
        return MAIN.findById(tableName, primaryKey, idValue);
    }

    /**
     * Find record by ids.
     * <pre>
     * Example:
     * Record user = Db.findByIds("user", "user_id", 123);
     * Record userRole = Db.findByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     */
    public static Record findByIds(String tableName, String primaryKey, Object... idValues) {
        return MAIN.findByIds(tableName, primaryKey, idValues);
    }

    /**
     * Delete record by id with default primary key.
     * <pre>
     * Example:
     * Db.deleteById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     * @return true if delete succeed otherwise false
     */

    public static boolean deleteById(String tableName, Object idValue) {
        return MAIN.deleteById(tableName, idValue);
    }
    public static boolean deleteByPrimaryKey(String tableName, Object idValue) {
        return MAIN.deleteByPrimaryKey(tableName, idValue);
    }

    public static boolean deleteById(String tableName, String primaryKey, Object idValue) {
        return MAIN.deleteById(tableName, primaryKey, idValue);
    }

    /**
     * Delete record by ids.
     * <pre>
     * Example:
     * Db.deleteByIds("user", "user_id", 15);
     * Db.deleteByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     * @return true if delete succeed otherwise false
     */
    public static boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
        return MAIN.deleteByIds(tableName, primaryKey, idValues);
    }

    /**
     * Delete record.
     * <pre>
     * Example:
     * boolean succeed = Db.delete("user", "id", user);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record
     * @return true if delete succeed otherwise false
     */
    public static boolean delete(String tableName, String primaryKey, Record record) {
        return MAIN.delete(tableName, primaryKey, record);
    }

    /**
     * <pre>
     * Example:
     * boolean succeed = Db.delete("user", user);
     * </pre>
     * @see #delete(String, String, Record)
     */
    public static boolean delete(String tableName, Record record) {
        return MAIN.delete(tableName, record);
    }

    /**
     * Execute delete sql statement.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the row count for <code>DELETE</code> statements, or 0 for SQL statements
     *         that return nothing
     */
    public static int delete(String sql, Object... paras) {
        return MAIN.delete(sql, paras);
    }

    /**
     * @see #delete(String, Object...)
     * @param sql an SQL statement
     */
    public static int delete(String sql) {
        return MAIN.delete(sql);
    }

    /*static Page<Record> paginate( int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws SQLException {
        return MAIN.paginate( pageNumber, pageSize, select, sqlExceptSelect, paras);
    }*/

    /**
     * Paginate.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param select the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @param paras the parameters of sql
     * @return the Page object
     */
    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return MAIN.paginate(pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    public static Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return MAIN.paginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    /**
     */
    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return MAIN.paginate(pageNumber, pageSize, select, sqlExceptSelect);
    }

    public static Page<Record> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        return MAIN.paginateByFullSql(pageNumber, pageSize, totalRowSql, findSql, paras);
    }

    public static Page<Record> paginateByFullSql(int pageNumber, int pageSize, boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        return MAIN.paginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
    }

    /*static boolean save(Connection conn, String tableName, String primaryKey, Record record) throws SQLException {
        return MAIN.save(conn, tableName, primaryKey, record);
    }*/

    /**
     * Save record.
     * <pre>
     * Example:
     * Record userRole = new Record().set("user_id", 123).set("role_id", 456);
     * Db.save("user_role", "user_id, role_id", userRole);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record will be saved
     */
    public static boolean save(String tableName, String primaryKey, Record record) {
        return MAIN.save(tableName, primaryKey, record);
    }

    /**
     * @see #save(String, String, Record)
     */
    public static boolean save(String tableName, Record record) {
        return MAIN.save(tableName, record);
    }

    /*static boolean update(String tableName, String primaryKey, Record record) throws SQLException {
        return MAIN.update(tableName, primaryKey, record);
    }*/

    /**
     * Update Record.
     * <pre>
     * Example:
     * Db.update("user_role", "user_id, role_id", record);
     * </pre>
     * @param tableName the table name of the Record save to
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the Record object
     */
    public static boolean update(String tableName, String primaryKey, Record record) {
        return MAIN.update(tableName, primaryKey, record);
    }

    /**
     * Update record with default primary key.
     * <pre>
     * Example:
     * Db.update("user", record);
     * </pre>
     * @see #update(String, String, Record)
     */
    public static boolean update(String tableName, Record record) {
        return MAIN.update(tableName, record);
    }


    /**
     * Execute callback. It is useful when all the API can not satisfy your requirement.
     * @param callback the ICallback interface
     */
    static Object execute(ICallback callback) {
        return MAIN.execute(callback);
    }

    /**
     * Execute transaction.
     * @param transactionLevel the transaction level
     * @param atom the atom operation
     * @return true if transaction executing succeed otherwise false
     */
    static boolean tx(int transactionLevel, IAtom atom) {
        return MAIN.tx(transactionLevel, atom);
    }

    /**
     * Execute transaction with default transaction level.
     * @see #tx(int, IAtom)
     */
    public static boolean tx(IAtom atom) {
        return MAIN.tx(atom);
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
    public static Future<Boolean> txInNewThread(IAtom atom) {
        return MAIN.txInNewThread(atom);
    }

    public static Future<Boolean> txInNewThread(int transactionLevel, IAtom atom) {
        return MAIN.txInNewThread(transactionLevel, atom);
    }

}



