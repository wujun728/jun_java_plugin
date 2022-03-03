package com.erp.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SyroleSyresources entity. @author Wujun
 */
@Entity
@Table(name = "syrole_syresources")
public class SyroleSyresources implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1569534568121828439L;
	private String id;
	private String roleId;
	private String resourcesId;

	// Constructors

	/** default constructor */
	public SyroleSyresources() {
	}

	/** full constructor */
	public SyroleSyresources(String id, String roleId, String resourcesId) {
		this.id = id;
		this.roleId = roleId;
		this.resourcesId = resourcesId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 36)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RESOURCES_ID", nullable = false, length = 36)
	public String getResourcesId() {
		return this.resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

}