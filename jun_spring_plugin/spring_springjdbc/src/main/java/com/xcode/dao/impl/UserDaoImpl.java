package com.xcode.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.crypto.provider.RSACipher;
import com.xcode.beans.User;
import com.xcode.dao.UserDao;

@Repository("UserDao")
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	public List<User> getAllUsers(){
		String sql="select * from user";
			
		@SuppressWarnings("unchecked")
		List<User> users=this.jdbcTemplate.query(sql, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs,int row)throws SQLException{
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
		
		return users;
	}
	
	@Override
	public User getUserById(int id) {
		User user=null;
		String sql="select * from user where id = ?";
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<>(User.class);
		user=jdbcTemplate.queryForObject(sql, rowMapper, id);
		return user;
	}

	@Override
	public int addUser(User user) {
		String sql="insert into user(username,password) values(?,?)";
		Object [] params=new Object[]{user.getUsername(),user.getPassword()}; 
		int temp=this.jdbcTemplate.update(sql, params);
		return temp;
	}

	@Override
	public int deleteUserById(int id) {
		String sqlString="delete from user where id="+id;
		int temp=this.jdbcTemplate.update(sqlString);
		return temp;
	}
	
	@Override
	public int update(User user){
	     String sql="update user set username=?, password=? where id="+user.getId();
	     Object[] parama=new Object[]{user.getUsername(),user.getPassword()};
	     int temp=this.jdbcTemplate.update(sql, parama);
	     return temp;
	}

}
