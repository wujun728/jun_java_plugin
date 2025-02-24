package com.lxhc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.lxhc.model.Emp;
import com.lxhc.model.EmpBalance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * Created on 2020/5/19.
 *
 * @author 拎小壶冲
 */
@Configuration
public class JfinalActiveRecordConfig {
    /**
     * 主数据源名称
     */
    private static final String MAIN_DATA_SOURCE_CONFIG = "main";

    /**
     * 业务数据源名称
     */
    private static final String BIZ_DATA_SOURCE_CONFIG = "biz";

    @Bean
    @ConfigurationProperties("spring.datasource.main")
    public DruidDataSource masterDataSource(){
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.biz")
    public DruidDataSource bizDataSource(){
        return new DruidDataSource();
    }

    /**
     * 主数据源
     * @return
     */
    @Bean
    public ActiveRecordPlugin initMainActiveRecord() {
        ActiveRecordPlugin arp = new ActiveRecordPlugin(MAIN_DATA_SOURCE_CONFIG, masterTransactionAwareDataSourceProxy());

        arp.addMapping("emp", Emp.class);
        arp.addMapping("emp_balance", EmpBalance.class);

        arp.start();

        return arp;
    }

    /**
     * 业务数据源
     * @return
     */
    @Bean
    public ActiveRecordPlugin initBizActiveRecord() {
        ActiveRecordPlugin arp = new ActiveRecordPlugin(BIZ_DATA_SOURCE_CONFIG, bizTransactionAwareDataSourceProxy());

//        arp2.addMapping("emp", Emp.class);

        arp.start();

        return arp;
    }

    /**
     * 设置数据源代理
     */
    @Bean
    public TransactionAwareDataSourceProxy masterTransactionAwareDataSourceProxy() {
        TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = new TransactionAwareDataSourceProxy();
        transactionAwareDataSourceProxy.setTargetDataSource(masterDataSource());
        return transactionAwareDataSourceProxy;
    }

    @Bean
    public TransactionAwareDataSourceProxy bizTransactionAwareDataSourceProxy() {
        TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = new TransactionAwareDataSourceProxy();
        transactionAwareDataSourceProxy.setTargetDataSource(bizDataSource());
        return transactionAwareDataSourceProxy;
    }

    /**
     * 设置事务管理
     */
    @Bean(name="mainDataSourceTransactionManager")
    public DataSourceTransactionManager masterDataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(masterTransactionAwareDataSourceProxy());
        return dataSourceTransactionManager;
    }

    @Bean(name="bizDataSourceTransactionManager")
    public DataSourceTransactionManager bizDataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(bizTransactionAwareDataSourceProxy());
        return dataSourceTransactionManager;
    }
}
