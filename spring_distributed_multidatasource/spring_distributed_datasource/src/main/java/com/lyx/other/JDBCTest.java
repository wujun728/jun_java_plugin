package com.lyx.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

    // 创建静态全局变量
    static Connection conn;

    static Statement st;

    public static void main(String[] args) {
        insert(); // 插入添加记录
        update(); // 更新记录数据
        query(); // 查询记录并显示
        delete(); // 删除记录
    }

    /* 插入数据记录，并输出插入的数据记录数 */
    public static void insert() {
        conn = getConnection(); // 首先要获取连接，即连接到数据库
        try {
            conn.setAutoCommit(false); // 设置事物不自动提交
            String sql = "INSERT INTO people(first_name,last_name)"
                    + " VALUES ('adsasd','weeewewe')"; // 插入数据的sql语句
            st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
            int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
            System.out.println("向people表中插入 " + count + " 条数据"); // 输出插入操作的处理结果
            conn.commit(); // 提交事物
        } catch (SQLException e) {
            System.out.println("插入数据失败" + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // 关闭数据库连接
        }
    }

    /* 更新符合要求的记录，并返回更新的记录数目 */
    public static void update() {
        conn = getConnection(); // 同样先要获取连接，即连接到数据库
        try {
            String sql = "update people set first_name='sdadfaf' where person_id = 1";// 更新数据的sql语句
            st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            int count = st.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数
            System.out.println("people表中更新 " + count + " 条数据"); // 输出更新操作的处理结果
        } catch (SQLException e) {
            System.out.println("更新数据失败");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // 关闭数据库连接
        }
    }

    /* 查询数据库，输出符合要求的记录的情况 */
    public static void query() {
        conn = getConnection(); // 同样先要获取连接，即连接到数据库
        try {
            String sql = "select * from people"; // 查询数据的sql语句
            st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
            System.out.println("最后的查询结果为：");
            while (rs.next()) { // 判断是否还有下一个数据
                // 根据字段名获取相应的值
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");

                // 输出查到的记录的各个字段的值
                System.out.println("first_name=" + first_name + " "
                        + "last_name=" + last_name);

            }
        } catch (SQLException e) {
            System.out.println("查询数据失败");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // 关闭数据库连接
        }
    }

    /* 删除符合要求的记录，输出情况 */
    public static void delete() {
        conn = getConnection(); // 同样先要获取连接，即连接到数据库
        try {
            String sql = "delete from people where person_id = 1";// 删除数据的sql语句
            st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            int count = st.executeUpdate(sql);// 执行sql删除语句，返回删除数据的数量
            System.out.println("people表中删除 " + count + " 条数据\n"); // 输出删除操作的处理结果
        } catch (SQLException e) {
            System.out.println("删除数据失败");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // 关闭数据库连接
        }
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
