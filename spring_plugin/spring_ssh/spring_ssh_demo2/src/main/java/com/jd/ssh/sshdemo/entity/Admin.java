package com.jd.ssh.sshdemo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.ImmutableList;
import com.jd.ssh.sshdemo.search.Searchable;

/**
 * 实体类 - 管理员和登录用户
 * ============================================================================
 * 版权所有 2013 qshihua。
 * 
 * @author Wujun
 * 
 * @version 0.1 2013-01-06
 * ============================================================================
 */

@Entity
@Table(name="ssh_admin")
public class Admin extends BaseEntity implements Searchable{

	private static final long serialVersionUID = -6665132594799061679L;

	public Admin() {
		super();
	}

	/**
	 * 简单admin对象
	 * @param username 账号
	 * @param password 密码
	 * @param isAccountEnabled 是否启用
	 */
	public Admin(String username, String password, Boolean isAccountEnabled) {
		super();
		this.username = username;
		this.password = password;
		this.isAccountEnabled = isAccountEnabled;
	}

	/**
	 * 返回对象属性Map集合
	 * username,password,isAccountEnabled(是/否),id
	 */
	public Map<String,Object> toMap() {
		Map<String,Object> row = new HashMap<String,Object>();
		row.put("username", username);
		row.put("password", password);
		row.put("isAccountEnabled", isAccountEnabled ? "是":"否");
		row.put("id", super.getId());
		return row;
	}
	
	private String salt;
	private String roles;
	
	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}
	
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 *  账号是否启用
	 */
	private Boolean isAccountEnabled;
	/**
	 *  账号是否锁定
	 */
	private Boolean isAccountLocked;
	/**
	 *  账号是否过期
	 */
	private Boolean isAccountExpired;
	/**
	 *  凭证是否过期
	 */
	private Boolean isCredentialsExpired;
	/**
	 *  连续登录失败的次数
	 */
	private Integer loginFailureCount;
	/**
	 * 	账号锁定日期
	 */
	private Date lockedDate;
	/**
	 *  最后登录日期
	 */
	private Date loginDate;
	/**
	 *  最后登录IP
	 */
	private String loginIp;
	
	/**
	 * 用户主题
	 */
	private String theme;
	
	/**
	 * 登陆系统显示名称
	 */
	private String name;
	
	@Column(updatable = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(Boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(nullable = false)
	public Boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	
	
	@Transient
	public boolean isEnabled() {
		return this.isAccountEnabled;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}
	


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public int compareTo(Searchable o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> storeFields() {
		// TODO Auto-generated method stub
		List<String> storeFields = new ArrayList<String>();
		storeFields.add("id");
		storeFields.add("username");
		return storeFields;
	}

	@Override
	public List<String> indexFields() {
		// TODO Auto-generated method stub
		List<String> storeFields = new ArrayList<String>();
		storeFields.add("name");
		return storeFields;
	}

	@Override
	public float boost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> extendStoreDatas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> extendIndexDatas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Searchable> ListAfter(long id, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return super.getId();
	}


}