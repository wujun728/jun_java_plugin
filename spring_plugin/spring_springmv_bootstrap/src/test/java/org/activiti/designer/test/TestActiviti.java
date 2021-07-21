package org.activiti.designer.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestActiviti {

    private static final Logger log = LoggerFactory.getLogger(TestActiviti.class);

    /* 初始化创建activiti需要的24张表 */
    // Activiti 核心接口如下：需要关注的有三个：RepositoryService\RuntimeService\TaskService
    // RepositoryService 管理流程定义
    // RuntimeService 执行管理，包括启动、推进、删除流程实例等操作
    // TaskService 任务管理
    // HistoryService 历史管理（执行完的数据管理）
    // IdentityService 组织机构管理
    // FormService 一个可选服务，任务表单管理

    @Test
    public void createActivitiTables1() {
        ProcessEngineConfiguration process = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        process.setJdbcDriver("oracle.jdbc.OracleDriver");
        process.setJdbcUrl("jdbc:oracle:thin:@180.168.1.59:1521:xjh");
        process.setJdbcUsername("chinapay");
        process.setJdbcPassword("chinapay");

        // process.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);//先删除表再创建表
        // process.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);//不能自动创建表，必须要表存在
        process.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);// 如果表不存在，则自动创建表

        // 工作流的核心对象,ProcessEngine对象
        ProcessEngine engine = process.buildProcessEngine();
        System.err.println("ProcessEngine:" + engine);
        log.debug("ProcessEngine:{}", engine);
    }

    @Test
    public void createActivitiTables2() {
        ProcessEngine engine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        System.err.println("ProcessEngine:" + engine);
        log.debug("ProcessEngine:{}", engine);
    }

}
