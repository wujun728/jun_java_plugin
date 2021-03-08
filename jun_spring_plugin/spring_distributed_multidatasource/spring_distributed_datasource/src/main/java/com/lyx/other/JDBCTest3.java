package com.lyx.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest3 {
    public static void main(String[] args) {
        // 创建10个线程执行插入操作
        for (int i = 0; i < 100; i++) {
            final int n = i;
            Runnable task = new Runnable() {
                public void run() {
                    Thread.currentThread().setName("thread_" + n);
                    insertByBatch(Thread.currentThread().getName());
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public static void insertByBatch(String name) {
        long startTime = System.currentTimeMillis(); // 获取开始时间
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into people_thread (first_name ,last_name ,"
                + "thread_name) values (?,?,?)";
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                ps.setString(1, Integer.toString(i));
                ps.setString(2, Integer.toString(i));
                ps.setString(3, name);
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
                    "jdbc:mysql://localhost:3306/local_database", "root",
                    "034039");// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; // 返回所建立的数据库连接
    }

}
