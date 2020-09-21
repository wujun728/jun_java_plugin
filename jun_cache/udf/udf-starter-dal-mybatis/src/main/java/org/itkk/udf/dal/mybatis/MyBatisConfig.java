package org.itkk.udf.dal.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.itkk.udf.dal.mybatis.plugin.pagequery.PageInterceptor;
import org.itkk.udf.dal.mybatis.plugin.tablesplit.DefaultStrategy;
import org.itkk.udf.dal.mybatis.plugin.tablesplit.IStrategy;
import org.itkk.udf.dal.mybatis.plugin.tablesplit.QueryTableSplitInterceptor;
import org.itkk.udf.dal.mybatis.plugin.tablesplit.UpdateTableSplitInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * MyBatisConfig
 */
@Configuration
public class MyBatisConfig {

    /**
     * 分页插件
     *
     * @return Interceptor
     */
    @Bean
    @ConditionalOnProperty(value = "org.itkk.dal.mybatis.properties.enablePageInterceptor", matchIfMissing = true)
    public Interceptor pageInterceptor() {
        return new PageInterceptor();
    }

    /**
     * 描述 : 分表插件(此插件一定要在所有插件之前执行)
     *
     * @param strategy strategy
     * @return 分表插件
     */
    @Bean
    @Order(1)
    @ConditionalOnProperty(value = "org.itkk.dal.mybatis.properties.tableSplitInterceptor", matchIfMissing = true)
    public Interceptor queryTableSplitInterceptor(IStrategy strategy) {
        return new QueryTableSplitInterceptor(strategy);
    }

    /**
     * 描述 : 分表插件(此插件一定要在所有插件之前执行)
     *
     * @param strategy strategy
     * @return 分表插件
     */
    @Bean
    @Order(0)
    @ConditionalOnProperty(value = "org.itkk.dal.mybatis.properties.tableSplitInterceptor", matchIfMissing = true)
    public Interceptor updateTableSplitInterceptor(IStrategy strategy) {
        return new UpdateTableSplitInterceptor(strategy);
    }

    /**
     * 描述 : 分表策略
     *
     * @return strategy
     */
    @Bean
    @ConditionalOnProperty(value = "org.itkk.dal.mybatis.properties.tableSplitInterceptor", matchIfMissing = true)
    public IStrategy strategy() {
        return new DefaultStrategy();
    }

}
