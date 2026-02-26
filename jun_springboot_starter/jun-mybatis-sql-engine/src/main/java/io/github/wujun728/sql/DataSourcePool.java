package io.github.wujun728.sql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DataSourcePool {

    public static final String main = "main";

    public static final String mysqlDriver5 = "com.mysql.jdbc.Driver";
    public static final String mysqlDriver6 = "com.mysql.cj.jdbc.Driver";
    public static final String postgresqlDriver6 = "org.postgresql.Driver";
    public static final String oracleDriver6 = "oracle.jdbc.driver.OracleDriver";
    public static final String sqlserverDriver6 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private static final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    /**
     * 连接池工厂实例，启动时按优先级自动检测：Druid > HikariCP。
     * 使用独立内部类隔离类引用，未引入的连接池 jar 不会触发 NoClassDefFoundError。
     */
    private static final PoolFactory poolFactory = detectPoolFactory();

    // ==================== 连接池工厂抽象 ====================

    private interface PoolFactory {
        DataSource create(String name, String url, String username, String password, String driver);
        String type();
    }

    /** Druid 连接池工厂 */
    private static class DruidPoolFactory implements PoolFactory {
        @Override
        public DataSource create(String name, String url, String username, String password, String driver) {
            com.alibaba.druid.pool.DruidDataSource ds = new com.alibaba.druid.pool.DruidDataSource();
            ds.setName(name);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setDriverClassName(driver);
            ds.setConnectionErrorRetryAttempts(3);
            ds.setBreakAfterAcquireFailure(true);
            return ds;
        }

        @Override
        public String type() {
            return "Druid";
        }
    }

    /** HikariCP 连接池工厂 */
    private static class HikariPoolFactory implements PoolFactory {
        @Override
        public DataSource create(String name, String url, String username, String password, String driver) {
            com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
            config.setPoolName(name);
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName(driver);
            config.setConnectionTimeout(30000);
            config.setMaximumPoolSize(10);
            return new com.zaxxer.hikari.HikariDataSource(config);
        }

        @Override
        public String type() {
            return "HikariCP";
        }
    }

    private static PoolFactory detectPoolFactory() {
        // 优先级：Druid > HikariCP
        try {
            Class.forName("com.alibaba.druid.pool.DruidDataSource");
            StaticLog.info("检测到 Druid 连接池依赖，使用 Druid");
            return new DruidPoolFactory();
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("com.zaxxer.hikari.HikariDataSource");
            StaticLog.info("检测到 HikariCP 连接池依赖，使用 HikariCP");
            return new HikariPoolFactory();
        } catch (ClassNotFoundException ignored) {
        }
        throw new RuntimeException("未找到可用的数据库连接池依赖，请引入 druid 或 HikariCP");
    }

    // ==================== 公开 API ====================

    public static DataSource init(String dsname, String url, String username, String password) {
        return init(dsname, url, username, password, identifyDatabaseTypeFromJdbcUrl(url));
    }

    public static String identifyDatabaseTypeFromJdbcUrl(String jdbcUrl) {
        if (jdbcUrl.startsWith("jdbc:mysql:")) {
            return mysqlDriver5;
        } else if (jdbcUrl.startsWith("jdbc:postgresql:")) {
            return postgresqlDriver6;
        } else if (jdbcUrl.startsWith("jdbc:oracle:")) {
            return oracleDriver6;
        } else if (jdbcUrl.startsWith("jdbc:sqlserver:")) {
            return sqlserverDriver6;
        } else {
            return "Unknown";
        }
    }

    /**
     * 初始化数据源，如果已存在则直接返回。
     * 使用 computeIfAbsent 保证同一 key 的原子性创建，不同 key 之间互不阻塞。
     */
    public static DataSource init(String dsname, String url, String username, String password, String driver) {
        return dataSourceMap.computeIfAbsent(dsname, key -> {
            DataSource ds = poolFactory.create(key, url, username, password, driver);
            StaticLog.info("创建{}连接池成功：{}", poolFactory.type(), key);
            return ds;
        });
    }

    public static void init(String dsname, DataSource dataSource) {
        DataSource existing = dataSourceMap.putIfAbsent(dsname, dataSource);
        if (existing == null) {
            StaticLog.info("添加连接池成功：{}", dsname);
        }
    }

    public static DataSource get(String dsname) {
        if (StrUtil.isEmpty(dsname)) {
            return null;
        }
        return dataSourceMap.get(dsname);
    }

    /**
     * 删除并关闭数据库连接池。
     * 通过 Closeable 接口统一关闭，兼容 Druid、HikariCP 及其他实现。
     */
    public static void remove(String dsname) {
        DataSource removed = dataSourceMap.remove(dsname);
        if (removed == null) {
            return;
        }
        if (removed instanceof Closeable) {
            try {
                ((Closeable) removed).close();
            } catch (IOException e) {
                StaticLog.error("关闭连接池失败：{}, {}", dsname, e.getMessage());
            }
        }
        StaticLog.info("关闭并移除连接池：{}", dsname);
    }

    public static Connection getConnection(String dsname) throws SQLException {
        DataSource dataSource = get(dsname);
        if (dataSource == null) {
            throw new SQLException("数据源不存在：" + dsname);
        }
        return dataSource.getConnection();
    }

    public static ConcurrentHashMap<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    /** 获取当前使用的连接池类型名称 */
    public static String getPoolType() {
        return poolFactory.type();
    }
}