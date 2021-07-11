package com.kenfo.service;

import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

@Service
public interface JBPMService {
	public String deployZipNew(String resourceName);		//发布这条流程定义
	
	public String deployNew(String resourceZipName);		//发布这条流程定义
	
	public ProcessInstance startPI(String staffName,String id);		//开始一个流程实例
	
	public List<Task> getTasksList(String assignee);	//经理或者老板获得所有任务列表
	
	public void checkReply(String taskId,int result,String name);		//经理完成任务，返回一个result结果
	
	public void addReply(String taskId);			//按照任务Id完成该任务
	
	public List<ProcessDefinition> getAllPd();		//获得所有流程定义
	
	
}
