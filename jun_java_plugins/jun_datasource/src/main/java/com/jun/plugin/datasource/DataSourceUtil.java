package com.jun.plugin.datasource;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceUtil {

	public static void main(String[] args) {
		System.err.println(DataSourceUtil.getDataSource());
	}
	
	private static DataSource dataSource = null;
	private static Connection conn = null;
	static {
		try {
//			dataSource = DataSourceC3p0.getDataSource();
//			dataSource = DataSourceDBCP.getDataSource();
			dataSource = DataSourceDruid.getDataSource();
			if (dataSource != null) {
				conn = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static final DataSource getDataSource() {
		return dataSource;
	}

	
	public static final Connection getConn() {
		try {
			if (dataSource != null) {
				conn = dataSource.getConnection();
			}
		} catch (SQLException e) {
			// log.error("获取数据库连接失败：" + e);
			e.printStackTrace();
		}
		return conn;
	}

	public static void rollback() throws SQLException {
		try {
			if (dataSource != null) {
				conn = dataSource.getConnection();
				if (conn != null) {
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			// log.error("获取数据库连接失败：" + e);
			e.printStackTrace();
			throw e;
		}
	}
	 
	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			// log.error("关闭数据库连接失败：" + e);
		}
	}
	public static void commit() throws SQLException {
		try {
			if (conn == null)
				return;
			if (conn.isClosed())
				return;
			if (!(conn.isClosed() || conn.getAutoCommit()))
				conn.commit(); // 事务提交
			conn.close();// 释放connection，是将其放回到连接池.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}









	
	
	
	public static Connection getConnJNDI2(){
		try{
			Context context = new InitialContext();
			Context tomcatContext = (Context) context.lookup("java:comp/env");
			DataSource ds = (DataSource) tomcatContext.lookup("tomcatDS");
			conn = ds.getConnection();
		}catch(Exception err){
			err.printStackTrace();
		}
		return conn;
	}
		public static Connection getConnJNDI(){
			try{
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/testDs");
				conn = ds.getConnection();
			}catch(Exception err){
				err.printStackTrace();
			}
			return conn;
		}


		/*public static Connection getConn() {
			return conn;
		}*/

}
