package com.lyx.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest4 {

    // test库people表插入100万数据
    public static void main(String args[]) throws SQLException {

        long startTime = System.currentTimeMillis(); // 获取开始时间
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into people (first_name ,last_name) values (?,?)";
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 100; i++) {
                ps.setString(1, "JOHN");
                ps.setString(2, "DOE");
                ps.addBatch();
                // 一批提交一次
                if (i % 10 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

    /* 获取数据库连接的函数 */
    public static Connection getConnection() {
        Connection con = null; // 创建用于连接数据库的Connection对象
        try {
            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "root", "034039");// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; // 返回所建立的数据库连接
    }
}
