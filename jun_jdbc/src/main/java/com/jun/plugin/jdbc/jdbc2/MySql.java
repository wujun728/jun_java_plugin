package com.jun.plugin.jdbc.jdbc2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySql类用于实施MySql数据库操作
 * @author Wujun
 */
public class MySql {

    // 定义MySql驱动,数据库地址,数据库用户名 密码, 执行语句和数据库连接
    public String driver = "com.mysql.jdbc.Driver";
    public String url = "jdbc:mysql://127.0.0.1:3306/htmldatacollection";
    public String user = "root";
    public String password = "root";
    public Statement stmt = null;
    public Connection conn = null;

    /**
     * 创建一个插入数据的方法  executeUpdate()
     * @param insertSQl
     */
    public void datatoMySql(String insertSQl) {

        try {
            try {
                Class.forName(driver).newInstance();
            } catch (Exception e) {
                System.out.println("无法找到驱动器");
                e.printStackTrace();
            }
            // 创建连接
            conn = DriverManager.getConnection(url, user, password);
            // 创建一个 Statement 对象来将 SQL 语句发送到数据库
            stmt = conn.createStatement();
            // 执行SQL 插入语句
            stmt.executeUpdate(insertSQl);
            // 执行完 停止执行语句
            stmt.close();
            // 执行完关闭数据库连接
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建一个用于select查看数据的方法 executeQuery();
     * @param strSelect
     * @return ResultSet
     */
    public ResultSet queryMySql(String strSelect) {
        // 创建一个数据集 用于获取查询到的行数据
        ResultSet rs = null;
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            System.out.println("无法找到驱动器!");
            e.printStackTrace();
        }

        try {
            // 创建连接
            conn = DriverManager.getConnection(url, user, password);
            // 创建一个 Statement 对象来将 SQL 语句发送到数据库
            stmt = conn.createStatement();
            // 执行查询语句   获取ResultSet对象
            rs = stmt.executeQuery(strSelect);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //返回结果集
        return rs;
    }
}