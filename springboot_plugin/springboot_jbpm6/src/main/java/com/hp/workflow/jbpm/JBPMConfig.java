package com.hp.workflow.jbpm;

import org.drools.core.impl.EnvironmentImpl;
import org.jbpm.services.task.HumanTaskServiceFactory;
import org.kie.api.runtime.Environment;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.task.api.InternalTaskService;
import org.kie.internal.task.api.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

/**
 * Created by JACK on 2017/4/19.
 */
@Configuration
@DependsOn("transactionManager")
public class JBPMConfig {
    @Autowired
    @Qualifier(value = "entityManagerFactoryWorkflow")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager transactionManager;
    @Autowired
    private UserGroupCallback userGroup;
    @Autowired
    private UserInfo userInfo;

    public Environment createEnvironment(){
        Environment environment = new EnvironmentImpl();
        environment.set("org.kie.transaction.TransactionManager",transactionManager);
        environment.set("org.kie.api.persistence.jpa.EntityManagerFactory",entityManagerFactory);

        return environment;
    }

    @Bean(name="taskService")
    public InternalTaskService buildTaskService(){
        return (InternalTaskService) HumanTaskServiceFactory.newTaskServiceConfigurator()
                .entityManagerFactory(entityManagerFactory)
                .environment(createEnvironment())
//                .userGroupCallback(userGroup)
//                .userInfo(userInfo)
                .getTaskService();
    }
}
