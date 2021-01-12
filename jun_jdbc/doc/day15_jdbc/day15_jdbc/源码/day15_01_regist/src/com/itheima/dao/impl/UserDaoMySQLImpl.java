package com.itheima.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.util.JdbcUtil;

public class UserDaoMySQLImpl implements UserDao {

	public User findByUsername(String username) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select username,password,email,birthday from user where username='"+username+"'");
			if(rs.next()){
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public void save(User user) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("insert into user (username,password,email,birthday) values ('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getEmail()+"','"+user.getBirthday().toLocaleString()+"')");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}

	}

	public User findUser(String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			String sql = "select username,password,email,birthday from user where username='"+username+"' and password='"+password+"'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

}
