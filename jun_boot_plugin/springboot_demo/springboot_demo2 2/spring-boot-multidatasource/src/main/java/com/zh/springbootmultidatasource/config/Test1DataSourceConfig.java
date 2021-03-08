package com.zh.springbootmultidatasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Configuration
@MapperScan(basePackages = "com.zh.springbootmultidatasource.dao.test1",sqlSessionTemplateRef  = "test1SqlSessionTemplate")
public class Test1DataSourceConfig {

    @Value("${mybatis.test1.mapper-locations}")
    private String test1MapperLocations;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource test1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource test1DataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(test1DataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(test1MapperLocations));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate test1SqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory test1SqlSessionFactory) {
        return new SqlSessionTemplate(test1SqlSessionFactory);
    }

}
