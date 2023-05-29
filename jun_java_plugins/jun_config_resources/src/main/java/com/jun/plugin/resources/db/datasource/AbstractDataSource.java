package com.jun.plugin.resources.db.datasource;

import javax.sql.DataSource;

/**
 * 数据库连接池
 * Created By Hong on 2018/8/17
 **/
public abstract class AbstractDataSource {

    private final String driverClass;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public AbstractDataSource(String driverClass, String jdbcUrl, String username, String password) {
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * 获取连接池
     *
     * @return DataSource
     */
    public abstract DataSource getDataSource();

    public String getDriverClass() {
        return driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
