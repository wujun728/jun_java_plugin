package com.java1234.activiti.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest01 {

	 
	@Test
	public void testCreateTable(){
		ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();  
		pec.setJdbcDriver("com.mysql.jdbc.Driver"); 
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/activiti5"); 
		pec.setJdbcUsername("root"); // �û���
		pec.setJdbcPassword("mysqladmin"); // ����
		 
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		ProcessEngine pe=pec.buildProcessEngine(); 
	}
	
	 
	@Test
	public void testCreateTableWithXml(){
	    ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	    ProcessEngine processEngine=pec.buildProcessEngine();
	}
}
