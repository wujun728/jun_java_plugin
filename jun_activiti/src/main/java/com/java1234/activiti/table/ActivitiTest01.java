package com.java1234.activiti.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest01 {

	/**
	 * ����Activiti��Ҫ��25��
	 */
	@Test
	public void testCreateTable(){
		ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration(); // ��ȡ������������
		pec.setJdbcDriver("com.mysql.jdbc.Driver"); // ��������
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/activiti5"); // �������ӵ�ַ
		pec.setJdbcUsername("root"); // �û���
		pec.setJdbcPassword("mysqladmin"); // ����
		
		/**
		 * ����ģʽ  true �Զ������͸��±�
		 */
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		// ��ȡ�����������
		ProcessEngine pe=pec.buildProcessEngine(); 
	}
	
	/**
	 * ����Activiti��Ҫ��25�� ʹ�������ļ�
	 */
	@Test
	public void testCreateTableWithXml(){
		 // ��������
	    ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	    // ��ȡ�����������
	    ProcessEngine processEngine=pec.buildProcessEngine();
	}
}
