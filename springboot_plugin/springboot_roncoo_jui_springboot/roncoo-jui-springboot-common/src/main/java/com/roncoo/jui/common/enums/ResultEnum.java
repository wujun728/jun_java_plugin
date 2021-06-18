package com.roncoo.jui.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

	SUCCESS(200, "成功"), ERROR(99, "失败");

	private Integer code;

	private String desc;

	private ResultEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
