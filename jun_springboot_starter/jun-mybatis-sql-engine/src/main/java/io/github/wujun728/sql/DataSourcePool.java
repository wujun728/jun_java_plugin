package io.github.wujun728.sql;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.AbstractDSFactory;
import cn.hutool.db.ds.pooled.DbConfig;
import cn.hutool.db.ds.pooled.PooledDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据源池工具类（仅依赖Hutool）
 * 替换原Druid+Spring实现，使用Hutool内置的PooledDataSource
 */
@Slf4j
public class DataSourcePool {

    public static final String MAIN = "main";
    private static final Lock lock = new ReentrantLock();
    private static final Lock deleteLock = new ReentrantLock();

    // 数据库驱动常量
    public static final String MYSQL_DRIVER5 = "com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER6 = "com.mysql.cj.jdbc.Driver";
    public static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // 存储所有数据源（使用Hutool PooledDataSource）
    private static final Map<String, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();
    // 记录已初始化的数据源名称，避免重复创建
    private static final Set<String> INITIALIZED_DS = new ConcurrentHashSet<>();

    /**
     * 初始化数据源（自动识别数据库类型）
     * @param dsname 数据源名称
     * @param url JDBC连接地址
     * @param username 用户名
     * @param password 密码
     * @return 初始化后的数据源
     */
    public static DataSource init(String dsname, String url, String username, String password) {
        if (StrUtil.hasEmpty(dsname, url, username)) {
            log.error("数据源初始化参数异常：dsname={}, url={}, username={}", dsname, url, username);
            throw new IllegalArgumentException("数据源名称、URL、用户名不能为空");
        }

        // 自动识别驱动
        String driverName = identifyDatabaseTypeFromJdbcUrl(url);
        if (StrUtil.equals("Unknown", driverName)) {
            log.warn("无法识别数据库类型，默认使用MySQL5驱动：{}", url);
            driverName = MYSQL_DRIVER5;
        }

        return init(dsname, url, username, password, driverName);
    }

