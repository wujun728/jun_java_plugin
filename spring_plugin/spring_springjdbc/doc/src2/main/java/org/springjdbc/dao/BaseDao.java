package org.springjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public BaseDao() {
		super();
	}
	
	public void initDb()
	{
		String sql = "drop table if exists t_user;";
		jdbcTemplate.execute(sql);
		sql = "create table t_user(user_id int primary key,user_name varchar(60));";
		jdbcTemplate.execute(sql);
	}

}