package com.jun.plugin.framework.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jun.plugin.common.utils.DataSourceComposeUtils;
import com.jun.plugin.common.utils.text.Convert;
import com.jun.plugin.framework.aspectj.lang.enums.DataSourceType;
import com.jun.plugin.framework.datasource.DynamicDataSource;
import com.jun.plugin.framework.datasource.DynamicDataSourceUtil;
import com.jun.plugin.project.domain.SysDataSource;

/**
 * druid 配置多数据源
 * 
 * @author ruoyi
 */
@Configuration
public class DruidConfig {
	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Bean()
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource masterDataSource() {
		SQLiteDataSource dataSource = new SQLiteDataSource();
//		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(dataSourceProperties.getUrl());
		return dataSource;
	}

	@Bean(name = "dynamicDataSource")
	@Primary
	public DynamicDataSource dataSource(DataSource masterDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
		// 从数据库中直接读取数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(masterDataSource);
		List<SysDataSource> dsList = jdbcTemplate.query("select * from sys_data_source", new Object[] {},
				new BeanPropertyRowMapper<SysDataSource>(SysDataSource.class));
		if (dsList != null && dsList.size() > 0) {
			for (SysDataSource item : dsList) {
				DruidDataSource dds = DataSourceComposeUtils.composeDruidDataSource(item);
				targetDataSources.put(DataSourceType.SLAVE.name() + Convert.toStr(item.getId()), dds);
			}
		}
		DynamicDataSourceUtil.setTargetDataSources(targetDataSources);
		return new DynamicDataSource(masterDataSource, targetDataSources);
	}

	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(masterDataSource());
		return sqlSessionFactoryBean.getObject();
	}
}