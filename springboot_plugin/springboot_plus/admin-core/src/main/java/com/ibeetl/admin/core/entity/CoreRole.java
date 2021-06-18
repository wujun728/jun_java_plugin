package com.ibeetl.admin.core.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.annotation.Dict;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.util.enums.CoreDictType;

/**
 * 角色
 */
public class CoreRole extends BaseEntity {

	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
	protected Long id;


	protected Date createTime;

	// 角色code
	@NotBlank(message = "角色编码不能为空", groups = ValidateConfig.ADD.class)
	private String code;

	// 角色名称
	@NotBlank(message = "角色名称不能为空", groups = { ValidateConfig.ADD.class, ValidateConfig.UPDATE.class })
	private String name;

	// 角色类型,系统的角色，工作流角色
	@NotBlank(message = "角色类型不能为空")
	@Dict(type = CoreDictType.ROLE_TYPE)
	private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}