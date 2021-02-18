package com.tjgd.DBHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtil {
//	private static final String JNDINM = "java:comp/env/jdbc/mysqlds";
//	private DataSource ds = null;
//	private Connection conn = null;
//	// 构造函数初始化
//	public DataSourceUtil() throws NamingException {
//		Context ctx = new InitialContext();  //初始化查询上下文
//		ds = (DataSource) ctx.lookup(JNDINM);  //通过名称查询DataSource对象
//	}
	/*// 获取数据库连接
	public Connection getConnection() {
		try {
			conn = ds.getConnection();  //通过DataSource取得一个数据库的连接
		} catch (SQLException e) {
			System.out.println("获取数据库连接失败!");
		}
		return conn;
	}*/
	
	private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	/*private final static String URL = "jdbc:mysql://localhost:3306/rbac?characterEncoding=utf-8";
	private static String USER_NAME = "root";
	private static String PASSWORD = "5076456754";*/
	private final static String URL = "jdbc:mysql://localhost:3306/jun_base?characterEncoding=utf-8";
	private static String USER_NAME = "root";
	private static String PASSWORD = "mysqladmin";
	private static  Connection conn = null;
	//��������
	static{
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * ������Ӷ���
	 * @author 
	 * @return ����ɹ�������Connection���󣬷��򷵻�null
	 */
	public static Connection getConnection(){
		
		try {
			//�õ����ݿ�����
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;	//��������
	}
	// 关闭数据库连接
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("关闭数据库连接失败!");
			}
		}
	}
/*	public static void main(String[] args) {
		System.out.println(DataSourceUtil.getConnection());
	}*/
}
