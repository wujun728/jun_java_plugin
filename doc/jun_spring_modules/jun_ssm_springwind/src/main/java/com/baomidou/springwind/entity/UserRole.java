package com.baomidou.springwind.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 用户角色表
 *
 */
@TableName(value = "user_role")
public class UserRole implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	private Long id;

	/** 用户ID */
	private Long uid;

	/** 角色ID */
	private Long rid;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRid() {
		return this.rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

}
