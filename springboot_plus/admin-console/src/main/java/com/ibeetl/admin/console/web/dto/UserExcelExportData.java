package com.ibeetl.admin.console.web.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.annotation.Dict;

/**
 * excel导出需要的模板数据
 * @author Wujun
 *
 */
public class UserExcelExportData  {
	protected Long id;
	private String code;
	private String name;
	private String orgText;
	private String password;
	private String stateText;
	private String jobType1Text;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgText() {
		return orgText;
	}
	public void setOrgText(String orgText) {
		this.orgText = orgText;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStateText() {
		return stateText;
	}
	public void setStateText(String stateText) {
		this.stateText = stateText;
	}
	public String getJobType1Text() {
		return jobType1Text;
	}
	public void setJobType1Text(String jobType1Text) {
		this.jobType1Text = jobType1Text;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
