package com.sam.demo.spring.boot.jpa.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;


/**
 * 指定数据源的Repository路径
 * 数据源的entityManagerFactory
 * 事务是公共事务
 * @author Wujun
 *
 */
@EnableJpaRepositories(basePackages = "com.sam.demo.spring.boot.jpa.primary", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaConfigDs1 {

	@Value("${spring.datasource.primary.driver-class-name}")
	String driverClass;
	@Value("${spring.datasource.primary.url}")
	String url;
	@Value("${spring.datasource.primary.username}")
	String userName;
	@Value("${spring.datasource.primary.password}")
	String passWord;

	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource dataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(url);
		mysqlXaDataSource.setPassword(passWord);
		mysqlXaDataSource.setUser(userName);
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("dataSource");
		xaDataSource.setMinPoolSize(10);
		xaDataSource.setPoolSize(10);
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setBorrowConnectionTimeout(60);
		xaDataSource.setReapTimeout(20);
		xaDataSource.setMaxIdleTime(60);
		xaDataSource.setMaintenanceInterval(60);

		return xaDataSource;
	}

	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		adapter.setGenerateDdl(true);
		return adapter;
	}

	@Bean(name = "entityManagerFactory")
	@DependsOn({ "atomikosJtaPlatfom" }) // 需要先注册atomikosJtaPlatfom
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		// entity package
		entityManager.setPackagesToScan("com.sam.demo.spring.boot.jpa.primary");
		entityManager.setJtaDataSource(dataSource());

		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		
		// jta设置
		properties.put("hibernate.current_session_context_class", "jta");
		properties.put("hibernate.transaction.jta.platform", "com.sam.demo.spring.boot.jpa.config.AtomikosJtaPlatfom");
		entityManager.setJpaProperties(properties);
		return entityManager;

	}
}
