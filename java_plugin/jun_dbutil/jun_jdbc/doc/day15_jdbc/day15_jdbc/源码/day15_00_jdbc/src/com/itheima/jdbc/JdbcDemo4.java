package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.itheima.user.User;

//ResultSet详解
public class JdbcDemo4 {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day15", "root", "sorry");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select id,name,password,email,birthday from users");
//		rs.afterLast();
//		boolean b = rs.previous();
//		if(b){
//			User user = new User();
//			user.setId(rs.getInt("id"));
//			user.setName(rs.getString("name"));
//			user.setPassword(rs.getString("password"));
//			user.setEmail(rs.getString("email"));
//			user.setBirthday(rs.getDate("birthday"));
//			System.out.println(user);
//		}else{
//			System.out.println("没有结果");
//		}
		
//		5、如果是查询语句，需要遍历结果集
		List<User> users = new ArrayList<User>();
		while(rs.next()){
			System.out.println("---------------------");
//			System.out.println(rs.getObject("id"));//不考虑字段的类型
//			System.out.println(rs.getObject("name"));
//			System.out.println(rs.getObject("password"));
//			System.out.println(rs.getObject("email"));
//			System.out.println(rs.getObject("birthday"));
			
//			System.out.println(rs.getObject(1));//第一列。所用从1开始。   做JDBC有关的框架时用
//			System.out.println(rs.getObject(2));
//			System.out.println(rs.getObject(3));
//			System.out.println(rs.getObject(4));
//			System.out.println(rs.getObject(5));
			User user = new User();
//			user.setId((Integer)rs.getObject("id"));
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setBirthday(rs.getDate("birthday"));
			
			users.add(user);
			
		}
		rs.close();
		stmt.close();
		conn.close();
		
		for(User u:users)
			System.out.println(u);
	}

}
