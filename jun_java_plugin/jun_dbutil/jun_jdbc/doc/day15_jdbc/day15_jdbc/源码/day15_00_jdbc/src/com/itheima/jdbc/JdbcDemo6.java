package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.itheima.util.JdbcUtil;

public class JdbcDemo6 {
	@Test
	public void testAdd(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("insert into users (name,password,email,birthday) values ('∑∂«‡œº','123','fqx@itcast.cn','2000-10-01')");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	@Test
	public void testUpdate(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("update users set password=111 where id=4");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	@Test
	public void testDelete(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("delete from users where id=1");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}
}
