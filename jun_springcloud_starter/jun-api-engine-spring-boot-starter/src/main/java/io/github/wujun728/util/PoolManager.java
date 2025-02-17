package io.github.wujun728.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.github.wujun728.entity.ApiDataSource;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class PoolManager {

    //所有数据源的连接池存在map里
    private static ConcurrentHashMap<String, DruidDataSource> map = new ConcurrentHashMap<>();

    private static DruidDataSource getJdbcConnectionPool(ApiDataSource ds) {
        if (map.containsKey(ds.getId())) {
            return map.get(ds.getId());
        } else {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(ds.getUrl());
            druidDataSource.setUsername(ds.getUsername());
            druidDataSource.setDriverClassName(ds.getDriver());
            druidDataSource.setConnectionErrorRetryAttempts(3);       //失败后重连次数
            druidDataSource.setBreakAfterAcquireFailure(true);
            druidDataSource.setPassword(ds.getPassword());
            druidDataSource.setName(ds.getId());
            map.put(ds.getId(), druidDataSource);
            return map.get(ds.getId());
        }
    }

    public static DruidPooledConnection getPooledConnection(ApiDataSource ds) throws SQLException {
        DruidDataSource pool = PoolManager.getJdbcConnectionPool(ds);
        DruidPooledConnection connection = pool.getConnection();
        return connection;
    }
}
