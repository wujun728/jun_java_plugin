package com.ky26.JDBCTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnTest {
	public static void main(String[] args) {
		Connection conn=null;
		PreparedStatement stat=null;
//		PreparedStatement
		ResultSet rs=null;//存放数据库结果集的临时表
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动类
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_food", "root","root");
			 //利用DriverManager类中的getConnection()方法建立与指定数据库的连接
			
			 //利用Connection接口中的createStatement()方法建立Statement对象，Statement对象的作用：已经建立连接的基础上，发送sql语句
			 String sql="insert into t_food(name,price) values (?,?)";
			 stat=conn.prepareStatement(sql);
			 //编辑sql语句
			 rs=stat.executeQuery(sql);
			 //利用Statement中的方法executeQuery()执行sql语句，并返回单个的结果
			 while(rs.next()){
				Food food=new Food();
				food.setName(rs.getString("name"));
				food.setPrice(rs.getInt("price"));
				System.out.println(food.getName()+","+food.getPrice());
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
