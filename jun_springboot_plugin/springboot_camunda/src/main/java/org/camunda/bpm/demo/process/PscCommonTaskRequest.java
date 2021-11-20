package org.camunda.bpm.demo.process;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务提交参数对象
 * @author liushaohua23
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="PscCommonProcessRequest-流程提交参数对象")
public class PscCommonTaskRequest {
	@ApiModelProperty(value="流程实例ID")
	private String	processInstId;
	
	@ApiModelProperty(value="流程定义ID")
	private String processDefId;
	
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	@ApiModelProperty(value="任务ID")
	private String	taskId;
	
	@ApiModelProperty(value="任务Key")
	private String	taskDefKey;
 
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	@ApiModelProperty(value="任务处理人")
	private String	userId;
	
	@ApiModelProperty(value="流程变量键值对")
	private Map<String, Object> variables;
	
	@ApiModelProperty(value="任务变量键值对")
	private Map<String, Object> localVariables;
	
	@ApiModelProperty(value="下一节点任务处理人")
	private String	nextUserId;
	
	@ApiModelProperty(value="驳回类型，1：起草节点，2：上一节点，3：目标节点")
	private String	rejectType;
	
	public String getRejectType() {
		return rejectType;
	}
	public void setRejectType(String rejectType) {
		this.rejectType = rejectType;
	}
	@ApiModelProperty(value="目标节点Id")
	private String	toActId;
	
	@ApiModelProperty(value="跳转类型，1：往前跳转，2：往回跳转")
	private String	jumpType;
    
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	public String getToActId() {
		return toActId;
	}
	public void setToActId(String toActId) {
		this.toActId = toActId;
	}
	public String getNextUserId() {
		return nextUserId;
	}
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}
	public static String REJECT_TO_START ="1";
	public static String REJECT_TO_LAST ="2";
	public static String REJECT_TO_TARGET ="3";
	
   
	public static String WITHDRAW_TO_START ="1";
	public static String WITHDRAW_TO_TARGET ="2";
	
	public static String JUMP_FORWARD ="1";
	public static  String JUMP_BACK ="2";
	
 
	public String getProcessInstId() {
		return processInstId;
	}
	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, Object> getLocalVariables() {
		return localVariables;
	}
	public void setLocalVariables(Map<String, Object> localVariables) {
		this.localVariables = localVariables;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
