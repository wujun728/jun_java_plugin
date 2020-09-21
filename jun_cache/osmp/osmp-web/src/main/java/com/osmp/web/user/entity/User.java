package com.osmp.web.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月17日
 */

@Table(name="tb_user")
public class User {

	@Id
	@Column
	private String id;
	
	@Column
	private String name;
	
	@Column
	private String password;

	@Column(name="real_name")
	private String realName;

	@Column(name="nick_name")
	private String nickName;
	
	@Column
	private int status;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	
	@Column(name="last_login_ip")
	private String lastLoginIp;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
}
