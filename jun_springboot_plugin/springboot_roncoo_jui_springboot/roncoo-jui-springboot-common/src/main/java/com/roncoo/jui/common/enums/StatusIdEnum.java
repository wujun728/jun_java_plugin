package com.roncoo.jui.common.enums;

import lombok.Getter;

@Getter
public enum StatusIdEnum {

	YES("1", "可用"), NO("0", "禁用");

	private String code;

	private String desc;

	private StatusIdEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
