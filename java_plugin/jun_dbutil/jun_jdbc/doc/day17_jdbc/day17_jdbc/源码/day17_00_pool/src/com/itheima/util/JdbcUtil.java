package com.itheima.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//π§æﬂ¿‡
public class JdbcUtil {
	
	private static String driverClass;
	private static String url;
	private static String user;
	private static String password;
	
	static{
		try {
			ClassLoader cl = JdbcUtil.class.getClassLoader();
			InputStream in = cl.getResourceAsStream("dbcfg.properties");
			Properties props = new Properties();
			props.load(in);
			driverClass = props.getProperty("driverClass");
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			
			Class.forName(driverClass);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	
	public static Connection getConnection() throws Exception{
		Connection conn = DriverManager.getConnection(url,user, password);
		return conn;
	}
	public static void release(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
