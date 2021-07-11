package com.objcoding.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 这个类中的方法，自己来处理连接的问题
 * 通过JdbcUtils.getConnection()得到连接！有可能是事务连接，也可能是普通的连接！
 * JdbcUtils.releaseConnection()完成对连接的释放！如果是普通连接，关闭之！
 * <p>
 * Created by chenghui.zhang on 2018/2/10.
 */
public class TxQueryRunner extends QueryRunner {
    @Override
    public int[] batch(String sql, Object[][] params) throws SQLException {
        /*
         * 1. 得到连接
         * 2. 执行父类方法，传递连接对象
         * 3. 释放连接
         * 4. 返回值
         */
        Connection con = JdbcUtils.getConnection();//这个类就把调用这个方法的人少写了这两行代码，如在dao层调用的时候使代码更优雅
        int[] result = super.batch(con, sql, params);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public <T> T query(String sql, Object param, ResultSetHandler<T> rsh)
            throws SQLException {
        Connection con = JdbcUtils.getConnection();
        T result = super.query(con, sql, param, rsh);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public <T> T query(String sql, Object[] params, ResultSetHandler<T> rsh)
            throws SQLException {
        Connection con = JdbcUtils.getConnection();
        T result = super.query(con, sql, params, rsh);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params)
            throws SQLException {
        Connection con = JdbcUtils.getConnection();
        T result = super.query(con, sql, rsh, params);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection con = JdbcUtils.getConnection();
        T result = super.query(con, sql, rsh);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public int update(String sql) throws SQLException {
        Connection con = JdbcUtils.getConnection();
        int result = super.update(con, sql);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public int update(String sql, Object param) throws SQLException {
        Connection con = JdbcUtils.getConnection();
        int result = super.update(con, sql, param);
        JdbcUtils.releaseConnection(con);
        return result;
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        Connection con = JdbcUtils.getConnection();
        int result = super.update(con, sql, params);
        JdbcUtils.releaseConnection(con);
        return result;
    }
}
