package io.github.wujun728.db.record;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import io.github.wujun728.db.record.mapper.BatchSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Db - ORM框架统一入口
 * <p>
 * 支持三种操作模式：
 * 1. Record模式 - JFinal风格的Key-Value操作 (Db.find, Db.save, Db.update, Db.delete)
 * 2. Bean模式 - 直接操作JPA/MyBatis-Plus实体 (Db.saveBean, Db.updateBean, Db.deleteBean)
 * 3. SQL模式 - 直接执行SQL (Db.execute, Db.queryList, Db.queryForInt)
 * <p>
 * 支持多数据源通过 Db.use(configName) 切换
 * 支持多种数据库方言（MySQL, Oracle, PostgreSQL, SQLite）
 */
@Slf4j
@Component
public class Db {

    public static final String main = "main";
    private static DbPro MAIN;

    @Value("${db.main.enable:false}")
    private String mainEnable;
    @Value("${db.main.url:#{null}}")
    private String url;
    @Value("${db.main.username:#{null}}")
    private String username;
    @Value("${db.main.password:#{null}}")
    private String password;
    @Value("${db.main.driver:#{null}}")
    private String driver;

    @PostConstruct
    void afterInit() {
        try {
            if ("true".equals(mainEnable) && StrUtil.isNotEmpty(url)) {
                DataSource ds = createDataSource(url, username, password, driver);
                init(ds);
                log.info("main数据源初始化成功");
            }
        } catch (Exception e) {
            log.warn("非Spring容器运行或配置缺失，请手动调用Db.init()初始化数据源: " + e.getMessage());
        }
    }

    // ==================== 初始化 ====================

    /**
     * 使用DataSource初始化主数据源
     */
    public static void init(DataSource dataSource) {
        init(main, dataSource);
    }

    /**
     * 使用指定名称初始化数据源
     */
    public static void init(String configName, DataSource dataSource) {
        init(configName, dataSource, false);
    }

    public static void init(String configName, DataSource dataSource, Boolean force) {
        DbPro dbPro = DbPro.init(configName, dataSource, force);
        if (main.equals(configName)) {
            MAIN = dbPro;
        }
    }

    /**
     * 通过连接参数初始化数据源
     */
    public static void init(String configName, String url, String username, String password) {
        DataSource ds = createDataSource(url, username, password, null);
        init(configName, ds);
    }

    /**
     * 获取主数据源的DbPro实例
     */
    public static DbPro use() {
        if (MAIN == null) {
            throw new RuntimeException("The main dataSource is not initialized. Please call Db.init(dataSource) first.");
        }
        return MAIN;
    }

    /**
     * 获取指定名称的DbPro实例
     */
    public static DbPro use(String configName) {
        if (StrUtil.isEmpty(configName)) {
            throw new RuntimeException("configName不能为空");
        }
        DbPro result = DbPro.cache.get(configName);
        if (result == null) {
            throw new RuntimeException("DbPro not found with configName: " + configName);
        }
        return result;
    }

    // ==================== SQL模式 ====================

    public static int execute(String sql) {
        return use().execute(sql);
    }

    public static int execute(String sql, Object[] objects) {
        return use().execute(sql, objects);
    }

    public static int update(String sql, Object... paras) {
        return use().execute(sql, paras);
    }

    public static long insert(String sql, Object[] objects) {
        return use().insert(sql, objects);
    }

    public static int batchExecute(String sql, List<Object[]> objectsList) {
        return use().batchExecute(sql, objectsList);
    }

    public static List<Map<String, Object>> queryList(String sql) {
        return use().queryList(sql);
    }

    public static List<Map<String, Object>> queryList(String sql, Object... params) {
        return use().queryList(sql, params);
    }

    public static Map<String, Object> queryForMap(String sql, Object... objects) {
        return use().queryForMap(sql, objects);
    }

    public static int queryForInt(String sql, Object... objects) {
        return use().queryForInt(sql, objects);
    }

    public static long queryForLong(String sql, Object... objects) {
        return use().queryForLong(sql, objects);
    }

    public static String queryForString(String sql, Object... objects) {
        return use().queryForString(sql, objects);
    }

    public static Date queryForDate(String sql, Object... objects) {
        return use().queryForDate(sql, objects);
    }

    public static <T> T queryFirst(String sql, Object... paras) {
        return use().queryFirst(sql, paras);
    }

    public static int count(String sql, Object... params) {
        return use().count(sql, params);
    }

    // ==================== Record模式 - 查询 ====================

    public static List<Record> find(String sql, Object... paras) {
        return use().find(sql, paras);
    }

    public static List<Record> find(String sql) {
        return use().find(sql);
    }

    public static List<Record> findAll(String tableName) {
        return use().findAll(tableName);
    }

    public static Record findFirst(String sql, Object... paras) {
        return use().findFirst(sql, paras);
    }

    public static Record findFirst(String sql) {
        return use().findFirst(sql);
    }

