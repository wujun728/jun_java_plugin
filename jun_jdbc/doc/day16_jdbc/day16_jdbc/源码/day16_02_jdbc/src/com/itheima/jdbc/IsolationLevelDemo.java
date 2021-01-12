package com.itheima.jdbc;

import java.sql.Connection;

import org.junit.Test;

import com.itheima.util.JdbcUtil;
//设置隔离级别
public class IsolationLevelDemo {
	@Test
	public void test1() throws Exception{
		Connection conn = JdbcUtil.getConnection();
		//一定要在开启事务前更改隔离级别
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		
//		....
		conn.commit();
	}
}
