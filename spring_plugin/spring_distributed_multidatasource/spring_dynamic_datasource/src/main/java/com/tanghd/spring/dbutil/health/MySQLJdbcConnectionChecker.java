package com.tanghd.spring.dbutil.health;

public class MySQLJdbcConnectionChecker extends AbstractJdbcConnectionChecker {

    @Override
    public String getCheckSql() {
        return "select 1";
    }

}
