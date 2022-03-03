package com.jun.mis.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Wujun
 * @createTime   Jul 28, 2011 9:11:06 PM
 */
public class User implements Serializable
{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 性别 0-女  1-男
	 */
	private String sex;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 用户的角色
	 */
	private Set<Role> roles;
	
	/**
	 * 所属部门
	 */
	private Department department;
	/**
	 * 收件箱消息列表
	 */
	private Set<Message> inBoxMessages;
	
	public User(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Message> getInBoxMessages() {
		return inBoxMessages;
	}

	public void setInBoxMessages(Set<Message> inBoxMessages) {
		this.inBoxMessages = inBoxMessages;
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
		final User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
