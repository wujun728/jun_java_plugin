package org.springrain.frame.shiro;


import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.system.entity.User;
/**
 * Shiro的内部User对象
 * @author caomei
 *
 */
public class ShiroUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 用户类型
	 */
	private java.lang.Integer userType;
	/**
	 * 账号
	 */
	private java.lang.String account;

	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 0.女1.男
	 */
	private java.lang.String sex;

	public ShiroUser() {

	}

	public ShiroUser(User user) {
		this.id = user.getId();
		this.account = user.getAccount();
		this.name = user.getName();
		this.email = user.getEmail();
		this.userType = user.getUserType();
		this.sex=user.getSex();
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null) {
                return false;
            }
		} else if (!account.equals(other.account)) {
            return false;
        }
		return true;
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getAccount())
			.toHashCode();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	
	public void setName(java.lang.String name) {
		this.name = name;
	}

	
	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public java.lang.Integer getUserType() {
		return userType;
	}

	public void setUserType(java.lang.Integer userType) {
		this.userType = userType;
	}
}