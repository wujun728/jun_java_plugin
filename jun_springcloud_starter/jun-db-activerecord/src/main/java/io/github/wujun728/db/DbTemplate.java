//package io.github.wujun728.db;
//
//import cn.hutool.db.meta.MetaUtil;
//import cn.hutool.db.meta.Table;
//import cn.hutool.extra.spring.SpringUtil;
//import cn.hutool.json.JSONUtil;
//import cn.hutool.log.StaticLog;
//import com.google.common.collect.Maps;
//import io.github.wujun728.db.exception.SqlException;
//import io.github.wujun728.db.record.Page;
//import io.github.wujun728.db.record.Record;
//import io.github.wujun728.db.record.dialect.Dialect;
//import io.github.wujun728.db.record.dialect.MysqlDialect;
//import io.github.wujun728.db.utils.DataSourcePool;
//import io.github.wujun728.db.utils.RecordUtil;
//import io.github.wujun728.db.utils.SqlContext;
//import io.github.wujun728.db.utils.SqlUtil;
//import io.github.wujun728.rest.entity.ApiSql;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
// */
//@Slf4j
//public class DbTemplate<T> {
//
////    private static Map<String, String> TABLE_PK_MAP = new HashMap<>();
//    private static final Map<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap<>(24);
//    private static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(24);
//    public static final Map<String, DbTemplate> dbMap = new ConcurrentHashMap<>(24);
//
//    private static Dialect dialect = new MysqlDialect();
//
//    private static JdbcTemplate jdbcTemplate = null;
//    private static DataSource dataSource = null;
//    private volatile static DbTemplate _MAIN = null;
//    public final static String main = "main";
//
//    static {
//        try {
//            dataSource = SpringUtil.getBean(DataSource.class);
//            dataSourceMap.put(main, dataSource);
//        } catch (Exception e) {
//            System.err.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请首次初始化DbTemplate.init的数据源。" + e.getMessage());
//        }
//        try {
//            jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
//            jdbcTemplateMap.put(main, jdbcTemplate);
//        } catch (Exception e) {
//            System.err.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请添加spring jdbc支持。" + e.getMessage());
//        }
//    }
//
//    public static JdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
//
//    private static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        DbTemplate.jdbcTemplate = jdbcTemplate;
//    }
//
//    public static DataSource getDataSource() {
//        return dataSource;
//    }
//
//    private static void setDataSource(DataSource dataSource) {
//        DbTemplate.dataSource = dataSource;
//    }
//
//    /**
//     * main方法，测试使用
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
//        String username = "root";
//        String password = "";
//        String driver = "com.mysql.cj.jdbc.Driver";
//        DataSource dataSource =  DataSourcePool.init("main", url, username, password);
//        //
//        DbTemplate.init(DbTemplate.main, dataSource);
//        //查询数据并返回单条Record
//        Record record = DbTemplate.findById("biz_mail", 1);
//        StaticLog.info(JSONUtil.toJsonPrettyStr(record));
//
//        List data1 = DbTemplate.use(main).queryAll(ApiSql.class);
//
//        //查询数据并转为Bean清单
//        List<ApiSql> data2 = DbTemplate.queryForBeanList(" SELECT * FROM api_sql ", ApiSql.class, null);
//
//        Page<Record> data3 = DbTemplate.paginate(1, 10, " SELECT * ", " FROM api_sql ");
//        StaticLog.info(JSONUtil.toJsonPrettyStr(data3));
//    }
//
//    public static DbTemplate use() {
//        return _MAIN;
//    }
//
//    public static DbTemplate use(String configName) {
//        DbTemplate result = dbMap.get(configName);
//        if (result == null || dataSourceMap.get(configName) == null || jdbcTemplateMap.get(configName) == null) {
//            System.err.println("error : 当前DbTemplate.use(" + configName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的configName数据源。");
//            throw new RuntimeException("error : 当前DbTemplate.use(" + configName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的configName数据源。");
//        }
//        result.setDataSource(dataSourceMap.get(configName));
//        result.setJdbcTemplate(jdbcTemplateMap.get(configName));
//        return result;
//    }
//
//    public static void init(String configName, DataSource dataSource) {
//        DbTemplate result = new DbTemplate();
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplateMap.put(configName, jdbcTemplate);
//        dataSourceMap.put(configName, dataSource);
//        result.jdbcTemplate = jdbcTemplateMap.get(configName);
//        result.dataSource = dataSourceMap.get(configName);
//        dbMap.put(configName, result);
//        _MAIN = dbMap.get(main);
//        registerRecord();
//    }
//
//    private static void registerRecord() {
//        /*List<String> tables = MetaUtil.getTables(dataSource);
//        tables.forEach(table -> {
//            TABLE_PK_MAP.put(table, getPkNames(dataSource, table));// map不清，只做替换
//        });*/
//        /*JdbcTemplate jdbcTemplate = new JdbcTemplate(Objects.requireNonNull(DataSourcePool.get("main")));
//        List<Map<String, Object>> tablePks = jdbcTemplate.queryForList("SELECT t.TABLE_NAME, c.COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t, INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c WHERE t.TABLE_NAME = c.TABLE_NAME  AND t.TABLE_SCHEMA = '" + dbName + "' AND t.CONSTRAINT_TYPE = 'PRIMARY KEY';");
//        for(Map<String, Object> tablePk : tablePks) {
//            if(tablePk.containsKey("COLUMN_NAME")){
//                log.info("正在注册Record " + (String)tablePk.get("TABLE_NAME") + " => " + (String)tablePk.get("COLUMN_NAME"));
//                TABLE_PK_MAP.put((String)tablePk.get("TABLE_NAME"), (String)tablePk.get("COLUMN_NAME"));
//            }
//        }*/
//    }
//
//    public static String getPkNames(DataSource ds, String tableName) {
//        Table table = MetaUtil.getTableMeta(ds, tableName);
//        return String.join(",", table.getPkNames());
//    }
//
//    //************************************************************************************************************************************************
//    //Record begin  **************************************************************************************************************************************
//    //************************************************************************************************************************************************
//
//    /**
//     * 根据id查找
//     *
//     * @param tableName 表名
//     * @param id        主键
//     * @return Record
//     */
//    public static Record findById(String tableName, Object... id) {
//        String primaryKeyStr = getPkNames(dataSource,tableName);
//        if (primaryKeyStr.contains(",")) {
//            return findByIds(tableName, primaryKeyStr, id);
//        }
//        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
//        Map<String, Object> resultMap = null;
//        try {
//            resultMap = jdbcTemplate.queryForMap(sql, id);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//        return mapping(resultMap);
//    }
//
//
//    public static Record findByIds(String tableName, String primaryKey, Object... idValues) {
//        Record record = new Record();
//        String[] pKeys = primaryKey.split(",");
//        if (pKeys.length != idValues.length)
//            throw new IllegalArgumentException("primary key number must equals id value number");
//
//        String sql = dialect.forDbFindById(tableName, pKeys);
//        Map<String, Object> resultMap;
//        try {
//            resultMap = jdbcTemplate.queryForMap(sql, idValues);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//        return mapping(resultMap);
//    }
//
//
//    public static List<Record> findBySql(String sql) {
//        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
//        return mappingList(results);
//    }
//    public static List findBySql(Class clazz,String sql) {
//        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
//        return RecordUtil.mapToBeans(results,clazz);
//    }
//
//
//    public static List<Record> findBySql(String sql, Object... param) {
//        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, param);
//        return mappingList(results);
//    }
//
//    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
//        return paginate(pageNumber,limit,select,from,Maps.newHashMap());
//    }
//    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from,Map<String, Object> params) {
//        String sqlStr = select + " " + from;
//        SqlContext sqlContext = SqlUtil.getSelect(new StringBuilder(sqlStr), params);
//        Page pageVo = new Page();
//        List<Map<String, Object>> results = queryList(sqlContext);
//        List<Record> records = mappingList(results);
//        int totalpage = queryInt(SqlUtil.getCount(sqlContext));
//        pageVo.setList(records);
//        pageVo.setTotalRow(totalpage);
//        pageVo.setPageNumber(pageNumber);
//        pageVo.setPageSize(limit);
//        pageVo.setTotalPage(totalpage / limit);
//        return pageVo;
//    }
//
//    public static List<Record> findAll(String tableName) {
//        String sql = "SELECT * FROM " + tableName + " ";
//        List<Map<String, Object>> resultMap = jdbcTemplate.queryForList(sql);
//        return mappingList(resultMap);
//    }
//
//
//    /**
//     * 保存record
//     *
//     * @param tableName 表名
//     * @param record    record对象
//     */
//    public static boolean save(String tableName, Record record) {
//        String primaryKey = getPkNames(dataSource,tableName);
//        String[] pKeys = primaryKey.split(",");
//        List<Object> paras = new ArrayList<Object>();
//        StringBuilder sql = new StringBuilder();
//        dialect.forDbSave(tableName, pKeys, record, sql, paras);
//        int flag = 0;
//        try {
//            flag = jdbcTemplate.update(sql.toString(), paras.toArray());
//        } catch (DuplicateKeyException e) {
//            throw new RuntimeException("主键冲突，保存失败！");
//        }catch (DataAccessException e){
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return flag > 0;
//    }
//
//    public static boolean updateSqlContext(String tableName, Record record) {
//        String primaryKey = getPkNames(dataSource,tableName);
//        String[] pKeys = primaryKey.split(",");
//        Object[] ids = new Object[pKeys.length];
//        for (int i=0; i<pKeys.length; i++) {
//            ids[i] = record.get(pKeys[i].trim());	// .trim() is important!
//            if (ids[i] == null)
//                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
//        }
//        StringBuilder sql = new StringBuilder();
//        List<Object> paras = new ArrayList<Object>();
//        dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);
//        if (paras.size() <= 1) {	// 参数个数为 1 的情况表明只有主键，也无需更新
//            return false;
//        }
//        int result = jdbcTemplate.update(sql.toString(), paras.toArray());
//        if (result >= 1) {
//            return true;
//        }
//        return false;
//    }
//
//    public static Record mapping(Map<String, Object> map) {
//        return toRecord(map);
//    }
//    private static Record toRecord(Map<String, Object> map) {
//        Record record = new Record();
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            record.set(entry.getKey(), entry.getValue());
//        }
//        return record;
//    }
//
//    public static List<Record> mappingList(List<Map<String, Object>> maps) {
//        return toRecords(maps);
//    }
//    private static List<Record> toRecords(List<Map<String, Object>> maps) {
//        List<Record> records = new ArrayList<>();
//        if (null != maps && !maps.isEmpty()) {
//            for (Map<String, Object> map : maps) {
//                records.add(mapping(map));
//            }
//            return records;
//        } else {
//            return null;
//        }
//    }
//
//
//    //************************************************************************************************************************************************
//    //Record end  **************************************************************************************************************************************
//    //************************************************************************************************************************************************
//
//
//    public static  List<Map<String, Object>> query(String sql, Object... paras) {
//        List results = jdbcTemplate.queryForList(sql, paras);
//        return results;
//    }
//
//    public static  List<Map<String, Object>> query(String sql) {
//        List results = jdbcTemplate.queryForList(sql);
//        return results;
//    }
//
//    public static int updateSqlContext(String sql) {
//        return jdbcTemplate.update(sql);
//    }
//
//
//    public static Boolean deleteById(String tableName, Object... idValues) {
//        String primaryKeyStr = getPkNames(dataSource,tableName);
//        if (primaryKeyStr.contains(",")) {
//            return deleteByIds(tableName, primaryKeyStr, idValues);
//        }
//        String sql = " DELETE FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
//        int flag = jdbcTemplate.update(sql, idValues);
//        return flag > 0;
//    }
//
//    public static Boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
//        String[] pKeys = primaryKey.split(",");
//        if (pKeys.length != idValues.length)
//            throw new IllegalArgumentException("primary key number must equals id value number");
//        String sql = dialect.forDbDeleteById(tableName, pKeys);
//        int flag = jdbcTemplate.update(sql, idValues);
//        return flag > 0;
//    }
//
//    public static Boolean deleteBySql(String sql, Object... paras) {
//        int flag = jdbcTemplate.update(sql, paras);
//        return flag>0;
//    }
//
//    public static Boolean deleteBySql(String sql) {
//        return deleteBySql(sql,null);
//    }
//
//    public static <T> List queryForBeanList(String sql, Class clazz, Object... params) {
//        List datas = queryForList(sql, params);
//        return RecordUtil.mapToBeans(datas, clazz);
//    }
//
//
//    public static Integer saveBackPrimaryKey(Object bean) {
//        save(bean);
//        return jdbcTemplate.queryForObject("SELECT last_insert_id() as id", Integer.class);
//    }
//
//
//    public static Integer save(Object bean) {
//        return updateSqlContext(SqlUtil.getInsert(bean));
//    }
//
//
//    public static Integer updateSqlContext(Object bean) {
//        return updateSqlContext(SqlUtil.getUpdate(bean));
//    }
//
//
//    public static Integer delete(Object bean) {
//        return updateSqlContext(SqlUtil.getDelete(bean));
//    }
//
//
//    public static Object getById(Class beanClass, Object... id) {
//        SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
//        return queryForObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
//    }
//
//
//    public static Object getByParams(Class beanClass, String[] fields, Object... parmas) {
//        SqlContext sqlContext = SqlUtil.getByParams(beanClass, fields, parmas);
//        Object obj =  queryForObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
//        return obj;
//    }
//
//    public static <T> List queryAll(Class beanClass) {
//        List<Map<String, Object>>  list =  queryList(SqlUtil.getSelect(beanClass));
//        return RecordUtil.mapToBeans(list,beanClass);
//    }
//
//
//    public static List<Map<String, Object>> queryList(Class beanClass, Map<String, Object> params) {
//        return queryList(SqlUtil.getSelect(beanClass, params));
//    }
//
//
//    public static List<Map<String, Object>> queryList(Class beanClass, String field, Object parmas) {
//        return queryList(beanClass, new String[]{field}, parmas);
//    }
//
//
//    public static List<Map<String, Object>> queryList(Class beanClass, String[] fields, Object... parmas) {
//        return queryList(SqlUtil.getByParams(beanClass, fields, parmas));
//    }
//
//
//    public static Page queryPage(Class beanClass, int page, int rows) {
//        return queryPage(beanClass,page,rows,Maps.newHashMap());
//    }
//    public static Page queryPage(Class beanClass, int page, int rows, Map<String, Object> params) {
//        SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
//        Page pageVo = new Page();
//        List<Map<String, Object>> listData = queryList(sqlContext);
//        pageVo.setList(RecordUtil.mapToBeans(listData,beanClass));
//        pageVo.setTotalRow(queryInt(SqlUtil.getCount(sqlContext)));
//        return pageVo;
//    }
//
//
//    /********************************************************************************
//     * 业务层 禁止写sql语句，以下方法仅供子类调用
//     ********************************************************************************/
//
//
//    public static List<Map<String, Object>> queryForList(String sql, Object... params) {
//        return jdbcTemplate.queryForList(sql, params);
//    }
//
//    public static List<Map<String, Object>> queryForList(String sql) {
//        return jdbcTemplate.queryForList(sql);
//    }
//
//    /**
//     * 查询
//     * <p>
//     * sql
//     *
//     * @param params 查询参数
//     * @return 返回查询结果
//     */
//    public static Object queryForObject(Class beanClass, String sql, Object... params) {
//        try {
//            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    public static Page queryForPage(String sql, int page, int rows, Object... params) {
//        Page pageVo = new Page();
//        pageVo.setList(queryForList(SqlUtil.getSelect(sql, page, rows), params));
//        pageVo.setTotalRow(count(SqlUtil.getCount(sql), params));
//        return pageVo;
//    }
//
//    public static int count(String sql, Object... params) {
//        try {
//            return jdbcTemplate.queryForObject(sql, Integer.class, params);
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//    public static int queryForInt(String sql, Object... params) {
//        return count(sql,params);
//    }
//    public static String queryForString(String sql, Object... params) {
//        try {
//            return jdbcTemplate.queryForObject(sql, String.class, params);
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//    public static Date queryForDate(String sql, Object... params) {
//        try {
//            return jdbcTemplate.queryForObject(sql, Date.class, params);
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//
//    /********************************************************************************
//     * 私有方法
//     ********************************************************************************/
//
//
//    private static int updateSqlContext(SqlContext sqlContext) {
//        int result;
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            result = jdbcTemplate.update(sql, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//        return result;
//    }
//
//    private static List<Map<String, Object>> queryList(SqlContext sqlContext) {
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            return jdbcTemplate.queryForList(sql, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//
//    private static <T> List queryList(SqlContext sqlContext, Class clazz) {
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            return queryForList(sql, clazz, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//
//    /**
//     * 查询
//     *
//     * @param sqlContext sql上下文
//     */
//    private static int queryInt(SqlContext sqlContext) {
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            return jdbcTemplate.queryForObject(sql, Integer.class, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new SqlException(e, sql);
//        }
//    }
//
//
//}
