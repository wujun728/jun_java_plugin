package com.zh.springbootatomikosjta.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zh.springbootatomikosjta.config.properties.Test1Config;
import com.zh.springbootatomikosjta.config.properties.Test2Config;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Configuration
@MapperScan(basePackages = "com.zh.springbootatomikosjta.dao.test2",sqlSessionTemplateRef  = "test2SqlSessionTemplate")
public class Test2DataSourceConfig {

    @Value("${mybatis.test2.mapper-locations}")
    private String test2MapperLocations;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public Test2Config test2Config(){
        return new Test2Config();
    }

    @Bean
    public DataSource test2DataSource(Test2Config test2Config) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(test2Config.getJdbcUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(test2Config.getUsername());
        mysqlXADataSource.setPassword(test2Config.getPassword());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("test2DataSource");
        return xaDataSource;
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

//    @Bean
//    public DataSourceTransactionManager test2TransactionManager(@Qualifier("test2DataSource") DataSource test2DataSource) {
//        return new DataSourceTransactionManager(test2DataSource);
//    }

}
