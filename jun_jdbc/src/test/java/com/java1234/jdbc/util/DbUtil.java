package com.java1234.jdbc.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DbUtil {

	// 数据库地址
	private static String dbUrl="jdbc:mysql://localhost:3306/db_bank";
	// 用户名
	private static String dbUserName="root";
	// 密码
	private static String dbPassword="123456";
	// 驱动名称
	private static String jdbcName="com.mysql.jdbc.Driver";
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}
	
	/**
	 * 关闭连接
	 * @param con
	 * @throws Exception
	 */
	public void close(Statement stmt,Connection con)throws Exception{
		if(stmt!=null){
			stmt.close();
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * 关闭连接
	 * @param con
	 * @throws Exception
	 */
	public void close(PreparedStatement pstmt,Connection con)throws Exception{
		if(pstmt!=null){
			pstmt.close();
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * 关闭连接
	 * @param con
	 * @throws Exception
	 */
	public void close(CallableStatement cstmt,Connection con)throws Exception{
		if(cstmt!=null){
			cstmt.close();
			if(con!=null){
				con.close();
			}
		}
	}
}
