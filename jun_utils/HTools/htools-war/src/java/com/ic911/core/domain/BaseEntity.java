package com.ic911.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.shiro.SecurityUtils;

import com.ic911.core.security.ShiroUser;
/**
 * 所有持久化实体的基本信息
 * @author caoyang
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
	
	@Id
	@GeneratedValue
    public Long id;
	
	@Version
	private Long version;
	
	@Column(updatable = false)
	private Date firstOperationTime = new Date();
	
	@Column(insertable = false)
	private Date lastOperatingTime = new Date();
	
	private String lastOperator = getCurrName();
	
	/**
	 * 改变操作日志信息，当在JPA的实体update之前，实体会被find出后修改再绑定值，所以会出现无法修改操作信息。
	 */
	public void mergeOpera(){
		//this.firstOperationTime = new Date();//重新初始化也不会修改的，所以注释掉
		this.lastOperatingTime = new Date();
		this.lastOperator = getCurrName();
	}

	public Date getFirstOperationTime() {
		return firstOperationTime;
	}

	public void setFirstOperationTime(Date firstOperationTime) {
		this.firstOperationTime = firstOperationTime;
	}
	
	public Date getLastOperatingTime() {
		return lastOperatingTime;
	}

	public void setLastOperatingTime(Date lastOperatingTime) {
		this.lastOperatingTime = lastOperatingTime;
	}

	public String getLastOperator() {
		return lastOperator;
	}
	
	private String getCurrName(){
		try{
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
			if(user!=null&&!user.loginName.isEmpty()){
				return user.loginName;
			}
		}catch(Exception e){
		}
		return "系统行为";
	}
	
	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
