package org.camunda.bpm.demo.process;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationService;
import org.camunda.bpm.webapp.impl.security.auth.Authentications;
import org.camunda.bpm.webapp.impl.security.auth.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class SimpleRestProcessHandlerImpl implements SimpleRestProcessHandler {
	private static final Logger logger = LoggerFactory.getLogger(SimpleRestProcessHandlerImpl.class);
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private CamundaRestfulApiClient camundaRestClient;
	
	@Autowired
	private IdentityService identityService;
	
	@Override
	public List<TaskDto>  simpleStartProcess(CommonProcessRequest procReq,HttpServletRequest request) throws Exception {
		AuthenticationService authenticationService = new AuthenticationService();
	    UserAuthentication authentication = (UserAuthentication) authenticationService.createAuthenticate("default", procReq.getStarter(), null, null);
	    logger.info("authentication--------->"+authentication.getName());
	    Authentications.revalidateSession(request, authentication);
		String processInstanceId = null;
		List<TaskDto> resultList = new ArrayList<TaskDto>();
		ProcessInstanceDto processInstance = null;
		
		Map<String, VariableValueDto> variables = procReq.getStartProcessInstanceDto().getVariables();
		variables = variables==null?new HashMap<String, VariableValueDto>():variables;
		
//		List<String> assList = new ArrayList(Arrays.asList("bx2","bx3"));
//		VariableValueDto assigneeList020 = new VariableValueDto();
//		assigneeList020.setType("Object");
//		assigneeList020.setValue(JSON.toJSONString(assList));
//		//assigneeList020.setValue(assList);
//		Map<String,Object> valueInfo = new HashMap<String,Object>();
//		valueInfo.put("serializationDataFormat",Variables.SerializationDataFormats.JSON.getName());
//		//valueInfo.put("serializationDataFormat", Variables.SerializationDataFormats.JAVA.getName());
//		valueInfo.put("objectTypeName", "java.util.ArrayList");
//		assigneeList020.setValueInfo(valueInfo);
////		
////		ObjectValue typedObjectValue = Variables
////				  .objectValue(assList)
////				  .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
////				  .create();
////		assigneeList020 = (VariableValueDto) typedObjectValue;
////		
//		//System.out.println("typedObjectValue--->"+JSON.toJSONString(typedObjectValue));
//		System.out.println("assigneeList020---->"+JSON.toJSONString(assigneeList020));
//		variables.put("assigneeList020", assigneeList020);
//		
//		procReq.getStartProcessInstanceDto().setVariables(variables);
		
		
		// 流程初始化
		if(StringUtils.isNotBlank(procReq.getProcessDefKey())){
			 //processInstance = runtimeService.startProcessInstanceByKey(pscCommonProcessRequest.getProcessDefKey(), variables);
			processInstance = camundaRestClient.startProcessByKey(procReq.getProcessDefKey(), JSON.toJSONString(procReq.getStartProcessInstanceDto()));
		}else{
			 //processInstance = runtimeService.startProcessInstanceById(pscCommonProcessRequest.getProcessDefId(), variables);
			processInstance = camundaRestClient.startProcessById(procReq.getProcessDefId(), JSON.toJSONString(procReq.getStartProcessInstanceDto()));
		}
		// 创建成功
		if ( processInstance !=null && StringUtils.isNotBlank(processInstance.getId())) {
			processInstanceId = processInstance.getId();
			List<TaskDto> taskList = simpleGetTasks(processInstanceId);
			logger.info(JSON.toJSONString(taskList));
			if(taskList !=null && taskList.size()==1){
				
				taskService.complete(taskList.get(0).getId());
				taskService.createComment(taskList.get(0).getId(), processInstanceId, "提交流程");
				resultList = simpleGetTasks(processInstanceId);
			}else{
				throw new Exception("获取提交任务失败："+taskList.size());
			}
		} else {
			throw new Exception("创建流程实例失败：");
		}
		return resultList;
	}
	
	public List<TaskDto> simpleGetTasks(String processInstId) throws Exception{
		List<TaskDto> resultList = new ArrayList<TaskDto>();
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstId).list();
		for(Task task : taskList) {
			TaskDto dto = new TaskDto();
			dto = TaskDto.fromEntity(task);
			resultList.add(dto);
		}
		return resultList;
	}
}
