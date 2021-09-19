package com.jun.plugin.jdbc.chap02.sec04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo1 {

	// ���ݿ��ַ
	private static String dbUrl="jdbc:mysql://localhost:3306/db_book";
	// �û���
	private static String dbUserName="root";
	// ����
	private static String dbPassword="123456";
	// ��������
	private static String jdbcName="com.mysql.jdbc.Driver";
			
	public static void main(String[] args) {
		try {
			Class.forName(jdbcName);
			System.out.println("���������ɹ���");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��������ʧ�ܣ�");
		}
		Connection con=null;
		try {
			// ��ȡ���ݿ�����
			con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("��ȡ���ݿ����ӳɹ���");
			System.out.println("�������ݿ������");
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
