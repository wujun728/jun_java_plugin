package com.itheima.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.util.JdbcUtil;

public class UserDaoImpl implements UserDao {

	public User findByUsername(String username) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select username,password,email,birthday from user where username=?");
			//设置参数
			stmt.setString(1, username);//第一个问号的索引是1
			
			rs = stmt.executeQuery();
			
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("insert into user (username,password,email,birthday) values (?,?,?,?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
			
			stmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public User findUser(String username, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select username,password,email,birthday from user where username=? and password=?");
			//设置参数
			stmt.setString(1, username);//第一个问号的索引是1
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			
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
