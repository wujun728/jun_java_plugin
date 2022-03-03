package com.jun.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 部门
 * @author Wujun
 * @createTime   Jul 30, 2011 10:42:13 AM
 */
public class Department implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门描述
	 */
	private String description;
	/**
	 * 是否叶子部门
	 */
	private boolean isLeaf;
	
	/**
	 * 部门的员工
	 */
	private Set<User> users;
	
	/**
	 * 父部门
	 */
	private Department parent;
	
	/**
	 * 子部门
	 */
	private Set<Department> children;

	public Department(){}
	
	public Department(Long id){
		this.id = id;
	}
	
	public Department(String deptName,String description,boolean isLeaf){
		this.deptName = deptName;
		this.description = description;
		this.isLeaf = isLeaf;
	}
	
	public Department(Long id,String deptName,String description,boolean isLeaf){
		this.id = id;
		this.deptName = deptName;
		this.description = description;
		this.isLeaf = isLeaf;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
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
	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
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
		final Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
