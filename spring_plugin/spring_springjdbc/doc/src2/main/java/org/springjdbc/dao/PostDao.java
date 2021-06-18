package org.springjdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao extends BaseDao {

	public Connection getNativeConn() throws SQLException
	{
			Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			return jdbcTemplate.getNativeJdbcExtractor().getNativeConnection(connection);
	}
	
}
