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
@MapperScan(basePackages = "com.zh.springbootmultidatasource.dao.test2",sqlSessionTemplateRef  = "test2SqlSessionTemplate")
public class Test2DataSourceConfig {

    @Value("${mybatis.test2.mapper-locations}")
    private String test2MapperLocations;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource test2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory test2SqlSessionFactory(@Qualifier("test2DataSource") DataSource test2DataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(test2DataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(test2MapperLocations));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate test2SqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory test2SqlSessionFactory) {
        return new SqlSessionTemplate(test2SqlSessionFactory);
    }

}
