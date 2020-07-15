package com.jun.base.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DbUtil {

	/**
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		String dbUrl="jdbc:mysql://localhost:3306/test";
		String dbUserName="root";
		String dbPassword="123456";
		String jdbcName="com.mysql.jdbc.Driver";
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;
	}
	
	/**
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
