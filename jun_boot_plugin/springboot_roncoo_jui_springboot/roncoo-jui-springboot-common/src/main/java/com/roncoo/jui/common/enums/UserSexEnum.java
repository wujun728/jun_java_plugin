package com.roncoo.jui.common.enums;

import lombok.Getter;

/**
 * 用户性别
 */
@Getter
public enum UserSexEnum {

	MEN("1", "男"), WOMEN("2", "女");

	private String code;

	private String desc;

	private UserSexEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
