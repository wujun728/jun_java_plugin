/*   
 * Project: OSMP
 * FileName: RolePrivilege.java
 * version: V1.0
 */
package com.osmp.web.role.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月20日
 */
@Table(name="tb_role_privilege")
public class RolePrivilege {
	
	@Id
	@Column
	private String id;
	
	@Column(name="role_id")
	private String roleId;
	
	@Column(name="privilege_id")
	private int privilegeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

}
