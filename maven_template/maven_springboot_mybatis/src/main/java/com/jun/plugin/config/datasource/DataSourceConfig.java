package com.jun.plugin.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name="master")
    @ConfigurationProperties("datasource.master")
    public DataSource master(){
        log.debug("-------------------- 主数据源 init ---------------------");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="slaveAlpha")
    @ConfigurationProperties("datasource.slave-alpha")
    public DataSource slaveAlpha(){
        log.debug("-------------------- 从数据源 init ---------------------");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DynamicDataSourceType.MASTER.name(), master());
        dataSourceMap.put(DynamicDataSourceType.SLAVEALPHA.name(), slaveAlpha());

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        // To put slave datasource keys into DataSourceContextHolder to load balance
        DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DynamicDataSourceType.MASTER.name());

        return dynamicRoutingDataSource;
    }


    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
