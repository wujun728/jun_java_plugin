package com.lin.config.mybatis;

import com.lin.config.datasource.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LinZhaoguan
 * Date: 2018-03-17
 * Time: 16:52
 */
@Slf4j
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = "com.lin.**.mapper")
public class MybatisConfiguration extends MybatisAutoConfiguration {

    @Resource
    private DataSource dynamicDataSource;

    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        log.debug("-------------------- 重载父类 sqlSessionFactory init ---------------------");
        return super.sqlSessionFactory(dynamicDataSource);
    }



}
