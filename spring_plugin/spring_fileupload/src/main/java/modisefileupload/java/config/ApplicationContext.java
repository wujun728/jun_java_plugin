package modisefileupload.java.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import modisefileupload.java.service.UserService;
import modisefileupload.java.service.impl.UserServiceImpl;

@EnableJpaRepositories(basePackages={"modisefileupload.java.dao"})
@EnableTransactionManagement
@Configuration
public class ApplicationContext {
	
	@Autowired
	private Environment environment;
	
	
	
	/**
	 * setting up the required Beans
	 */
	
	@Bean
	public UserService userService(){
		return new UserServiceImpl();
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.driverClass"));
		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		
		return dataSource;
	}
	
	public DatabasePopulator databasePopulator(){
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.setContinueOnError(true);
		databasePopulator.addScript(new ClassPathResource("test-data.sql"));
		return databasePopulator;
	}
	
	/**
	 * we need the entity manager for handling Spring transactions
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		
		DatabasePopulatorUtils.execute(databasePopulator(), dataSource());
		
		return jpaTransactionManager;	
	}
	
	/**
	 * We need the multipartResolver for uploading images
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxUploadSize(1024000);
		return resolver;
	}
	
	/**
	 * Specifying the JpaVendorAdapter implementation for the desired JPA provider.
	 * This will initialize appropriate defaults for the given provider, 
	 * such as persistence provider class and JpaDialect, unless locally overridden in this FactoryBean.
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		
		hibernateJpaVendorAdapter.setShowSql(true);
		
		return hibernateJpaVendorAdapter;
	}
	/**
	 * 
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		
		entityManagerFactory.setDataSource(dataSource());
		
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		
		
		entityManagerFactory.setPackagesToScan("modisefileupload.java.domain");
		
		Properties jpaProperties = new Properties();
		
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		
		entityManagerFactory.setJpaProperties(jpaProperties);
		
		return entityManagerFactory;
	}
	
	/**
	 * end of setting up MySQL
	 */

}
