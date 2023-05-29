package com.jun.plugin.resources.db.datasource;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * 默认连接池
 * 需要导入com.alibaba.druid连接池
 * Created By Hong on 2018/8/17
 **/
public class DefaultDataSource extends AbstractDataSource {

    public DefaultDataSource(String driverClass, String jdbcUrl, String username, String password) {
        super(driverClass, jdbcUrl, username, password);
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(super.getDriverClass());
        dataSource.setUrl(super.getJdbcUrl());
        dataSource.setUsername(super.getUsername());
        dataSource.setPassword(super.getPassword());
        return dataSource;
    }
}
