package com.kenfo.util;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

/**
 * jBPM4.4工具类
 * 
 * @author kenfo
 * 
 */
public class JBPMUtil {
	
	private ProcessEngine processEngine;
	private RepositoryService repositoryService = null;
	private ExecutionService executionService = null;
	private TaskService taskService = null;
	private HistoryService historyService = null;
	private ManagementService managementService = null;
	
	public JBPMUtil(){
		
	}
	public JBPMUtil(ProcessEngine processEngine) {
		this.processEngine = processEngine;
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		managementService = processEngine.getManagementService();
	}

	

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
		System.out.println("processEngine="+processEngine);
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		managementService = processEngine.getManagementService();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	
	
	
	/**
	 * 部署新流程定义
	 * @param resourceName
	 * @return 返回流程定义id
	 */
	public String deployNew(String resourceName) {
		return repositoryService.createDeployment().addResourceFromClasspath(
				resourceName).deploy();
	}

	
	/**
	 * 部署新流程定义(zip)
	 * @param resourceName
	 * @return 返回流程定义id
	 */
	public String deployZipNew(String resourceZipName){
		ZipInputStream zis = new ZipInputStream(this.getClass().getResourceAsStream(resourceZipName));
		
		return repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();	
		
	}
	
	
	/**
	 * 开始一个流程实例
	 * @param id
	 * @param map
	 * @return
	 */
	
	public ProcessInstance startPIById(String id,Map<String,Object> map){	
		
		return executionService.startProcessInstanceById(id, map);
		
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param map
	 */
	
	public void completeTask(String taskId,Map map){
		
		taskService.completeTask(taskId, map);	
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 */
	public void completeTask(String taskId){
		taskService.completeTask(taskId);
	}
	
	
	/**
	 * 将任务流转到指定名字的流程outcome中去
	 * @param taskId
	 * @param outcome
	 */
	public void completeTask(String taskId,String outcome){
		taskService.completeTask(taskId, outcome);
	}

	/**
	 * 获得所有发布了的流程
	 * @return
	 */
	public List<ProcessDefinition> getAllPdList(){
		return repositoryService.createProcessDefinitionQuery().list();
	}
	
	/**
	 * 获得所有流程实例
	 * @return
	 */
	public List<ProcessInstance> getAllPiList(){
		return executionService.createProcessInstanceQuery().list();
	}
	
	/**
	 * 根据流程实例Id，即executionId获取指定的变量值
	 * @param executionId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByexecutionId(String executionId,String variableName){
		return executionService.getVariable(executionId, variableName);
	}
	
	
	/**
	 * 根据任务id，即taskId获取指定变量值
	 * @param taskId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByTaskId(String taskId,String variableName){
		return taskService.getVariable(taskId, variableName);
	}
	
	/**
	 * 获取指定用户名字的任务
	 * @param userName
	 * @return
	 */
	public List<Task> findPersonalTasks(String userName){
		return taskService.findPersonalTasks(userName);
	}
	
	/**
	 * 根据任务id获取任务
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId) {
		return taskService.getTask(taskId);
		
	}
	
	

	/**
	 * 级联删除流程定义，直接删除该流程定义下的所有实例
	 * 
	 * @param deploymentId  流程定义id
	 */
	public void deleteDeploymentCascade(String deploymentId) {
		repositoryService.deleteDeploymentCascade(deploymentId);
	}
	
	/**
	 * 执行等待中的execution
	 * @param id 	流程实例id
	 * @param map	参数
	 */

	public void signalExecutionById(String id,Map<String,?> map){
		executionService.signalExecutionById(id, map);
		
	}

	

	

}
