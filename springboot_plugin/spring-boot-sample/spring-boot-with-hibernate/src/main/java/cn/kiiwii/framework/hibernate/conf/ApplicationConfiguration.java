package cn.kiiwii.framework.hibernate.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by zhong on 2017/1/5.
 */

@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class,HibernatePropertes.class})
@ConditionalOnMissingClass
public class ApplicationConfiguration {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;
    @Autowired
    private HibernatePropertes hibernatePropertes;

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource setDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidDataSourceProperties.getDriverClassName());
        dataSource.setUrl(druidDataSourceProperties.getUrl());
        dataSource.setUsername(druidDataSourceProperties.getUsername());
        dataSource.setPassword(druidDataSourceProperties.getPassword());
        try {
            dataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }


    @Bean(name = "sessionFactory")
    @ConditionalOnMissingBean
    public LocalSessionFactoryBean setSessionFactory(DataSource dataSource){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql",String.valueOf(hibernatePropertes.isShowSql()));
        properties.setProperty("hibernate.use_sql_comments",String.valueOf(hibernatePropertes.getUseSqlComments()));
        properties.setProperty("hibernate.dialect",String.valueOf(hibernatePropertes.getDialect()));
        properties.setProperty("hibernate.use_outer_join",String.valueOf(hibernatePropertes.isUseOuterJoin()));
        properties.setProperty("hibernate.query.factory_class",String.valueOf(hibernatePropertes.getQuery().getFactoryClass()));
        properties.setProperty("hibernate.format_sql",String.valueOf(hibernatePropertes.isShowSql()));
        properties.setProperty("hibernate.hbm2ddl.auto",String.valueOf(hibernatePropertes.getHbm2ddl()));
        properties.setProperty("hibernate.query.substitutions",String.valueOf(hibernatePropertes.getQuery().getSubstitutions()));
        properties.setProperty("hibernate.query.jpaql_strict_compliance",String.valueOf(hibernatePropertes.getQuery().isJpaqlStrictCompliance()));
        properties.setProperty("hibernate.jdbc.fetch_size",String.valueOf(hibernatePropertes.getJdbc().getFetchSize()));
        properties.setProperty("hibernate.jdbc.batch_size",String.valueOf(hibernatePropertes.getJdbc().getBatchSize()));
        properties.setProperty("hibernate.jdbc.sql_exception_converter",String.valueOf(hibernatePropertes.getJdbc().getSqlExceptionConverter()));
        properties.setProperty("hibernate.jdbc.wrap_result_sets",String.valueOf(hibernatePropertes.getJdbc().isWrapResultSets()));
        properties.setProperty("hibernate.jdbc.use_streams_for_binary",String.valueOf(hibernatePropertes.getJdbc().isUseStreamsForBinary()));
        properties.setProperty("hibernate.jdbc.use_scrollable_resultset",String.valueOf(hibernatePropertes.getJdbc().isUseScrollableResultset()));
        properties.setProperty("hibernate.jdbc.factory_class",String.valueOf(hibernatePropertes.getJdbc().getFactoryClass()));
        properties.setProperty("hibernate.jdbc.use_get_generated_keys",String.valueOf(hibernatePropertes.getJdbc().isUseGetGeneratedKeys()));
        properties.setProperty("hibernate.cache.jndi",String.valueOf(hibernatePropertes.getCache().isJndi()));
        properties.setProperty("hibernate.cache.use_second_level_cache",String.valueOf(hibernatePropertes.getCache().isUseSecondLevelCache()));
        properties.setProperty("hibernate.cache.use_structured_entries",String.valueOf(hibernatePropertes.getCache().isUseStructuredEntries()));
        properties.setProperty("hibernate.cache.use_query_cache",String.valueOf(hibernatePropertes.getCache().isUseStructuredEntries()));
        properties.setProperty("hibernate.cache.use_minimal_puts",String.valueOf(hibernatePropertes.getCache().isUseQueryCache()));
        properties.setProperty("hibernate.cache.provider_class",String.valueOf(hibernatePropertes.getCache().getProviderClass()));
        properties.setProperty("hibernate.cache.provider_configuration_file_resource_path",String.valueOf(hibernatePropertes.getCache().getProviderConfigurationFileResourcePath()));
        properties.setProperty("hibernate.cache.query_cache_factory",String.valueOf(hibernatePropertes.getCache().getQueryCacheFactory()));
        properties.setProperty("hibernate.cache.region_prefix",String.valueOf(hibernatePropertes.getCache().getRegionPrefix()));

        sessionFactory.setHibernateProperties(properties);
        sessionFactory.setPackagesToScan(hibernatePropertes.getPackagesToScan());
        sessionFactory.setDataSource(dataSource);
        return sessionFactory;
    }

    @Bean(name = "transactionManager")
    @ConditionalOnMissingBean
    public HibernateTransactionManager setHibernateTransactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

}
