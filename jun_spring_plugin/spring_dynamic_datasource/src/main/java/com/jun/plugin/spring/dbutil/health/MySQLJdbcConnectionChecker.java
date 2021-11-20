package com.jun.plugin.spring.dbutil.health;

public class MySQLJdbcConnectionChecker extends AbstractJdbcConnectionChecker {

    @Override
    public String getCheckSql() {
        return "select 1";
    }

}
