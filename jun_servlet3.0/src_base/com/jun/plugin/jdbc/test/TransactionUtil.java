package com.jun.plugin.jdbc.test;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.jun.plugin.datasource.DataSourceUtil;

//�ѵõ����Ӽ������йصķ���д��������
public class TransactionUtil {
	
	// �ڲ���ά���� һ�� map , ���map ��key ʼ�� ���� ��ǰ ���߳� 
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	private static DataSource ds = DataSourceUtil.getDataSource();
	
	public static DataSource getDataSource(){
		return ds;
	}
	
	//  ����, ���һ�� connection  ���� 
	public static Connection getConnection(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				//���������ӳ� �� ȡ һ������ ���� 
				conn = ds.getConnection();
				
				//�� ȡ����  connection �ŵ� tl��ȥ
				tl.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// ���� ����  
	// ��� ���� ������ һ�� connection����, ���� �� ���ص� connection�ŵ��� threadlocal�� , 
	public static void startTransaction(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void rollback(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void commit(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void relase(){
		try {
			Connection conn = tl.get();
			if(conn!=null){
				conn.close();
				tl.remove();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
