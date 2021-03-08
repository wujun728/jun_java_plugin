package com.tanghd.spring.dbutil.health;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public abstract class AbstractJdbcConnectionChecker implements JdbcConnectionChecker {

    @Override
    public boolean isAlive(DataSource ds) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            Statement st = conn.createStatement();
            String sql = getCheckSql();
            st.executeQuery(sql);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public abstract String getCheckSql();

}