    /**
     * 初始化数据源（指定驱动）
     * @param dsname 数据源名称
     * @param url JDBC连接地址
     * @param username 用户名
     * @param password 密码
     * @param driver 驱动类名
     * @return 初始化后的数据源
     */
    public static DataSource init(String dsname, String url, String username, String password, String driver) {
        // 双重检查锁，避免重复创建
        if (DATA_SOURCE_MAP.containsKey(dsname)) {
            return DATA_SOURCE_MAP.get(dsname);
        }

        lock.lock();
        try {
            if (!DATA_SOURCE_MAP.containsKey(dsname)) {
                // 构建Hutool数据库连接配置
                DbConfig dbConfig = new DbConfig();
                dbConfig.setUrl(url);
                dbConfig.setUser(username);
                dbConfig.setPassword(password);
                dbConfig.setDriver(driver);
                // 连接池配置（可根据需要调整）
                dbConfig.setInitialSize(5);       // 初始连接数
                dbConfig.setMaxActive(20);        // 最大活跃连接数
                dbConfig.setMaxIdle(10);          // 最大空闲连接数
                dbConfig.setMinIdle(2);           // 最小空闲连接数
                dbConfig.setMaxWait(30000);       // 获取连接最大等待时间（ms）
                dbConfig.setConnectionErrorRetryAttempts(3); // 连接失败重试次数

                // 创建Hutool池化数据源
                PooledDataSource dataSource = new PooledDataSource(dbConfig);
                DATA_SOURCE_MAP.put(dsname, dataSource);
                INITIALIZED_DS.add(dsname);
                log.info("创建Hutool连接池成功：{}，驱动：{}", dsname, driver);
            }
            return DATA_SOURCE_MAP.get(dsname);
        } catch (Exception e) {
            log.error("创建数据源失败：{}", dsname, e);
            throw new RuntimeException("数据源初始化失败", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 手动添加已存在的数据源
     * @param dsname 数据源名称
     * @param dataSource 数据源实例
     */
    public static void init(String dsname, DataSource dataSource) {
        if (StrUtil.isEmpty(dsname) || dataSource == null) {
            log.error("添加数据源参数异常：dsname={}, dataSource={}", dsname, dataSource);
            return;
        }

        add(dsname, dataSource);
    }

    /**
     * 内部添加数据源方法
     * @param dsname 数据源名称
     * @param dataSource 数据源实例
     */
    private static void add(String dsname, DataSource dataSource) {
        lock.lock();
        try {
            DATA_SOURCE_MAP.put(dsname, dataSource);
            INITIALIZED_DS.add(dsname);
            log.info("添加数据源成功：{}", dsname);
        } catch (Exception e) {
            log.error("添加数据源失败：{}", dsname, e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 根据名称获取数据源
     * @param dsname 数据源名称
     * @return 数据源实例（不存在返回null）
     */
    public static DataSource get(String dsname) {
        if (StrUtil.isEmpty(dsname)) {
            return null;
        }
        return DATA_SOURCE_MAP.get(dsname);
    }

    /**
     * 删除指定数据源（并关闭连接池）
     * @param dsname 数据源名称
     */
    public static void remove(String dsname) {
        if (StrUtil.isEmpty(dsname)) {
            return;
        }

        deleteLock.lock();
        try {
            DataSource dataSource = DATA_SOURCE_MAP.get(dsname);
            if (dataSource != null) {
                // 关闭Hutool池化数据源
                if (dataSource instanceof PooledDataSource) {
                    ((PooledDataSource) dataSource).close();
                } else {
                    // 通用关闭数据源连接的方法（Hutool工具类）
                    DbUtil.close(dataSource);
                }
                DATA_SOURCE_MAP.remove(dsname);
                INITIALIZED_DS.remove(dsname);
                log.info("移除并关闭数据源成功：{}", dsname);
            }
        } catch (Exception e) {
            log.error("移除数据源失败：{}", dsname, e);
        } finally {
            deleteLock.unlock();
        }
    }

    /**
     * 获取指定数据源的数据库连接
     * @param dsname 数据源名称
     * @return 数据库连接
     * @throws SQLException 获取连接异常
     */
    public static Connection getConnection(String dsname) throws SQLException {
        DataSource dataSource = get(dsname);
        if (dataSource == null) {
            throw new SQLException("数据源不存在：" + dsname);
        }
        return dataSource.getConnection();
    }

    /**
     * 从JDBC URL识别数据库类型并返回对应驱动
     * @param jdbcUrl JDBC连接地址
     * @return 驱动类名（未知返回"Unknown"）
     */
    public static String identifyDatabaseTypeFromJdbcUrl(String jdbcUrl) {
        if (StrUtil.isEmpty(jdbcUrl)) {
            return "Unknown";
        }

        if (jdbcUrl.startsWith("jdbc:mysql:")) {
            // 自动区分MySQL5/8驱动（可根据需要调整）
            return jdbcUrl.contains("mysql.cj") ? MYSQL_DRIVER6 : MYSQL_DRIVER5;
        } else if (jdbcUrl.startsWith("jdbc:postgresql:")) {
            return POSTGRESQL_DRIVER;
        } else if (jdbcUrl.startsWith("jdbc:oracle:")) {
            return ORACLE_DRIVER;
        } else if (jdbcUrl.startsWith("jdbc:sqlserver:")) {
            return SQLSERVER_DRIVER;
        } else {
            return "Unknown";
        }
    }

    /**
     * 关闭所有数据源（应用关闭时调用）
     */
    public static void closeAll() {
        deleteLock.lock();
        try {
            for (String dsname : INITIALIZED_DS) {
                DataSource dataSource = DATA_SOURCE_MAP.get(dsname);
                if (dataSource != null) {
                    DbUtil.close(dataSource);
                }
            }
            DATA_SOURCE_MAP.clear();
            INITIALIZED_DS.clear();
            log.info("所有数据源已关闭");
        } catch (Exception e) {
            log.error("关闭所有数据源失败", e);
        } finally {
            deleteLock.unlock();
        }
    }
}