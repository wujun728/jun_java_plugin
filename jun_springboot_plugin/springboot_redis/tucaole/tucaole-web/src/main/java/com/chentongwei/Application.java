package com.chentongwei;

import com.chentongwei.controller.system.SysLogInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 启动类
 */
//扫描servlet需要用
@ServletComponentScan
// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableTransactionManagement
//mapper 接口类扫描包配置
@MapperScan("com.chentongwei.core.*.dao")
@PropertySource(value = {"classpath:system.properties"}, encoding="utf-8")
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Application extends WebMvcConfigurerAdapter {
	private static final Logger LOG = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		LOG.info("tucaole-web启动成功！");
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
