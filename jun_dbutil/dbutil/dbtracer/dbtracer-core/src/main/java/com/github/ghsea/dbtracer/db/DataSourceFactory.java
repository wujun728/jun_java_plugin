package com.github.ghsea.dbtracer.db;

import javax.sql.DataSource;

public interface DataSourceFactory {
    
    public DataSource getDataSource();

}
