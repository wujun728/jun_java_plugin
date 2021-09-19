package com.jun.plugin.javase.JDBCTest;

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
		ResultSet rs=null;//������ݿ���������ʱ��
		try {
			Class.forName("com.mysql.jdbc.Driver");//�������ݿ�������
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_food", "root","root");
			 //����DriverManager���е�getConnection()����������ָ�����ݿ������
			
			 //����Connection�ӿ��е�createStatement()��������Statement����Statement��������ã��Ѿ��������ӵĻ����ϣ�����sql���
			 String sql="insert into t_food(name,price) values (?,?)";
			 stat=conn.prepareStatement(sql);
			 //�༭sql���
			 rs=stat.executeQuery(sql);
			 //����Statement�еķ���executeQuery()ִ��sql��䣬�����ص����Ľ��
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
