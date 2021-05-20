package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//Statement详解
public class JdbcDemo3 {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day15", "root", "sorry");
//		3、创建代表SQL语句的对象
		Statement stmt = conn.createStatement();
		
//		int num = stmt.executeUpdate("update users set password=123");
//		System.out.println(num);
		
//		boolean b = stmt.execute("update users set password=111");
//		System.out.println(b);
		
//		boolean b = stmt.execute("select * from users");
//		System.out.println(b);
//		if(b){
//			ResultSet rs = stmt.getResultSet();
//		}
		
		stmt.close();
		conn.close();
	}

}
