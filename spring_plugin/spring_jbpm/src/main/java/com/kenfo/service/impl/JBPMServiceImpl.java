package com.kenfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.inject.internal.Maps;
import com.kenfo.service.JBPMService;
import com.kenfo.util.JBPMUtil;

@Service("JBPMService")
public class JBPMServiceImpl implements JBPMService {
	
	private JBPMUtil jBPMUtil = new JBPMUtil(Configuration.getProcessEngine());

	
	//发布流程
	public String  deployNew(String resourceName) {
		return jBPMUtil.deployNew(resourceName);
	}
	

	//发布流程
	public String  deployZipNew(String resourceZipName) {
		return jBPMUtil.deployZipNew(resourceZipName);
	}

	//开始流程实例
	public ProcessInstance startPI(String staffName,String id) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("staff", staffName);
		return jBPMUtil.startPIById(id, map);
		
	}

	//由普通员工发起申请
	public void addReply(String taskId) {
		jBPMUtil.completeTask(taskId);
	}
	
	//由manager或者boss完成会签
	public void checkReply(String taskId, int result,String name) {
		System.out.println("name="+name);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if(result==1){
			map.put("RESULT", "同意");
		}else{
			map.put("RESULT", "不同意");
		}
		
		jBPMUtil.signalExecutionById(jBPMUtil.getTask(taskId).getExecutionId(), map);
	}


	public List<Task> getTasksList(String assignee) {
		
		return jBPMUtil.findPersonalTasks(assignee);
	}


	public List<ProcessDefinition> getAllPd() {
		return jBPMUtil.getAllPdList();
	}

	
}
