package org.springrain.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see org.springrain.system.entity.UserRole
 */
@Table(name="t_user_role")
public class UserRole  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "UserRole";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_USERID = "用户编号";
	public static final String ALIAS_ROLEID = "角色编号";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 用户编号
	 */
	private java.lang.String userId;
	/**
	 * 角色编号
	 */
	private java.lang.String roleId;
	//columns END 数据库字段结束
	
	//concstructor

	public UserRole(){
	}

	public UserRole(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserRole_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserRole_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setRoleId(java.lang.String value) {
		this.roleId = value;
	}
	
     @WhereSQL(sql="roleId=:UserRole_roleId")
	public java.lang.String getRoleId() {
		return this.roleId;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("编号[").append(getId()).append("],")
			.append("用户编号[").append(getUserId()).append("],")
			.append("角色编号[").append(getRoleId()).append("],")
			.toString();
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof UserRole == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		UserRole other = (UserRole)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
