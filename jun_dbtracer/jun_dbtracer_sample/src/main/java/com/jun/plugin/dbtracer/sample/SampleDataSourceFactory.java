package com.jun.plugin.dbtracer.sample;

import javax.sql.DataSource;

import com.jun.plugin.dbtracer.db.DataSourceFactory;

public class SampleDataSourceFactory implements DataSourceFactory {

    public DataSource getDataSource() {
        return (DataSource) SpringUtil.getBean("dataSource");
    }

}
