package com.lyx;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected DataSourceLookupKey determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        if (!(DbContextHolder.getDbType() == null)) {
            System.out.println("====>" + DbContextHolder.getDbType());
        } else {
            System.out.println("====>没有初始化数据库上下文环境");
        }

        return DbContextHolder.getDbType();
    }
}
