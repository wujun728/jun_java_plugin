package com.java1234.jdbc.chap02.sec04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo1 {

	// 数据库地址
	private static String dbUrl="jdbc:mysql://localhost:3306/db_book";
	// 用户名
	private static String dbUserName="root";
	// 密码
	private static String dbPassword="123456";
	// 驱动名称
	private static String jdbcName="com.mysql.jdbc.Driver";
			
	public static void main(String[] args) {
		try {
			Class.forName(jdbcName);
			System.out.println("加载驱动成功！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加载驱动失败！");
		}
		Connection con=null;
		try {
			// 获取数据库连接
			con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("获取数据库连接成功！");
			System.out.println("进行数据库操作！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
