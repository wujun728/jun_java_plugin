package com.ibeetl.admin.core.util.enums;

import org.beetl.sql.core.annotatoin.EnumMapping;

/**
 * 描述: 工作流角色
 *
 * @author Wujun
 */
@EnumMapping("value")
public enum RoleTypeEnum {
	
	/**
	 * 操作角色
	 */
	ACCESS("R0"),
	/**
	 * 工作流角色
	 */
	WORKFLOW("R1");
	

	private String value;

	RoleTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static RoleTypeEnum getEnum(String value) {
		for (RoleTypeEnum stateEnum : RoleTypeEnum.values()) {
			if (stateEnum.value == value) {
				return stateEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
