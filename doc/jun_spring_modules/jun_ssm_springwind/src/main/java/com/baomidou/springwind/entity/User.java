package com.baomidou.springwind.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 用户表
 *
 */
@TableName(value = "user")
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId
	private Long id;

	/** 登录名称 */
	private String loginName;

	/** 密码 */
	private String password;

	/** 邮箱 */
	private String email;

	/** 0、普通用户 1、管理员 */
	private Integer type;

	/** 0、禁用 1、正常 */
	private Integer status;

	/** 创建时间 */
	private Date crTime;

	/** 最后登录时间 */
	private Date lastTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCrTime() {
		return this.crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

}
