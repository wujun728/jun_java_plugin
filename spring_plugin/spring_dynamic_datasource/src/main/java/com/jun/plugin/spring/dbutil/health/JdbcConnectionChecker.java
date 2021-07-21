package com.jun.plugin.spring.dbutil.health;

import javax.sql.DataSource;

public interface JdbcConnectionChecker {

    boolean isAlive(DataSource ds);
}
