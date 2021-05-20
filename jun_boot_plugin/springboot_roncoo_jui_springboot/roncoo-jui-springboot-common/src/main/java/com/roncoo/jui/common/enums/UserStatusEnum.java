package com.roncoo.jui.common.enums;

import lombok.Getter;

/**
 * 用户状态
 */
@Getter
public enum UserStatusEnum {

	NORMAL("1", "正常"), CANCEL("2", "注销");

	private String code;

	private String desc;

	private UserStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
