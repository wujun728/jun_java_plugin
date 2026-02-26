package io.github.wujun728.sql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
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

    // 所有数据源的连接池存在map里
    private static final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

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
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setDriverClassName(driver);
            druidDataSource.setConnectionErrorRetryAttempts(3);
            druidDataSource.setBreakAfterAcquireFailure(true);
            StaticLog.info("创建Druid连接池成功：{}", key);
            return druidDataSource;
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
     * 删除数据库连接池。
     * 使用 ConcurrentHashMap.remove 原子移除，避免 get+remove 的竞态条件。
     */
    public static void remove(String dsname) {
        DataSource removed = dataSourceMap.remove(dsname);
        if (removed instanceof DruidDataSource) {
            try {
                ((DruidDataSource) removed).close();
                StaticLog.info("关闭并移除连接池：{}", dsname);
            } catch (Exception e) {
                StaticLog.error("关闭连接池失败：{}, {}", dsname, e.getMessage());
            }
        }
    }

    public static Connection getConnection(String dsname) throws SQLException {
        DataSource dataSource = get(dsname);
        if (dataSource == null) {
            throw new SQLException("数据源不存在：" + dsname);
        }
        return dataSource.getConnection();
    }

    /**
     * 获取当前所有数据源的只读快照（用于监控等场景）。
     */
    public static ConcurrentHashMap<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }
}