package com.qunar.vacation.example.dao;

import org.springframework.stereotype.Repository;

import com.qunar.vacation.example.model.User;

@Repository
public class UserDao /*extends JdbcDaoSupport*/{
	public User get(Integer id){
		return new User(id, "zhangsan", 20);
	}
	
	//some sample code to use jdbcTemplate, to use the code must configuration dataSource, and create database/table first

	/*public User get(Integer id){
		String sql = "select id, name, age from user where id=?";
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<User>(User.class));
	}
	
	public void delete(Integer id){
		this.getJdbcTemplate().update("delete from user where id=?", id);
	}
	
	public void insert(User user){
		this.getJdbcTemplate().update("insert into user(name,age)value(?,?)", user.getName(), user.getAge());
	}
	
	public List<User> getUser(int offset, int size){
		return this.getJdbcTemplate().query("select id, name,age from user offset ? limit ?", new Object[]{offset, size}, new BeanPropertyRowMapper<User>(User.class));
	}
	
	public int getUserSize(){
		return this.getJdbcTemplate().queryForInt("select count(1) from user");
	}*/
}
