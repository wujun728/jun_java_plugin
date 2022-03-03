package com.jun.mis.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 角色
 * @author Wujun
 * @createTime   Jul 28, 2011 9:10:26 PM
 */
public class Role implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 角色的员工集合
	 */
	private Set<User> users;
	
	/**
	 * 是否选中(不做持久化)
	 */
	private boolean chcked;
	
	/**
	 * 角色拥有的资源集合
	 */
	private Set<Resource> resources;
	
	public Role(){}
	
	public Role(String name){
		this.name = name;
	}
	
	public Role(String name,String description){
		this.name = name;
		this.description = description;
	}
	
	public Role(Long id,String name,String description){
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public boolean isChcked() {
		return chcked;
	}

	public void setChcked(boolean chcked) {
		this.chcked = chcked;
	}
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
