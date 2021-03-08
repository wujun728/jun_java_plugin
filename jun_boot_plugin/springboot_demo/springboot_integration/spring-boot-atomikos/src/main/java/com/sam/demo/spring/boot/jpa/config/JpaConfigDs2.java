package com.sam.demo.spring.boot.jpa.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
// 指定数据源1的Repository路径，数据源1的entityManagerFactory，事务是公共事务
@EnableJpaRepositories(basePackages = "com.sam.demo.spring.boot.jpa.secondary", entityManagerFactoryRef = "entityManagerFactory2", transactionManagerRef = "transactionManager")
public class JpaConfigDs2 {

	@Value("${spring.datasource.secondary.driver-class-name}")
	String driverClass;
	@Value("${spring.datasource.secondary.url}")
	String url;
	@Value("${spring.datasource.secondary.username}")
	String userName;
	@Value("${spring.datasource.secondary.password}")
	String passWord;

	@Bean(name = "dataSource2", initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(url);
		mysqlXaDataSource.setPassword(passWord);
		mysqlXaDataSource.setUser(userName);
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("dataSource2");
		xaDataSource.setMinPoolSize(10);
		xaDataSource.setPoolSize(10);
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setBorrowConnectionTimeout(60);
		xaDataSource.setReapTimeout(20);
		xaDataSource.setMaxIdleTime(60);
		xaDataSource.setMaintenanceInterval(60);

		return xaDataSource;
	}

	@Bean(name = "jpaVendorAdapter2")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		adapter.setGenerateDdl(true);
		return adapter;
	}

	@Bean(name = "entityManagerFactory2")
	@DependsOn({ "atomikosJtaPlatfom" }) // 需要先注册atomikosJtaPlatfom
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		// entity package
		entityManager.setPackagesToScan("com.sam.demo.spring.boot.jpa.secondary");
		entityManager.setJtaDataSource(dataSource());

		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");

		// jta设置
		properties.put("hibernate.current_session_context_class", "jta");
		// 这里指定我们自己创建的AtomikosJtaPlatfom
		properties.put("hibernate.transaction.jta.platform", "com.sam.demo.spring.boot.jpa.config.AtomikosJtaPlatfom");
		entityManager.setJpaProperties(properties);
		return entityManager;

	}
}
