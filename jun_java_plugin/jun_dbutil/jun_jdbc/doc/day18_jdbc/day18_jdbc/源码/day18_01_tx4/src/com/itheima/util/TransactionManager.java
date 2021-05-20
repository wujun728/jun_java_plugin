package com.itheima.util;

import java.sql.Connection;
import java.sql.SQLException;
//封装了所有与事务有关的方法
public class TransactionManager {
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	public static Connection getConnection(){
		Connection conn = tl.get();
		if(conn==null){//从当前线程中获取链接
			conn = DBCPUtil.getConnection();
			tl.set(conn);
		}
		return conn;
	}
	public static void startTransaction(){
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(){
		try {
			Connection conn = getConnection();
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commit(){
		try {
			Connection conn = getConnection();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void release(){
		try {
			Connection conn = getConnection();
			conn.close();
			tl.remove();//从当前线程中解绑。  与服务器实现有关：服务器采用线程池。
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
