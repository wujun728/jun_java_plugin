package com.ibeetl.admin.core.entity;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibeetl.admin.core.util.ValidateConfig;


public class CoreAudit extends BaseEntity  {
	
	@SeqID(name = "ORACLE_AUDIT_SEQ_NAME")
	@AutoID
	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	protected Long id;

    //删除标识
    @JsonIgnore
    protected Integer delFlag;
    //创建时间

	private Integer success ;
	private String functionCode ;
	private String functionName ;
	private String ip ;
	private String message ;
	private String userName ;
	private Long userId ;
	protected Date createTime;
	
	public CoreAudit() {
	}
	
	
	
	public Integer getSuccess(){
		return  success;
	}
	public void setSuccess(Integer success ){
		this.success = success;
	}

	
	public String getFunctionCode(){
		return  functionCode;
	}
	public void setFunctionCode(String functionCode ){
		this.functionCode = functionCode;
	}
	
	public String getFunctionName(){
		return  functionName;
	}
	public void setFunctionName(String functionName ){
		this.functionName = functionName;
	}
	
	public String getIp(){
		return  ip;
	}
	public void setIp(String ip ){
		this.ip = ip;
	}
	
	public String getMessage(){
		return  message;
	}
	public void setMessage(String message ){
		this.message = message;
	}
	
	public String getUserName(){
		return  userName;
	}
	public void setUserName(String userName ){
		this.userName = userName;
	}
	
	public Long getUserId(){
		return  userId;
	}
	public void setUserId(Long userId ){
		this.userId = userId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	

}