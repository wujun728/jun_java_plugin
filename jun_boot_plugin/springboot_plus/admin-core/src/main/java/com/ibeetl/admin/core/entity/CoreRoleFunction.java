package com.ibeetl.admin.core.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.util.ValidateConfig;

/*
* 
* gen by beetlsql 2016-11-22
*/
public class CoreRoleFunction extends BaseEntity {
	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
	protected Long id;
	// 删除标识
	@JsonIgnore
	protected Integer delFlag = 0;
	// 创建时间

	protected Date createTime;

	private String dataAccessPolicy;
	// 数据访问类型，1 只看自己的，2 看部门的，3 看公司的 4 自定义，参考policy字段
	private Integer dataAccessType;
	private Long functionId;
	private Long roleId;

	public CoreRoleFunction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataAccessPolicy() {
		return dataAccessPolicy;
	}

	public void setDataAccessPolicy(String dataAccessPolicy) {
		this.dataAccessPolicy = dataAccessPolicy;
	}


	public Integer getDataAccessType() {
		return dataAccessType;
	}

	public void setDataAccessType(Integer dataAccessType) {
		this.dataAccessType = dataAccessType;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
