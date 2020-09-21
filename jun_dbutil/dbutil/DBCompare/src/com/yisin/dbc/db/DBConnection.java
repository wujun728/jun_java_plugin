package com.yisin.dbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yisin.dbc.util.CommonUtils;

public class DBConnection {

    private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private String url = "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding={3}";
    private String dnurl = "";
    private String port = "3306";
    private String username = "";
    private String password = "";

    private Connection conn = null;

    public DBConnection setDbInfo(String ip, String dnname, String encode, String userName, String pwd) {
        dnurl = CommonUtils.format(url, ip, port, dnname, encode);
        username = userName;
        password = pwd;
        return this;
    }

    public DBConnection setDbInfo(String ip, String port, String dnname, String encode, String userName, String pwd) {
        dnurl = CommonUtils.format(url, ip, port, dnname, encode);
        username = userName;
        password = pwd;
        return this;
    }

    public Connection getConnection() {
        try {
            // 加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 连接MySql数据库，用户名和密码都是root
            conn = DriverManager.getConnection(dnurl, username, password);
        } catch (Exception e) {
            logger.error("找不到驱动程序类 ，加载驱动失败！", e);
        }
        return conn;
    }

    public Statement getStatement() {
        Statement stat = null;
        try {
            conn = getConnection();
            stat = conn.createStatement();
        } catch (Exception e) {
            logger.error("创建Statement失败！", e);
        }
        return stat;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement stat = null;
        try {
            conn = getConnection();
            stat = conn.prepareStatement(sql);
        } catch (Exception e) {
            logger.error("创建PreparedStatement失败！", e);
        }
        return stat;
    }

    public DBConnection close(ResultSet rs, PreparedStatement stmt) {
        close(rs, stmt, conn);
        return this;
    }

    public DBConnection close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) { // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) { // 关闭声明
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) { // 关闭连接对象
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public DBConnection close(ResultSet rs, Statement stmt) {
        close(rs, stmt, conn);
        return this;
    }

    public DBConnection close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) { // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) { // 关闭声明
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) { // 关闭连接对象
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

}
