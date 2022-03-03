package com.mis.httpModel;

import java.util.Date;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.map.annotate.JsonSerialize;


//import com.jun.plugin.utils.biz.JsonDateSerializer;

/**
 * 用户模型
 * 
 * @author Wujun
 * 
 */
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class User implements java.io.Serializable {

	private String id;
	private String name;
	private String password;
	private Date createdatetime;
	private Date modifydatetime;

	private Date createdatetimeStart;
	private Date modifydatetimeStart;
	private Date createdatetimeEnd;
	private Date modifydatetimeEnd;

	private String roleId;
	private String roleText;

	private String resourcesId;
	private String resourcesText;

	private String oldPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	public String getResourcesText() {
		return resourcesText;
	}

	public void setResourcesText(String resourcesText) {
		this.resourcesText = resourcesText;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleText() {
		return roleText;
	}

	public void setRoleText(String roleText) {
		this.roleText = roleText;
	}

	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getModifydatetimeStart() {
		return modifydatetimeStart;
	}

	public void setModifydatetimeStart(Date modifydatetimeStart) {
		this.modifydatetimeStart = modifydatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Date getModifydatetimeEnd() {
		return modifydatetimeEnd;
	}

	public void setModifydatetimeEnd(Date modifydatetimeEnd) {
		this.modifydatetimeEnd = modifydatetimeEnd;
	}

	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
