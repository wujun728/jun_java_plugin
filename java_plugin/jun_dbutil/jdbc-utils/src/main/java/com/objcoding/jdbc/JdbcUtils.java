package com.objcoding.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chenghui.zhang on 2018/2/10.
 */
public class JdbcUtils {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 采用饿汉模式，默认 c3p0-copnfig.xml 文件，所以使用该工具需在 classpath 中添加 c3p0-copnfig.xml 配置文件
     */
    private static DataSource ds = new ComboPooledDataSource();

    /**
     * 它为null表示没有事务
     * 它不为null表示有事务
     * 当开启事务时，需要给它赋值
     * 当结束事务时，需要给它赋值为null
     * 并且在开启事务时，让dao的多个方法共享这个Connection
     */
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static DataSource getDataSource() {
        return ds;
    }
//
//    private static void setDataSource(DataSource ds) {
//        JdbcUtils.ds = ds;
//    }

    /**
     * 获取当前线程数据库连接
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 如果有事务，返回当前事务的con
        // 如果没有事务，通过连接池返回新的con
        Connection con = tl.get();
        if (con != null) {
            return con;
        }
        return ds.getConnection();
    }

    /**
     * 开启事务
     *
     * @throws SQLException
     */
    public static void begin() throws SQLException {
        //获取当前线程的事务连接
        Connection con = tl.get();
        if (con != null) {
            throw new SQLException("已经开启了事务，不能重复开启！");
        }
        //给con赋值，表示开启了事务
        con = ds.getConnection();
        //设置为手动提交
        con.setAutoCommit(false);
        tl.set(con);
    }

    /**
     * 提交事务
     *
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        //获取当前线程的事务连接
        Connection con = tl.get();
        if (con == null) {
            throw new SQLException("没有事务不能提交！");
        }
        con.commit();
        con.close();
        tl.remove();
    }

    /**
     * 回滚事务
     *
     * @throws SQLException
     */
    public static void rollback() throws SQLException {
        Connection con = tl.get();
        //获取当前线程的事务连接
        if (con == null) {
            throw new SQLException("没有事务不能回滚！");
        }
        con.rollback();
        con.close();
        tl.remove();
    }

    /**
     * 释放Connection
     *
     * @param connection
     * @throws SQLException
     */
    public static void releaseConnection(Connection connection) throws SQLException {
        //获取当前线程的事务连接
        Connection con = tl.get();
        //如果参数连接，与当前事务连接不同，说明这个连接不是当前事务，可以关闭！
        if (connection != con) {
            //如果参数连接没有关闭，关闭之！
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
