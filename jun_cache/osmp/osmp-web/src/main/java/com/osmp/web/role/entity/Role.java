/*   
 * Project: OSMP
 * FileName: Role.java
 * version: V1.0
 */
package com.osmp.web.role.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Description: 角色
 *
 * @author: chelongquan
 * @date: 2015年4月18日
 */
@Table(name="tb_role")
public class Role {
	
	@Id
	@Column
	private String id;
	
	@Column(name="role_name")
	private String name;
	
	@Column
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
