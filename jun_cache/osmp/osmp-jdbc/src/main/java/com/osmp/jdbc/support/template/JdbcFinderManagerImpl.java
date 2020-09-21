/*   
 * Project: OSMP
 * FileName: JdbcFinderManagerImpl.java
 * version: V1.0
 */
package com.osmp.jdbc.support.template;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.druid.pool.DruidDataSource;
import com.osmp.jdbc.define.DBType;
import com.osmp.jdbc.define.tool.JdbcFinderHolder;
import com.osmp.jdbc.parse.SqlFormatter;
import com.osmp.jdbc.service.JdbcTemplateManager;
import com.osmp.jdbc.service.SqlStatementManager;
import com.osmp.jdbc.support.JdbcFinderManager;
import com.osmp.jdbc.support.JdbcTemplate;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 下午2:50:33
 */

public class JdbcFinderManagerImpl implements JdbcFinderManager, InitializingBean {
	private static final Object lock = new Object();
	private Logger logger = LoggerFactory.getLogger(JdbcFinderManager.class);
	private JdbcTemplateManager jdbcTemplateManager;
	private SqlStatementManager sqlStatementManager;
	private SqlFormatter sqlFormatter = new SqlFormatter();

	public void setJdbcTemplateManager(JdbcTemplateManager jdbcTemplateManager) {
		this.jdbcTemplateManager = jdbcTemplateManager;
	}

	public void setSqlStatementManager(SqlStatementManager sqlStatementManager) {
		this.sqlStatementManager = sqlStatementManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(jdbcTemplateManager, "jdbcTemplateManager未初始化...");
		Assert.notNull(sqlStatementManager, "sqlStatementManager未初始化...");
		// 设置JdbcFinderManager
		JdbcFinderHolder.set(this);
	}

	@Override
	public JdbcTemplate findJdbcTemplate(String catalog) {
		if (catalog == null)
			return null;
		return jdbcTemplateManager.getJdbcTemplate(catalog);
	}

	@Override
	public String getQueryStringByStatementKey(String statementName, Map<String, Object> parasMap) {
		String result = null;
		if (sqlStatementManager != null) {
			result = sqlStatementManager.getStatementByName(statementName, parasMap);
			result = (result != null) ? result.trim() : null;
			if (result != null && logger.isDebugEnabled()) {
				logger.debug("获取sql:" + statementName + "\n" + sqlFormatter.format(result));
			}
		}
		return result;
	}

	@Override
	public String getQueryStringByStatementKey(String statementName) {
		String result = null;
		if (sqlStatementManager != null) {
			result = sqlStatementManager.getStatementByName(statementName);
			result = (result != null) ? result.trim() : null;
			if (logger.isDebugEnabled()) {
				logger.debug("获取sql:" + statementName + "\n" + sqlFormatter.format(result));
			}
		}
		return result;
	}

	@Override
	public JdbcTemplate findJdbcTemplate(String dbIp, String dbName, String user, String pass) {
		if (dbIp == null || dbName == null)
			return null;
		String catalog = generateCatalog(dbIp, dbName);
		JdbcTemplate template = jdbcTemplateManager.getJdbcTemplate(catalog);
		if (template != null)
			return template;

		synchronized (lock) {
			if (jdbcTemplateManager.getJdbcTemplate(catalog) == null) {
				DataSource ds = createFzDataSource(dbIp, dbName, user, pass);
				if (ds == null)
					return null;
				jdbcTemplateManager.addDataSource(ds, catalog, DBType.SQLSERVER);
			}
		}
		return jdbcTemplateManager.getJdbcTemplate(catalog);
	}

	private DataSource createFzDataSource(String dbIp, String dbName, String user, String pass) {
		String url = "jdbc:jtds:sqlserver://" + dbIp + ":1433/" + dbName
				+ ";sendStringParametersAsUnicode=false;socketTimeout=120";
		DruidDataSource ds = new DruidDataSource();
		ds.setName(generateCatalog(dbIp, dbName));
		ds.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		ds.setInitialSize(100);
		ds.setMaxActive(200);
		ds.setMinIdle(100);
		ds.setMaxWait(15000);
		ds.setValidationQuery("select 1");
		ds.setTimeBetweenEvictionRunsMillis(100000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setRemoveAbandonedTimeoutMillis(30000);
		try {
			ds.setFilters("stat");
			ds.init();
		} catch (SQLException e) {
			logger.error("初始化ol负载数据源(dbIp:" + dbIp + ",dbName:" + dbName + ")失败", e);
			return null;
		}
		return ds;
	}

	private String generateCatalog(String ip, String dbName) {
		if (ip == null || dbName == null)
			return "";
		return dbName.trim() + "(" + ip.trim() + ")";
	}

}
