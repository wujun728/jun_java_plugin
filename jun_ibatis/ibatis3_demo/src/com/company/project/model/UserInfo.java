/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 */

package com.company.project.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;


/**
 *	该文件通过代码生成器自动生成,只需编写模板,可以生成任何代码
 *  具体请查看: http://code.google.com/p/rapid-framework/
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */
public class UserInfo {
	
	//alias
	public static final String TABLE_ALIAS = "UserInfo";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_USERNAME = "username";
	public static final String ALIAS_PASSWORD = "password";
	public static final String ALIAS_BIRTH_DATE = "birthDate";
	public static final String ALIAS_SEX = "sex";
	public static final String ALIAS_AGE = "age";
	
	//columns START
	private java.lang.Long userId;
	private java.lang.String username;
	private java.lang.String password;
	private java.sql.Date birthDate;
	private java.lang.Byte sex;
	private java.lang.Integer age;
	//columns END

	public UserInfo(){
	}

	public UserInfo(
		java.lang.Long userId
	){
		this.userId = userId;
	}

	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}

	public void setBirthDate(java.sql.Date value) {
		this.birthDate = value;
	}
	
	public java.sql.Date getBirthDate() {
		return this.birthDate;
	}
	public void setSex(java.lang.Byte value) {
		this.sex = value;
	}
	
	public java.lang.Byte getSex() {
		return this.sex;
	}
	public void setAge(java.lang.Integer value) {
		this.age = value;
	}
	
	public java.lang.Integer getAge() {
		return this.age;
	}
	
	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("UserId",getUserId())
			.append("Username",getUsername())
			.append("Password",getPassword())
			.append("BirthDate",getBirthDate())
			.append("Sex",getSex())
			.append("Age",getAge())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserId())
			.append(getUsername())
			.append(getPassword())
			.append(getBirthDate())
			.append(getSex())
			.append(getAge())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserInfo == false) return false;
		if(this == obj) return true;
		UserInfo other = (UserInfo)obj;
		return new EqualsBuilder()
			.append(getUserId(),other.getUserId())
			.append(getUsername(),other.getUsername())
			.append(getPassword(),other.getPassword())
			.append(getBirthDate(),other.getBirthDate())
			.append(getSex(),other.getSex())
			.append(getAge(),other.getAge())
			.isEquals();
	}
}

