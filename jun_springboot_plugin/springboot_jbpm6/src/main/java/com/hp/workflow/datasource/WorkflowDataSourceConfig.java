package com.hp.workflow.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class WorkflowDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("workflowDS")
    private DataSource WorkflowDS;

    @Bean(name = "workflowDS")
    @Qualifier("workflowDS")
    @Primary
    @ConfigurationProperties(prefix = "dragon.workflow.datasource")
    public DataSource workflowDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryWorkflow")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryWorkflow(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(WorkflowDS)
                .properties(jpaProperties.getProperties())
                .packages("com.hp.workflow.domain.entities")
                .persistenceUnit("WorkflowPersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManagerWorkflow(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryWorkflow(builder).getObject());
    }
}
