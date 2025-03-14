//package io.github.wujun728.db.record;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.db.meta.MetaUtil;
//import cn.hutool.db.meta.Table;
//import cn.hutool.log.StaticLog;
//import io.github.wujun728.db.record.dialect.*;
//import io.github.wujun728.db.record.mapper.BaseRowMapper;
//import io.github.wujun728.db.record.mapper.BatchSql;
//import io.github.wujun728.db.utils.RecordUtil;
//import io.github.wujun728.db.utils.SqlContext;
//import io.github.wujun728.db.utils.SqlUtils;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallbackWithoutResult;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.Date;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static io.github.wujun728.db.record.Db.main;
//
///**
// * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
// */
//public class DbTemplate {
//
//    private String configName;
//    private DataSource dataSource = null;
//
//    private JdbcTemplate jdbcTemplate;
//    private TransactionTemplate transactionTemplate;
//    public final static int DEFAULT_FETCHSIZE = 32; //默认的fetchsize
////    private Dialect dialect;
//
//    public volatile static ConcurrentHashMap<String, DbTemplate> cache = new ConcurrentHashMap<>(32, 0.25F);
//    public volatile static ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(32);
//    public volatile static ConcurrentHashMap<String, JdbcTemplate> dbTemplateMap = new ConcurrentHashMap<>(32);
//
//    static final Object[] NULL_PARA_ARRAY = new Object[0];
//    private static final Map<String, String> TABLE_PK_MAP = new HashMap<>();
//
//    public DbTemplate() {
//    }
//
//    static DbTemplate init(String configName, DataSource dataSource) {
//        DbTemplate dbTemplate = DbTemplate.cache.get(configName);
//        if (dbTemplate == null) {
//            dbTemplate = new DbTemplate();
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//            TransactionTemplate transactionTemplate = new TransactionTemplate();
//            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//            transactionManager.setDataSource(dataSource);
//            transactionTemplate.setTransactionManager(transactionManager);
//            dbTemplate.setConfigName(configName);
//            dbTemplate.setJdbcTemplate(jdbcTemplate);
//            dbTemplate.setTransactionTemplate(transactionTemplate);
//            dbTemplate.setDataSource(dataSource);
////            dbTemplate.setDialect(DbTemplate.getDialect(dbTemplate.getDataSource()));
//            dataSourceMap.put(configName,dataSource);
//            DbTemplate.registerRecord(dbTemplate.getDataSource());
//            /*if("main".equalsIgnoreCase(configName)){
//                Register.initReadWrite(dataSource,dataSource);
//                Register.initThreadPool(100, 100, 1000); //初始化线程池 0为使用默认值
//            }*/
//            DbTemplate.cache.put(configName, dbTemplate);
//        }
//        return dbTemplate;
//    }
//
//    private static void registerRecord(DataSource dataSource) {
//        List<String> tables = MetaUtil.getTables(dataSource);
//        tables.forEach(tableName -> {
//            Table table = MetaUtil.getTableMeta(dataSource, tableName);
//            String pkStr = String.join(",", table.getPkNames());
//            TABLE_PK_MAP.put(tableName, pkStr);// map不清，只做替换
//        });
//    }
//
//    public DataSource getDataSource() {
//        return this.dataSource;
//    }
//
//    private void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
////    private Dialect getDialect() {
////        return dialect;
////    }
////
////    private void setDialect(Dialect dialect) {
////        this.dialect = dialect;
////    }
//
//    public JdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
//
//    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private String getConfigName() {
//        return configName;
//    }
//
//    private void setConfigName(String configName) {
//        this.configName = configName;
//    }
//
//    private TransactionTemplate getTransactionTemplate() {
//        return transactionTemplate;
//    }
//
//    private void setTransactionTemplate(TransactionTemplate transactionTemplate) {
//        this.transactionTemplate = transactionTemplate;
//    }
//
//    private static Dialect getDialect(DataSource dataSource) {
//        Connection connection  = null;
//        try {
//            connection = dataSource.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            String dbName = connection.getMetaData().getDatabaseProductName();
//            if (dbName.equalsIgnoreCase("Oracle")) {
//                return new OracleDialect();
//            } else if (dbName.equalsIgnoreCase("mysql")) {
//                return new MysqlDialect();
//            } else if (dbName.equalsIgnoreCase("sqlite")) {
//                return new Sqlite3Dialect();
//            } else if (dbName.equalsIgnoreCase("postgresql")) {
//                return new PostgreSqlDialect();
//            } else {
//                return new AnsiSqlDialect();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("获取数据库连接失败");
//        }finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public static String getPkNames(String tableName) {
//        if(CollectionUtil.isEmpty(DbTemplate.TABLE_PK_MAP)){
//            DbTemplate.registerRecord(DbTemplate.dataSourceMap.get(main));
//        }
//        String primaryKey = DbTemplate.TABLE_PK_MAP.get(tableName);
//        if(StrUtil.isEmpty(primaryKey)){
//            primaryKey = "id";
//            //throw new RuntimeException("当前操作的表没有主键或表不存在，tableName="+tableName);
//        }
//        return primaryKey;
//    }
//
//
//
//    /********************************************************************************
//     * 私有方法   66666666666666666666   begin
//     ********************************************************************************/
//
//    private int updateSqlContext(SqlContext sqlContext) {
//        int result;
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            result = execute(sql, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new DbException(e, sql);
//        }
//        return result;
//    }
//    private long insertSqlContext(SqlContext sqlContext) {
//        long result;
//        String sql = null;
//        try {
//            sql = sqlContext.getSql();
//            result = insert(sql, sqlContext.getParams());
//        } catch (Exception e) {
//            throw new DbException(e, sql);
//        }
//        return result;
//    }
//
//    public long saveBean(Object bean) {
//        return insertSqlContext(SqlUtils.getInsert(bean));
//    }
//
//    public Integer updateBean(Object bean) {
//        return updateSqlContext(SqlUtils.getUpdate(bean));
//    }
//
//    public Integer deleteBean(Object bean) {
//        return updateSqlContext(SqlUtils.getDelete(bean));
//    }
//
//
//    public List<Map<String, Object>> queryList(String sql) {
//        return queryList(sql,null);
//    }
//    public List<Map<String, Object>> queryList(String sql, Object[] params) {
//        return getJdbcTemplate().queryForList(sql,params);
//    }
//
//
//
//    /************************************************************************************************************************
//     * 私有方法  66666666666666666666  end
//     ************************************************************************************************************************/
//
//
//    // Save ***********************************************************************************************************
//    // Save ***********************************************************************************************************
//
//
//
//
//
//
//
//
//    // Query ***********************************************************************************************************
//    // Query ***********************************************************************************************************
//
//
//
//    public <T> List<T> findBeanList(Class<T> clazz, String sql) {
//        return findBeanList(clazz, sql,null);
//    }
//
//
//    public <T> List<T> findBeanList(Class clazz, String sql, Object... params) {
//        List datas = queryList(sql, params);
//        return RecordUtil.mapToBeans(datas, clazz);
//    }
//
//
//
//    public <T> Page findBeanPages(Class beanClass, int page, int rows) {
//        return findBeanPages(beanClass, page, rows, null);
//    }
//
//
//    public <T> Page findBeanPages(Class beanClass, int page, int rows, String sql) {
//        //SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
//        Page pageVo = new Page();
//        if(StrUtil.isEmpty(sql)){
//            sql = SqlUtils.getSelectSQl(beanClass);
//        }
//        String sql1 = SqlUtils.getSelect(sql, page, rows);
//        SqlContext sqlContext = SqlUtils.getSelect(new StringBuilder(sql1), new HashMap());
//        List<Map<String, Object>> listData = queryList(sqlContext.getSql(), sqlContext.getParams());
//        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
//        int totalRow = count(SqlUtils.getCount(sql));
//        pageVo.setTotalRow(totalRow);
//        pageVo.setPageSize(rows);
//        pageVo.setPageNumber(page);
//        if (totalRow % rows == 0) {
//            pageVo.setTotalPage(totalRow / rows);
//        } else {
//            pageVo.setTotalPage(totalRow / rows + 1);
//        }
//        return pageVo;
//    }
//
//
//    public Page queryBeanPage(Class beanClass, int page, int rows) {
//        return queryBeanPage(beanClass, page, rows, null);
//    }
//
//
//    public Page queryBeanPage(Class beanClass, int page, int limit, String sql) {
//        //SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
//        Page pageVo = new Page();
//        if(StrUtil.isEmpty(sql)){
//            sql = SqlUtils.getSelectSQl(beanClass);
//        }
//        SqlContext sqlContext = SqlUtils.getSelect(new StringBuilder(SqlUtils.getSelect(sql, page, limit)), new HashMap());
//        List<Map<String, Object>> listData = queryList(sqlContext.getSql(), sqlContext.getParams());
//        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
//        int totalRow = count(SqlUtils.getCount(sql));
//        pageVo.setTotalRow(totalRow);
//        pageVo.setPageNumber(page);
//        pageVo.setPageSize(limit);
//        if (totalRow % limit == 0) {
//            pageVo.setTotalPage(totalRow / limit);
//        } else {
//            pageVo.setTotalPage(totalRow / limit + 1);
//        }
//        return pageVo;
//    }
//
//
//    public Page<Map> queryMapPages(String sql, int page, int limit, Object[] params) {
//        Page pageVo = new Page();
//        pageVo.setList(queryList(SqlUtils.getSelect(sql, page, limit), params));
//        int totalRow = count(SqlUtils.getCount(sql), params);
//        pageVo.setTotalRow(totalRow);
//        pageVo.setPageNumber(page);
//        pageVo.setPageSize(limit);
//        if (totalRow % limit == 0) {
//            pageVo.setTotalPage(totalRow / limit);
//        } else {
//            pageVo.setTotalPage(totalRow / limit + 1);
//        }
//        return pageVo;
//    }
//
//
//    public int count(String sql, Object... params) {
//        try {
//            return queryForInt(sql, params);
//            //return queryInt(sql,params);
//        } catch (Exception e) {
//            throw new DbException(e, sql);
//        }
//    }
//
//
//
//
//}
//
//
//
