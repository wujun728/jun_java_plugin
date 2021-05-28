package com.ibeetl.admin.core.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;
@Configuration
public class DataSourceConfig {	
	@Bean(name = "baseDataSource")
	public DataSource datasource(Environment env) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(env.getProperty("spring.datasource.baseDataSource.url"));
		ds.setUsername(env.getProperty("spring.datasource.baseDataSource.username"));
		ds.setPassword(env.getProperty("spring.datasource.baseDataSource.password"));
		ds.setDriverClassName(env.getProperty("spring.datasource.baseDataSource.driver-class-name"));
		return ds;
	}
}



