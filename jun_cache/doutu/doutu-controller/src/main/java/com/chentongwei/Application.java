package com.chentongwei;

import javax.sql.DataSource;

import com.chentongwei.controller.common.SysLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 启动类
 * 
 * @author TongWei.Chen 2017年5月14日13:44:44
 */
//mapper 接口类扫描包配置
@MapperScan("com.chentongwei.dao")
//扫描servlet需要用
@ServletComponentScan
// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
public class Application extends WebMvcConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		LOG.info("SpringBoot启动成功！");
	}
	/**
	 * 其中DataSource框架会为我们自动注入
	 *
	 * @param dataSource 数据源
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	SysLogInterceptor logInterceptor() {
		return new SysLogInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
	}
}