    public static Record findById(String tableName, Object idValue) {
        return use().findById(tableName, idValue);
    }

    public static Record findById(String tableName, String primaryKey, Object idValue) {
        return use().findById(tableName, primaryKey, idValue);
    }

    public static Record findByIds(String tableName, String primaryKey, Object... idValues) {
        return use().findByIds(tableName, primaryKey, idValues);
    }

    public static List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        return use().findByColumnValueRecords(tableName, columnNames, columnValues);
    }

    // ==================== Record模式 - 保存/更新/删除 ====================

    public static boolean save(String tableName, Record record) {
        return use().save(tableName, record);
    }

    public static boolean save(String tableName, String primaryKey, Record record) {
        return use().save(tableName, primaryKey, record);
    }

    public static boolean update(String tableName, Record record) {
        return use().update(tableName, record);
    }

    public static boolean update(String tableName, String primaryKey, Record record) {
        return use().update(tableName, primaryKey, record);
    }

    public static boolean delete(String tableName, Record record) {
        return use().delete(tableName, record);
    }

    public static boolean delete(String tableName, String primaryKey, Record record) {
        return use().delete(tableName, primaryKey, record);
    }

    public static boolean deleteById(String tableName, Object idValue) {
        return use().deleteById(tableName, idValue);
    }

    public static boolean deleteById(String tableName, String primaryKey, Object... idValues) {
        return use().deleteById(tableName, primaryKey, idValues);
    }

    // ==================== Record模式 - 分页 ====================

    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return use().paginate(pageNumber, pageSize, select, sqlExceptSelect);
    }

    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return use().paginate(pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    public static Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return use().paginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    public static Page<Record> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        return use().paginateByFullSql(pageNumber, pageSize, totalRowSql, findSql, paras);
    }

    public static Page<Record> paginateByFullSql(int pageNumber, int pageSize, boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        return use().paginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
    }

    // ==================== Bean模式 ====================

    /**
     * 保存实体Bean（支持JPA @Table/@Column 和 MyBatis-Plus @TableName/@TableField注解）
     */
    public static boolean saveBean(Object bean) {
        return use().saveBean(bean);
    }

    /**
     * 更新实体Bean
     */
    public static boolean updateBean(Object bean) {
        return use().updateBean(bean);
    }

    /**
     * 删除实体Bean
     */
    public static boolean deleteBean(Object bean) {
        return use().deleteBean(bean);
    }

    /**
     * 通过SQL查询Bean列表
     */
    public static <T> List<T> findBeanList(Class<T> clazz, String sql) {
        return use().findBeanList(clazz, sql);
    }

    public static <T> List<T> findBeanList(Class<T> clazz, String sql, Object... params) {
        return use().findBeanList(clazz, sql, params);
    }

    /**
     * 通过主键查询Bean
     */
    public static <T> T findBeanById(Class<T> clazz, Object idValue) {
        return use().findBeanById(clazz, idValue);
    }

    /**
     * Bean分页查询
     */
    public static <T> Page<T> findBeanPages(Class<T> beanClass, int page, int rows) {
        return use().findBeanPages(beanClass, page, rows);
    }

    public static <T> Page<T> findBeanPages(Class<T> beanClass, int page, int rows, String sql) {
        return use().findBeanPages(beanClass, page, rows, sql);
    }

    /**
     * Map分页查询
     */
    public static Page<Map<String, Object>> queryMapPages(String sql, int page, int limit, Object[] params) {
        return use().queryMapPages(sql, page, limit, params);
    }

    // ==================== 事务支持 ====================

    public static int doInTransaction(BatchSql batchSql) {
        return use().doInTransaction(batchSql);
    }

    public static boolean tx(IAtom atom) {
        return use().tx(atom);
    }

    // ==================== 内部方法 ====================

    static DataSource createDataSource(String url, String username, String password, String driver) {
        if (driver == null || driver.isEmpty()) {
            driver = identifyDriver(url);
        }
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        ds.setConnectionErrorRetryAttempts(3);
        ds.setBreakAfterAcquireFailure(true);
        return ds;
    }

    static String identifyDriver(String jdbcUrl) {
        if (jdbcUrl.startsWith("jdbc:mysql:")) {
            return "com.mysql.jdbc.Driver";
        } else if (jdbcUrl.startsWith("jdbc:postgresql:")) {
            return "org.postgresql.Driver";
        } else if (jdbcUrl.startsWith("jdbc:oracle:")) {
            return "oracle.jdbc.driver.OracleDriver";
        } else if (jdbcUrl.startsWith("jdbc:sqlserver:")) {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if (jdbcUrl.startsWith("jdbc:h2:")) {
            return "org.h2.Driver";
        } else if (jdbcUrl.startsWith("jdbc:sqlite:")) {
            return "org.sqlite.JDBC";
        }
        return "com.mysql.jdbc.Driver";
    }
}
