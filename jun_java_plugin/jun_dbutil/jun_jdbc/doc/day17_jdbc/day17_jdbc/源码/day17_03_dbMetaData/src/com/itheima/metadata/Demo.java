package com.itheima.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.itheima.util.DBCPUtil;

//数据库元信息的获取
public class Demo {
	//数据库本身信息的获取
	@Test
	public void test1() throws Exception{
		Connection conn = DBCPUtil.getConnection();
		DatabaseMetaData dmd = conn.getMetaData();
		String name = dmd.getDatabaseProductName();//能知道说什么方言
		System.out.println(name);
		int isolation = dmd.getDefaultTransactionIsolation();
		System.out.println(isolation);
	}
	//参数元数据信息：PreparedStatement时
	@Test
	public void test2() throws Exception{
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement("??????????");
		
		ParameterMetaData pmd = stmt.getParameterMetaData();
		int count = pmd.getParameterCount();
		System.out.println(count);//统计语句中的占位符个数
	}
	//结果集元数据信息：
	@Test
	public void test3()throws Exception{
		Connection conn = DBCPUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select * from account");
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();//有几列
		System.out.println(count);
		
		for(int i=0;i<count;i++){
			String fieldName = rsmd.getColumnName(i+1);
			int type = rsmd.getColumnType(i+1);
			System.out.println(fieldName+":"+type);
		}
	}
}
