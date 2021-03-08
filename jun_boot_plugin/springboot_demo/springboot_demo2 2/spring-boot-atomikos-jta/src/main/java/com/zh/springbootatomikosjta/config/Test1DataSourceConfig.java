package com.zh.springbootatomikosjta.config;

import com.zh.springbootatomikosjta.config.properties.Test1Config;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;

import java.sql.SQLException;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Configuration
@MapperScan(basePackages = "com.zh.springbootatomikosjta.dao.test1",sqlSessionTemplateRef  = "test1SqlSessionTemplate")
public class Test1DataSourceConfig {

    @Value("${mybatis.test1.mapper-locations}")
    private String test1MapperLocations;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public Test1Config test1Config(){
        return new Test1Config();
    }

    @Bean
    @Primary
    public DataSource test1DataSource(Test1Config test1Config) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(test1Config.getJdbcUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(test1Config.getUsername());
        mysqlXADataSource.setPassword(test1Config.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName("test1DataSource");
        xaDataSource.setXaDataSource(mysqlXADataSource);

        return xaDataSource;
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

//    @Bean
//    @Primary
//    public DataSourceTransactionManager test1TransactionManager(@Qualifier("test1DataSource") DataSource test1DataSource) {
//        return new DataSourceTransactionManager(test1DataSource);
//    }

}
