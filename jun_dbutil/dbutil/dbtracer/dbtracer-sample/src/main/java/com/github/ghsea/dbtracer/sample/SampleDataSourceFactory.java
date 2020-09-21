package com.github.ghsea.dbtracer.sample;

import javax.sql.DataSource;

import com.github.ghsea.dbtracer.db.DataSourceFactory;

public class SampleDataSourceFactory implements DataSourceFactory {

    public DataSource getDataSource() {
        return (DataSource) SpringUtil.getBean("dataSource");
    }

}
