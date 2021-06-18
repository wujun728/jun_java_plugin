package com.cjt.springjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cjt.springjdbc.model.User;

@Repository
public class UserDao implements IUserDao{
	
	//注入spring-jdbc
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		User user = this.jdbcTemplate.queryForObject(
		        "select id, name, address from t_user where id = ?",
		        new Object[]{id},
		        new RowMapper<User>() {
		            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		                User user = new User();
		                user.setId(rs.getInt("id"));
		                user.setName(rs.getString("name"));
		                user.setAddress(rs.getString("address"));
		                return user;
		            }
		        });
		return user;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> users = this.jdbcTemplate.query(
		        "select id, name, address from t_user",
		        new RowMapper<User>() {
		            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		                User user = new User();
		                user.setId(rs.getInt("id"));
		                user.setName(rs.getString("name"));
		                user.setAddress(rs.getString("address"));
		                return user;
		            }
		        });
		return users;
	}

	@Override
	public KeyHolder save(final User user) {
		// TODO Auto-generated method stub
		KeyHolder keyHolder = new GeneratedKeyHolder();
		/*this.jdbcTemplate.update(
				"insert into t_user (name, address) values (?, ?)",
				user.getName(), user.getAddress(), keyHolder);*/
		this.jdbcTemplate.update(new PreparedStatementCreator(){
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {   
                PreparedStatement ps =   
                    conn.prepareStatement("insert into t_user (name, address) values (?, ?)", new String[]{"id"});//返回id   
                ps.setString(1, user.getName());   
                ps.setString(2, user.getAddress());   
                return ps;   
            }   
		}, keyHolder);
		
		System.out.println(keyHolder.getKey());
		return keyHolder;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update(
		        "update t_user set name = ?, address = ? where id = ?",
		        user.getName(), user.getAddress(), user.getId());	
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update(
		        "delete from t_user where id = ?",
		        id);		
	}

}
