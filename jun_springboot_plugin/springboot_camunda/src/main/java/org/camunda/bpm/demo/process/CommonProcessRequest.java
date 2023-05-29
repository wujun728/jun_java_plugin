package org.camunda.bpm.demo.process;

import java.util.Map;

import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程提交参数对象
 * @author liushaohua23
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="CommonProcessRequest-流程提交参数对象")
public class CommonProcessRequest {
	@ApiModelProperty(value="流程定义ID")
	private String	processDefId;
	
	@ApiModelProperty(value="流程定义Key")
	private String	processDefKey;
	
	public String getProcessDefKey() {
		return processDefKey;
	}
	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}
	@ApiModelProperty(value="启动者")
	private String	starter;
	
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	@ApiModelProperty(value="流程标题")
	private	String	title;
	
	
	private StartProcessInstanceDto startProcessInstanceDto;
   
	public static String REJECT_TO_START ="1";
	public static String REJECT_TO_LAST ="2";
	public static String REJECT_TO_TARGET ="3";
   
	public static String WITHDRAW_TO_START ="1";
	public static String WITHDRAW_TO_TARGET ="2";
	
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public StartProcessInstanceDto getStartProcessInstanceDto() {
		return startProcessInstanceDto;
	}
	public void setStartProcessInstanceDto(StartProcessInstanceDto startProcessInstanceDto) {
		this.startProcessInstanceDto = startProcessInstanceDto;
	}
}
